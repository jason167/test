package com.e9.constant;

/**
 * @project OSP
 * @date 2013-3-5
 * @version 1.0
 * @company 33e9
 * @author Jason
 * 
 * @review_history
 * [2013-3-5] create by Jason
 */
public class Constants {
	
	/**
	 * 应答时间戳
	 */
	public static long submitResp_ttl = 0l;
	
	/**
	 * 用于共享缓存的前缀标识
	 */
	public static String FM = "FreezeMoney";
	public static String SMS = "SMS";
	public static String AUDIT = "AuditSms";
	public static String SMALL_BATCH_SMS = "SmallBatchSms";
	public static String SMALL_BATCH_COUNTER = "SmallBatchCounter";
	
	/**
	 * 短信有效期，单位：秒
	 */
	public static int VALID_TIME = 30 * 60;

}
