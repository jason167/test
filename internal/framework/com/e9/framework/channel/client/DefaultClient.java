package com.e9.framework.channel.client;


import com.e9.framework.channel.AbstractChannelHandler;
import com.e9.framework.channel.i.CoderFactory;
import com.e9.framework.channel.i.IMessageCoreController;
import com.e9.framework.channel.i.Listener;
import com.e9.framework.channel.i.PipelineFactory;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.pipeline.DefaultPipelineFactory;
import com.e9.framework.channel.util.ChannelConfig;

/**
 * @date 2013-5-22
 * @author Jason
 */
public abstract class DefaultClient extends NettyClient {

	private final AbstractChannelHandler handler;
	private final Listener listener;
	
	public DefaultClient(ChannelConfig channelConfig, CoderFactory coderFactory,
			IMessageCoreController messageCoreController, 
			boolean isNeedReConnect, boolean keepAlive,
			AbstractChannelHandler handler, String threadName) {
		super(channelConfig.getHost(), channelConfig.getPort());
		
		listener = new ClientListener(messageCoreController, channelConfig, this, isNeedReConnect);
		handler.setListener(listener);
		this.handler = handler;
		PipelineFactory pipelineFactory = new DefaultPipelineFactory(
				channelConfig, 
				coderFactory, 
				handler,
				keepAlive
				);
		
		setPipelineFactory(pipelineFactory);
	}
	
	/**
	 * 默认值：自动重连、链路检测、管道工厂默认线程数为：processors * 2
	 * @param channelConfig
	 * @param coderFactory
	 * @param messageCoreController
	 * @param handler
	 */
	public DefaultClient(ChannelConfig channelConfig, CoderFactory coderFactory,
			IMessageCoreController messageCoreController, AbstractChannelHandler handler, String threadName) {
		this(channelConfig, coderFactory, messageCoreController, true, true, handler, threadName);
	}
	

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.netty.client.NettyClient#connect()
	 */
	@Override
	public Session connect() {
		// TODO Auto-generated method stub
		Session session = super.connect();
		this.listener.connected(session);
		return session;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.netty.client.NettyClient#close()
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
