package com.e9.framework.channel.i;


/**
 * 服务适配器
 * @project E9Framework
 * @date 2013-4-19
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-19] create by Jason
 */
public interface ServiceAdapter {

	public Message execute(Message message, Session session, MessageType messageType);
}
