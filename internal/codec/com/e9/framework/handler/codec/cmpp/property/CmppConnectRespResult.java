package com.e9.framework.handler.codec.cmpp.property;

/**
 * @project OSP
 * @date 2013-1-29
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-29] create by Jason
 */
public interface CmppConnectRespResult {
/*
 * 	0：正确
	1：消息结构错
	2：非法源地址
	3：认证错
	4：版本太高
	5~ ：其他错误
 */
	

	/**
	 * 0:成功
	 */
	int SUCCESS = 0;
	
	/**
	 * 1:消息结构错
	 */
	int INVALID_MESSAGE = 1;
	
	/**
	 * 2:非法源地址
	 */
	int INVALID_IP = 2;

	/**
	 * 3:认证错
	 */
	int INVALID_AUTHENTICATOR = 3;
	
	/**
	 * 4:版本太高
	 */
	int INVALID_VERSION = 4;
	
	/**
	 * 4:其他错误
	 */
	int OTHER_ERROR = 5;
}
