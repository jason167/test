package com.e9.framework.handler.codec.sgip.property;

/**
 * 报告状态码，见SGIP V1.21 5.2章节
 * @date 2013-8-23
 * @author Jason
 */
public interface SgipReportResult {

	/**
	 * 发送成功
	 */
	int SUCCESSFUL = 0;
	
	/**
	 * 非法用户（HLR中不存在的用户）
	 */
	int ILLEGAL_USER = 1;
	
	/**
	 * 用户鉴权失败，临时错误（MT，打电话提示暂时不能接通）
	 */
	int LOGIN_AUTH_FAILED = 5;
	
	/**
	 * 不存在的被叫号码（MO失败，手机号或SP接入号输入错误）
	 */
	int USER_NUMBER_NO_FOUND = 9;
	
	/**
	 * 通信服务未提供(欠费停机)
	 */
	int NO_SERVICE = 11;
	
	/**
	 * 移动设备鉴权失败（手机与网络配合问题，较少）
	 */
	int MOBILE_DEVICE_AUTH_FAILED = 12;
	
	/**
	 * 呼叫受限
	 */
	int CALL_LIMIT = 13;
	
	/**
	 * 闭合用户
	 */
	int CLOSED_USER = 15;
	
	/**
	 * 目的地址不可达,漫游的网络不支持短消息服务
	 */
	int ROAMING_NETWORK_DOES_NOT_SUPPORT_SMS = 21;
	
	/**
	 * 路由错，指路由表存在路由但消息路由出错的情况，如转错SMG等
	 */
	int ROUT_ERROR = 22;
	
	/**
	 * 路由不存在，指消息路由的节点在路由表中不存在
	 */
	int ROUT_NO_FOUND = 23;
	
	/**
	 * 计费号码无效，鉴权不成功时反馈的错误信息
	 */
	int FEE_PHONE_INVALID = 24;
	
	/**
	 * 用户不能通信（如不在服务区、未开机等情况）
	 */
	int USER_CAN_NOT_CALL = 25;
	
	/**
	 * 手机内存不足
	 */
	int MOBILE_PHONES_NO_MEMORY = 26;
	
	/**
	 * 手机不支持短消息或者用户缺席(关机或不在服务区)
	 */
	int POWER_DOWN_OR_NOT_IN_SERVICE_AREA = 27;
	
	/**
	 * 手机接收短消息出现错误
	 */
	int MOBILE_PHONES_RECEIVE_SMS_ERROR = 28;
	
	/**
	 * 不知道的用户
	 */
	int UNKNOWN_USER = 29;
	
	/**
	 * 不提供此功能
	 */
	int NO_FUNCTION = 30;
	
	/**
	 * 非法设备或者用户忙
	 */
	int ILLEGAL_DEVICE_OR_USER_BUSY = 31;
	
	/**
	 * 系统失败
	 */
	int SYSTEM_FAILED = 32;
	
	/**
	 * HLR等待列表满（关机或不在服务区）
	 */
	int HLR_WAIT_QUEUE_IS_FULL = 33;
	
	/**
	 * 不可知错误
	 */
	int UNKNOWN_ERROR = 34;
	
	/**
	 * 数据缺失
	 */
	int DATA_MISS = 35;
	
	/**
	 * 非法的数据值
	 */
	int ILLEGAL_DATA_VALUE = 36;
	
	/**
	 * 用户手机存储空间满
	 */
	int MOBILE_PHONES_MEMORY_IS_FULL = 50;
	
	/**
	 * 设备协议错误（手机与基站的配合问题，随机出现）
	 */
	int DEVICE_PROTOCOL_ERROR = 51;
	
	/**
	 * 不可知的短消息中心（MO，短信中心号码设错）
	 */
	int UNKNOWN_SMSC = 53;
	
	/**
	 * 短信中心拥塞（或某SME拥塞）
	 */
	int SMSC_CONGESTION = 54;
	
	/**
	 * 非法的SME地址 
	 */
	int ILLEGAL_SME_ADDRESS = 55;
	
	/**
	 * 用户未在短信中心开户（MO失败）
	 */
	int USER_NO_REGISTER_ON_SMSC = 56;
	
	/**
	 * 短消息超长（MO、MT）
	 */
	int SMS_TOO_LONG = 60;
	
	/**
	 * SP费用不足
	 */
	int SP_NO_MONEY = 93;
	
	/**
	 * 网络超时无响应,原因:MT查询路由时超时或者MT下发超时
	 */
	int MT_TIME_OUT = 172;
	
	
}
