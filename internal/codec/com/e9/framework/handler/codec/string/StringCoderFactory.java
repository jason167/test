package com.e9.framework.handler.codec.string;

import org.jboss.netty.channel.ChannelHandler;

import com.e9.framework.channel.i.CoderFactory;

/**
 * @project E9Framework
 * @date 2013-4-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-20] create by Jason
 */
public class StringCoderFactory implements CoderFactory {

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.CoderFactory#newEncoder()
	 */
	@Override
	public ChannelHandler newEncoder() {
		// TODO Auto-generated method stub
		return new StringEncoder();
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.CoderFactory#newDecoder()
	 */
	@Override
	public ChannelHandler newDecoder() {
		// TODO Auto-generated method stub
		return new StringDecoder();
	}

}
