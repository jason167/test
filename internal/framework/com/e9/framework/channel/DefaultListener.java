package com.e9.framework.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9.framework.channel.i.IMessageCoreController;
import com.e9.framework.channel.i.Listener;
import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.MessageType;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.util.ChannelConfig;
import com.e9.framework.channel.util.SessionManagement;

/**
 * @project E9Framework
 * @date 2012-12-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-20] create by Jason
 */
public class DefaultListener implements Listener {
	
	protected final IMessageCoreController messageController;
	protected final ChannelConfig channelConfig;
	private Logger log = LoggerFactory.getLogger(DefaultListener.class);
	
	/**
	 * 
	 */
	public DefaultListener(IMessageCoreController messageController, ChannelConfig channelConfig) {
		// TODO Auto-generated constructor stub
		this.messageController = messageController;
		this.channelConfig = channelConfig;
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Listener#connected(com.e9.tcp.framework.Session)
	 */
	@Override
	public void connected(Session session) {
		// TODO Auto-generated method stub
		log.debug("Connected a User:{}", session);
		SessionManagement.add(session.getChannel(), session);
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Listener#messageReceived(com.e9.tcp.framework.Session, com.e9.tcp.framework.Message)
	 */
	@Override
	public void messageReceived(Session session, Message message) {
		// TODO Auto-generated method stub
		if (message.isSyncMessage()) {
			session.putAttachment(session.getChannel(), message);
			session.unlock();
		}else{
			
			try {
				Message recvMessage = messageController.process(session, message);
				if (recvMessage != null) {
					session.send(recvMessage);
					//log.debug("Send Response Message, Session:{}, Message:{}", session, recvMessage);
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.error("",e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Listener#disconnected(com.e9.tcp.framework.Session)
	 */
	@Override
	public void disconnected(Session session) {
		// TODO Auto-generated method stub
		
		if (session == null) {
			return;
		}
		log.debug("Disconnected a User:{}", session);
		session.setAuthenticated(false);
		session.disconnect();
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Listener#keepAliveTest(com.e9.tcp.framework.Session)
	 */
	@Override
	public void keepAliveTest(Session session) {
		// TODO Auto-generated method stub
		
		if( session.isKeepAliveTesting() /*|| !session.isAuthenticated()*/ )
		{
			return;
		}
		
		session.setKeepAliveTesting(true);
		Message aliveTest = this.messageController.process(session, null, MessageType.aliveTest);
		
		if (aliveTest != null) {
			int count = 0;
			int retryTimes = this.channelConfig.getKeepalive_retry();
			do{
				log.debug("KeepAliveTest send a Message to User Client, Session:{}, Message:{}", session, aliveTest);
				Message message = session.submit(aliveTest, this.channelConfig.getResponse_timeout());
				if (message != null) {
					log.debug("KeepAliveTest Receive a Message from User Client, Session:{}, Message:{}", session, message);
					break;
				}
				count++;
			}while(count < retryTimes);
			
		}else{
			throw new IllegalArgumentException("AliveTestProcessor process method is error . please check it. processor class:"+messageController.getClass().getCanonicalName());
		}
		session.setKeepAliveTesting(false);
	}
	

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Listener#exceptionCaught(com.e9.tcp.framework.Session, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(Session session, Throwable cause) {
		// TODO Auto-generated method stub
		if (session == null) {
			return;
		}
		log.error("ExceptionCaught Session:{}, Exception:{}", session, cause);
//		this.close(session);
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Listener#close(com.e9.tcp.framework.Session)
	 */
	@Override
	public void close(Session session) {
		// TODO Auto-generated method stub
		if (session == null) {
			return;
		}
		log.debug("Close a User:{}", session);
		try{
			session.setAuthenticated(false);
			this.messageController.process(session, null, MessageType.Non_normal_exit);
			
			if (session.getChannel() != null) {
				SessionManagement.remove(session.getChannel());
			}
		}
		finally{
			session.close();
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Listener#destory()
	 */
	@Override
	public void destory() {
		// TODO Auto-generated method stub
	}

	


}
