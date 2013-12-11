package com.e9.framework.handler.codec.sgip.property;

/**
 * 登陆类型，见SGIP 1.21版 4.2.3.1.1章节
 * <pre>
	1：SP向SMG建立的连接，用于发送命令
	2：SMG向SP建立的连接，用于发送命令
	3：SMG之间建立的连接，用于转发命令
	4：SMG向GNS建立的连接，用于路由表的检索和维护
	5：GNS向SMG建立的连接，用于路由表的更新
	6：主备GNS之间建立的连接，用于主备路由表的一致性
	11：SP与SMG以及SMG之间建立的测试连接，用于跟踪测试
	其它：保留
 * </per>
 * @date 2013-8-23
 * @author Jason
 */
public interface SgipLoginType {

	/**
	 * SP向SMG建立的连接，用于发送命令
	 */
	int SP_2_SMG = 1;
	
	/**
	 * SMG向SP建立的连接，用于发送命令
	 */
	int SMG_2_SP = 2;
	
	/**
	 * SMG之间建立的连接，用于转发命令
	 */
	int SMG_2_SMG = 3;
	
	/**
	 * SMG向GNS建立的连接，用于路由表的检索和维护
	 */
	int SMG_2_GNS = 4;
	
	/**
	 * GNS向SMG建立的连接，用于路由表的更新
	 */
	int GNS_2_SMG = 5;
	
	/**
	 * 主备GNS之间建立的连接，用于主备路由表的一致性
	 */
	int AGNS_2_BGNS = 6;
	
	/**
	 * SP与SMG以及SMG之间建立的测试连接，用于跟踪测试
	 */
	int TEST = 11;
}
