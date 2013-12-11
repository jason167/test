package test.com.e9.launcher.string.adapter;


import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.MessageType;
import com.e9.framework.channel.i.ServiceAdapter;
import com.e9.framework.channel.i.Session;

/**
 * @project E9Framework
 * @date 2013-4-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-20] create by Jason
 */
public class StringServiceAdapter implements ServiceAdapter {

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.ServiceAdapter#execute(com.e9.tcp.framework.Message, com.e9.tcp.framework.Session, com.e9.tcp.gateway.utils.MessageType)
	 */
	@Override
	public Message execute(Message message, Session session, MessageType messageType) {
		// TODO Auto-generated method stub
		if (message == null) {
			return null;
		}
		System.out.println("StringServiceAdapter.message:"+message.toString());
		return null;
	}

}
