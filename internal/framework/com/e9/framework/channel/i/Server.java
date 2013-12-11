package com.e9.framework.channel.i;

import java.net.SocketAddress;

/**
 * @project E9Framework
 * @date 2012-12-19
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-19] create by Jason
 */
public interface Server {
	
	public boolean bind();
	
	public boolean bind(SocketAddress address);
	
	public void close();
}
