package com.e9.framework.cache.util;

/**
 * @date 2013-8-13
 * @author Jason
 */
public interface IQueueRunnable extends Runnable {
	
	public static final int PAUSED   = 0;
	public static final int NONE    = 1;
	public static final int IDLE    = 2;
	public static final int USING    = 3;
	public static final int UNUSED   = 4;
	
	public int getState();
	public abstract void doRun();

}
