package com.e9.framework.handler.codec.ex.gw.property;

/**
 * 短消息结果描述，见《短信通道协议开发包V3.06(内部使用).doc》
 * @date 2013-8-28
 * @author Jason
 */
public interface GwResultDetail {

	String E00000 = "成功";
	String E00001 = "错误的目标地址";
	String E00002 = "消息内容不能为空";
	String E00003 = "内容含有非法信息";
	String E00004 = "内容有汉字,但消息内容长度错误";
	String E00005 = "内容无汉字,但消息内容长度错误";
	String E00006 = "非协议手机用户";
	String E00007 = "该接收手机没有对应网关通道";
	String E00008 = "内容含有通道不允许的非法信息";
	String E00009 = "客户通道没有设定或者该客户的通道已经停止";
	
}
