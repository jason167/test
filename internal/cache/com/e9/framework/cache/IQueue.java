package com.e9.framework.cache;

import java.util.concurrent.TimeUnit;

/**
 * 队列接口
 * @project E9Framework
 * @date 2013-1-23
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-23] create by Jason
 */
public interface IQueue<E> {
	
	public boolean offer(E e, MessageLevel level);
	public boolean offer(E e, MessageLevel level, boolean persistentOnCacheFailed);
	public boolean offer(E e, long timeout, TimeUnit unit, MessageLevel level);
	
	public E poll();
	public E poll(MessageLevel level);
	public E poll(long timeout, TimeUnit unit);
	public E poll(MessageLevel level, long timeout, TimeUnit unit);
	
	public boolean put(E e, MessageLevel level);
	public E take();
	public E take(MessageLevel level);
	public void destory();
	public int size(MessageLevel level);
	public int size();
	
	/**
	 * 当缓存消息失败时，本函数将被回调
	 * @param e
	 * @param level
	 * @return boolean
	 */
	public boolean persistentOnCacheFailed(E e, MessageLevel level);
	
	/**
	 * 当调用destory函数时，本函数将被回调
	 * @param queues
	 */
	public void persistentOnDestory(E9BlockingQueue<E>[] queues);
}
