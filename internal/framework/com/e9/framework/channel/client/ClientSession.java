package com.e9.framework.channel.client;


import org.jboss.netty.channel.Channel;

import com.e9.framework.channel.DefaultSession;

/**
 * @project E9Framework
 * @date 2012-12-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-20] create by Jason
 */
public class ClientSession extends DefaultSession {

	/**
	 * @param channel
	 */
	public ClientSession(Channel channel) {
		super(channel);
		// TODO Auto-generated constructor stub
	}

}
