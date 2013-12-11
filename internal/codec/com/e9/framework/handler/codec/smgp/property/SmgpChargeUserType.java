package com.e9.framework.handler.codec.smgp.property;

/**
 * 计费用户类型,见《中国电信CTMP开发接口－中国电信SMGP协议》6.3.5章节
 * @project 33e9
 * @date 2012-7-26
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-26] create by zengweizhi
 */
public interface SmgpChargeUserType {
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
	 * 3＝表示本字段无效，对谁计费参见ChargeTermID或ChargeTermPseudo 字段。
	 */
	int IGNORE = 3;
}
