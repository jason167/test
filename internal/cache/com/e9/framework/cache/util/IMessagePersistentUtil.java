package com.e9.framework.cache.util;

import com.e9.framework.cache.E9BlockingQueue;
import com.e9.framework.cache.MessageLevel;
import com.e9.framework.channel.i.Message;

/**
 * @date 2013-10-11
 * @author Jason
 */
public interface IMessagePersistentUtil {

	public boolean persistentOnCacheFailed(Message message, MessageLevel messageLevel);
	
	public boolean persistentOnDestory(E9BlockingQueue<Message>[] queue);
}
