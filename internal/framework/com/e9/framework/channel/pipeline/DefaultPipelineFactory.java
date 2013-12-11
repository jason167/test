package com.e9.framework.channel.pipeline;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.MemoryAwareThreadPoolExecutor;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;

import com.e9.framework.channel.i.CoderFactory;
import com.e9.framework.channel.i.PipelineFactory;
import com.e9.framework.channel.util.ChannelConfig;
import com.e9.framework.channel.util.NamingThreadFactory;

/**
 * 通信管道
 * @project E9Framework
 * @date 2012-12-19
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-19] create by Jason
 */
public class DefaultPipelineFactory implements PipelineFactory {
	
	private IdleStateHandler idleStateHandler;
	private final CoderFactory coderFactory;
	private final ChannelHandler handler;
	private final ExecutionHandler executionHandler;
	
	public DefaultPipelineFactory(ChannelConfig channelConfig, CoderFactory coderFactory, ChannelHandler handler, boolean keepAlive){
		this.coderFactory = coderFactory;
		this.handler = handler;
		if (keepAlive) {
			HashedWheelTimer hashedWheelTimer = new HashedWheelTimer();
			idleStateHandler = new IdleStateHandler(
					hashedWheelTimer, 
					channelConfig.getKeepalive_idle() * channelConfig.getKeepalive_retry(), 
					0,
					channelConfig.getKeepalive_idle()
					);
		}
		Executor executor = new MemoryAwareThreadPoolExecutor(
				Runtime.getRuntime().availableProcessors() * 2, 
				65536, 1048576, 
				30, TimeUnit.SECONDS, 
				new NamingThreadFactory("DefaultPipelineExecutor")
				);
		executionHandler = new ExecutionHandler(executor);
	}
	
	public DefaultPipelineFactory(ChannelConfig channelConfig, CoderFactory coderFactory, ChannelHandler handler){
		this(channelConfig, coderFactory, handler, true);
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		// TODO Auto-generated method stub
		
		if (coderFactory == null) {
			throw new IllegalArgumentException("CoderFactory can't null");
		}
		ChannelPipeline pipeline = Channels.pipeline();
		
		pipeline.addLast("E9Decoder", coderFactory.newDecoder());
		if (idleStateHandler != null) {
			pipeline.addLast("E9IdleStateHandler", idleStateHandler);
		}
		pipeline.addLast("E9ExecutionHandler", executionHandler);
		pipeline.addLast("E9NettyHandler", handler);
		pipeline.addLast("E9Encoder", coderFactory.newEncoder());
		
		return pipeline;
	}
	
	public void destory(){
		if (idleStateHandler != null) {
			idleStateHandler.releaseExternalResources();
			idleStateHandler = null;
		}
	}

}
