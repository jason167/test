package com.e9.framework.handler.codec.smgp.property;

/**
 * 短消息类型,见《中国电信CTMP开发接口－中国电信SMGP协议》6.2.9章节
 * @project 33e9
 * @date 2012-7-26
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-26] create by zengweizhi
 */
public interface SmgpMsgType {
	/**
	 * 0＝MO消息（终端发给SP）；
	 */
	int MO = 0;
	
	/**
	 * MT消息（SP发给终端，包括WEB上发送的点对点短消息）；
	 */
	int MT = 6;
	
	/**
	 * 点对点短消息；
	 */
	int PEER_TO_PEER = 7;
}
