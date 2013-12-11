package com.e9.framework.handler.codec.ex.gw.property;

/**
 * 指令常熟定义，见《短信通道协议开发包V3.06(内部使用).doc》3.2.2章节
 * @date 2013-8-28
 * @author Jason
 */
public interface GwCommandId {

	/**
	 * ISP系统登录短信网关
	 */
	public int LOGIN = 0x01;
	public int LOGIN_RESP = 0x81;
	
	/**
	 * ISP系统从短信网关退出
	 */
	public int EXIT = 0x02;
	public int EXIT_RESP = 0x82;
	
	/**
	 * Server和ISP系统之间检查连接状态
	 */
	public int ALIVETEST = 0x03;
	public int ALIVETEST_RESP = 0x83;
	
	/**
	 * ISP系统请求短信网关发送信息
	 */
	public int SUBMIT = 0x04;
	public int SUBMIT_RESP = 0x84;
	
	/**
	 * 短信网关将收到的MO信息发送给ISP系统
	 */
	public int DELIVER = 0x05;
	public int DELIVER_RESP = 0x85;
	
	/**
	 * 短信网关将收到的Report信息发送给ISP系统
	 */
	public int REPORT = 0x14;
	public int REPORT_RESP = 0X94;
	
	/**
	 * ISP系统向短信网关查询话费记录清单
	 */
	public int QUERY_FEE = 0x08;
	public int QUERY_FEE_RESP = 0x88;
	
	/**
	 * ISP系统向Server查询当前可用的通道 
	 */
	public int QUERY_CHANNEL_LIST = 0x09;
	public int QUERY_CHANNEL_LIST_RESP = 0x89;
	
	/**
	 * 修改密码
	 */
	public int MODIFY_PASSWORD = 0x10;
	public int MODIFY_PASSWORD_RESP = 0x90;
	
	/**
	 * 群发短信
	 */
	public int SUBMIT_GROUP = 0x11;
	public int SUBMIT_GROUP_RESP = 0x91;
	
	/**
	 * 速度控制
	 */
	public int SPEED_CTRL = 0x19;
	public int SPEED_CTRL_RESP = 0x99;
	
	
	
}
