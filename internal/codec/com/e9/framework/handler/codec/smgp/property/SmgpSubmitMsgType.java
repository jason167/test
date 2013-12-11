package com.e9.framework.handler.codec.smgp.property;

/**
 * SP发送的消息类型,见《中国电信CTMP开发接口－中国电信SMGP协议》6.3.12章节
 * @project 33e9
 * @date 2012-7-26
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-26] create by zengweizhi
 */
public interface SmgpSubmitMsgType {
	int NORMAL = 0;
	
	int WEB_SUBSCRIPTION_NOTIFICATION = 1;
	
	int WEB_CANCEL_SUBSCRIPTION_NOTIFICATION = 2;
	
	int TERMINAL_SUBSCRIPTION_NOTIFICATION = 3;
	
	int TERMINAL_CANCEL_SUBSCRIPTION_NOTIFICATION = 4;
	
	int MONTHLY_CHARGE_NOTIFICATION = 5;
}
