package com.e9.framework.logger;

/**
 * 日志记录级别
 * @project E9Framework
 * @date 2013-1-8
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-8] create by Jason
 */
public enum LoggerLevel {

	ALL("ALL",0),
	DEBUG("DEBUG",1),
	INFO("INFO",2),
	ERROR("ERROR",3);
	
	private String name;
	private int id;
	
	/**
	 * 
	 */
	private LoggerLevel(String name, int id) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
