package com.e9.framework.handler.codec.ex.gw.property;

/**
 * 计费方式，见《短信通道协议开发包V3.06(内部使用).doc》，3.2.5.3章节
 * <pre>
	0对目的终端计费,
	1对源终端计费,
	2对SP计费, 
	3 对指定的号码计费
	默认：2
 * </pre>
 * @date 2013-8-28
 * @author Jason
 */
public interface GwFeeUser {

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
	 * 3＝对指定的号码计费，对谁计费参见FeePhone字段。
	 */
	int IGNORE = 3;
}
