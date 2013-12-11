package com.e9.framework.handler.codec.smgp.property;

/**
 * 是否要求返回状态报告；见《中国电信CTMP开发接口－中国电信SMGP协议》6.2.10章节
 * @project 33e9
 * @date 2012-8-28
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-8-28] create by Jason
 */
public interface SmgpNeedReport {
	
	/**
	 * 0＝不要求返回状态报告
	 */
	int NO = 0;
	
	/**
	 * 1＝要求返回状态报告
	 */
	int YES = 1;

}
