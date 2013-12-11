package com.e9.framework.channel.client;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9.framework.channel.i.Client;
import com.e9.framework.channel.i.PipelineFactory;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.util.NamingThreadFactory;

/**
 * @project E9Framework
 * @date 2012-12-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-20] create by Jason
 */
public abstract class NettyClient implements Client {
	
	private PipelineFactory pipelineFactory;
	private ClientBootstrap bootstrap;
	private boolean ready = false;
	private Session session;
	private ChannelFuture future = null;
	private final String host;
	private final int port;
	private long connect_timeout;
	private TimeUnit unit;
	private Logger logger = LoggerFactory.getLogger(NettyClient.class);
	
	/**
	 * 
	 */
	public NettyClient(String host, int port, long connect_timeout, TimeUnit unit) {
		// TODO Auto-generated constructor stub
		this.host = host;
		this.port = port;
		this.connect_timeout = connect_timeout;
		this.unit = unit;
	}
	
	public NettyClient(String host, int port) {
		// TODO Auto-generated constructor stub
		this(host, port, 10, TimeUnit.SECONDS);
	}
	
	
	protected void initialize(){
		
		if (pipelineFactory == null) {
			throw new IllegalArgumentException("NettyClinet PipelineFactory instance can't be null !!");
		}
		
		NioClientSocketChannelFactory clientSocketChannelFactory = new NioClientSocketChannelFactory(
				Executors.newCachedThreadPool(new NamingThreadFactory("NettyClient-Boss")), 
				Executors.newCachedThreadPool(new NamingThreadFactory("NettyClient-Worker")));
		bootstrap = new ClientBootstrap(
				clientSocketChannelFactory
			);
			
		bootstrap.setPipelineFactory(pipelineFactory);
		bootstrap.setOption("tcpNoDelay", true);  
		bootstrap.setOption("keepAlive", false);  
		bootstrap.setOption("soLinger", -1);   
		bootstrap.setOption("sendBufferSize", 8192);   
		bootstrap.setOption("receiveBufferSize", 8192);   
		bootstrap.setOption("reuserAddress", true); 
		
		ready = true;
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Client#connect()
	 */
	@Override
	public Session connect() {
		// TODO Auto-generated method stub
		
		if (!isReady()) {
			initialize();
		}

		SocketAddress remoteAddress = new InetSocketAddress(host, port);
		future = bootstrap.connect(remoteAddress);
		boolean successful = future.awaitUninterruptibly(connect_timeout, unit);
		if(successful){
			
			Channel channel = future.getChannel();
			if (getSession() == null) {
				
				session = new ClientSession(channel);
			}else{
				
				this.getSession().setChannel(channel);
			}
		}
		return session;
		
		
	}
	
	public Session getSession() {
	    return this.session;
	}
	
	private boolean isReady() {
		return ready;
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Client#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		try {
			if (future != null) {
				future.addListener(ChannelFutureListener.CLOSE);
			}
			
			if (pipelineFactory != null) {
				pipelineFactory.destory();
			}
			
		}catch(Exception e){
			logger.error("",e);
		}
		finally{
			ready = false;
			if (null != bootstrap) {
				bootstrap.shutdown();
				bootstrap.releaseExternalResources();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Client#login(com.e9.tcp.framework.Session)
	 */
	@Override
	public abstract boolean login(Session session);
	
	protected void setPipelineFactory(PipelineFactory pipelineFactory){
		this.pipelineFactory = pipelineFactory;
	}
}
