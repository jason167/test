package com.e9.framework.handler.codec.cmpp;

import com.e9.framework.channel.i.Message;
import com.e9.framework.handler.codec.cmpp.message.CmppActiveTest;
import com.e9.framework.handler.codec.cmpp.message.CmppActiveTestResp;
import com.e9.framework.handler.codec.cmpp.message.CmppConnect;
import com.e9.framework.handler.codec.cmpp.message.CmppConnectResp;
import com.e9.framework.handler.codec.cmpp.message.CmppDeliver;
import com.e9.framework.handler.codec.cmpp.message.CmppDeliverResp;
import com.e9.framework.handler.codec.cmpp.message.CmppSubmit;
import com.e9.framework.handler.codec.cmpp.message.CmppSubmitResp;
import com.e9.framework.handler.codec.cmpp.message.CmppTerminate;
import com.e9.framework.handler.codec.cmpp.message.CmppTerminateResp;
import com.e9.framework.handler.codec.cmpp.property.CmppCommandId;

/**
 * 解码器工厂
 * @project E9Framework
 * @date 2013-1-25
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-25] create by Jason
 */
public class CmppMessageDecoderFactory{

	public static Message createDecoder(int commandid) {
		// TODO Auto-generated method stub
		
		switch (commandid) {
		
			case CmppCommandId.CONNECT:
				return new CmppConnect();
				
			case CmppCommandId.CONNECT_RESP:
				return new CmppConnectResp();
				
			case CmppCommandId.SUBMIT:
				return new CmppSubmit();
				
			case CmppCommandId.SUBMIT_RESP:
				return new CmppSubmitResp();
				
			case CmppCommandId.DELIVER:
				return new CmppDeliver();
				
			case CmppCommandId.DELIVER_RESP:
				return new CmppDeliverResp();
				
			case CmppCommandId.ACTIVE_TEST:
				return new CmppActiveTest();
				
			case CmppCommandId.ACTIVE_TEST_RESP:
				return new CmppActiveTestResp();
				
			case CmppCommandId.TERMINATE:
				return new CmppTerminate();
				
			case CmppCommandId.TERMINATE_RESP:
				return new CmppTerminateResp();
				
			default:
				return null;
			
			}
		
	}


}
