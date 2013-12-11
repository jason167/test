package com.e9.framework.channel.i;


/**
 * @project E9Framework
 * @date 2012-12-27
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-27] create by Jason
 */
public class MessageCoreController implements IMessageCoreController{
	
	private ServiceAdapter serviceAdapter;
	/**
	 * 
	 */
	public MessageCoreController(ServiceAdapter serviceAdapter) {
		// TODO Auto-generated constructor stub
		this.serviceAdapter = serviceAdapter;
	}
	
	
	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.IMessageCoreController#process(com.e9.framework.channel.i.Session, com.e9.framework.channel.i.Message)
	 */
	@Override
	public Message process(Session session, Message message) {
		// TODO Auto-generated method stub
		return process(session, message, MessageType.standard);
	}



	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.IMessageCoreController#process(com.e9.framework.channel.i.Session, com.e9.framework.channel.i.Message, com.e9.framework.handler.codec.util.MessageType)
	 */
	@Override
	public Message process(Session session, Message message, MessageType messageType) {
		// TODO Auto-generated method stub
		return serviceAdapter.execute(message, session, messageType);
	}



}
