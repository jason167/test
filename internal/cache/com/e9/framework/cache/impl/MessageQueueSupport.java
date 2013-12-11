package com.e9.framework.cache.impl;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9.framework.cache.AbstractMessageQueueImpl;
import com.e9.framework.cache.IQueueSupport;
import com.e9.framework.cache.util.IMessagePersistentUtil;
import com.e9.framework.cache.util.MessageQueuePriority;
import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.Session;

/**
 * Message queue内部辅助类，主要用于Message queue关键参数的设置
 * @date 2013-8-1
 * @author Jason
 */
public abstract class MessageQueueSupport<E extends Message> extends AbstractMessageQueueImpl<E> implements IQueueSupport<E>{

	/**
	 * 
	 */
	private volatile Session session;
	private MessageQueuePriority priority;
	protected volatile int state = IDLE;
	private Logger logger = LoggerFactory.getLogger(MessageQueueSupport.class);
    private final AtomicBoolean MQSupportInnerRunnableState = new AtomicBoolean();
    private IMessagePersistentUtil messagePersistentUtil;
    /**
	 * 允许延迟的时间，单位：毫秒
	 */
    private long allowDelayTime = 3 * 1000;
    
	/**
	 * 允许延迟的次数
	 */
    private int allowDelayTimes = 3;
	
    /**
	 * 暂停时间阀值，单位：毫秒
	 */
    private long pausedTime = 30 * 1000;
    
    /**
     * 发送消息总量的平均用时大于该值，将被降级处理，单位：毫秒
     */
    private long allowUpgradeTime = 1 * 1000;
	
    
	/**
	 * 
	 */
	public MessageQueueSupport(Session session) {
		// TODO Auto-generated constructor stub
		this(session, Integer.MAX_VALUE, MessageQueuePriority.NINE, null);
	}
	
	/**
	 * 
	 */
	public MessageQueueSupport(Session session, IMessagePersistentUtil messagePersistentUtil) {
		// TODO Auto-generated constructor stub
		this(session, Integer.MAX_VALUE, MessageQueuePriority.NINE, messagePersistentUtil);
	}
	
	/**
	 * 
	 */
	public MessageQueueSupport(Session session, int capacity, MessageQueuePriority priority, IMessagePersistentUtil messagePersistentUtil) {
		// TODO Auto-generated constructor stub
		super(capacity);
		this.session = session;
		this.priority = priority;
		this.messagePersistentUtil = messagePersistentUtil;
	}
	

	/* (non-Javadoc)
	 * @see com.e9.cache.IQueueSupport#setSession(com.e9.tcp.framework.Session)
	 */
	@Override
	public void setSession(Session session) {
		// TODO Auto-generated method stub
		if (session != null) {
			this.session = session;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.e9.framework.cache.IQueueComparator#getPriority()
	 */
	@Override
	public MessageQueuePriority getPriority() {
		// TODO Auto-generated method stub
		return priority;
	}
	/* (non-Javadoc)
	 * @see com.e9.framework.cache.IQueueComparator#setPriority(com.e9.framework.cache.util.MessageQueuePriority)
	 */
	@Override
	public void setPriority(MessageQueuePriority priority) {
		// TODO Auto-generated method stub
		this.priority = priority;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		if (size() <= 0) {
			state = NONE;
			return;
		}
		
		try {
			doRun();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("", e);
		}
	}
	
	
	protected void toSleep() {
		// TODO Auto-generated method stub
		toSleep(getPausedTime());
	}
	
	protected void toSleep(long pausedTime) {
		logger.debug("toSleep");
		state = PAUSED;
		if (MQSupportInnerRunnableState.compareAndSet(false, true)) {
			new Thread(new MQSupportInnerRunnable(getPausedTime()), "MQSupportInnerRunnable").start();
		}
	}
	
	
	/**
	 * @return the session
	 */
	protected Session getSession() {
		return session;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.framework.cache.util.IQueueRunnable#getState()
	 */
	@Override
	public int getState() {
		// TODO Auto-generated method stub
		return state;
	}
	
	/**
	 * @return the allowDelayTime
	 */
	public long getAllowDelayTime() {
		return allowDelayTime;
	}
	/**
	 * @param allowDelayTime the allowDelayTime to set
	 */
	public void setAllowDelayTime(long allowDelayTime) {
		this.allowDelayTime = allowDelayTime;
	}
	/**
	 * @return the allowDelayTimes
	 */
	public int getAllowDelayTimes() {
		return allowDelayTimes;
	}
	/**
	 * @param allowDelayTimes the allowDelayTimes to set
	 */
	public void setAllowDelayTimes(int allowDelayTimes) {
		this.allowDelayTimes = allowDelayTimes;
	}
	/**
	 * @return the pausedTime
	 */
	public long getPausedTime() {
		return pausedTime;
	}
	/**
	 * @param pausedTime the pausedTime to set
	 */
	public void setPausedTime(long pausedTime) {
		this.pausedTime = pausedTime;
	}
	/**
	 * @return the allowUpgradeTime
	 */
	public long getAllowUpgradeTime() {
		return allowUpgradeTime;
	}
	/**
	 * @param allowUpgradeTime the allowUpgradeTime to set
	 */
	public void setAllowUpgradeTime(long allowUpgradeTime) {
		this.allowUpgradeTime = allowUpgradeTime;
	}


	class MQSupportInnerRunnable implements Runnable{

		long ms;
		Logger logger = LoggerFactory.getLogger(MQSupportInnerRunnable.class);
		/**
		 * 
		 */
		public MQSupportInnerRunnable(long ms) {
			// TODO Auto-generated constructor stub
			this.ms = ms;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				logger.debug("线程[{}]启动成功", Thread.currentThread().getName());
				logger.info("This a Task {} paused time {} seconds", this, ms / 1000);
				TimeUnit.MILLISECONDS.sleep(ms);
			} catch (InterruptedException e) {
				logger.error("",e);
			}
			finally{
				state = IDLE;
				logger.debug("线程[{}]正常关闭", Thread.currentThread().getName());
				MQSupportInnerRunnableState.set(false);
			}
		}
		
	}


	/**
	 * @return the messagePersistentUtil
	 */
	public IMessagePersistentUtil getMessagePersistentUtil() {
		return messagePersistentUtil;
	}

	/**
	 * @param messagePersistentUtil the messagePersistentUtil to set
	 */
	public void setMessagePersistentUtil(IMessagePersistentUtil messagePersistentUtil) {
		this.messagePersistentUtil = messagePersistentUtil;
	}
	
	

}
