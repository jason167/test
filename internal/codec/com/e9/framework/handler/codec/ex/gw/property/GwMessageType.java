package com.e9.framework.handler.codec.ex.gw.property;

/**
 * 消息类型，见《短信通道协议开发包V3.06(内部使用).doc》，3.2.5.3章节
 * <pre>
 *  1文本, 2图片, 3铃声, 4二进制
 * </pre>
 * @date 2013-8-28
 * @author Jason
 */
public interface GwMessageType {

	/**
	 * 文本
	 */
	int TXT = 1;
	
	/**
	 * 图片
	 */
	int PICTURE = 2;
	
	/**
	 * 铃声
	 */
	int MUSIC = 3;
	
	/**
	 * 二进制
	 */
	int BINARY = 4;
}
