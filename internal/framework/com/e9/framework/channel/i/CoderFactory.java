package com.e9.framework.channel.i;

import org.jboss.netty.channel.ChannelHandler;

/**
 * 编解码器工厂
 * @project E9Framework
 * @date 2012-12-19
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-19] create by Jason
 */
public interface CoderFactory {
	
	public ChannelHandler newEncoder();
	
	public ChannelHandler newDecoder();

}
