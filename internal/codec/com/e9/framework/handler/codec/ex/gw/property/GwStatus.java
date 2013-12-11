package com.e9.framework.handler.codec.ex.gw.property;

/**
 * 除登陆状态以外的应答状态表，见《短信通道协议开发包V3.06(内部使用).doc》
 * @date 2013-8-28
 * @author Jason
 */
public enum GwStatus {

	SUCCESSFUL(0x01, "成功"),
	SEND_SUCCESSFUL(0x03, "发送成功"),
	SEND_FAILED(0x04, "发送失败"),
	CHANNEL_DISCONNECT(0X32, "失败－Agent 断线"),
	SMSC_DISCONNECT(0x33, "失败－SMSC 断线"),
	NO_MONEY(0x36, "费用不足"),
	NO_CHANNEL(0x38, "无可用的MT通道"),
	GATEWAY_CLOSED(0x34, "失败－Agent 与 SMSC 之间断线"),
	SUBMIT_FAILED(0x35, "其它原因而提交失败"),
	NO_MT_AUTH(0x37, "无 MT 权限"),
	MODIFY_PASSWORD_FAILED(0x71, "修改密码失败"),
	EXEC_PROC_ERROR(0x72, "执行过程有错误"),
	INVALID_COMMAND(0xff, "错误的指令或格式");
	
	int errorCode;
	String txt;
	
	/**
	 * 
	 */
	private GwStatus(int errorCode, String txt) {
		// TODO Auto-generated constructor stub
		this.errorCode = errorCode;
		this.txt = txt;
	}
	
	public static GwStatus getTxt(int errorCode){
		GwStatus[] results = values();
		for (GwStatus gwResult : results) {
			if (gwResult.errorCode == errorCode) {
				return gwResult;
			}
		}
		return null;
	}
}
