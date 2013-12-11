package com.e9.tcp.framework.utils;

import com.e9.framework.cache.ICache;
import com.e9.framework.cache.impl.CacheFactory;

/**
 * @project E9Framework
 * @date 2013-1-17
 * @version 1.0
 * @author Jason
 * 
 * @review_history [2013-1-17] create by Jason
 */
public class CacheManagement {

	@SuppressWarnings("unused")
	private final static ICache<Object, Object> localCache;

	static {
		localCache = CacheFactory.createLocalCache(-1);
	}

	


}
