package com.e9.framework.cache.util;

/**
 * <p>Message queue 的优先级</p>
 * <p>该类可用于Message queue在某容器内的排序条件</p>
 * <p>0为最高优先级，9为最低优先级</p>
 * @date 2013-8-1
 * @author Jason
 */
public enum MessageQueuePriority {
	
	ZERO(0, "ZERO"),
	ONE(1, "ONE"),
	TWO(2, "TWO"),
	THREE(3, "THREE"),
	FOUR(4, "FOUR"),
	FIVE(5, "FIVE"),
	SIX(6, "SIX"),
	SEVEN(7, "SEVEN"),
	EIGHT(8, "EIGHT"),
	NINE(9, "NINE");
	
	int code;
	String ch;
	
	/**
	 * 
	 */
	private MessageQueuePriority(int code, String ch) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.ch = ch;
	}
	
	public static MessageQueuePriority getPriority(int code){
		MessageQueuePriority[] priorities = values();
		for (MessageQueuePriority priority : priorities) {
			if (priority.code == code) {
				return priority;
			}
		}
		return null;
	}

}
