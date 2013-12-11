package com.e9.framework.handler.codec.cmpp.property;

/**
 * 短消息发送优先级,见CMPP v2.0 7.4.3.1章节
 * @project 33e9
 * @date 2012-7-26
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-26] create by zengweizhi
 */
public interface CmppMsg_level {
	/**
	 * 0＝低优先级；
	 */
	int LOW = 0;
	
	/**
	 * 1＝普通优先级；
	 */
	int NORMAL = 1;
	
	/**
	 * 2＝较高优先级；
	 */
	int LITTLE_HIGH = 2;
	
	/**
	 * 3＝高优先级；
	 */
	int HIGH = 3;
}

