package com.e9.launcher.string;

import test.com.e9.launcher.string.adapter.StringServiceAdapter;

import com.e9.framework.channel.AbstractChannelHandler;
import com.e9.framework.channel.client.DefaultClient;
import com.e9.framework.channel.i.CoderFactory;
import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.MessageCoreController;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.util.ChannelConfig;
import com.e9.framework.handler.codec.string.StringCoderFactory;

/**
 * @project E9Framework
 * @date 2013-4-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-20] create by Jason
 */
public class StringClient extends DefaultClient {

	/**
	 * @param channelConfig
	 * @param coderFactory
	 * @param messageCoreController
	 */
	public StringClient(ChannelConfig channelConfig, CoderFactory coderFactory) {
		super(
				channelConfig, 
				coderFactory, 
				new MessageCoreController(new StringServiceAdapter()), 
				new AbstractChannelHandler("StringClient") {
					
					@Override
					protected void doMessageReceived(Message message, Session session) throws Exception {
						// TODO Auto-generated method stub
						getListener().messageReceived(session, message);
					}
				}, "StringClient");
		// TODO Auto-generated constructor stub
	}
	public StringClient(ChannelConfig channelConfig) {
		this(channelConfig, new StringCoderFactory());
		// TODO Auto-generated constructor stub
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
