package com.e9.constant;

/**
 * message.properties文件的键值
 * @project 33e9
 * @date 2012-7-27
 * @version 1.0
 * @company 33e9
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-27] create by zengweizhi
 */
public interface MessageKey {

	/**
	 * 连接到运营商 {0}:{1}...
	 */
	String CLIENT_RECONNECTING_TO_BOSS = "CLIENT_RECONNECTING_TO_BOSS";
	
	/**
	 * 连接已经建立
	 */
	String CLIENT_RECONNECT_SUCCESSFUL = "CLIENT_RECONNECT_SUCCESSFUL";
	
	/**
	 * 连接失败，{0}秒后重试...
	 */
	String CLIENT_RECONNECT_FAILED = "CLIENT_RECONNECT_FAILED";
	
	/**
	 * 连接失败
	 */
	String CLIENT_CONNECT_FAILED = "CLIENT_CONNECT_FAILED";
	
	/**
	 * 正在登陆到运营商 {0}:{1}...
	 */
	String CLIENT_LOGINING_TO_BOSS = "CLIENT_LOGINING_TO_BOSS";
	
	/**
	 * 登陆成功
	 */
	String CLIENT_LOGIN_SUCCESSFUL = "CLIENT_LOGIN_SUCCESSFUL";
	
	/**
	 * 登陆失败
	 */
	String CLIENT_LOGIN_FAILED = "CLIENT_LOGIN_FAILED";
	
	/**
	 * 登陆失败，{0}秒后重新登陆...
	 */
	String CLIENT_RELOGIN_FAILED = "CLIENT_RELOGIN_FAILED";
	
	/**
	 * 服务器端正在启动...
	 */
	String SERVER_STARTING = "SERVER_STARTING";
	
	/**
	 * 服务器端已经启动，正在监听[{0}]端口...
	 */
	String SERVER_STARTED = "SERVER_STARTED";
	
	/**
	 * 服务器端启动失败
	 */
	String SERVER_START_FAILED = "SERVER_START_FAILED";
}

