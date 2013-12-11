package com.e9.framework.logger;

/**
 * 日志工厂类
 * @project E9Framework
 * @date 2012-12-18
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-18] create by Jason
 */
public final class LoggerFactory {
	
	public static Logger getLogger(){
		return LoggerService.getInstance();
	}

}
