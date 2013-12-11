package com.e9.framework.handler.codec.ex;

import com.e9.framework.channel.i.Message;

/**
 * GW协议同SMS标准协议的报头不同，这里重新定义报头不同之处，供子类实现。
 * @date 2013-8-29
 * @author Jason
 */
public interface GwMessage extends Message {

	public String getSequenceString();
	public void setSequenceString(String sequence);
	
	public String getUid();
	public void setUid(String uid);
}
