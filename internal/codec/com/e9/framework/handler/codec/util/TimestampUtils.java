package com.e9.framework.handler.codec.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间戳工具
 * @date 2013-8-27
 * @author Jason
 */
public class TimestampUtils {
	
	private static ThreadLocal<DateFormat> SDF_MMDDHHMMSS_Thread = new ThreadLocal<DateFormat>(){
		protected DateFormat initialValue() {
			return new SimpleDateFormat("MMddHHmmss", Locale.CHINA);
		};
	};
	
	private static ThreadLocal<DateFormat> SDF_MMDDHHMM_Thread = new ThreadLocal<DateFormat>(){
		protected DateFormat initialValue() {
			return new SimpleDateFormat("MMddHHmm", Locale.CHINA);
		};
	};
	
	/**
	 * 获取《中国电信CTMP开发接口－中国电信SMGP协议》6.2.4章节规定的整型时间戳
	 * @param timestamp
	 * @return
	 */
	public static Integer getIntTimestamp(Date timestamp){
		if(null == timestamp){
			return null;
		}
		return Integer.parseInt(SDF_MMDDHHMMSS_Thread.get().format(timestamp));
	}
	
	/**
	 * 获取《中国电信CTMP开发接口－中国电信SMGP协议》6.2.4章节规定的字符串时间戳
	 * @param timestamp
	 * @return MMddHHmmss
	 */
	public static String getStringTimestamp(Date timestamp){
		if(null == timestamp){
			return null;
		}
		return SDF_MMDDHHMMSS_Thread.get().format(timestamp);
	}
	
	/**
	 * 
	 * @param timestamp
	 * @return MMddHHmm
	 */
	public static String getStringTimestampForSmgpMsgid(Date timestamp){
		if(null == timestamp){
			return null;
		}
		return SDF_MMDDHHMM_Thread.get().format(timestamp);
	}
	
	/**
	 * 根据整型时间戳获取字符串时间戳
	 * @param timestamp
	 * @return
	 */
	public static String getStringTimestamp(int timestamp){
		if(timestamp < 1000000000){
			return "0" + timestamp;
		} else {
			return "" + timestamp;
		}
	}
}
