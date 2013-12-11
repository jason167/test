package com.e9.framework.util.algorithm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @project E9Framework
 * @date 2013-1-14
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-14] create by Jason
 */
public class MD5 {
	
	private static MessageDigest messageDigest;
	static {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
		}
	}
	
	/**
	 * MD5摘要运算
	 * @param source 源
	 * @return 摘要
	 */
	public static byte[] encode(byte [] source){
		return messageDigest.digest(source);
	}
	

}
