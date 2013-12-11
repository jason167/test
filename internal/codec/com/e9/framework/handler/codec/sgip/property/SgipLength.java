package com.e9.framework.handler.codec.sgip.property;

/**
 * SGIP消息字段长度常量
 * @date 2013-8-23
 * @author Jason
 */
public interface SgipLength {

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
	int SEQUENCE_ID = 12;
	
	/**
	 * 消息头长度:
	 * PacketLength(int4) + 
	 * RequestId(int4) +
	 * SequenceId(12)
	 */
	int SGIP_HEADER = PACKET_LENGTH + REQUEST_ID + SEQUENCE_ID ;
	
	/**
	 * 登陆类型
	 */
	int LOGIN_TYPE = INT1;
	int LOGIN_NAME = 16;
	int LOGIN_PASSWORD = 16;
	int RESERVE = 8;
	int RESULT = INT1;
	
	/**
	 * SP的接入号码
	 */
	int SP_NUMBER = 21;
	
	/**
	 * 付费号码
	 */
	int CHARGE_NUMBER = 21;
	
	/**
	 * 接收短消息的手机数量
	 */
	int USER_COUNT =  INT1;
	
	/**
	 * 接收该短消息的手机号
	 */
	int USER_NUMBER = 21;
	
	/**
	 * 企业代码，取值范围0-99999
	 */
	int CORP_ID = 5;
	
	/**
	 * 业务代码，由SP定义
	 */
	int SERVICE_TYPE = 10;
	
	/**
	 * 计费类型
	 */
	int FEE_TYPE = INT1;
	
	/**
	 * 该条短消息的收费值，单位为分
	 */
	int FEE_VALUE = 6;
	
	/**
	 * 赠送用户的话费，单位为分
	 */
	int GIVEN_VALUE = 6;
	
	/**
	 * 代收费标志，0：应收；1：实收
	 */
	int AGENT_FLAG = INT1;
	
	/**
	 * 引起MT消息的原因
	 */
	int MORELATETO_MT_FLAG = INT1;
	
	/**
	 * 优先级0-9从低到高，默认为0
	 */
	int PRIORITY = INT1;
	
	/**
	 * 短消息寿命的终止时间
	 */
	int EXPIRE_TIME = 16;
	
	/**
	 * 短消息定时发送的时间
	 */
	int SCHEDULE_TIME = 16;
	
	/**
	 * 状态报告标记
	 */
	int REPORT_FLAG = INT1;
	
	int TP_PID = INT1;
	int TP_UDHI = INT1;
	
	/**
	 * 短消息的编码格式
	 */
	int MESSAGE_CODING = INT1;
	
	/**
	 * 信息类型
	 */
	int MESSAGE_TYPE = INT1;
	
	/**
	 * 短消息的长度
	 */
	int MESSAGE_LENGTH = INT4;
	
	/**
	 * Report命令类型
	 */
	int REPORT_TYPE = INT1;
	
	/**
	 * 该命令所涉及的短消息的当前执行状态
	 */
	int STATE = INT1;
	
	/**
	 * 当State=2时为错误码值，否则为0
	 */
	int ERROR_CODE = INT1;
	
	/**
	 * 短信内容最大长度
	 */
	int MSG_CONTENT_MAX_LENGTH = 160;
	
	/**
	 * 请求类型
	 */
	int QUERY_TYPE = INT1;
	
	/**
	 * <pre>
		请求类型为
		     0-忽略
		     1和3-SP接入号码
		　　2-手机号码段
		　　4-SMG节点编号
		左对齐，剩余部分填’\0’
	 * </pre>
	 */
	int Number = 21;
	
	/**
	 * 业务代码，该字段为空时不考虑业务代码，请求类型为0、1、2时，该字段无效。
	 */
	int SERVICE_TAG = 10;
	
	
}
