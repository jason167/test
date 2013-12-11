package com.e9.framework.handler.codec.sgip.property;

/**
 * Report命令类型, 见SGIP V1.21 4.2.3.5.1 REPORT章节
 * <pre>
	0：对先前一条Submit命令的状态报告
	1：对先前一条前转Deliver命令的状态报告
 * </pre>
 * @date 2013-8-23
 * @author Jason
 */
public interface SgipReportType {

	/**
	 * 对先前一条Submit命令的状态报告
	 */
	int PRE_FIRST_SUBMIT = 0;
	
	/**
	 * 对先前一条前转Deliver命令的状态报告
	 */
	int PRE_FIRST_DELIVER = 1;
}
