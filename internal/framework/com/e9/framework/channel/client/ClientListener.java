package com.e9.framework.channel.client;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9.framework.channel.DefaultListener;
import com.e9.framework.channel.i.Client;
import com.e9.framework.channel.i.IMessageCoreController;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.util.ChannelConfig;
import com.e9.framework.channel.util.SessionManagement;

/**
 * @project E9Framework
 * @date 2012-12-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history [2012-12-20] create by Jason
 */
public class ClientListener extends DefaultListener {

	private Client client;
	private volatile boolean isNeedReConnect;
	private Logger log = LoggerFactory.getLogger(ClientListener.class);
	private ChannelConfig channelConfig;
	private final AtomicBoolean reConnecting = new AtomicBoolean();

	/**
	 * @param messageProcessor
	 */
	public ClientListener(IMessageCoreController messageCoreController, ChannelConfig channelConfig, Client client, boolean isNeedReConnect) {
		super(messageCoreController, channelConfig);
		// TODO Auto-generated constructor stub

		this.client = client;
		this.isNeedReConnect = isNeedReConnect;
		this.channelConfig = channelConfig;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.netty.DefaultListener#close(com.e9.tcp.framework.Session)
	 */
	@Override
	public void close(Session session) {
		// TODO Auto-generated method stub
		this.disconnected(session);
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.e9.tcp.framework.netty.DefaultListener#disconnected(com.e9.tcp.framework
	 * .Session)
	 */
	@Override
	public void disconnected(Session session) {
		// TODO Auto-generated method stub
		if (!reConnecting.compareAndSet(false, true)) {
			return;
		}
		
		if (session != null) {
			try {
				session.setAuthenticated(false);
			} finally {

				if (session.getChannel() != null) {

					SessionManagement.remove(session.getChannel());
				}

			}
		}

		try {
			while (isNeedReConnect) {

				if (client == null) {
					break;
				}
				
				log.info("Connecting to server, Host:{}, Port:{} ...", channelConfig.getHost(), channelConfig.getPort());

				synchronized(ClientListener.this){
					Session t_session = client.getSession();
					if (t_session != null) {
						t_session.close();
					}
					t_session = client.connect();
					if (t_session == null || !t_session.isConnected()) {
						if (t_session != null) {
							SessionManagement.remove(t_session.getChannel());
						}
						try {
							log.info("Connect server is failed, After 5 seconds reconnect...");
							TimeUnit.SECONDS.sleep(5);
							continue;
						} catch (InterruptedException e) {
						}
					}
					
					log.info("Connected");
					log.info("Start login to server, Host:{}, Port:{} ...", channelConfig.getHost(), channelConfig.getPort());
					boolean state = client.login(t_session);
					if (state) {
						log.info("Login successful! session:{}", t_session);
						break;
					}
					if (t_session != null) {
						SessionManagement.remove(t_session.getChannel());
					}
					try {
						log.info("Login failed, After 5 retry ...");
						TimeUnit.SECONDS.sleep(5);
					} catch (InterruptedException e) {
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("", e);
		}
		finally{
			reConnecting.set(false);
		}

	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.netty.DefaultListener#destory()
	 */
	@Override
	public void destory() {
		// TODO Auto-generated method stub
		super.destory();
		this.isNeedReConnect = false;
		this.client = null;
	}
	
	

}
