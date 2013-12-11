package com.e9.framework.handler.codec.cmpp;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.e9.framework.channel.NettyIoBuffer;
import com.e9.framework.channel.i.Message;
import com.e9.framework.handler.codec.AbstractDecoder;
import com.e9.framework.handler.codec.cmpp.property.CmppLength;


/**
 * cmpp 解码器
 * @project E9Framework
 * @date 2012-12-27
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-27] create by Jason
 */
public class CmppDecoder extends AbstractDecoder {

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
		if(buffer.readableBytes() < CmppLength.INT4) {
			return null;
		}
		// Mark begin index
		buffer.markReaderIndex();
		
		// PacketLength
		int packetLength = buffer.readInt();
		if(packetLength < CmppLength.CMPP_HEADER){
			throw new RuntimeException("Invalid packet length:[" + packetLength + "]");
		}
		
		if(buffer.readableBytes() < packetLength - CmppLength.INT4) {
			buffer.resetReaderIndex();
			return null;
		}
		
		// RequestId
		int commandId = buffer.readInt();
		Message message = CmppMessageDecoderFactory.createDecoder(commandId);
		
		if(null == message){
			throw new RuntimeException("Invalid request id:[" + commandId + "]");
		}
		
		buffer.resetReaderIndex();
		
		ChannelBuffer realBuffer = buffer.readBytes(packetLength);
		message.decode(new NettyIoBuffer(realBuffer));

		return message;
	}

}
