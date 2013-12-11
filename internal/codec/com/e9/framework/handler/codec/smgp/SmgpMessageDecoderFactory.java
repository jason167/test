package com.e9.framework.handler.codec.smgp;

import com.e9.framework.channel.i.Message;
import com.e9.framework.handler.codec.smgp.message.SmgpActiveTest;
import com.e9.framework.handler.codec.smgp.message.SmgpActiveTestResp;
import com.e9.framework.handler.codec.smgp.message.SmgpDeliver;
import com.e9.framework.handler.codec.smgp.message.SmgpDeliverResp;
import com.e9.framework.handler.codec.smgp.message.SmgpExit;
import com.e9.framework.handler.codec.smgp.message.SmgpExitResp;
import com.e9.framework.handler.codec.smgp.message.SmgpLogin;
import com.e9.framework.handler.codec.smgp.message.SmgpLoginResp;
import com.e9.framework.handler.codec.smgp.message.SmgpSubmit;
import com.e9.framework.handler.codec.smgp.message.SmgpSubmitResp;
import com.e9.framework.handler.codec.smgp.property.SmgpCommandId;

/**
 * 解码器工厂
 * @project E9Framework
 * @date 2013-1-14
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-14] create by Jason
 */
public class SmgpMessageDecoderFactory{

	public static Message createDecoder(int commandid) {
		// TODO Auto-generated method stub
		
		switch (commandid) {
		
			case SmgpCommandId.LOGIN:
				return new SmgpLogin();
				
			case SmgpCommandId.LOGIN_RESP:
				return new SmgpLoginResp();
				
			case SmgpCommandId.SUBMIT:
				return new SmgpSubmit();
				
			case SmgpCommandId.SUBMIT_RESP:
				return new SmgpSubmitResp();
				
			case SmgpCommandId.DELIVER:
				return new SmgpDeliver();
				
			case SmgpCommandId.DELIVER_RESP:
				return new SmgpDeliverResp();
				
			case SmgpCommandId.ACTIVE_TEST:
				return new SmgpActiveTest();
				
			case SmgpCommandId.ACTIVE_TEST_RESP:
				return new SmgpActiveTestResp();
				
			case SmgpCommandId.EXIT:
				return new SmgpExit();
				
			case SmgpCommandId.EXIT_RESP:
				return new SmgpExitResp();
				
			default:
				return null;
			
			}
		
	}


}
