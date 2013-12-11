package com.e9.framework.handler.codec.ex.gw;

import org.jboss.netty.channel.ChannelHandler;

import com.e9.framework.channel.i.CoderFactory;

/**
 * @date 2013-9-10
 * @author Jason
 */
public class GwCoderFactory implements CoderFactory {

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.CoderFactory#newEncoder()
	 */
	@Override
	public ChannelHandler newEncoder() {
		// TODO Auto-generated method stub
		return new GwEncoder();
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.CoderFactory#newDecoder()
	 */
	@Override
	public ChannelHandler newDecoder() {
		// TODO Auto-generated method stub
		return new GwDecoder();
	}

}
