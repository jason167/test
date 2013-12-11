package com.e9.framework.handler.codec.cmpp.property;

/**
 * SubmitResp.Result,见CMPP v2.0 7.4.3.2章节
 * @project E9Framework
 * @date 2013-1-24
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-24] create by Jason
 */
public interface CmppResult {

	/*
	 结果
	0：正确
	1：消息结构错
	2：命令字错
	3：消息序号重复
	4：消息长度错
	5：资费代码错
	6：超过最大信息长
	7：业务代码错
	8：流量控制错
	9~ ：其他错误
	 */
		
		/**
		 * 0:成功
		 */
		int SUCCESS = 0;
		
		/**
		 * 1:消息结构错
		 */
		int INVALID_MESSAGE = 1;
		
		/**
		 * 2:命令字错
		 */
		int INVALID_COMMAND_ID = 2;

		/**
		 * 3:序列号重复
		 */
		int SEQUENCE_REPEATED = 3;
		
		/**
		 * 4:短消息长度错误（MsgLength）
		 */
		int INVALID_MSG_LENGTH = 4;
		
		/**
		 * 5:非法资费代码（FeeCode）
		 */
		int INVALID_FEE_CODE = 5;
		
		/**
		 * 6:超过最大信息长
		 */
		int INVALID_SMS_LENGTH = 6;
		
		/**
		 * 7:业务代码错（ServiceId）
		 */
		int INVALID_SERVICE_ID = 7;
		
		/**
		 * 8:流量控制错
		 */
		int SP_QUOTAS_EXCEEDED = 8;
		
	     /**
         * 9~ ：其他错误
         */
        int INVALID_OTHER = 9;
			
}
