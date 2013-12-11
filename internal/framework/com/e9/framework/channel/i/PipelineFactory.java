package com.e9.framework.channel.i;

import org.jboss.netty.channel.ChannelPipelineFactory;

/**
 * @date 2013-5-22
 * @author Jason
 */
public interface PipelineFactory extends ChannelPipelineFactory {

	void destory();
}
