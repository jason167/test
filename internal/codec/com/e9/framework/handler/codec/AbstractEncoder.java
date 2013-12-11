package com.e9.framework.handler.codec;

import java.nio.ByteOrder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.e9.framework.channel.NettyIoBuffer;
import com.e9.framework.channel.i.Message;

/**
 * 基础编码器
 * @project E9Framework
 * @date 2013-4-19
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-19] create by Jason
 */
public abstract class AbstractEncoder extends OneToOneEncoder {

	protected Object toEncode(ChannelHandlerContext ctx, Channel channel,
			Object msg) throws Exception {
		// TODO Auto-generated method stub
		if (!(msg instanceof Message)) {
			return msg;
		}
		
		ChannelBuffer buffer = ChannelBuffers.dynamicBuffer(ByteOrder.BIG_ENDIAN, 384);
		((Message)msg).encode(new NettyIoBuffer(buffer));
		return buffer.copy(0, buffer.writerIndex());
	}

}
