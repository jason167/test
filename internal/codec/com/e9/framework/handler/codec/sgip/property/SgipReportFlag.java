package com.e9.framework.handler.codec.sgip.property;

/**
 * 状态报告标记, 见SGIP V1.21 4.2.3.3.1 SUBMIT章节
 * <pre>
	0-该条消息只有最后出错时要返回状态报告
	1-该条消息无论最后是否成功都要返回状态报告
	2-该条消息不需要返回状态报告
	3-该条消息仅携带包月计费信息，不下发给用户，要返回状态报告
	其它-保留
	缺省设置为0
 * </pre>
 * @date 2013-8-23
 * @author Jason
 */
public interface SgipReportFlag {

	/**
	 * 该条消息只有最后出错时要返回状态报告
	 */
	int TRUE_IF_ONLY_ERROR = 0;
	
	/**
	 * 该条消息无论最后是否成功都要返回状态报告
	 */
	int TRUE = 1;
	
	/**
	 * 该条消息不需要返回状态报告
	 */
	int FALSE = 2;
	
	/**
	 * 该条消息仅携带包月计费信息，不下发给用户，要返回状态报告
	 */
	int TRUE_BUT_NO_MT = 3;
}
