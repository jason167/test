package com.e9.framework.handler.codec.smgp.property;

/**
 * 请求标识常量,见《中国电信CTMP开发接口－中国电信SMGP协议》6.1.2章节
 * @project 33e9
 * @date 2012-7-24
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-24] create by zengweizhi
 */
public interface SmgpCommandId {
	// 客户端登录
	int LOGIN = 0x00000001;
	int LOGIN_RESP = 0x80000001;
	
	// 提交短信息
	int SUBMIT = 0x00000002;
	int SUBMIT_RESP = 0x80000002;
	
	// 下发短消息
	int DELIVER = 0x00000003;
	int DELIVER_RESP = 0x80000003;
	
	// 链路检测
	int ACTIVE_TEST = 0x00000004;
	int ACTIVE_TEST_RESP = 0x80000004;
	
	// 退出
	int EXIT = 0x00000006;
	int EXIT_RESP = 0x80000006;
	
	// 请求标识与响应标识之间的差
	int DIFFERENCE = 0x80000000;
}

