package com.e9.framework.channel.i;


/**
 * @project E9Framework
 * @date 2012-12-19
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-19] create by Jason
 */
public interface Message {
	
	public Integer getCommandId();
	public void setCommandId(Integer commandid);
	
	public Integer getSequenceId();
	public void setSequenceId(Integer sequenceId);
	
	public boolean isSyncMessage();
	public void setSyncMessage(boolean sync);
	
	public void encode(IoBuffer ioBuffer) throws Exception;
	
	public void decode(IoBuffer ioBuffer) throws Exception;
	
	// body and head ?

}
