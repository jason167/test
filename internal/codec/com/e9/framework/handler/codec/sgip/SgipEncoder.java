package com.e9.framework.handler.codec.sgip;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.e9.framework.handler.codec.AbstractEncoder;

/**
 * SGIP编码器
 * @date 2013-8-23
 * @author Jason
 */
public class SgipEncoder extends AbstractEncoder {

	/* (non-Javadoc)
	 * @see org.jboss.netty.handler.codec.oneone.OneToOneEncoder#encode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, java.lang.Object)
	 */
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		return super.toEncode(ctx, channel, msg);
	}

}
