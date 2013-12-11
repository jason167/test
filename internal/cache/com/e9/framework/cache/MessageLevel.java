package com.e9.framework.cache;

/**
 * Message Level;
 * @project E9Framework
 * @date 2013-1-23
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-23] create by Jason
 */
public enum MessageLevel {

	ZERO(0, 0),
	ONE(1, 1),
	TWO(2, 2),
	THREE(3, 3);
	
	private int level;
	private int value;
	
	public static MessageLevel getLevel(int levelCode){
		MessageLevel[] levels = values();
		for (MessageLevel level : levels) {
			
			if (level.getLevel() == levelCode) {
				return level;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 */
	private MessageLevel(int level, int value) {
		// TODO Auto-generated constructor stub
		this.level = level;
		this.value = value;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * 取得当前枚举最大value
	 * @return
	 */
	public static int getMaxValue(){
		return THREE.value + 1;
	}
	
	
}
