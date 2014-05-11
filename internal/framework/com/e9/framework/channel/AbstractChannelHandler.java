package com.e9.framework.channel;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9.framework.channel.i.Listener;
import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.util.NamingThreadFactory;
import com.e9.framework.channel.util.SessionManagement;

/**
 * @project E9Framework
 * @date 2012-12-21
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-21] create by Jason
 */
public abstract class AbstractChannelHandler extends IdleStateAwareChannelUpstreamHandler {

	protected Listener listener;
	protected final ChannelGroup channelGroup;
	private Logger logger = LoggerFactory.getLogger(AbstractChannelHandler.class);
	private ExecutorService MT_Executor;
	private ExecutorService MO_Executor;
	private ExecutorService loginExecutor;
	private ExecutorService aliveTestExecutor;
	
	/**
	 * 
	 */
	public AbstractChannelHandler(String threadName, int nThreads) {
		// TODO Auto-generated constructor stub
		this.channelGroup = new DefaultChannelGroup("DefaultHandler-ChannelGroup");
		
		MT_Executor = Executors.newFixedThreadPool((nThreads <= 0 ? Runtime.getRuntime().availableProcessors() * 2 : nThreads), new NamingThreadFactory(threadName + "-MT-ThreadPoolExecutor"));
		aliveTestExecutor = Executors.newFixedThreadPool(2, new NamingThreadFactory(threadName + "-AliveTest-ThreadPoolExecutor"));
		MO_Executor = Executors.newFixedThreadPool(2, new NamingThreadFactory(threadName + "-MO-ThreadPoolExecutor"));
		loginExecutor = Executors.newFixedThreadPool(2, new NamingThreadFactory(threadName + "-Login-ThreadPoolExecutor"));
	}
	
	
	public AbstractChannelHandler(String threadName) {
		this(threadName, Runtime.getRuntime().availableProcessors() * 2);
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.handler.timeout.IdleStateAwareChannelUpstreamHandler#channelIdle(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.handler.timeout.IdleStateEvent)
	 */
	@Override
	public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e)throws Exception{
		
		Session session = SessionManagement.get(ctx.getChannel());
		if (session == null) {
			return;
		}
		if(IdleState.READER_IDLE.equals(e.getState())){
			this.listener.close(session);
		} else if (IdleState.ALL_IDLE.equals(e.getState())) {
			this.listener.keepAliveTest(session);
		}
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		Object o = e.getMessage();
		if (!(o instanceof Message)) {
			ctx.sendUpstream(e);
			return;
		}
		
		Session session = SessionManagement.get(ctx.getChannel());
		doMessageReceived((Message)o, session);
		
	}
	
	protected abstract void doMessageReceived(Message message, Session session)throws Exception;
	public void destory(){
		try {
			if (MT_Executor != null && !MT_Executor.isShutdown()) {
				MT_Executor.shutdownNow();
				MT_Executor = null;
			}
			if (MO_Executor != null && !MO_Executor.isShutdown()) {
				MO_Executor.shutdownNow();
				MO_Executor = null;
			}
			if (loginExecutor != null && !loginExecutor.isShutdown()) {
				loginExecutor.shutdownNow();
				loginExecutor = null;
			}
			if (aliveTestExecutor != null && !aliveTestExecutor.isShutdown()) {
				aliveTestExecutor.shutdownNow();
				aliveTestExecutor = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
		if (listener != null) {
			listener.destory();
		}
		if (channelGroup != null) {
			channelGroup.close();
		}
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		Session session = SessionManagement.get(ctx.getChannel());
		logger.debug("exceptionCaught session:{}", session);
		if (session != null) {
			
			this.listener.exceptionCaught(session, e.getCause());
		}
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelDisconnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx,
			ChannelStateEvent e) throws Exception {
		// TODO Auto-generated method stub
		Session session = SessionManagement.get(ctx.getChannel());
		logger.debug("channelDisconnected session:{}", session);
		if (session != null) {
			
			this.listener.disconnected(session);
		}
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelClosed(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		Session session = SessionManagement.get(ctx.getChannel());
		logger.debug("channelClosed session:{}", session);
		if (session != null) {
			
			this.listener.close(session);
		}
	}

	/* (non-Javadoc)
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelOpen(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		this.channelGroup.add(ctx.getChannel());
	}

	/**
	 * @return the listener
	 */
	public Listener getListener() {
		return listener;
	}

	/**
	 * @param listener the listener to set
	 */
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	/**
	 * @return the aliveTestExecutor
	 */
	public Executor getAliveTestExecutor() {
		return aliveTestExecutor;
	}
	/**
	 * @return the LoginExecutor
	 */
	public Executor getLoginExecutor() {
		return loginExecutor;
	}
	
	/**
	 * @return the MtExecutor
	 */
	public Executor getMtExecutor() {
		return MT_Executor;
	}
	
	/**
	 * @return the MoExecutor
	 */
	public Executor getMoExecutor() {
		return MO_Executor;
	}
	
	
}
