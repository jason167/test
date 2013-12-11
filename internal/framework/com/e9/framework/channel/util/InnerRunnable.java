package com.e9.framework.channel.util;

import com.e9.framework.channel.i.Listener;
import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.Session;


/**
 * @date 2013-8-3
 * @author Jason
 */
public final class InnerRunnable implements Runnable {

	Session session;
	Message message;
	Listener listener;
	/**
	 * 
	 */
	public InnerRunnable(Session session, Message message, Listener listener) {
		// TODO Auto-generated constructor stub
		this.session = session;
		this.message = message;
		this.listener = listener;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		listener.messageReceived(session, message);
	}

}
