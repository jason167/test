package com.e9.framework.handler.codec.sgip.message;

import java.util.LinkedList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.sgip.AbstractSgipMessage;
import com.e9.framework.handler.codec.sgip.property.SgipAgentFlag;
import com.e9.framework.handler.codec.sgip.property.SgipCommandId;
import com.e9.framework.handler.codec.sgip.property.SgipLength;
import com.e9.framework.handler.codec.sgip.property.SgipMessageCoding;
import com.e9.framework.handler.codec.sgip.property.SgipMessageType;
import com.e9.framework.handler.codec.sgip.property.SgipMorelatetoMTFlag;
import com.e9.framework.handler.codec.sgip.property.SgipPriority;
import com.e9.framework.handler.codec.sgip.property.SgipReportFlag;
import com.e9.framework.util.Common;

/**
 * MT命令，见SGIP 1.21版4.2.3.3.1章节
 * @date 2013-8-24
 * @author Jason
 */
public class SgipSubmit extends AbstractSgipMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2497056809610233664L;
	
	/**
	 * SP的接入号码
	 */
	private String spNumber;
	private String uid;
	
	/**
	 * <pre>
	 * 付费号码，手机号码前加“86”国别标志；
	 * 当且仅当群发且对用户收费时为空；
	 * 如果为空，则该条短消息产生的费用由UserNumber代表的用户支付；
	 * 如果为全零字符串“000000000000000000000”，表示该条短消息产生的费用由SP支付。
	 * </pre>
	 */
	private String chargeNumber = "000000000000000000000";
	
	/**
	 * 接收短消息的手机数量，取值范围1至100
	 */
	private Integer userCount;
	
	/**
	 * 接收该短消息的手机号，该字段重复UserCount指定的次数，手机号码前加“86”国别标志
	 */
	private List<String> userNumberList = new LinkedList<String>();
	
	/**
	 * 企业代码，取值范围0-99999
	 */
	private String corpId;
	
	/**
	 * 业务代码，由SP定义
	 */
	private String serviceType = "0";
	
	/**
	 * 计费类型
	 */
	private Integer feeType= 2;
	
	/**
	 * <pre>
	 * 取值范围0-99999，该条短消息的收费值，单位为分，由SP定义
	 * 对于包月制收费的用户，该值为月租费的值
	 * </pre>
	 */
	private String feeValue = "000000";
	
	/**
	 * 取值范围0-99999，赠送用户的话费，单位为分，由SP定义，特指由SP向用户发送广告时的赠送话费
	 */
	private String givenValue = "000000";
	
	/**
	 * 代收费标志，0：应收；1：实收
	 */
	private Integer agentFlag = SgipAgentFlag.RECEIVABLE;
	
	/**
	 * <pre>
	 	引起MT消息的原因
		0-MO点播引起的第一条MT消息；
		1-MO点播引起的非第一条MT消息；
		2-非MO点播引起的MT消息；
		3-系统反馈引起的MT消息。
	 * </pre>
	 */
	private Integer morelatetoMTFlag = SgipMorelatetoMTFlag.NO_MO_CAUSE_MT;
	
	/**
	 * 优先级0-9从低到高，默认为0
	 */
	private Integer priority = SgipPriority.ZERO;
	
	/**
	 * <pre>
	 * 短消息寿命的终止时间，如果为空，表示使用短消息中心的缺省值。
	 * 时间内容为16个字符，格式为”yymmddhhmmsstnnp” ，其中“tnnp”取固定值“032+”，即默认系统为北京时间
	 * </pre>
	 */
	private String expireTime = "";
	
	/**
	 * <pre>
	 * 短消息定时发送的时间，如果为空，表示立刻发送该短消息。
	 * 时间内容为16个字符，格式为“yymmddhhmmsstnnp” ，其中“tnnp”取固定值“032+”，即默认系统为北京时间
	 * </pre>
	 */
	private String scheduleTime = "";
	
	/**
	 * <pre>
		状态报告标记
		0-该条消息只有最后出错时要返回状态报告
		1-该条消息无论最后是否成功都要返回状态报告
		2-该条消息不需要返回状态报告
		3-该条消息仅携带包月计费信息，不下发给用户，要返回状态报告
		其它-保留
		缺省设置为0
	 * </pre>
	 */
	private Integer reportFlag =  SgipReportFlag.TRUE;
	private Integer TP_pid = 0;
	private Integer TP_udhi = 0;
	
	/**
	 * <pre>
		短消息的编码格式。
		0：纯ASCII字符串
		3：写卡操作
		4：二进制编码
		8：UCS2编码 （TP_udhi = 1时）
		15: GBK编码
		其它参见GSM3.38第4节：SMS Data Coding Scheme
	 * </pre>
	 */
	private Integer messageCoding = SgipMessageCoding.GBK;
	
	/**
	 * <pre>
		信息类型：
		0-短消息信息
		其它：待定
	 * </pre>
	 */
	private Integer messageType = SgipMessageType.SMS;
	
	/**
	 * 短消息的长度
	 */
	private Integer messageLength;
	
	/**
	 * 短消息的内容
	 */
	private String messageContent;
	
	/**
	 * 保留，扩展用
	 */
	private String reserve = "";
	private String tp_udhi_protocol;
	private boolean lastSmsForLongSms = false;
	private Integer userNodeCode;
	private Integer userIntDate;
	private Integer userSequenceId;
	
	/**
	 * 
	 */
	public SgipSubmit() {
		// TODO Auto-generated constructor stub
		setCommandId(SgipCommandId.SUBMIT);
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#encode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
		try {
			ChannelBuffer buffer = beginEncode(ioBuffer);
			writeOctetString(buffer, getSpNumber(), SgipLength.SP_NUMBER);
			writeOctetString(buffer, getChargeNumber(), SgipLength.CHARGE_NUMBER);
			writeInt(buffer, getUserCount(), SgipLength.USER_COUNT);
			for (String userNumber : userNumberList) {
				writeOctetString(buffer, "86" + userNumber, SgipLength.USER_NUMBER);
			}
			writeOctetString(buffer, getCorpId(), SgipLength.CORP_ID);
			writeOctetString(buffer, getServiceType(), SgipLength.SERVICE_TYPE);
			writeInt(buffer, getFeeType(), SgipLength.FEE_TYPE);
			writeOctetString(buffer, getFeeValue(), SgipLength.FEE_VALUE);
			writeOctetString(buffer, getGivenValue(), SgipLength.GIVEN_VALUE);
			writeInt(buffer, getAgentFlag(), SgipLength.AGENT_FLAG);
			writeInt(buffer, getMorelatetoMTFlag(), SgipLength.MORELATETO_MT_FLAG);
			writeInt(buffer, getPriority(), SgipLength.PRIORITY);
			writeOctetString(buffer, getExpireTime(), SgipLength.EXPIRE_TIME);
			writeOctetString(buffer, getScheduleTime(), SgipLength.SCHEDULE_TIME);
			writeInt(buffer, getReportFlag(), SgipLength.REPORT_FLAG);
			writeInt(buffer, getTP_pid(), SgipLength.TP_PID);
			writeInt(buffer, getTP_udhi(), SgipLength.TP_UDHI);
			writeInt(buffer, getMessageCoding(), SgipLength.MESSAGE_CODING);
			writeInt(buffer, getMessageType(), SgipLength.MESSAGE_TYPE);
			writeInt(buffer, getMessageLength(), SgipLength.MESSAGE_LENGTH);
			
			if (getTP_udhi() == 1) {
				buffer.writeBytes(Common.hex2Bytes(getTp_udhi_protocol()));
				writeOctetString(buffer, getMessageContent(), getMessageLength() - (getTp_udhi_protocol().length() / 2), getMessageCoding());
			}else{
				writeOctetString(buffer, getMessageContent(), getMessageLength(), getMessageCoding());
			}
			
			writeOctetString(buffer, getReserve(), SgipLength.RESERVE);
			endEncode(buffer);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(this.toString(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#decode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub

		ChannelBuffer buffer = null;
		try {
			buffer = beginDecode(ioBuffer);
			setUserNodeCode(getNodeCode());
			setUserIntDate(getIntDate());
			setUserSequenceId(getSequenceId());
			setSpNumber(readOctetString(buffer, SgipLength.SP_NUMBER));
			setChargeNumber(readOctetString(buffer, SgipLength.CHARGE_NUMBER));
			setUserCount(readInt(buffer, SgipLength.USER_COUNT));
			for (int i = 0; i < getUserCount(); i++) {
				String userPhoneNumber = readOctetString(buffer, SgipLength.USER_NUMBER);
				if (userPhoneNumber != null) {
					if (userPhoneNumber.startsWith("+86")) {
						userPhoneNumber = userPhoneNumber.substring(3);
					}else if (userPhoneNumber.startsWith("86")) {
						userPhoneNumber = userPhoneNumber.substring(2);
					}
				}
				userNumberList.add(userPhoneNumber);
			}
			
			setCorpId(readOctetString(buffer, SgipLength.CORP_ID));
			setServiceType(readOctetString(buffer, SgipLength.SERVICE_TYPE));
			setFeeType(readInt(buffer, SgipLength.FEE_TYPE));
			setFeeValue(readOctetString(buffer, SgipLength.FEE_VALUE));
			setGivenValue(readOctetString(buffer, SgipLength.GIVEN_VALUE));
			setAgentFlag(readInt(buffer, SgipLength.AGENT_FLAG));
			setMorelatetoMTFlag(readInt(buffer, SgipLength.MORELATETO_MT_FLAG));
			setPriority(readInt(buffer, SgipLength.PRIORITY));
			setExpireTime(readOctetString(buffer, SgipLength.EXPIRE_TIME));
			setScheduleTime(readOctetString(buffer, SgipLength.SCHEDULE_TIME));
			setReportFlag(readInt(buffer, SgipLength.REPORT_FLAG));
			setTP_pid(readInt(buffer, SgipLength.TP_PID));
			setTP_udhi(readInt(buffer, SgipLength.TP_UDHI));
			setMessageCoding(readInt(buffer, SgipLength.MESSAGE_CODING));
			setMessageType(readInt(buffer, SgipLength.MESSAGE_TYPE));
			
			setMessageLength(readInt(buffer, SgipLength.MESSAGE_LENGTH));
			if (getTP_udhi() == 1) {
				//  05 00 03 00 01 01
				//	06 08 04 00 11 01 01
				ChannelBuffer contentBuffer = buffer.readBytes(getMessageLength());
				int firstIndex = contentBuffer.readerIndex();
				int longSmsLength = contentBuffer.readByte() & 0xff;
				if (longSmsLength == 5) {
					contentBuffer.skipBytes(3);
					if (contentBuffer.readByte() == contentBuffer.readByte()) {
						this.lastSmsForLongSms = true;
					}
					
				}else{
					contentBuffer.skipBytes(4);
					if (contentBuffer.readByte() == contentBuffer.readByte()) {
						this.lastSmsForLongSms = true;
					}
				}
				
				contentBuffer.writerIndex(firstIndex);
				// set tp_udhi_protocol value:
				setTp_udhi_protocol(Common.toHex(contentBuffer.readBytes(longSmsLength + 1).array()));
				
				// set sms content value:
				setMessageContent(readOctetString(contentBuffer, getMessageLength() - (longSmsLength + 1), getMessageCoding()));
			}else{
				setMessageContent(readOctetString(buffer, getMessageLength(), getMessageCoding()));
			}
			setReserve(readOctetString(buffer, SgipLength.RESERVE));
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(buffer == null ? "" : Common.toHex(buffer.array()), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.handler.codec.sgip.AbstractSgipMessage#validateParameters()
	 */
	@Override
	protected boolean validateParameters() {
		// TODO Auto-generated method stub
		boolean valid = false;
		
		// 必选参数检查
		valid = 
			null != spNumber && 
			userNumberList.size() > 0 &&
			null != corpId
		;
		
		return valid;
	}
	
	

	/**
	 * @return the spNumber
	 */
	public String getSpNumber() {
		return spNumber;
	}

	/**
	 * @param spNumber the spNumber to set
	 */
	public void setSpNumber(String spNumber) {
		this.spNumber = spNumber;
	}

	/**
	 * @return the chargeNumber
	 */
	public String getChargeNumber() {
		return chargeNumber;
	}

	/**
	 * @param chargeNumber the chargeNumber to set
	 */
	public void setChargeNumber(String chargeNumber) {
		this.chargeNumber = chargeNumber;
	}

	/**
	 * @return the userCount
	 */
	public Integer getUserCount() {
		return userCount;
	}

	/**
	 * @param userCount the userCount to set
	 */
	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	

	/**
	 * @return the userNumberList
	 */
	public List<String> getUserNumberList() {
		return userNumberList;
	}

	/**
	 * @param userNumberList the userNumberList to set
	 */
	public void setUserNumberList(List<String> userNumberList) {
		this.userNumberList = userNumberList;
	}

	/**
	 * @return the corpId
	 */
	public String getCorpId() {
		return corpId;
	}

	/**
	 * @param corpId the corpId to set
	 */
	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @return the feeType
	 */
	public Integer getFeeType() {
		return feeType;
	}

	/**
	 * @param feeType the feeType to set
	 */
	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
	}

	/**
	 * @return the feeValue
	 */
	public String getFeeValue() {
		return feeValue;
	}

	/**
	 * @param feeValue the feeValue to set
	 */
	public void setFeeValue(String feeValue) {
		this.feeValue = feeValue;
	}

	/**
	 * @return the fivenValue
	 */
	public String getGivenValue() {
		return givenValue;
	}

	/**
	 * @param fivenValue the fivenValue to set
	 */
	public void setGivenValue(String givenValue) {
		this.givenValue = givenValue;
	}

	/**
	 * @return the agentFlag
	 */
	public Integer getAgentFlag() {
		return agentFlag;
	}

	/**
	 * @param agentFlag the agentFlag to set
	 */
	public void setAgentFlag(Integer agentFlag) {
		this.agentFlag = agentFlag;
	}

	/**
	 * @return the morelatetoMTFlag
	 */
	public Integer getMorelatetoMTFlag() {
		return morelatetoMTFlag;
	}

	/**
	 * @param morelatetoMTFlag the morelatetoMTFlag to set
	 */
	public void setMorelatetoMTFlag(Integer morelatetoMTFlag) {
		this.morelatetoMTFlag = morelatetoMTFlag;
	}

	/**
	 * @return the priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	/**
	 * @return the expireTime
	 */
	public String getExpireTime() {
		return expireTime;
	}

	/**
	 * @param expireTime the expireTime to set
	 */
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * @return the scheduleTime
	 */
	public String getScheduleTime() {
		return scheduleTime;
	}

	/**
	 * @param scheduleTime the scheduleTime to set
	 */
	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	/**
	 * @return the reportFlag
	 */
	public Integer getReportFlag() {
		return reportFlag;
	}

	/**
	 * @param reportFlag the reportFlag to set
	 */
	public void setReportFlag(Integer reportFlag) {
		this.reportFlag = reportFlag;
	}

	/**
	 * @return the tP_pid
	 */
	public Integer getTP_pid() {
		return TP_pid;
	}

	/**
	 * @param tP_pid the tP_pid to set
	 */
	public void setTP_pid(Integer tP_pid) {
		TP_pid = tP_pid;
	}

	/**
	 * @return the tP_udhi
	 */
	public Integer getTP_udhi() {
		return TP_udhi;
	}

	/**
	 * @param tP_udhi the tP_udhi to set
	 */
	public void setTP_udhi(Integer tP_udhi) {
		TP_udhi = tP_udhi;
	}

	/**
	 * @return the messageCoding
	 */
	public Integer getMessageCoding() {
		return messageCoding;
	}

	/**
	 * @param messageCoding the messageCoding to set
	 */
	public void setMessageCoding(Integer messageCoding) {
		this.messageCoding = messageCoding;
	}

	/**
	 * @return the messageType
	 */
	public Integer getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the messageLength
	 */
	public Integer getMessageLength() {
		return messageLength;
	}

	/**
	 * @param messageLength the messageLength to set
	 */
	public void setMessageLength(Integer messageLength) {
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
		this.messageContent = messageContent;
	}

	/**
	 * @return the reserve
	 */
	public String getReserve() {
		return reserve;
	}

	/**
	 * @param reserve the reserve to set
	 */
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	
	

	/**
	 * @return the tp_udhi_protocol
	 */
	public String getTp_udhi_protocol() {
		return tp_udhi_protocol;
	}

	/**
	 * @param tp_udhi_protocol the tp_udhi_protocol to set
	 */
	public void setTp_udhi_protocol(String tp_udhi_protocol) {
		this.tp_udhi_protocol = tp_udhi_protocol;
	}

	
	/**
	 * @return the lastSmsForLongSms
	 */
	public boolean isLastSmsForLongSms() {
		return lastSmsForLongSms;
	}

	/**
	 * @param lastSmsForLongSms the lastSmsForLongSms to set
	 */
	public void setLastSmsForLongSms(boolean lastSmsForLongSms) {
		this.lastSmsForLongSms = lastSmsForLongSms;
	}
	
	

	/**
	 * @param userNodeCode the userNodeCode to set
	 */
	private void setUserNodeCode(Integer userNodeCode) {
		this.userNodeCode = userNodeCode;
	}

	/**
	 * @param userIntDate the userIntDate to set
	 */
	private void setUserIntDate(Integer userIntDate) {
		this.userIntDate = userIntDate;
	}

	/**
	 * @param userSequenceId the userSequenceId to set
	 */
	private void setUserSequenceId(Integer userSequenceId) {
		this.userSequenceId = userSequenceId;
	}

	/**
	 * @return the userNodeCode
	 */
	public Integer getUserNodeCode() {
		return userNodeCode;
	}

	/**
	 * @return the userIntDate
	 */
	public Integer getUserIntDate() {
		return userIntDate;
	}

	/**
	 * @return the userSequenceId
	 */
	public Integer getUserSequenceId() {
		return userSequenceId;
	}
	
	

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "SgipSubmit [spNumber=" + spNumber + ", uid=" + uid + ", chargeNumber="
				+ chargeNumber + ", userCount=" + userCount + ", userNumberList=" + userNumberList
				+ ", corpId=" + corpId + ", serviceType=" + serviceType + ", feeType=" + feeType
				+ ", feeValue=" + feeValue + ", givenValue=" + givenValue + ", agentFlag="
				+ agentFlag + ", morelatetoMTFlag=" + morelatetoMTFlag + ", priority=" + priority
				+ ", expireTime=" + expireTime + ", scheduleTime=" + scheduleTime + ", reportFlag="
				+ reportFlag + ", TP_pid=" + TP_pid + ", TP_udhi=" + TP_udhi + ", messageCoding="
				+ messageCoding + ", messageType=" + messageType + ", messageLength="
				+ messageLength + ", messageContent=" + messageContent + ", reserve=" + reserve
				+ ", tp_udhi_protocol=" + tp_udhi_protocol + ", lastSmsForLongSms="
				+ lastSmsForLongSms + ", userNodeCode=" + userNodeCode + ", userIntDate="
				+ userIntDate + ", userSequenceId=" + userSequenceId + "]";
	}

	

}
