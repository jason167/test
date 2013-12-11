package com.e9.framework.handler.codec.cmpp.property;

/**
 * CMPP消息字段长度常量
 * @project 33e9
 * @date 2012-7-24
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-24] create by zengweizhi
 */
public interface CmppLength {
	/**
	 * 无符号Byte型数据的长度
	 */
	int INT1 = 1;
	
	/**
	 * 无符号Short型数据的长度
	 */
	int INT2 = 2;
	
	/**
	 * Integer型数据的长度
	 */
	int INT4 = 4;
	
	/**
	 * 数据包长度
	 */
	int PACKET_LENGTH = INT4;
	
	/**
	 * 请求标识
	 */
	int REQUEST_ID = INT4;
	
	/**
	 * 消息流水号
	 */
	int SEQUENCE_ID = INT4;
	
	/**
	 * 消息头长度:
	 * PacketLength(int4) + 
	 * RequestId(int4) +
	 * SequenceId(int4)
	 */
	int CMPP_HEADER = PACKET_LENGTH + REQUEST_ID + SEQUENCE_ID ;
	
	/**
	 * 源地址，此处为SP_Id，即SP的企业代码(客户端用来登录服务器端的用户账号)。
	 */
	int SOURCE_ADDR = 6;
	
	/**
	 * 用于鉴别源地址。
	 */
	int AUTHENTICATORSOURCE = 16;
	
	/**
	 * 版本号(请求时为客户端的版本号，应答时为服务器支持的最高版本号）。
	 */
	int VERSION = INT1;
	
	/**
	 * 时间戳
	 */
	int TIMESTAMP = INT4;
	
	/**
	 * 请求返回结果
	 */
	int STATUS = INT1;
	
	/**
	 * ISMG认证码，用于鉴别ISMG。
	 */
	int AUTHENTICATORISMG = 16;

	/**
	 * 信息标识
	 */
	int MSG_ID = 8;
	
	/**
	 * 相同Msg_Id的信息总条数，从1开始
	 */
	int PK_TOTAL = INT1;
	
	/**
	 * 相同Msg_Id的信息序号，从1开始
	 */
	int PK_NUMBER = INT1;
	
	/**
	 * 是否要求返回状态确认报告
	 */
	int Registered_Delivery = INT1;
	
	/**
	 * 信息级别
	 */
	int MSG_LEVEL = INT1;
	
	/**
	 * 业务类型
	 */
	int SERVICE_ID = 10;
	
	/**
	 * 计费用户类型字段
	 */
	int FEE_USERTYPE = INT1;
	
	/**
	 * 被计费用户的号码
	 */
	int FEE_TERMINAL_ID = 21;
	
	/**
	 * 
	 */
	int TP_PID = INT1;
	
	/**
	 * 
	 */
	int TP_UDHI = INT1;
	
	/**
	 * 信息格式
	 */
	int MSG_FMT = INT1;
	
	/**
	 * 信息内容来源(SP_Id)
	 */
	int Msg_src = 6;
	
	
	/**
	 * 资费类别
	 */
	int FEE_TYPE = 2;
	
	/**
	 * 资费代码
	 */
	int FEE_CODE = 6;
	
	/**
	 * 存活有效期
	 */
	int VALID_TIME = 17;
	
	/**
	 * 定时发送时间
	 */
	int AT_TIME = 17;
	
	/**
	 * 源号码
	 */
	int SRC_ID = 21;
	
	/**
	 * 接收信息的用户数量
	 */
	int DESTUSR_TL = INT1;
	
	/**
	 * 接收短信的MSISDN号码
	 */
	int Dest_terminal_Id = 21;
	
	/**
	 * 信息长度
	 */
	int MSG_LENGTH = INT1;
	
	/**
	 * 保留
	 */
	int RESERVE = 8;
	
	/**
	 * 结果
	 */
	int RESULT = INT1;
	
	/**
	 * 目的号码
	 */
	int DEST_ID = 21;
	
	/**
	 * 源终端MSISDN号码
	 */
	int SRC_TERMINAL_ID = 21;
	
	/**
	 * 发送短信的应答结果
	 */
	int STAT = 7;
	
	/**
	 * Submit_time
	 */
	int SUBMIT_TIME = 10;
	
	/**
	 * Done_time
	 */
	int DONE_TIME = 10;
	
	/**
	 * 取自SMSC发送状态报告的消息体中的消息标识
	 */
	int SMSC_sequence = INT4;
	
	/**
	 * 信息长度(Msg_Fmt值为0时：<160个字节；其它<140个字节)
	 */
	int MSG_CONTENT_ASCII_MAX_LENGTH = 160;
	int MSG_CONTENT_MAX_LENGTH = 140;
	
}
