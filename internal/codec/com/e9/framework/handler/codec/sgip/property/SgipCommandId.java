package com.e9.framework.handler.codec.sgip.property;

/**
 * 消息ID定义，见SGIP 1.21版5.1章节
 * @date 2013-8-23
 * @author Jason
 */
public interface SgipCommandId {

	int BIND = 0x1;
	int BIND_RESP = 0x80000001;
	
	int UNBIND = 0x2;
	int UNBIND_RESP = 0x80000002;
	
	int SUBMIT = 0x3;
	int SUBMIT_RESP = 0x80000003;
	
	int DELIVER = 0x4;
	int DELIVER_RESP = 0x80000004;
	
	int REPORT = 0x5;
	int REPORT_RESP = 0x80000005;
	
	int QUERYROUTE = 0x9;
	int QUERYROUTE_RESP = 0x80000009;
	
	int TRACE = 0x1000;
	int TRACE_RESP = 0x80001000;
}
