package com.e9.framework.channel.server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9.framework.channel.i.PipelineFactory;
import com.e9.framework.channel.i.Server;
import com.e9.framework.channel.util.NamingThreadFactory;

/**
 * @project E9Framework
 * @date 2012-12-19
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-19] create by Jason
 */
public abstract class NettyServer implements Server {

	private PipelineFactory pipelineFactory;
	private boolean started = false;
	private ServerBootstrap bootstrap;
	private Logger log = LoggerFactory.getLogger(NettyServer.class);
	private int port;
	private Channel channel;
	/**
	 * 
	 */
	public NettyServer(int port) {
		// TODO Auto-generated constructor stub
		this.port = port;
	}
	
	
	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Server#bind()
	 */
	@Override
	public boolean bind() {
		// TODO Auto-generated method stub
		return bind(null);
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Server#bind(java.net.SocketAddress)
	 */
	@Override
	public boolean bind(SocketAddress address) {
		// TODO Auto-generated method stub
		log.info("Server starting...");
		
		if (pipelineFactory == null) {
			throw new IllegalArgumentException("Server PipelineFactory instance can't be null !!");
		}
		NioServerSocketChannelFactory serverSocketChannelFactory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(new NamingThreadFactory("Server-Boss")),
				Executors.newCachedThreadPool(new NamingThreadFactory("Server-Worker"))
			);
		
		bootstrap = new ServerBootstrap(
				serverSocketChannelFactory
			); 
		
		bootstrap.setPipelineFactory(pipelineFactory);
		bootstrap.setOption("child.tcpNoDelay", true);  
		bootstrap.setOption("child.keepAlive", false);  
		bootstrap.setOption("child.soLinger", -1);   
		bootstrap.setOption("child.sendBufferSize", 8192);   
		bootstrap.setOption("child.receiveBufferSize", 8192);   
		bootstrap.setOption("receiveBufferSize", 8192);   
		bootstrap.setOption("reuserAddress", true);   
			
		try {
			if (address != null) {
				channel = bootstrap.bind(address);
			}else{
				channel = bootstrap.bind(new InetSocketAddress(port));
			}
			log.info("Server already started, listener port is "+port);
			started = true;
		} catch (Exception e) {
			// TODO: handle exception
			log.info("Server start failed !!");
			log.error("",e);
			started = false;
		}
		
		return isStarted();
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Server#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		try {
			if (channel != null) {
				channel.close();
			}
			if (pipelineFactory != null) {
				pipelineFactory.destory();
			}
				
		} catch (Exception e) {
			// TODO: handle exception
			log.error("",e);
		}
		finally{
			started = false;
			if (bootstrap != null) {
				bootstrap.shutdown();
				bootstrap.releaseExternalResources();
			}
		}
		
	}
	
	private boolean isStarted(){
		return started;
	}
	
	protected void setPipelineFactory(PipelineFactory pipelineFactory){
		this.pipelineFactory = pipelineFactory;
	}

}
