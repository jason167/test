package com.e9.framework.channel.server;

import org.jboss.netty.channel.Channel;

import com.e9.framework.channel.DefaultSession;

/**
 * @project E9Framework
 * @date 2012-12-21
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-21] create by Jason
 */
public class ServerSession extends DefaultSession {

	/**
	 * @param channel
	 */
	public ServerSession(Channel channel) {
		super(channel);
		// TODO Auto-generated constructor stub
	}

}
