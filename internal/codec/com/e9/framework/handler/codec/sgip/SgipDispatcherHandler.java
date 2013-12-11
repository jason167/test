package com.e9.framework.handler.codec.sgip;

import com.e9.framework.channel.AbstractChannelHandler;
import com.e9.framework.channel.i.IMessageDispatcherHandler;
import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.Session;
import com.e9.framework.channel.util.InnerRunnable;
import com.e9.framework.handler.codec.sgip.property.SgipCommandId;

/**
 * @date 2013-10-11
 * @author Jason
 */
public class SgipDispatcherHandler implements IMessageDispatcherHandler {

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.IMessageDispatcherHandler#action(com.e9.framework.channel.AbstractChannelHandler, com.e9.framework.channel.i.Session, com.e9.framework.channel.i.Message)
	 */
	@Override
	public void action(AbstractChannelHandler channelHandler, Session session, Message message)
			throws Exception {
		// TODO Auto-generated method stub
		Integer commandId = message.getCommandId();
		if (commandId == SgipCommandId.BIND || commandId == SgipCommandId.BIND_RESP ||
				commandId == SgipCommandId.UNBIND || commandId == SgipCommandId.UNBIND_RESP) {
			channelHandler.getLoginExecutor().execute(new InnerRunnable(session, message, channelHandler.getListener()));
			
		}else if (commandId == SgipCommandId.TRACE || commandId == SgipCommandId.TRACE_RESP) {
			channelHandler.getAliveTestExecutor().execute(new InnerRunnable(session, message, channelHandler.getListener()));
			
		}else if (commandId == SgipCommandId.DELIVER || commandId == SgipCommandId.DELIVER_RESP ||
				commandId == SgipCommandId.REPORT || commandId == SgipCommandId.REPORT_RESP) {
			channelHandler.getMoExecutor().execute(new InnerRunnable(session, message, channelHandler.getListener()));
			
		}else{
			channelHandler.getMtExecutor().execute(new InnerRunnable(session, message, channelHandler.getListener()));
		}
	}

}
