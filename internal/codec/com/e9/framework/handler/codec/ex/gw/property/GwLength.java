package com.e9.framework.handler.codec.ex.gw.property;

/**
 * 见《短信通道协议开发包V3.06(内部使用).doc》
 * 字段名称来自文档，为便于配合文档阅读，字段名称直接拷贝而来。
 * 部分字段语义较难理解，请参考中文说明。
 * @date 2013-8-28
 * @author Jason
 */
public interface GwLength {

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
	int REQUEST_ID = INT1;
	
	/**
	 * 用户账号
	 */
	int USER_ID = 8;
	
	/**
	 * 消息流水号
	 */
	int SEQUENCE_ID = 10;
	
	/**
	 * 消息头长度:
	 * PacketLength(int4) + 
	 * RequestId(int1) +
	 * user_id(8) +
	 * SequenceId(10)
	 */
	int GW_HEADER = PACKET_LENGTH + REQUEST_ID  + USER_ID + SEQUENCE_ID ;
	
	/**
	 * 客户端密码
	 */
	int PASSWORD = 8;
	
	/**
	 * 在本次会话中使用加密算法(默认为空串)
	 */
	int CMETHOD = 8;
	
	/**
	 * 1支持MT, 2支持MT和MO,3 支持缓存方式MO(默认为2)
	 */
	int TYPE = 1;
	
	/**
	 * (默认为空串)
	 */
	int AGENT_LIST = 90;
	int STATUS = 1;
	
	/**
	 * 本次会话使用的密码
	 */
	int CPASS = 16;
	
	/**
	 * 消息通道路径ID
	 */
	int AGENT_ID = 6;
	
	/**
	 * 短消息发送者手机号码
	 */
	int FROM_PHONE = 21;
	
	/**
	 * 接收短信的手机号码
	 */
	int TO_PHONE = 21;
	
	/**
	 * 0对目的终端计费,1对源终端计费,2对SP计费, 3 对指定的号码计费。默认：2
	 */
	int FEE_USER = 1;
	
	/**
	 * 付费手机号码
	 */
	int FEE_PHONE = 21;
	
	/**
	 * 业务代码,默认：SURGESMS
	 */
	int SERVICE_ID = 10;
	
	/**
	 * 收费类型：默认02
	 */
	int FEE_TYPE = 6;
	
	/**
	 * 资费，以分为单位
	 */
	int FEE_VALUE = 4;
	
	/**
	 * 消息类型, 1文本, 2图片, 3铃声, 4二进制
	 */
	int MESSAGE_TYPE = 1;
	
	/**
	 * 对应的MO点播ID
	 */
	int MO_ID = 10;
	
	/**
	 * 消息长度
	 */
	int MESSAGE_LENGTH = 1;
	
	/**
	 * 签名长度
	 */
	int SIGN_LENGTH = 4;
	
	/**
	 * 请求ID, send_resp命令的返回值，实际应该是标准协议中的msgid吧？
	 */
	int REQ_ID = 10;
	
	/**
	 * 接收人个数（设置50个）
	 */
	int I_TARGET = 1;
	
	/**
	 * 发送时间
	 */
	int SUBMIT_TIME = 14;
	
	/**
	 * 收到时间
	 */
	int RECEIVE_TIME = 14;
	
	/**
	 * 3.2.5.6 Fee(费用查询). 起始日期(从这天的 零时开始)
	 */
	int FROM_DATE = 8;
	
	/**
	 * 3.2.5.6 Fee(费用查询). 截止日期(到这天的23:59:59秒止)
	 */
	int END_DATE = 8;
	
	/**
	 * 费用余额
	 */
	int BALANCE = 10; 
	
	/**
	 * 个种状态的记录数（暂时不提供）
	 */
	int NUM_REC = 4;
	
	/**
	 * 状态1代码（暂时不提供）
	 */
	int CODE_1 = 2;
	
	/**
	 * 状态1记录数（暂时不提供）
	 */
	int NUM_CODE = 10;
	
	/**
	 * 可用的SMSC数目
	 */
	int NUM_SMSC = 4;
	
	/**
	 * SMSC/Agetn ID (length = 6 * n)
	 */
	int CHANNEL_LIST = 6;
	
	/**
	 * 发送速度，单位：条/秒
	 */
	int SPEED = 4;
	
	/**
	 * 原来密码
	 */
	int OLD_PASS = 8;
	
	/**
	 * 新的密码
	 */
	int NEW_PASS = 8;
	
	/**
	 * 产品编号
	 */
	int PRODUCTID = 10;
	
	/**
	 * 短信下行费用
	 */
	int MT_FEE = 6;
	
	int MSG_CONTENT_MAX_LENGTH = 120;
}
