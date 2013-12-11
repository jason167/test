package com.e9.framework.handler.codec.string;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;

import com.e9.framework.channel.NettyIoBuffer;
import com.e9.framework.channel.i.Message;
import com.e9.framework.handler.codec.string.property.StringLength;


/**
 * 
 * @project E9Framework
 * @date 2013-4-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-20] create by Jason
 */
public class StringDecoder extends LengthFieldBasedFrameDecoder {

	/**
	 * 
	 */
	public StringDecoder() {
		// TODO Auto-generated constructor stub
		this(Integer.MAX_VALUE, 0, 4, -4, 0);
	}
	/**
	 * @param maxFrameLength
	 * @param lengthFieldOffset
	 * @param lengthFieldLength
	 * @param lengthAdjustment
	 * @param initialBytesToStrip
	 */
	public StringDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment,
				initialBytesToStrip);
		// TODO Auto-generated constructor stub
		
	}
	
	/* (non-Javadoc)
	 * @see org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer channelBuffer) throws Exception {
		// TODO Auto-generated method stub
		
		ChannelBuffer buffer = (ChannelBuffer) super.decode(ctx, channel, channelBuffer);
		if (buffer == null) {
			return null;
		}
		if(buffer.readableBytes() < StringLength.INT4) {
			return null;
		}
		// Mark begin index
		buffer.markReaderIndex();
		
		// PacketLength
		int packetLength = buffer.readInt();
		if(packetLength < StringLength.STRING_HEADER){
			throw new RuntimeException("Invalid packet length:[" + packetLength + "]");
		}
		
		if(buffer.readableBytes() < packetLength - StringLength.INT4) {
			buffer.resetReaderIndex();
			return null;
		}
		
		// RequestId
		int commandId = buffer.readInt();
		Message message = StringMessageDecoderFactory.createDecoder(commandId);
		
		if(null == message){
			throw new RuntimeException("Invalid request id:[" + commandId + "]");
		}
		
		buffer.resetReaderIndex();
		
		ChannelBuffer realBuffer = buffer.readBytes(packetLength);
		message.decode(new NettyIoBuffer(realBuffer));

		return message;
	}

}
