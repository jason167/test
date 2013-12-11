package com.e9.framework.handler.codec.ex.gw.message;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.MsgFormat;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.handler.codec.ex.gw.property.GwStatus;
import com.e9.framework.util.Common;

/**
 * 状态报告，见《短信通道协议开发包V3.06(内部使用)》3.2.5.5章节
 * @date 2013-9-2
 * @author Jason
 */
public class GwReport extends AbstractGwMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -177435559230130655L;
	
	/**
	 * MT短消息ID 
	 */
	private String reqidID;
	
	/**
	 * 通道ID
	 */
	private String agentID = "";
	
	/**
	 * 接收手机号码
	 */
	private String fromPhone;
	
	/**
	 * 短信息接收状态
	 */
	private int status = GwStatus.SUCCESSFUL.ordinal();
	
	/**
	 * 状态描述
	 */
	private String sDescribe = "";
	
	/**
	 * 
	 */
	public GwReport() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.REPORT);
	}
	

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#decode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer arg0) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ChannelBuffer buffer = null;
		try {
			buffer = beginDecode(arg0);
			setReqidID(readOctetString(buffer, GwLength.REQ_ID));
			setAgentID(readOctetString(buffer, GwLength.AGENT_ID));
			setFromPhone(readOctetString(buffer, GwLength.FROM_PHONE));
			setStatus(readInt(buffer, GwLength.STATUS));
			setsDescribe(readOctetString(buffer, getPacketLength() - buffer.writerIndex(), MsgFormat.GBK));
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(buffer == null ? "" : Common.toHex(buffer.array()), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#encode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer arg0) throws Exception {
		// TODO Auto-generated method stub
		try {
			ChannelBuffer buffer = beginEncode(arg0);
			writeOctetString(buffer, getReqidID(), GwLength.REQ_ID);
			writeOctetString(buffer, getAgentID(), GwLength.AGENT_ID);
			writeOctetString(buffer, getFromPhone(), GwLength.FROM_PHONE);
			writeInt(buffer, getStatus(), GwLength.STATUS);
			int descLength = getsDescribe().getBytes(Charset.forName("GBK")).length;
			writeOctetString(buffer, getsDescribe(), descLength, MsgFormat.GBK);
			endEncode(buffer);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(this.toString(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.eap.codec.ex.gw.AbstractGwMessage#validateParameters()
	 */
	@Override
	protected boolean validateParameters() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @return the reqidID
	 */
	public String getReqidID() {
		return reqidID;
	}

	/**
	 * @param reqidID the reqidID to set
	 */
	public void setReqidID(String reqidID) {
		this.reqidID = reqidID;
	}

	/**
	 * @return the agentID
	 */
	public String getAgentID() {
		return agentID;
	}

	/**
	 * @param agentID the agentID to set
	 */
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}

	/**
	 * @return the fromPhone
	 */
	public String getFromPhone() {
		return fromPhone;
	}

	/**
	 * @param fromPhone the fromPhone to set
	 */
	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the sDescribe
	 */
	public String getsDescribe() {
		return sDescribe;
	}

	/**
	 * @param sDescribe the sDescribe to set
	 */
	public void setsDescribe(String sDescribe) {
		this.sDescribe = sDescribe;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "GwReport [reqidID=" + reqidID + ", agentID=" + agentID + ", fromPhone=" + fromPhone
				+ ", status=0x" + Integer.toHexString(status) + ", sDescribe=" + sDescribe + "]";
	}
	
	

}
