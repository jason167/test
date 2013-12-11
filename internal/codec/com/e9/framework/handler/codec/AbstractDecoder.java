package com.e9.framework.handler.codec;

import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;

/**
 * @date 2013-7-19
 * @author Jason
 */
public class AbstractDecoder extends LengthFieldBasedFrameDecoder {

	/**
	 * @param maxFrameLength
	 * @param lengthFieldOffset
	 * @param lengthFieldLength
	 * @param lengthAdjustment
	 * @param initialBytesToStrip
	 */
	public AbstractDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
			int lengthAdjustment, int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 */
	public AbstractDecoder() {
		// TODO Auto-generated constructor stub
		this(Integer.MAX_VALUE, 0, 4, -4, 0);
	}

}
