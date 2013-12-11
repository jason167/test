package com.e9.framework.cache;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * 本地缓存接口
 * @project E9Framework
 * @date 2012-12-28
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-28] create by Jason
 */
public interface ICache<K,V> {

	/**
	 * 保存数据
	 * @param key
	 * @param value
	 * @return
	 */
	public V put(K key,V value);	
	
	/**
	 * 保存数据
	 * @param key
	 * @param value
	 * @return
	 */
	public V putIfAbsent(K key,V value);	
	
	/**
	 * 保存有有效期的数据
	 * @param key
	 * @param value
	 * @param 有效期
	 * @return
	 */
	public V put(K key,V value, Date expiry);
	
	/**
	 * 保存有有效期的数据
	 * @param key
	 * @param value
	 * @param 有效期
	 * @return
	 */
	public V putIfAbsent(K key,V value, Date expiry);
	
	/**
	 * 保存有有效期的数据
	 * @param key
	 * @param value
	 * @param 数据超时的秒数
	 * @return
	 */
	public Object putIfAbsent(Object key, Object value, int TTL);
	
	/**
	 * 保存有有效期的数据
	 * @param key
	 * @param value
	 * @param 数据超时的秒数
	 * @return
	 */
	public V put(K key,V value, int TTL);
	
	/**
	 * 获取缓存数据
	 * @param key
	 * @return
	 */
	public V get(K key);
	
	/**
	 * 移出缓存数据
	 * @param key
	 * @return
	 */
	public V remove(K key);	
	
	/**
	 * 删除所有缓存内的数据
	 * @return
	 */
	public boolean clear();
	
	/**
	 * 缓存数据数量
	 * @return
	 */
	public int size();
	
	/**
	 * 缓存所有的key的集合
	 * @return
	 */
	public Set<K> keySet();
	
	/**
	 * 缓存的所有value的集合
	 * @return
	 */
	public Collection<V> values();
	
	/**
	 * 是否包含了指定key的数据
	 * @param key
	 * @return
	 */
	public boolean containsKey(K key);
	
	/**
	 * 释放Cache占用的资源
	 */
	public void destroy();
}
