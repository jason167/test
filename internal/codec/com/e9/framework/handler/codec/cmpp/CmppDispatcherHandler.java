package com.e9.framework.handler.codec.cmpp;

import com.e9.framework.channel.AbstractChannelHandler;
import com.e9.framework.channel.i.IMessageDispatcherHandler;
import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.util.InnerRunnable;
import com.e9.framework.handler.codec.cmpp.property.CmppCommandId;

/**
 * @date 2013-10-11
 * @author Jason
 */
public class CmppDispatcherHandler implements IMessageDispatcherHandler {

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.IMessageDispatcherHandler#action(com.e9.framework.channel.AbstractChannelHandler, com.e9.framework.channel.i.Session, com.e9.framework.channel.i.Message)
	 */
	@Override
	public void action(AbstractChannelHandler channelHandler, Session session, Message message)
			throws Exception {
		// TODO Auto-generated method stub
		Integer commandId = message.getCommandId();
		if (commandId == CmppCommandId.CONNECT || commandId == CmppCommandId.CONNECT_RESP ||
				commandId == CmppCommandId.TERMINATE || commandId == CmppCommandId.TERMINATE_RESP) {
			channelHandler.getLoginExecutor().execute(new InnerRunnable(session, message, channelHandler.getListener()));
			
		}else if(commandId == CmppCommandId.ACTIVE_TEST || commandId == CmppCommandId.ACTIVE_TEST_RESP){
			channelHandler.getAliveTestExecutor().execute(new InnerRunnable(session, message, channelHandler.getListener()));
			
		}else if(commandId == CmppCommandId.DELIVER || commandId == CmppCommandId.DELIVER_RESP){
			channelHandler.getMoExecutor().execute(new InnerRunnable(session, message, channelHandler.getListener()));
			
		}else{
			channelHandler.getMtExecutor().execute(new InnerRunnable(session, message, channelHandler.getListener()));
		}
	}

}
