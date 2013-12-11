package com.e9.framework.util;


/**
 * @date 2013-7-19
 * @author Jason
 */
public class Common {

	
	public static String toHex(byte[] buffer){
		if (buffer == null || buffer.length <= 0) {
			return "";
		}
		StringBuilder sf = new StringBuilder();
		for (int i = 0; i < buffer.length; i++) {
			String x = Integer.toHexString(buffer[i] & 0xff);
			if (x.length() == 1) {
				sf.append("0").append(x);
			}else{
				sf.append(x);
			}
		}
		
		return sf.toString();
	}
	
	public static byte[] hex2Bytes(String hexString){
		 int length = hexString.length();
	        byte[] buffer = new byte[length/2];
	        int index = 0;
	        for (int i=0; i<length;) {
	            buffer[index++] = (byte)Integer.parseInt(hexString.substring(i, i += 2), 16);
	        }
	        return buffer;
	}
	
	
}
