package com.e9.framework.channel.i;

/**
 * @project E9Framework
 * @date 2012-12-19
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-19] create by Jason
 */
public interface Client {
	
	public Session connect();
	
	public void close();
	
	public boolean login(Session session);
	
	public Session getSession();

}
