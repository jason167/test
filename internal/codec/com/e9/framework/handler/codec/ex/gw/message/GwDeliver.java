package com.e9.framework.handler.codec.ex.gw.message;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.MsgFormat;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.handler.codec.ex.gw.property.GwMessageType;
import com.e9.framework.handler.codec.ex.gw.property.GwServiceID;
import com.e9.framework.util.Common;

/**
 * 上行命令，见《短信通道协议开发包V3.06(内部使用)》3.2.5.5章节
 * @date 2013-9-2
 * @author Jason
 */
public class GwDeliver extends AbstractGwMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9079341774016157827L;
	/**
	 * 
	 */
	public GwDeliver() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.DELIVER);
	}
	/**
	 * Agent或SMSC 
	 */
	private String agentID = "";
	
	/**
	 * MO记录ID
	 */
	private String moID = "";
	
	/**
	 * 发送时间
	 * yyyymmddhhmmss
	 */
	private String submitTime = "";
	
	/**
	 * 收到时间
	 * yyyymmddhhmmss
	 */
	private String receiveTime = "";
	
	/**
	 * 源电话
	 */
	private String fromPhone;
	
	/**
	 * 目的电话
	 */
	private String toPhone;
	
	/**
	 * 业务类型
	 */
	private String serviceID = GwServiceID.id;
	private int messageType = GwMessageType.TXT;
	private int messageLength;
	private String messageContent = "";

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#decode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer arg0) throws Exception {
		// TODO Auto-generated method stub
		ChannelBuffer buffer = null;
		try {
			buffer = beginDecode(arg0);
			setAgentID(readOctetString(buffer, GwLength.AGENT_ID));
			setMoID(readOctetString(buffer, GwLength.MO_ID));
			setSubmitTime(readOctetString(buffer, GwLength.SUBMIT_TIME));
			setReceiveTime(readOctetString(buffer, GwLength.RECEIVE_TIME));
			setFromPhone(readOctetString(buffer, GwLength.FROM_PHONE));
			setToPhone(readOctetString(buffer, GwLength.TO_PHONE));
			setServiceID(readOctetString(buffer, GwLength.SERVICE_ID));
			setMessageType(readInt(buffer, GwLength.MESSAGE_TYPE));
			int messageLength = readInt(buffer, GwLength.MESSAGE_LENGTH);
			setMessageContent(readOctetString(buffer, messageLength, MsgFormat.GBK));
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
			writeOctetString(buffer, getAgentID(), GwLength.AGENT_ID);
			writeOctetString(buffer, getMoID(), GwLength.MO_ID);
			writeOctetString(buffer, getSubmitTime(), GwLength.SUBMIT_TIME);
			writeOctetString(buffer, getReceiveTime(), GwLength.RECEIVE_TIME);
			writeOctetString(buffer, getFromPhone(), GwLength.FROM_PHONE);
			writeOctetString(buffer, getToPhone(), GwLength.TO_PHONE);
			writeOctetString(buffer, getServiceID(), GwLength.SERVICE_ID);
			writeInt(buffer, getMessageType(), GwLength.MESSAGE_TYPE);
			writeInt(buffer, getMessageLength(), GwLength.MESSAGE_LENGTH);
			writeOctetString(buffer, getMessageContent(), getMessageLength(), MsgFormat.GBK);
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
		boolean valid = false;
		
		// 必选参数检查
		valid = 
			null != fromPhone && 
			null != toPhone
		;
		
		return valid;
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
	 * @return the moID
	 */
	public String getMoID() {
		return moID;
	}

	/**
	 * @param moID the moID to set
	 */
	public void setMoID(String moID) {
		this.moID = moID;
	}

	/**
	 * @return the submitTime
	 */
	public String getSubmitTime() {
		return submitTime;
	}

	/**
	 * @param submitTime the submitTime to set
	 */
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	/**
	 * @return the receiveTime
	 */
	public String getReceiveTime() {
		return receiveTime;
	}

	/**
	 * @param receiveTime the receiveTime to set
	 */
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
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
	 * @return the toPhone
	 */
	public String getToPhone() {
		return toPhone;
	}

	/**
	 * @param toPhone the toPhone to set
	 */
	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}

	/**
	 * @return the serviceID
	 */
	public String getServiceID() {
		return serviceID;
	}

	/**
	 * @param serviceID the serviceID to set
	 */
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	/**
	 * @return the messageType
	 */
	public int getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the messageLength
	 */
	public int getMessageLength() {
		return messageLength;
	}

	/**
	 * @param messageLength the messageLength to set
	 */
	private void setMessageLength(int messageLength) {
		this.messageLength = messageLength;
	}

	/**
	 * @return the messageContent
	 */
	public String getMessageContent() {
		return messageContent;
	}

	/**
	 * @param messageContent the messageContent to set
	 */
	public void setMessageContent(String messageContent) {
		if (messageContent != null && !messageContent.equals("")) {
			setMessageLength(messageContent.getBytes(Charset.forName("GBK")).length);
			this.messageContent = messageContent;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "GwDeliver [agentID=" + agentID + ", moID=" + moID + ", submitTime=" + submitTime
				+ ", receiveTime=" + receiveTime + ", fromPhone=" + fromPhone + ", toPhone="
				+ toPhone + ", serviceID=" + serviceID + ", messageType=" + messageType
				+ ", messageLength=" + messageLength + ", messageContent=" + messageContent + "]";
	}

	
}
