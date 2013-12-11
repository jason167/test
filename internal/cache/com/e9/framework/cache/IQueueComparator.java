package com.e9.framework.cache;

import com.e9.framework.cache.util.MessageQueuePriority;

/**
 * 比较器接口
 * @date 2013-8-1
 * @author Jason
 */
public interface IQueueComparator {

	MessageQueuePriority getPriority();
	void setPriority(MessageQueuePriority priority);
	
}
