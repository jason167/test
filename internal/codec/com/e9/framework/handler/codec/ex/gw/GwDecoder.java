package com.e9.framework.handler.codec.ex.gw;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.e9.framework.channel.NettyIoBuffer;
import com.e9.framework.channel.i.Message;
import com.e9.framework.handler.codec.AbstractDecoder;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;

/**
 * @date 2013-9-10
 * @author Jason
 */
public class GwDecoder extends AbstractDecoder {

	/* (non-Javadoc)
	 * @see org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer channelBuffer)
			throws Exception {
		// TODO Auto-generated method stub
		ChannelBuffer buffer = (ChannelBuffer) super.decode(ctx, channel, channelBuffer);
		if (buffer == null) {
			return null;
		}
		if(buffer.readableBytes() < GwLength.INT4) {
			return null;
		}
		// Mark begin index
		buffer.markReaderIndex();
		
		// PacketLength
		int packetLength = buffer.readInt();
		if (packetLength < GwLength.PACKET_LENGTH) {
			throw new RuntimeException("Invalid packet length:[" + packetLength + "]");
		}
		
		if(buffer.readableBytes() < packetLength - GwLength.INT4) {
			buffer.resetReaderIndex();
			return null;
		}
		
		// RequestId
		int commandId = buffer.readInt();
		Message message = GwMessageDecoderFactory.createDecoder(commandId);
		if(null == message){
			throw new RuntimeException("Invalid request id:[" + commandId + "]");
		}
		buffer.resetReaderIndex();
		
		ChannelBuffer realBuffer = buffer.readBytes(packetLength);
		message.decode(new NettyIoBuffer(realBuffer));

		return message;
	}
}
