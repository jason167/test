package com.e9.framework.handler.codec.sgip;

import org.jboss.netty.channel.ChannelHandler;

import com.e9.framework.channel.i.CoderFactory;

/**
 * SGIP编解码器工厂类
 * @date 2013-8-23
 * @author Jason
 */
public class SgipCoderFactory implements CoderFactory {

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.CoderFactory#newEncoder()
	 */
	@Override
	public ChannelHandler newEncoder() {
		// TODO Auto-generated method stub
		return new SgipEncoder();
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.CoderFactory#newDecoder()
	 */
	@Override
	public ChannelHandler newDecoder() {
		// TODO Auto-generated method stub
		return new SgipDecoder();
	}

}
