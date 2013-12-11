package com.e9.framework.handler.codec.sgip.property;

/**
 * 信息类型, 见SGIP V1.21 4.2.3.3.1 SUBMIT章节
 * <pre>
	0-短消息信息
	其它：待定
 * </pre>
 * @date 2013-8-23
 * @author Jason
 */
public interface SgipMessageType {

	/**
	 * 短消息信息
	 */
	int SMS = 0;
}
