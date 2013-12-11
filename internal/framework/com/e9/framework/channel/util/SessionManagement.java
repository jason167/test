package com.e9.framework.channel.util;


import java.util.Collection;

import org.jboss.netty.channel.Channel;

import com.e9.framework.cache.ICache;
import com.e9.framework.cache.impl.LocalCacheImpl;
import com.e9.framework.channel.i.Session;

/**
 * @project E9Framework
 * @date 2012-12-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-20] create by Jason
 */
public class SessionManagement {
	
	private final static ICache<Object,Object> cache;
	static{
		cache = new LocalCacheImpl(-1);
	}
	
	
	public static Session get(Channel channel){
		return (Session) cache.get(channel);
	}
	
	public static boolean add(Channel channel, Session session){
		Object result = cache.put(channel, session);
		return result == null ? true : false;
	}
	
	public static boolean remove(Channel channel){
		Object result = cache.remove(channel);
		return result != null ? true : false;
	}
	
	public static int size(){return cache.size();}
	
	public static void destroy(){
		cache.destroy();
	}
	
	public static Collection<Object> getSessionList(){
		return cache.values();
	}
	
	

}
