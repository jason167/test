package com.e9.framework.handler.codec.ex.gw.message;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.MsgFormat;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.handler.codec.ex.gw.property.GwFeeType;
import com.e9.framework.handler.codec.ex.gw.property.GwFeeUser;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.handler.codec.ex.gw.property.GwMessageType;
import com.e9.framework.handler.codec.ex.gw.property.GwServiceID;
import com.e9.framework.util.Common;

/**
 * 短信单发命令， 见《短信通道协议开发包V3.06(内部使用)》3.2.5.3章节
 * @date 2013-9-2
 * @author Jason
 */
public class GwSubmit extends AbstractGwMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5223798972452196654L;
	
	/**
	 * 消息通道路径ID, 
	 */
	private String agentID = "";
	
	/**
	 * 短消息发送者手机号码
	 */
	private String fromPhone;
	
	/**
	 * 接收短信的手机号码
	 */
	private String toPhone;
	
	/**
	0对目的终端计费,1对源终端计费,2对SP计费, 3 对指定的号码计费
	默认：2
	 */
	private int feeUser = GwFeeUser.BY_RECEIVER;
	
	/**
	 * 付费手机号码
	 */
	private String feePhone = "";
	
	/**
	 * 业务代码,默认：SURGESMS
	 */
	private String serviceID = GwServiceID.id;
	
	/**
	01：对“计费用户号码”免费
	02：对“计费用户号码”按条计信息费
	03：对“计费用户号码”按包月收取信息费
	 */
	private String feeType = GwFeeType.type;
	
	/**
	 * 资费，以分为单位
	 */
	private int feeValue = 0;
	
	/**
	 * 消息类型, 1文本, 2图片, 3铃声, 4二进制
	 */
	private int messageType = GwMessageType.TXT;
	
	/**
	 * 对应的MO点播ID
	 */
	private String moID = "";
	
	/**
	 * 消息长度
	 */
	private int messageLen;
	
	/**
	 * 消息内容,最大长度为60个字符
	 */
	private String message;
	
	/**
	 * 签名长度
	 */
	private int signLen;
	
	/**
	 * 签名
	 */
	private String sign = "";
	/**
	 * 
	 */
	public GwSubmit() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.SUBMIT);
	}

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
			setFromPhone(readOctetString(buffer, GwLength.FROM_PHONE));
			setToPhone(readOctetString(buffer, GwLength.TO_PHONE));
//			setFeeUser(readInt(buffer, GwLength.FEE_USER));
			buffer.skipBytes(GwLength.FEE_USER);
			setFeePhone(readOctetString(buffer, GwLength.FEE_PHONE));
			setServiceID(readOctetString(buffer, GwLength.SERVICE_ID));
//			setFeeType(readOctetString(buffer, GwLength.FEE_TYPE));
//			setFeeValue(readInt(buffer, GwLength.FEE_VALUE));
			buffer.skipBytes(GwLength.FEE_TYPE);
			buffer.skipBytes(GwLength.FEE_VALUE);
			setMessageType(readInt(buffer, GwLength.MESSAGE_TYPE));
			setMoID(readOctetString(buffer, GwLength.MO_ID));
			int msgLength = readInt(buffer, GwLength.MESSAGE_LENGTH);
			setMessage(readOctetString(buffer, msgLength, MsgFormat.GBK));
			int signLength = readInt(buffer, GwLength.SIGN_LENGTH);
			setSign(readOctetString(buffer, signLength));
			
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
			writeOctetString(buffer, getFromPhone(), GwLength.FROM_PHONE);
			if (getToPhone().startsWith("86")) {
				setToPhone(getToPhone().substring(2));
			}else if(getToPhone().startsWith("+86")){
				setToPhone(getToPhone().substring(3));
			}
			writeOctetString(buffer, getToPhone(), GwLength.TO_PHONE);
			writeInt(buffer, getFeeUser(), GwLength.FEE_USER);
			writeOctetString(buffer, getFeePhone(), GwLength.FEE_PHONE);
			writeOctetString(buffer, getServiceID(), GwLength.SERVICE_ID);
			writeOctetString(buffer, getFeeType(), GwLength.FEE_TYPE);
			writeInt(buffer, getFeeValue(), GwLength.FEE_VALUE);
			writeInt(buffer, getMessageType(), GwLength.MESSAGE_TYPE);
			writeOctetString(buffer, getMoID(), GwLength.MO_ID);
			writeInt(buffer, getMessageLen(), GwLength.MESSAGE_LENGTH);
			writeOctetString(buffer, getMessage(), getMessageLen(), MsgFormat.GBK);
			writeInt(buffer, getSignLen(), GwLength.SIGN_LENGTH);
			writeOctetString(buffer, getSign());
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
	 * @return the feeUser
	 */
	public int getFeeUser() {
		return feeUser;
	}

	/**
	 * @param feeUser the feeUser to set
	 */
	public void setFeeUser(int feeUser) {
		this.feeUser = feeUser;
	}

	/**
	 * @return the feePhone
	 */
	public String getFeePhone() {
		return feePhone;
	}

	/**
	 * @param feePhone the feePhone to set
	 */
	public void setFeePhone(String feePhone) {
		this.feePhone = feePhone;
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
	 * @return the feeType
	 */
	public String getFeeType() {
		return feeType;
	}

	/**
	 * @param feeType the feeType to set
	 */
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	/**
	 * @return the feeValue
	 */
	public int getFeeValue() {
		return feeValue;
	}

	/**
	 * @param feeValue the feeValue to set
	 */
	public void setFeeValue(int feeValue) {
		this.feeValue = feeValue;
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
	 * @return the messageLen
	 */
	public int getMessageLen() {
		return messageLen;
	}

	/**
	 * @param messageLen the messageLen to set
	 */
	private void setMessageLen(int messageLen) {
		this.messageLen = messageLen;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		if (message != null && !message.equals("")) {
			int len = message.getBytes(Charset.forName("GBK")).length;
			if (len > 120) {
				setMessageLen(120);
				this.message = message.substring(0, 60);
			}else{
				setMessageLen(len);
				this.message = message;
			}
		}else{
			this.message = "";
		}
	}

	/**
	 * @return the signLen
	 */
	public int getSignLen() {
		return signLen;
	}

	/**
	 * @param signLen the signLen to set
	 */
	private void setSignLen(int signLen) {
		this.signLen = signLen;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		if (sign != null && !sign.equals("")) {
			setSignLen(sign.getBytes(Charset.forName("GBK")).length);
			this.sign = sign;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "GwSubmit [agentID=" + agentID + ", fromPhone=" + fromPhone + ", toPhone=" + toPhone
				+ ", feeUser=" + feeUser + ", feePhone=" + feePhone + ", serviceID=" + serviceID
				+ ", feeType=" + feeType + ", feeValue=" + feeValue + ", messageType="
				+ messageType + ", moID=" + moID + ", messageLen=" + messageLen + ", message="
				+ message + ", signLen=" + signLen + ", sign=" + sign + "]";
	}
	
	

}
