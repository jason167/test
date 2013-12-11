package com.e9.framework.handler.codec.cmpp.property;

/**
 * Command_Id定义,见CMPP v2.0 7.7.1章节
 * @project E9Framework
 * @date 2013-1-24
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-24] create by Jason
 */
public interface CmppCommandId {
	
	// 请求连接与应答
	int CONNECT = 0x00000001;
	int CONNECT_RESP = 0x80000001;
	
	// 终止连接与应答
	int TERMINATE = 0x00000002;
	int TERMINATE_RESP = 0x80000002;
	
	// 提交短信与应答
	int SUBMIT = 0x00000004;
	int SUBMIT_RESP = 0x80000004;
	
	// 短信下发与应答
	int DELIVER = 0x00000005;
	int DELIVER_RESP = 0x80000005;
	
	// 激活测试与应答
	int ACTIVE_TEST = 0x00000008;
	int ACTIVE_TEST_RESP = 0x80000008;
	
	// 请求标识与响应标识之间的差
	int DIFFERENCE = 0x80000000;
}

