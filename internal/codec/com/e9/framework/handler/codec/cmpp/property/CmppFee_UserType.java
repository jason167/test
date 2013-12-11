package com.e9.framework.handler.codec.cmpp.property;

/**
 * 计费用户类型,见CMPP v2.0 7.4.3.1章节
 * @project E9Framework
 * @date 2013-1-24
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-24] create by Jason
 */
public interface CmppFee_UserType {
	/**
	 * 0＝对短消息接收方计费；
	 */
	int BY_RECEIVER = 0;
	
	/**
	 * 1＝对短消息发送方计费；
	 */
	int BY_SENDER = 1;
	
	/**
	 * 2＝对SP计费；
	 */
	int BY_SP = 2;
	
	/**
	 * 3＝表示本字段无效，对谁计费参见Fee_terminal_Id字段。
	 */
	int IGNORE = 3;
}
