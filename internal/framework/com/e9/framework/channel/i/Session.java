package com.e9.framework.channel.i;

import java.net.SocketAddress;

import org.jboss.netty.channel.Channel;


/**
 * @project E9Framework
 * @date 2012-12-19
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-19] create by Jason
 */
public interface Session {
	
	/**
	 * 获取Session id
	 * @return Integer
	 */
	Integer getId();
	/**
	 * 会话连接是否建立
	 * @return
	 */
	boolean isConnected();
	
	/**
	 * 断开当前会话连接
	 */
	void disconnect();
	
	/**
	 * 关闭会话
	 */
	void close();
	
	/**
	 * 获取本地地址
	 * @return
	 */
	SocketAddress getLocalAddress();
	
	/**
	 * 获取对端地址
	 * @return
	 */
	SocketAddress getRemoteAddress();
	
	/**
	 * 向对端发送消息
	 * @param message
	 */
	boolean send(Message message);
	
	/**
	 * 
	 * @param message
	 * @param timeout 单位：秒
	 * @return
	 */
	boolean send(Message message, long timeout);
	
	/**
	 * 向对端发送消息,并等待响应消息
	 * @param message
	 * @param timeout 单位：秒
	 * @return
	 */
	Message submit(Message message, long timeout);
	
	/**
	 * 向对端发送消息,并等待响应消息
	 * @param message
	 * @return
	 */
	Message submit(Message message);
	
	/**
	 * 添加附件
	 * @param key
	 * @param value
	 */
	void putAttachment(Object key,Object value);
	
	/**
	 * 获取附件
	 * @param key
	 * @return
	 */
	Object getAttachment(Object key);
	
	/**
	 * 删除附件
	 * @param key
	 * @return
	 */
	Object removeAttachment(Object key);
	
	/**
	 * 同步锁
	 * @param timeout
	 * @throws InterruptedException
	 */
	void lock(long timeout)throws InterruptedException;
	
	/**
	 * 释放同步锁
	 */
	void unlock();
	
	/**
	 * 取得该会话对应的通道编号
	 * @return
	 */
	String getChannelCode();
	
	/**
	 * 设置会话对应的通道编号
	 * @param channelCode
	 */
	void setChannelCode(String channelCode);
	
	/**
	 * @return the authenticated
	 */
	public boolean isAuthenticated();

	/**
	 * @param authenticated the authenticated to set
	 */
	public void setAuthenticated(boolean authenticated);
	
	public boolean isKeepAliveTesting();
	
	public void setKeepAliveTesting(boolean keepAliveTesting);
	
	public void setChannel(Channel channel);
	public Channel getChannel();

}
