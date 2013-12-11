package com.e9.framework.handler.codec.sgip.property;

/**
 * 短消息的编码格式, 见SGIP V1.21 4.2.3.3.1 SUBMIT章节
	<pre>
	0：纯ASCII字符串
	3：写卡操作
	4：二进制编码
	8：UCS2编码 （TP_udhi = 1时）
	15: GBK编码
	其它参见GSM3.38第4节：SMS Data Coding Scheme
	</pre>
 * @date 2013-8-23
 * @author Jason
 */
public interface SgipMessageCoding {

	/**
	 * 纯ASCII字符串
	 */
	int ASCII = 0;
	
	/**
	 * 写卡操作
	 */
	int WRITES_CARD = 3;
	
	/**
	 * 二进制编码
	 */
	int BINARY = 4;
	
	/**
	 * UCS2编码 （TP_udhi = 1时）
	 */
	int UCS2 = 8;
	
	/**
	 * GBK编码
	 */
	int GBK = 15;
}
