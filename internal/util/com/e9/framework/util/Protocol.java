package com.e9.framework.util;

/**
 * @project E9Framework
 * @date 2013-2-27
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-2-27] create by Jason
 */
public enum Protocol {
	
	CMPP,SGIP,SMGP;
	
	public static Protocol getProtocol(int protocol_ordinal){
		Protocol[] protocols = values();
		for (Protocol protocol : protocols) {
			if (protocol.ordinal() == protocol_ordinal) {
				
				return protocol;
			}
		}
		
		return null;
	}
	
}
