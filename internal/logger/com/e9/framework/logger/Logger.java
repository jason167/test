package com.e9.framework.logger;

/**
 * 日志模版类
 * @project E9Framework
 * @date 2012-12-18
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-18] create by Jason
 */
public interface Logger {
	
	public void setLevel(LoggerLevel level);
	public LoggerLevel getLevel();
	
	public void debug(String s);
	public void debug(String s, Object arg);
	public void debug(String s, Object arg1, Object arg2);
	public void debug(String s, Object[] args);
	
	public void info(String s);
	public void info(String s, Object arg);
	public void info(String s, Object arg1, Object arg2);
	public void info(String s, Object[] args);
	
	public void error(String s);
	public void error(String s, Object arg);
	public void error(String s, Object arg1, Object arg2);
	public void error(String s, Object[] args);
	public void error(Throwable e);
	public void error(String s, Throwable e);
	
	public void close();

}
