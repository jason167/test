package com.e9.framework.handler.codec.ex.gw;

import com.e9.framework.channel.i.Message;
import com.e9.framework.handler.codec.ex.gw.message.GwAliveTest;
import com.e9.framework.handler.codec.ex.gw.message.GwAliveTestResp;
import com.e9.framework.handler.codec.ex.gw.message.GwDeliver;
import com.e9.framework.handler.codec.ex.gw.message.GwDeliverResp;
import com.e9.framework.handler.codec.ex.gw.message.GwExit;
import com.e9.framework.handler.codec.ex.gw.message.GwExitResp;
import com.e9.framework.handler.codec.ex.gw.message.GwLogin;
import com.e9.framework.handler.codec.ex.gw.message.GwLoginResp;
import com.e9.framework.handler.codec.ex.gw.message.GwModifyPassword;
import com.e9.framework.handler.codec.ex.gw.message.GwModifyPasswordResp;
import com.e9.framework.handler.codec.ex.gw.message.GwQueryChannelList;
import com.e9.framework.handler.codec.ex.gw.message.GwQueryChannelListResp;
import com.e9.framework.handler.codec.ex.gw.message.GwQueryFee;
import com.e9.framework.handler.codec.ex.gw.message.GwQueryFeeResp;
import com.e9.framework.handler.codec.ex.gw.message.GwReport;
import com.e9.framework.handler.codec.ex.gw.message.GwReportResp;
import com.e9.framework.handler.codec.ex.gw.message.GwSpeedCtrl;
import com.e9.framework.handler.codec.ex.gw.message.GwSpeedCtrlResp;
import com.e9.framework.handler.codec.ex.gw.message.GwSubmit;
import com.e9.framework.handler.codec.ex.gw.message.GwSubmitGroup;
import com.e9.framework.handler.codec.ex.gw.message.GwSubmitGroupResp;
import com.e9.framework.handler.codec.ex.gw.message.GwSubmitResp;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;

/**
 * @date 2013-9-10
 * @author Jason
 */
public class GwMessageDecoderFactory {

	public static Message createDecoder(int commandid) {
		switch (commandid) {
			case GwCommandId.SUBMIT:
				return new GwSubmit();
				
			case GwCommandId.SUBMIT_RESP:
				return new GwSubmitResp();
				
			case GwCommandId.SUBMIT_GROUP:
				return new GwSubmitGroup();
				
			case GwCommandId.SUBMIT_GROUP_RESP:
				return new GwSubmitGroupResp();
				
			case GwCommandId.DELIVER:
				return new GwDeliver();
				
			case GwCommandId.DELIVER_RESP:
				return new GwDeliverResp();
				
			case GwCommandId.REPORT:
				return new GwReport();
				
			case GwCommandId.REPORT_RESP:
				return new GwReportResp();
				
			case GwCommandId.ALIVETEST:
				
				return new GwAliveTest();
			case GwCommandId.ALIVETEST_RESP:
				return new GwAliveTestResp();
				
			case GwCommandId.EXIT:
				return new GwExit();
				
			case GwCommandId.EXIT_RESP:
				return new GwExitResp();
				
			case GwCommandId.LOGIN:
				return new GwLogin();
				
			case GwCommandId.LOGIN_RESP:
				return new GwLoginResp();
				
			case GwCommandId.MODIFY_PASSWORD:
				return new GwModifyPassword();
				
			case GwCommandId.MODIFY_PASSWORD_RESP:
				return new GwModifyPasswordResp();
				
			case GwCommandId.QUERY_CHANNEL_LIST:
				return new GwQueryChannelList();
				
			case GwCommandId.QUERY_CHANNEL_LIST_RESP:
				return new GwQueryChannelListResp();
				
			case GwCommandId.QUERY_FEE:
				return new GwQueryFee();
				
			case GwCommandId.QUERY_FEE_RESP:
				return new GwQueryFeeResp();
				
			case GwCommandId.SPEED_CTRL:
				return new GwSpeedCtrl();
				
			case GwCommandId.SPEED_CTRL_RESP:
				return new GwSpeedCtrlResp();
				
			default:
				return null;
			}
	}
}
