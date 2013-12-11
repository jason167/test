package com.e9.framework.handler.codec.cmpp.property;

/**
 * 是否要求返回状态报告,见CMPP v2.0 7.4.3.1章节
 * @project 33e9
 * @date 2012-8-28
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-8-28] create by Jason
 */
public interface CmppRegistered_Delivery {
	
	/**
	 * 0＝不要求返回状态报告
	 */
	int NO = 0;
	
	/**
	 * 1＝要求返回状态报告
	 */
	int YES = 1;

}
