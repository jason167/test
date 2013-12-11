package com.e9.framework.handler.codec.cmpp.property;

/**
 * FeeType,见CMPP v2.0 7.4.3.1章节
 * @project E9Framework
 * @date 2013-1-24
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-24] create by Jason
 */
public interface CmppFeeType {
	/**
	 * 01＝对“计费用户号码”免费
	 */
	String FREE = "01";
	
	/**
	 * 02＝对“计费用户号码”按条计信息费
	 */
	String BY_COUNT = "02";
	
	/**
	 * 03＝对“计费用户号码”按包月收取信息费
	 */
	String BY_MONTH = "03";
	
	/**
	 * 04＝对“计费用户号码”的信息费封顶
	 */
	String FIXED = "04";
	
	/**
	 * 05＝对“计费用户号码”的收费是由SP实现
	 */
	String BY_SP = "05";
}

