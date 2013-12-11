package com.e9.framework.cache.impl;

import java.util.concurrent.BlockingQueue;

import com.e9.framework.cache.E9BlockingQueue;
import com.e9.framework.cache.ICache;
import com.e9.framework.channel.i.Message;


/**
 * @project E9Framework
 * @date 2013-1-14
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-14] create by Jason
 */
public class CacheFactory {
	


	public static ICache<Object, Object> createLocalCache(){
		return new LocalCacheImpl();
	}
	
	public static ICache<Object, Object> createLocalCache(int expiryInterval){
		return new LocalCacheImpl(expiryInterval);
	}
	
	public static BlockingQueue<Message> createLocalQueue(int capacity){
		return new E9BlockingQueue<Message>(capacity);
	}
}
