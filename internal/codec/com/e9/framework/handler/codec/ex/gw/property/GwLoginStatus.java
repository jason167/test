package com.e9.framework.handler.codec.ex.gw.property;

/**
 * 登陆状态，见《短信通道协议开发包V3.06(内部使用).doc》，3.2.5.1章节
 * @date 2013-8-28
 * @author Jason
 */
public enum GwLoginStatus {
	
	LOGIN_SUCCESSFUL_AGIN(0x00, "登录成功(如之前已登录(从同一IP地址)，重复登录直接返回登录成功)"),
	SUCCESSFUL(0x01, "登录成功"),
	SYSTEM_ERROR(0x02, "系统内部错误或者无效的客户状号"),
	USERID_AND_PASSWORD_IS_ERROR(0x12, "账号或密码错误 或者该状号已经禁止使用"),
	USER_AUTH_ERROR(0x14, "客户权限不对"),
	INVALID_IP(0x15, "不是从指定的IP处登录"),
	USER_ALREADY_LOGIN(0x16, "状号已经登录"),
	INVALID_PACKET(0x88, "数据包参数格式不对"),
	INVALID_COMMAND(0xff, "错误的请求(如指令、格式等错误)");
	
	int errorCode;
	String txt;
	
	private GwLoginStatus(int errorCode, String txt){
		this.errorCode = errorCode;
		this.txt = txt;
	}
	
	public static GwLoginStatus getTxt(int errorCode){
		GwLoginStatus[] results = values();
		for (GwLoginStatus gwLoginResult : results) {
			if (gwLoginResult.errorCode == errorCode) {
				return gwLoginResult;
			}
		}
		return null;
	}
}
