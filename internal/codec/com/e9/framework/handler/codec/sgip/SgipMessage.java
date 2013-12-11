package com.e9.framework.handler.codec.sgip;

import com.e9.framework.channel.i.Message;

/**
 * <pre>
 * sgip消息接口
 * 重新定义Message接口，是由于sgip协议sequenceid的特殊，
 * 其他sms标准协议的sequenceid都是4个字节，而他是12个字节
 * 根据sgip sequenceid的生成方式，现将sequenceid拆分为三个部分：
 * 1、node code  4bit
 * 2、int date	 4bit
 * 3、sequence	 4bit
 * </pre>
 * @date 2013-8-24
 * @author Jason
 */
public interface SgipMessage extends Message {

	public void setHexSequenceNumber(String hexSequenceNumber);
	public String getHexSequenceNumber();
	
	public void setNodeCode(Integer nodeCode);
	public Integer getNodeCode();
	
	public void setIntDate(Integer intDate);
	public Integer getIntDate();
	
}
