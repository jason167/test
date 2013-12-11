package com.e9.framework.handler.codec.sgip;

import com.e9.framework.channel.i.Message;
import com.e9.framework.handler.codec.sgip.message.SgipBind;
import com.e9.framework.handler.codec.sgip.message.SgipBindResp;
import com.e9.framework.handler.codec.sgip.message.SgipDeliver;
import com.e9.framework.handler.codec.sgip.message.SgipDeliverResp;
import com.e9.framework.handler.codec.sgip.message.SgipReport;
import com.e9.framework.handler.codec.sgip.message.SgipReportResp;
import com.e9.framework.handler.codec.sgip.message.SgipSubmit;
import com.e9.framework.handler.codec.sgip.message.SgipSubmitResp;
import com.e9.framework.handler.codec.sgip.message.SgipUnbind;
import com.e9.framework.handler.codec.sgip.message.SgipUnbindResp;
import com.e9.framework.handler.codec.sgip.property.SgipCommandId;

/**
 * @date 2013-8-23
 * @author Jason
 */
public class SgipMessageDecoderFactory {

	public static Message createDecoder(int commandid) {
		switch (commandid) {
		case SgipCommandId.SUBMIT:
			return new SgipSubmit();
			
		case SgipCommandId.SUBMIT_RESP:
			return new SgipSubmitResp();
			
		case SgipCommandId.REPORT:
			return new SgipReport();
			
		case SgipCommandId.REPORT_RESP:
			return new SgipReportResp();
			
		case SgipCommandId.BIND:
			return new SgipBind();
			
		case SgipCommandId.BIND_RESP:
			return new SgipBindResp();
			
		case SgipCommandId.DELIVER:
			return new SgipDeliver();
			
		case SgipCommandId.DELIVER_RESP:
			return new SgipDeliverResp();
			
		case SgipCommandId.UNBIND:
			return new SgipUnbind();
			
		case SgipCommandId.UNBIND_RESP:
			return new SgipUnbindResp();
			
		default:
			break;
		}
		return null;
	}
}
