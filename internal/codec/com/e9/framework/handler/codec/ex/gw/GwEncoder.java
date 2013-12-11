package com.e9.framework.handler.codec.ex.gw;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

import com.e9.framework.handler.codec.AbstractEncoder;

/**
 * @date 2013-9-10
 * @author Jason
 */
public class GwEncoder extends AbstractEncoder {

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
