package com.e9.framework.handler.codec.ex.gw;

import com.e9.framework.channel.AbstractChannelHandler;
import com.e9.framework.channel.i.IMessageDispatcherHandler;
import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.util.InnerRunnable;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;

/**
 * @date 2013-10-11
 * @author Jason
 */
public class GwDispatcherHandler implements IMessageDispatcherHandler {

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.IMessageDispatcherHandler#action(com.e9.framework.channel.AbstractChannelHandler, com.e9.framework.channel.i.Session, com.e9.framework.channel.i.Message)
	 */
	@Override
	public void action(AbstractChannelHandler channelHandler, Session session, Message message)
			throws Exception {
		// TODO Auto-generated method stub
		Integer commandId = message.getCommandId();
		if (commandId == GwCommandId.SUBMIT || commandId == GwCommandId.SUBMIT_RESP ||
				commandId == GwCommandId.SUBMIT_GROUP || commandId == GwCommandId.SUBMIT_GROUP_RESP) {
			channelHandler.getMtExecutor().execute(new InnerRunnable(session, message, channelHandler.getListener()));
			
		}else if(commandId == GwCommandId.ALIVETEST || commandId == GwCommandId.ALIVETEST_RESP){
			channelHandler.getAliveTestExecutor().execute(new InnerRunnable(session, message, channelHandler.getListener()));
			
		}else if(commandId == GwCommandId.DELIVER || commandId == GwCommandId.DELIVER_RESP ||
				commandId == GwCommandId.REPORT || commandId == GwCommandId.REPORT_RESP){
			channelHandler.getMoExecutor().execute(new InnerRunnable(session, message, channelHandler.getListener()));
			
		}else{
			channelHandler.getLoginExecutor().execute(new InnerRunnable(session, message, channelHandler.getListener()));
			
		}
	}

}
