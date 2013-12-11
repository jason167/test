package com.e9.framework.cache;

import com.e9.framework.cache.util.IQueueRunnable;
import com.e9.framework.channel.i.Session;

/**
 * Message queue内部辅助类接口
 * @project E9Framework
 * @date 2013-1-30
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-30] create by Jason
 */
public interface IQueueSupport<E> extends IQueue<E>, IQueueRunnable, IQueueComparator {

	public void setSession(Session session);
}
