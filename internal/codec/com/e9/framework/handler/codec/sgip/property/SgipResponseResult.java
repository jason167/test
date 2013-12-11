package com.e9.framework.handler.codec.sgip.property;

/**
 * 应答状态码，见SGIP V1.21 5.2章节
 * @date 2013-8-23
 * @author Jason
 */
public interface SgipResponseResult {

	/**
	 * 无错误，命令正确接收
	 */
	int SUCCESSFUL = 0;
	
	/**
	 * 非法登录，如登录名、口令出错、登录名与口令不符等
	 */
	int ILLEGAL_LOGIN = 1;
	
	/**
	 * 重复登录，如在同一TCP/IP连接中连续两次以上请求登录
	 */
	int LOGIN_AGAIN = 2;
	
	/**
	 * 连接过多，指单个节点要求同时建立的连接数过多。
	 */
	int LOGIN_NUMBER_LIMIT = 3;
	
	/**
	 * 登录类型错，指bind命令中的logintype字段出错
	 */
	int LOGIN_TYPE_ERROR = 4;
	
	/**
	 * 参数格式错，指命令中参数值与参数类型不符或与协议规定的范围不符
	 */
	int PARAMS_FORMAT_ERROR = 5;
	
	/**
	 * 非法手机号码，协议中所有手机号码字段出现非86130号码或手机号码前未加“86”时都应报错
	 */
	int ILLEGAL_MOBILE_PHONE = 6;
	
	/**
	 * 消息ID错
	 */
	int COMMANDID_ERROR = 7;
	
	/**
	 * 信息长度错
	 */
	int PACKET_LENGTH_ERROR = 8;
	
	/**
	 * 非法序列号，包括序列号重复、序列号格式错误等
	 */
	int ILLEGAL_SEQUENCE = 9;
	
	/**
	 * 非法操作GNS
	 */
	int ILLEGAL_OPERATOR_GNS = 10;
	
	/**
	 * 节点忙，指本节点存储队列满或其他原因，暂时不能提供服务的情况
	 */
	int NODE_BUSY = 11; 
	
}
