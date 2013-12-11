package com.e9.framework.handler.codec.string;

import com.e9.framework.channel.i.Message;
import com.e9.framework.handler.codec.string.message.Hello;
import com.e9.framework.handler.codec.string.property.StringCommandId;

/**
 * @project E9Framework
 * @date 2013-4-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-20] create by Jason
 */
public class StringMessageDecoderFactory {
	
	public static Message createDecoder(int commandid){
		switch (commandid) {
		case StringCommandId.HELLO:
			return new Hello();
		default:
			break;
		}
		
		return null;
	}

}
