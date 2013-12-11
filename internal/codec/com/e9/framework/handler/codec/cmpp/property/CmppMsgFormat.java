package com.e9.framework.handler.codec.cmpp.property;

/**
 * @project 33e9
 * @date 2012-7-26
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-26] create by zengweizhi
 */
public interface CmppMsgFormat {
	/**
	 * 0＝ASCII编码；
	 */
	int ASCII = 0;
	
	/**
	 * 3＝短消息写卡操作；
	 */
	int MESSAGE_WRITES_CARD = 3;
	
	/**
	 * 4＝二进制短消息；
	 */
	int BINARY_MESSAGE = 4;
	
	/**
	 * 8＝UCS2编码；
	 */
	int UCS2 = 8;
	
	/**
	 * 15＝GBK编码；
	 */
	int GBK = 15;
	
}
