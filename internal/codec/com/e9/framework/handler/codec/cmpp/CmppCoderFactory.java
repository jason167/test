package com.e9.framework.handler.codec.cmpp;

import org.jboss.netty.channel.ChannelHandler;

import com.e9.framework.channel.i.CoderFactory;

/**
 * @project E9Framework
 * @date 2013-1-25
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-25] create by Jason
 */
public class CmppCoderFactory implements CoderFactory {

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.netty.CoderFactory#newEncoder()
	 */
	@Override
	public ChannelHandler newEncoder() {
		// TODO Auto-generated method stub
		return new CmppEncoder();
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.netty.CoderFactory#newDecoder()
	 */
	@Override
	public ChannelHandler newDecoder() {
		// TODO Auto-generated method stub
		return new CmppDecoder();
	}

}
