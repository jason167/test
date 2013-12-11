package com.e9.framework.channel.server;


import com.e9.framework.channel.AbstractChannelHandler;
import com.e9.framework.channel.i.CoderFactory;
import com.e9.framework.channel.i.IMessageCoreController;
import com.e9.framework.channel.i.PipelineFactory;
import com.e9.framework.channel.pipeline.DefaultPipelineFactory;
import com.e9.framework.channel.util.ChannelConfig;

/**
 * @date 2013-5-22
 * @author Jason
 */
public class DefaultServer extends NettyServer {

	private final AbstractChannelHandler handler;
	/**
	 * @param port
	 */
	public DefaultServer(ChannelConfig channelConfig, CoderFactory coderFactory,
			IMessageCoreController messageCoreController, AbstractChannelHandler handler,
			String threadName) {
		super(channelConfig.getPort());
		// TODO Auto-generated constructor stub
		
		handler.setListener(new ServerListener(messageCoreController, channelConfig));
		this.handler = handler;
		PipelineFactory pipelineFactory = new DefaultPipelineFactory(channelConfig, coderFactory, handler);
		setPipelineFactory(pipelineFactory);
	}
	
	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.netty.server.NettyServer#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
		if (this.handler != null) {
			this.handler.destory();
		}
		
	}

}
