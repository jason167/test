package com.e9.launcher.string;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;

import test.com.e9.launcher.string.adapter.StringServiceAdapter;

import com.e9.framework.channel.AbstractChannelHandler;
import com.e9.framework.channel.i.CoderFactory;
import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.MessageCoreController;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.server.DefaultServer;
import com.e9.framework.channel.server.ServerSession;
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
public class StringServer extends DefaultServer {

	/**
	 * @param channelConfig
	 * @param coderFactory
	 * @param messageCoreController
	 */
	public StringServer(ChannelConfig channelConfig, CoderFactory coderFactory) {
		super(
				channelConfig, 
				coderFactory, 
				new MessageCoreController(new StringServiceAdapter()), 
				new AbstractChannelHandler("StringServer") {
					
					@Override
					protected void doMessageReceived(Message message, Session session) throws Exception {
						// TODO Auto-generated method stub
						getListener().messageReceived(session, message);
					}
					/* (non-Javadoc)
					 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
					 */
					@Override
					public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
							throws Exception {
						// TODO Auto-generated method stub
						getListener().connected(new ServerSession(ctx.getChannel()));
					}
				}, "StringServer");
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public StringServer(ChannelConfig channelConfig) {
		// TODO Auto-generated constructor stub
		this(channelConfig, new StringCoderFactory());
	}

}
