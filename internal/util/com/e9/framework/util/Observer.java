package com.e9.framework.util;


/**
 * @project E9Framework
 * @date 2013-1-23
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-23] create by Jason
 */
public interface Observer {
	
	
	void update(Subject subject, Object arg );
	
	Integer getId();

}
