package com.e9.framework.handler.codec;

/**
 * 短消息编码格式，CMPP/SGIP/SMGP协议通用
 * @date 2013-8-26
 * @author Jason
 */
public interface MsgFormat {
	/**
	 * 0＝ASCII编码；
	 * 对于回执消息，要求MsgFormat＝0。
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
	 * 对于文字短消息，要求MsgFormat＝15。
	 */
	int GBK = 15;
	
	/**
	 * 246（F6）＝(U)SIM相关消息；
	 */
	int SIM = 246;
	
	/**
	 * 使用该编码通道不签名（中文内容短信、长短信使用该编码），编解码统一使用USC2
	 */
	int GSM25 = 25;
	
	/**
	 * 使用该编码通道不签名（英文短信），编解码统一使用USC2
	 */
	int GSM17 = 17;
}
