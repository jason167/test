package com.e9.framework.handler.codec.smgp.property;

/**
 * SMGP消息字段长度常量
 * @project 33e9
 * @date 2012-7-24
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-24] create by zengweizhi
 */
public interface SmgpLength {
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
	 * TLV可选参数 - 字段标记
	 */
	int TLV_TAG = INT2;
	
	/**
	 * TLV可选参数 - 值长度
	 */
	int TLV_LENGTH = INT2;
	
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
	int SMGP_HEADER = PACKET_LENGTH + REQUEST_ID + SEQUENCE_ID ;
	
	/**
	 * 客户端用来登录服务器端的用户账号。
	 */
	int CLIENT_ID = 8;
	
	/**
	 * 客户端认证码，用来鉴别客户端的合法性。
	 */
	int AUTHENTICATOR_CLIENT = 16;
	
	/**
	 * 客户端用来登录服务器端的登录类型。
	 */
	int LOGIN_MODE = INT1;
	
	/**
	 * 时间戳
	 */
	int TIMESTAMP = INT4;
	
	/**
	 * 客户端支持的协议版本号
	 */
	int CLIENT_VERSION = INT1;
	
	/**
	 * 请求返回结果
	 */
	int STATUS = INT4;
	
	/**
	 * 客户端用来登录服务器端的登录类型。
	 */
	int AUTHENTICATOR_SERVER = 16;
	
	/**
	 * 服务器端支持的最高版本号
	 */
	int SERVER_VERSION = INT1;
	
	/**
	 * 短消息类型
	 */
	int MSG_TYPE = INT1;
	
	/**
	 * SP是否要求返回状态报告
	 */
	int NEED_REPORT = INT1;
	
	/**
	 * 短消息发送优先级
	 */
	int PRIORITY = INT1;
	
	/**
	 * 业务代码
	 */
	int SERVICE_ID = 10;
	
	/**
	 * 收费类型
	 */
	int FEE_TYPE = 2;
	
	/**
	 * 资费代码
	 */
	int FEE_CODE = 6;
	
	/**
	 * 包月费/封顶费
	 */
	int FIXED_FEE = 6;
	
	/**
	 * 短消息格式
	 */
	int MSG_FORMAT = INT1;
	
	/**
	 * 短消息有效时间
	 */
	int VALID_TIME = 17;
	
	/**
	 * 短消息定时发送时间
	 */
	int AT_TIME = 17;
	
	/**
	 * 号码
	 */
	int TERM_ID = 21;
	
	/**
	 * 短信息发送方号码
	 */
	int SRC_TERM_ID = TERM_ID;
	
	/**
	 * 计费用户号码
	 */
	int CHARGE_TERM_ID = TERM_ID;
	
	/**
	 * 短消息接收号码总数
	 */
	int DEST_TERM_ID_COUNT = INT1;
	
	/**
	 * 短消息接收号码
	 */
	int DEST_TERM_ID = TERM_ID;
	
	/**
	 * 短消息长度
	 */
	int MSG_LENGTH = INT1;
	
	/**
	 * 最大长度
	 */
	int MSG_CONTENT_MAX_LENGTH = 140;
	
	/**
	 * 保留
	 */
	int RESERVE = 8;
	
	/**
	 * 短消息流水号
	 */
	int MSG_ID = 10;
	
	/**
	 * 是否为状态报告
	 */
	int IS_REPORT = INT1;
	
	/**
	 * 短消息接收时间
	 */
	int RECV_TIME = 14;
	
	// ========== TLV 定长字段长度 ========== 
	int TLV_V_TP_PID = INT1;
	int TLV_V_TP_UDHI = INT1;
	int TLV_V_LINK_ID = 20;
	int TLV_V_CHARGE_USER_TYPE = INT1;
	int TLV_V_CHARGE_TERM_TYPE = INT1;
	int TLV_V_DEST_TERM_TYPE = INT1;
	int TLV_V_PK_TOTAL = INT1;
	int TLV_V_PK_NUMBER = INT1;
	int TLV_V_SUBMIT_MSG_TYPE = INT1;
	int TLV_V_SP_DEAL_RESULT = INT1;
	int TLV_V_SRC_TERM_TYPE = INT1;
	int TLV_V_NODES_COUNT = INT1;
	int TLV_V_MSG_SRC = 8;
	int TLV_V_SRC_TYPE = INT1;
	int TLV_V_M_SERVICE_ID = 21;
	
	// ========= 状态报告 content 内容 =============
	int sub = 3;
	int Dlvrd = 3;
	int Submit_date = 10;
	int done_date = 10;
	int Stat = 7;
	int Err = 3;
	int Txt = 20;
}
