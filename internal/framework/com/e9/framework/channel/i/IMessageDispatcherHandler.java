package com.e9.framework.channel.i;

import com.e9.framework.channel.AbstractChannelHandler;

/**
 * 消息分发控制器
 * @date 2013-10-11
 * @author Jason
 */
public interface IMessageDispatcherHandler {

	public void action(AbstractChannelHandler channelHandler, Session session, Message message)throws Exception;
}
