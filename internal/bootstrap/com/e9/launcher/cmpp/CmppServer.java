package com.e9.launcher.cmpp;


import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;

import test.com.e9.launcher.cmpp.adapter.CmppServiceAdapter;

import com.e9.framework.channel.AbstractChannelHandler;
import com.e9.framework.channel.i.CoderFactory;
import com.e9.framework.channel.i.IMessageDispatcherHandler;
import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.MessageCoreController;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.server.DefaultServer;
import com.e9.framework.channel.server.ServerSession;
import com.e9.framework.channel.util.ChannelConfig;
import com.e9.framework.handler.codec.cmpp.CmppCoderFactory;
import com.e9.framework.handler.codec.cmpp.CmppDispatcherHandler;

/**
 * 
 * @date 2013-11-14
 * @author Jason
 */
public class CmppServer extends DefaultServer {

	/**
	 * 
	 * @param channelConfig
	 * @param coderFactory
	 * @param messageCoreController
	 * @param handler
	 */
	public CmppServer(ChannelConfig channelConfig, CoderFactory coderFactory) {
		super(
				channelConfig, 
				coderFactory, 
				new MessageCoreController(new CmppServiceAdapter()), 
				new AbstractChannelHandler("CmppServer") {
					
					private IMessageDispatcherHandler dispatcherHandler = new CmppDispatcherHandler();
					@Override
					protected void doMessageReceived(Message message, Session session) throws Exception {
						// TODO Auto-generated method stub
						dispatcherHandler.action(this, session, message);
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
				},
				"CmppServer"
			);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param channelConfig
	 * @param messageCoreController
	 * @param handler
	 */
	public CmppServer(ChannelConfig channelConfig) {
		this(channelConfig, new CmppCoderFactory());
		// TODO Auto-generated constructor stub
	}
	
	
	

}
