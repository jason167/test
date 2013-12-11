package com.e9.framework.handler.codec.smgp.property;

/**
 * 可选参数标签定义,见《中国电信CTMP开发接口－中国电信SMGP协议》6.3.1章节
 * @project 33e9
 * @date 2012-7-25
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-25] create by zengweizhi
 */
public interface SmgpTag {
	int TP_PID = 0x0001;
	
	int TP_UDHI = 0x0002;
	
	int LINK_ID = 0x0003;
	
	int CHARGE_USER_TYPE = 0x0004;
	
	int CHARGE_TERM_TYPE = 0x0005;
	
	int CHARGE_TERM_PSEUDO = 0x0006;
	
	int DEST_TERM_TYPE = 0x0007;
	
	int DEST_TERM_PSEUDO = 0x0008;
	
	int PK_TOTAL = 0x0009;
	
	int PK_NUMBER = 0x000A;
	
	int SUBMIT_MSG_TYPE = 0x000B;

	int SP_DEAL_RESULT = 0x000C;
	
	int SRC_TERM_TYPE = 0x000D;
	
	int SRC_TERM_PSEUDO = 0x000E;
	
	int NODES_COUNT = 0x000F;
	
	int MSG_SRC = 0x0010;
	
	int SRC_TYPE = 0x0011;
	
	int M_SERVICE_ID = 0x0012;
}
