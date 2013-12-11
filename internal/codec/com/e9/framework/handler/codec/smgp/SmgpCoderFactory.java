package com.e9.framework.handler.codec.smgp;

import org.jboss.netty.channel.ChannelHandler;

import com.e9.framework.channel.i.CoderFactory;

/**
 * @project E9Framework
 * @date 2013-1-18
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-18] create by Jason
 */
public class SmgpCoderFactory implements CoderFactory {

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.netty.CoderFactory#newEncoder()
	 */
	@Override
	public ChannelHandler newEncoder() {
		// TODO Auto-generated method stub
		return new SmgpEncoder();
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.netty.CoderFactory#newDecoder()
	 */
	@Override
	public ChannelHandler newDecoder() {
		// TODO Auto-generated method stub
		return new SmgpDecoder();
	}

}
