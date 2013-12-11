package com.e9.framework.channel;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;

/**
 * @project E9Framework
 * @date 2012-12-27
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-27] create by Jason
 */
public class NettyIoBuffer implements IoBuffer {
	
	private final ChannelBuffer buffer;

	/**
	 * 
	 */
	public NettyIoBuffer(ChannelBuffer buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.IBuffer#getBuffer()
	 */
	@Override
	public Object getBuffer() {
		// TODO Auto-generated method stub
		return buffer;
	}

}
