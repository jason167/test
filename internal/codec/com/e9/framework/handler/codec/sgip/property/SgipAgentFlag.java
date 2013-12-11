package com.e9.framework.handler.codec.sgip.property;

/**
 * 代收费标志，见SGIP V1.21 4.2.3.3.1 SUBMIT章节
 * @date 2013-8-23
 * @author Jason
 */
public interface SgipAgentFlag {

	/**
	 *  0：应收；
	 */
	int RECEIVABLE = 0;
	
	/**
	 * 1：实收
	 */
	int PAID_UP = 1;
}
