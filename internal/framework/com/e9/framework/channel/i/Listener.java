package com.e9.framework.channel.i;



/**
 * @project E9Framework
 * @date 2012-12-19
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-19] create by Jason
 */
public interface Listener {
	
	/**
	 * 与对端的连接建立
	 * @param session
	 */
	void connected(Session session);
	
	/**
	 * 接收到对端消息
	 * @param session 消息来源会话
	 * @param message 会话发送的消息
	 */
	void messageReceived(Session session,Message message);
	
	/**
	 * 与对端的连接断开
	 * @param session
	 */
	void disconnected(Session session);
	
	/**
	 * 关闭连接
	 * @param session
	 */
	void close(Session session);
	
	/**
	 * 触发链路检测
	 * @param connection 链路检测超时的连接
	 */
	void keepAliveTest(Session session);

	/**
	 * 通道发生异常将触发该方法
	 * @param conn
	 * @param cause
	 */
	void exceptionCaught(Session session, Throwable cause);
	
	void destory();

}
