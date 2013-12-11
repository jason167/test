package com.e9.framework.handler.codec.sgip.property;

/**
 * 引起MT消息的原因，见SGIP V1.21 4.2.3.3.1 SUBMIT章节
 <pre>
	0-MO点播引起的第一条MT消息；
	1-MO点播引起的非第一条MT消息；
	2-非MO点播引起的MT消息；
	3-系统反馈引起的MT消息。
 </pre>
 * @date 2013-8-23
 * @author Jason
 */
public interface SgipMorelatetoMTFlag {

	/**
	 * MO点播引起的第一条MT消息
	 */
	int MO_CAUSE_FIRST_MT = 0;
	
	/**
	 * MO点播引起的非第一条MT消息
	 */
	int MO_CAUSE_NO_FIRST_MT = 1;
	
	/**
	 * 非MO点播引起的MT消息
	 */
	int NO_MO_CAUSE_MT = 2;
	
	/**
	 * 系统反馈引起的MT消息
	 */
	int SYSTEM_CAUSE_MT = 3;
}
