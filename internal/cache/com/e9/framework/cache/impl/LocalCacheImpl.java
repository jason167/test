package com.e9.framework.cache.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9.framework.cache.ICache;

/**
 * 本地缓存
 * @project E9Framework
 * @date 2012-12-28
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-28] create by Jason
 */
public class LocalCacheImpl implements ICache<Object,Object>
{
	private final Logger logger =
            LoggerFactory.getLogger(LocalCacheImpl.class);
	
	/**
	 * 具体内容存放的地方
	 */
	ConcurrentHashMap<Object, Object>[] caches;
	/**
	 * 超期信息存储
	 */
	ConcurrentHashMap<Object, Long> expiryCache;
	
	/**
	 * 清理超期内容的服务
	 */
	private  ScheduledExecutorService scheduleService;
	
	/**
	 * 清理超期信息的时间间隔，默认10分钟
	 */
	private int expiryInterval = 10;
	
	/**
	 * 内部cache的个数，根据key的hash对module取模来定位到具体的某一个内部的Map，
	 * 减小阻塞情况发生。
	 */
	private int moduleSize = 10;
	
	public LocalCacheImpl()
	{
		init();
	}
	
	public LocalCacheImpl(int expiryInterval)
	{
		this.expiryInterval = expiryInterval;
		init();
	}
	
	public LocalCacheImpl(int expiryInterval, int moduleSize)
	{
		this.expiryInterval = expiryInterval;
		this.moduleSize = moduleSize;
		init();
	}
	
	
	@SuppressWarnings("unchecked")
	private void init()
	{
		caches = new ConcurrentHashMap[moduleSize];
		
		for(int i = 0 ; i < moduleSize ;i ++)
			caches[i] = new ConcurrentHashMap<Object, Object>();
		
		expiryCache = new ConcurrentHashMap<Object, Long>();
		
		if (expiryInterval > 0) {
			
			scheduleService = Executors.newScheduledThreadPool(1);
			
			scheduleService.scheduleAtFixedRate(new CheckOutOfDateSchedule(caches,expiryCache), 
					0, expiryInterval * 60, TimeUnit.SECONDS);
			
			logger.info("DefaultCache CheckService is start!");
		}
	}
	
	public boolean clear()
	{
		if (caches != null)
			for(ConcurrentHashMap<Object, Object> cache : caches)
			{
				cache.clear();
			}
		
		if (expiryCache != null)
			expiryCache.clear();
		
		return true;
	}


	public boolean containsKey(Object key)
	{
		checkValidate(key);
		return getCache(key).containsKey(key);
	}


	public Object get(Object key)
	{
		checkValidate(key);
		return getCache(key).get(key);
	}

	
	public Set<Object> keySet()
	{
		checkAll();
		return expiryCache.keySet();
	}


	public Object put(Object key, Object value)
	{
		
		Object result = getCache(key).put(key, value);
		expiryCache.put(key,(long)-1);
		
		return result;
	}
	
	public Object putIfAbsent(Object key, Object value)
	{
		Object result = getCache(key).putIfAbsent(key, value);
		if (result == null) {
			
			expiryCache.put(key,(long)-1);
		}
		
		return result;
	}

	public Object put(Object key, Object value, Date expiry)
	{
		
		Object result = getCache(key).put(key, value);
		expiryCache.put(key,expiry.getTime());
		
		return result;
	}
	
	public Object putIfAbsent(Object key, Object value, Date expiry)
	{
		Object result = getCache(key).putIfAbsent(key, value);
		if (result == null) {
			
			expiryCache.put(key,expiry.getTime());
		}
		
		return result;
	}
	
	
	public Object putIfAbsent(Object key, Object value, int TTL)
	{
		Object result = getCache(key).putIfAbsent(key, value);
		if (result == null) {
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.SECOND, TTL);
			expiryCache.put(key,calendar.getTime().getTime());
		}
		
		return result;
	}


	public Object remove(Object key)
	{
		Object result = getCache(key).remove(key);
		expiryCache.remove(key);
		return result;
	}


	public int size()
	{
		checkAll();
		
		return expiryCache.size();
	}


	public Collection<Object> values()
	{
		checkAll();
		
		Collection<Object> values = new ArrayList<Object>();
		
		for(ConcurrentHashMap<Object, Object> cache : caches)
		{
			values.addAll(cache.values());	
		}

		return values;
	}
	
	private ConcurrentHashMap<Object, Object>getCache(Object key)
	{
		long hashCode = (long)key.hashCode();
		
		if (hashCode < 0)
			hashCode = -hashCode;
		
		int moudleNum = (int)hashCode % moduleSize;
		
		return caches[moudleNum];
	}
	
	private void checkValidate(Object key)
	{
		if (key != null && expiryCache.get(key) != null && expiryCache.get(key) != -1 
				&& new Date(expiryCache.get(key)).before(new Date()))
		{
			getCache(key).remove(key);
			expiryCache.remove(key);
		}
	}
	
	private void checkAll()
	{
		Iterator<Object> iter = expiryCache.keySet().iterator();
		
		while(iter.hasNext())
		{
			Object key =  iter.next();
			checkValidate(key);
		}
	}
	
	class CheckOutOfDateSchedule implements java.lang.Runnable
	{
		/**
		 * 具体内容存放的地方
		 */
		ConcurrentHashMap<Object, Object>[] caches;
		/**
		 * 超期信息存储
		 */
		ConcurrentHashMap<Object, Long> expiryCache;
		
		public CheckOutOfDateSchedule(ConcurrentHashMap<Object, Object>[] caches,
				ConcurrentHashMap<Object, Long> expiryCache)
		{
			this.caches = caches;
			this.expiryCache = expiryCache;
		}
		

		public void run()
		{
			check();
		}
		
		public void check()
		{
			try
			{
				for(ConcurrentHashMap<Object, Object> cache : caches)
				{
					Iterator<Object> keys = cache.keySet().iterator();
					
					while(keys.hasNext())
					{
						Object key = keys.next();
						
						if (expiryCache.get(key) == null)
							continue;
						
						long date = expiryCache.get(key);
						
//						if ((date > 0)&&(new Date(date).before(new Date())))
						if ((date > 0) && System.currentTimeMillis() > date)
						{
							expiryCache.remove(key);
							cache.remove(key);
						}
							
					}
					
				}
			}
			catch(Exception ex)
			{
				logger.error("", ex);
			}
		}
		
	}

	
	public Object put(Object key, Object value, int TTL)
	{
		Object result = getCache(key).put(key, value);
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, TTL);
		expiryCache.put(key,calendar.getTime().getTime());
		
		return result;
	}

	public void destroy() 
	{
		try
		{
			clear();
			
			if (scheduleService != null)
				scheduleService.shutdown();
			
			scheduleService = null;
		}
		catch(Exception ex)
		{
			logger.error("", ex);
		}
	}

}
