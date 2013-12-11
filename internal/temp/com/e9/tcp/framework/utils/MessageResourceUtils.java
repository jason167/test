package com.e9.tcp.framework.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * 资源工具
 * @project E9Framework
 * @date 2012-7-27
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-27] create by zengweizhi
 */
public final class MessageResourceUtils {
	private static ResourceBundle rb = null;
	
	/**
	 * 初始化ResourceBundle
	 * @param baseName
	 * @param locale
	 */
	public static void init(String baseName){
		try{
			rb = ResourceBundle.getBundle(baseName);
		} catch(Exception ex){
			rb = null;
		}
	}
	
	/**
	 * 初始化ResourceBundle
	 * @param baseName
	 * @param locale
	 */
	public static void init(String baseName,Locale locale){
		try{
			rb = ResourceBundle.getBundle(baseName,locale);
		} catch(Exception ex){
			rb = null;
		}
	}
	
	/**
	 * 获取格式化后的文本
	 * @param key
	 * @param param
	 * @return
	 */
	public static String getMessage(String key,Object ... arguments){
		try{
			String pattern = rb.getString(key);
			return MessageFormat.format(pattern, arguments);
		} catch(Exception ex){
			return null;
		}
	}
	
	/**
	 * <p>
	 * Gets a string for the given key from this resource bundle or one of its parents.
     * Calling this method is equivalent to calling
     * <blockquote>
     * <code>(String) {@link #getObject(java.lang.String) getObject}(key)</code>.
     * </blockquote>
	 * </p>
	 * @param key the key for the desired string
	 * @return the string for the given key
	 */
	public static String getMessage(String key){
		try{
			return rb.getString(key);
		} catch(Exception ex){
			return null;
		}
	}
	
	/**
	 * Returns the locale of this resource bundle. This method can be used after
	 * a call to getBundle() to determine whether the resource bundle returned
	 * really corresponds to the requested locale or is a fallback.
	 * 
	 * @return the locale of this resource bundle
	 */
	public static Locale getLocale() {
		try {
			return rb.getLocale();
		} catch (Exception ex) {
			return null;
		}
	}
}

