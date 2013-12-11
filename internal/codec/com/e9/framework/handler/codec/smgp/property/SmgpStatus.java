package com.e9.framework.handler.codec.smgp.property;

/**
 * SMGP协议的结果码,见《中国电信CTMP开发接口－中国电信SMGP协议》6.2.6章节
 * @project 33e9
 * @date 2012-7-25
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-25] create by zengweizhi
 */
public interface SmgpStatus {
	/**
	 * 0:成功
	 */
	int SUCCESS = 0;
	
	/**
	 * 1:系统忙
	 */
	int SYSTEM_BUSY = 1;
	
	/**
	 * 2:超过最大连接数
	 */
	int MAX_CONNECTION_EXCEEDED = 2;
	
	/**
	 * 3~9:保留
	 */
	int STATUS_3 = 3;
	int STATUS_4 = 4;
	int STATUS_5 = 5;
	int STATUS_6 = 6;
	int STATUS_7 = 7;
	int STATUS_8 = 8;
	int STATUS_9 = 9;
	
	/**
	 * 10:消息结构错
	 */
	int INVALID_MESSAGE = 10;
	
	/**
	 * 11:命令字错
	 */
	int INVALID_REQUEST_ID = 11;
	
	/**
	 * 12:序列号重复
	 */
	int SEQUENCE_REPEATED = 12;
	
	/**
	 * 13~19:保留
	 */
	int STATUS_13 = 13;
	int STATUS_14 = 14;
	int STATUS_15 = 15;
	int STATUS_16 = 16;
	int STATUS_17 = 17;
	int STATUS_18 = 18;
	int STATUS_19 = 19;
	
	/**
	 * 20:IP地址错
	 */
	int INVALID_IP = 20;
	
	/**
	 * 21:认证错 
	 */
	int INVALID_AUTHENTICATOR = 21;
	
	/**
	 * 22:版本太高
	 */
	int INVALID_VERSION = 22;
	
	/**
	 * 23~29:保留
	 */
	int STATUS_23 = 23;
	int STATUS_24 = 24;
	int STATUS_25 = 25;
	int STATUS_26 = 26;
	int STATUS_27 = 27;
	int STATUS_28 = 28;
	int STATUS_29 = 29;
	
	/**
	 * 30:非法消息类型（MsgType）
	 */
	int INVALID_MSG_TYPE = 30;
	
	/**
	 * 31:非法优先级（Priority）
	 */
	int INVALID_PRIORITY = 31;
	
	/**
	 * 32:非法资费类型（FeeType）
	 */
	int INVALID_FEE_TYPE = 32;
	
	/**
	 * 33:非法资费代码（FeeCode）
	 */
	int INVALID_FEE_CODE = 33;
	
	/**
	 * 34:非法短消息格式（MsgFormat）
	 */
	int INVALID_MSG_FORMAT = 34;
	
	/**
	 * 35:非法时间格式
	 */
	int INVALID_TIME_FORMAT = 35;
	
	/**
	 * 36:非法短消息长度（MsgLength）
	 */
	int INVALID_MSG_LENGTH = 36;
	
	/**
	 * 37:有效期已过
	 */
	int VALIDITY_EXPIRED = 37;
	
	/**
	 * 38:非法查询类别（QueryType） 
	 */
	int INVALID_QUERY_TYPE = 38;
	
	/**
	 * 39:路由错误
	 */
	int ROUTE_ERROR = 39;
	
	/**
	 * 40:非法包月费/封顶费（FixedFee）
	 */
	int INVALID_FIXED_FEE = 40;
	
	/**
	 * 41:非法更新类型（UpdateType）
	 */
	int INVALID_UPDATE_TYPE = 41;
	
	/**
	 * 42:非法路由编号（RouteId）
	 */
	int INVALID_ROUTE_ID = 42;
	
	/**
	 * 43:非法服务代码（ServiceId）
	 */
	int INVALID_SERVICE_ID = 43;
	
	/**
	 * 44:非法有效期（ValidTime）
	 */
	int INVALID_VALID_TIME = 44;
	
	/**
	 * 45:非法定时发送时间（AtTime）
	 */
	int INVALID_AT_TIME = 45;
	
	/**
	 * 46:非法发送用户号码（SrcTermId）
	 */
	int INVALID_SRC_TERM_ID = 46;
	
	/**
	 * 47:非法接收用户号码（DestTermId）
	 */
	int INVALID_DEST_TERM_ID = 47;
	
	/**
	 * 48:非法计费用户号码（ChargeTermId）
	 */
	int INVALID_CHARGE_TERM_ID = 48;
	
	/**
	 * 49:非法SP服务代码（SPCode）
	 */
	int INVALID_SP_CODE = 49;
	
	/**
	 * 50~55:其它用途
	 */
	int STATUS_50 = 50;
	int STATUS_51 = 51;
	int STATUS_52 = 52;
	int STATUS_53 = 53;
	int STATUS_54 = 54;
	int STATUS_55 = 55;
	
	/**
	 * 56:非法源网关代码（SrcGatewayID）
	 */
	int INVALID_SRC_GATEWAY_ID = 56;
	
	/**
	 * 57:非法查询号码（QueryTermID）
	 */
	int INVALID_QUERY_TERM_ID = 57;
	
	/**
	 * 58:没有匹配路由
	 */
	int ROUTE_NOT_FOUND = 58;
	
	/**
	 * 59:非法SP类型（SPType）
	 */
	int INVALID_SP_TYPE = 59;
	
	/**
	 * 60:非法上一条路由编号（LastRouteID）
	 */
	int INVALID_LAST_ROUTE_ID = 60;
	
	/**
	 * 61:非法路由类型（RouteType）
	 */
	int INVALID_ROUTE_TYPE = 61;
	
	/**
	 * 62:非法目标网关代码（DestGatewayID）
	 */
	int INVALID_DEST_GATEWAY_ID = 62;
	
	/**
	 * 63:非法目标网关IP（DestGatewayIP）
	 */
	int INVALID_DEST_GATEWAY_IP = 63;
	
	/**
	 * 64:非法目标网关端口（DestGatewayPort）
	 */
	int INVALID_DEST_GATEWAY_PORT = 64;
	
	/**
	 * 65:非法路由号码段（TermRangeID）
	 */
	int INVALID_TERM_RANGE_ID = 65;
	
	/**
	 * 66:非法终端所属省代码（ProvinceCode）
	 */
	int INVALID_PROVINCE_CODE = 66;
	
	/**
	 * 67:非法用户类型（UserType）
	 */
	int INVALID_USER_TYPE = 67;
	
	/**
	 * 68:本节点不支持路由更新
	 */
	int NODE_DOES_NOT_SUPPORT_ROUTE_UPDATING = 68;
	
	/**
	 * 69:非法SP企业代码（SPID）
	 */
	int INVALID_SP_ID = 69;
	
	/**
	 * 70:非法SP接入类型（SPAccessType）
	 */
	int INVALID_SP_ACCESS_TYPE = 70;
	
	/**
	 * 71:路由信息更新失败
	 */
	int ROUTE_UPDATING_FAILURE = 71;
	
	/**
	 * 72:非法时间戳（Time）
	 */
	int INVALID_TIME = 72;
	
	/**
	 * 73:非法业务代码（MServiceID）
	 */
	int INVALID_M_SERVICE_ID = 73;
	
	/**
	 * 74:SP禁止下发时段
	 */
	int SP_DELIVER_FORBIDDEN = 74;
	
	/**
	 * 75:SP发送超过日流量
	 */
	int SP_QUOTAS_EXCEEDED = 75;
	
	/**
	 * 76:SP帐号过有效期
	 */
	int SP_ACCOUNT_VALID_TIME_EXPIRED = 76;
	
	/**
	 * 101:无此帐号或帐号被禁用 
	 */
	int INVALID_ACCOUNT = 101;
	
	/**
	 * 102:密码错
	 */
	int INVALID_PASSWORD = 102;
	
	/**
	 * 103:提交过快
	 */
	int SUBMIT_TOO_QUICK = 103;
	
	/**
	 * 104:系统忙
	 */
	int SYSTEM_BUSY_104 = 104;
	
	/**
	 * 105:非法发送号码
	 */
	int INVALID_SRC_NUMBER = 105;
	
	/**
	 * 106:短信内容包含敏感词
	 */
	int DENIABLE_WORD_FOUND = 106;
	
	/**
	 * 107:短信内容长度非法
	 */
	int INVALID_SMS_LENGTH = 107;
	
	/**
	 * 108:手机数非法
	 */
	int INVALID_PHONE_COUNT = 108;
	
	/**
	 * 109:保留
	 */
	int STATUS_109 = 109;
	
	/**
	 * 110:非法登录IP
	 */
	int INVALID_LOGIN_IP = 110;
	
	/**
	 * 111:保留;
	 * 112:保留;
	 * 113:保留;
	 */
	int INVALID_111 = 111;
	int INVALID_112 = 112;
	int INVALID_113 = 113;
	
	/**
	 * 114:无SMGP连接权限
	 */
	int SMGP_PERMISSION_DENY = 114;
	
	/**
	 * 115:免费短信量用完
	 */
	int OUT_OF_FREE_QUOTAS = 115;
}
