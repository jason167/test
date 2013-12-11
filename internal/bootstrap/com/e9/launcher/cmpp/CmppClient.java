package com.e9.launcher.cmpp;

import test.com.e9.launcher.cmpp.adapter.CmppServiceAdapter;

import com.e9.framework.channel.AbstractChannelHandler;
import com.e9.framework.channel.client.DefaultClient;
import com.e9.framework.channel.i.CoderFactory;
import com.e9.framework.channel.i.IMessageDispatcherHandler;
import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.MessageCoreController;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.util.ChannelConfig;
import com.e9.framework.handler.codec.cmpp.CmppCoderFactory;
import com.e9.framework.handler.codec.cmpp.CmppDispatcherHandler;

/**
 * 
 * @date 2013-11-14
 * @author Jason
 */
public class CmppClient extends DefaultClient {

	/**
	 * @param channelConfig
	 * @param coderFactory
	 * @param messageCoreController
	 */
	public CmppClient(ChannelConfig channelConfig, CoderFactory coderFactory) {
		super(
				channelConfig, 
				coderFactory, 
				new MessageCoreController(new CmppServiceAdapter()), 
				new AbstractChannelHandler("CmppClient") {
					
					private IMessageDispatcherHandler dispatcherHandler = new CmppDispatcherHandler();
					@Override
					protected void doMessageReceived(Message message, Session session) throws Exception {
						// TODO Auto-generated method stub
						dispatcherHandler.action(this, session, message);
					}
				}, 
				"CmppClient"
			);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public CmppClient(ChannelConfig channelConfig) {
		// TODO Auto-generated constructor stub
		this(channelConfig, new CmppCoderFactory());
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.netty.client.NettyClient#login(com.e9.tcp.framework.Session)
	 */
	@Override
	public boolean login(Session session) {
		// TODO Auto-generated method stub
		
		// do something...
		return true;
	}
	
}
