package com.e9.framework.channel.i;


/**
 * 消息控制、调度
 * @project E9Framework
 * @date 2013-5-7
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-5-7] create by Jason
 */
public interface IMessageCoreController {

	public Message process(Session session, Message message);
	public Message process(Session session, Message message, MessageType messageType);
}
