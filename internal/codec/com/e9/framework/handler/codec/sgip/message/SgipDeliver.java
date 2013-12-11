package com.e9.framework.handler.codec.sgip.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.MsgFormat;
import com.e9.framework.handler.codec.sgip.AbstractSgipMessage;
import com.e9.framework.handler.codec.sgip.property.SgipCommandId;
import com.e9.framework.handler.codec.sgip.property.SgipLength;
import com.e9.framework.util.Common;

/**
 * DELIVER命令，见SGIP 1.21版4.2.3.4.1章节
 * @date 2013-8-26
 * @author Jason
 */
public class SgipDeliver extends AbstractSgipMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2896135223394432762L;
	
	/**
	 * 发送短消息的用户手机号，手机号码前加“86”国别标志
	 */
	private String userNumber;
	/**
	 * SP的接入号码
	 */
	private String sPNumber;
	private Integer TP_pid;
	private Integer TP_udhi;
	
	/**
	 * <pre>
		短消息的编码格式。
		0：纯ASCII字符串
		3：写卡操作
		4：二进制编码
		8：UCS2编码
		15: GBK编码
		其它参见GSM3.38第4节：SMS Data Coding Scheme
	 * </pre>
	 */
	private Integer messageCoding = MsgFormat.GBK;
	private Integer messageLength;
	private String messageContent;
	private String reserve = "";
	private String tp_udhi_protocol;
	private int updateInfoCount = 1;
	private boolean isPush = false;
	private boolean isSave = false;
	
	/**
	 * 
	 */
	public SgipDeliver() {
		// TODO Auto-generated constructor stub
		setCommandId(SgipCommandId.DELIVER);
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#encode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
		try {
			ChannelBuffer buffer = beginEncode(ioBuffer);
			writeOctetString(buffer, "86"+getUserNumber(), SgipLength.USER_NUMBER);
			writeOctetString(buffer, getsPNumber(), SgipLength.SP_NUMBER);
			writeInt(buffer, getTP_pid(), SgipLength.TP_PID);
			writeInt(buffer, getTP_udhi(), SgipLength.TP_UDHI);
			writeInt(buffer, getMessageCoding(), SgipLength.MESSAGE_CODING);
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
			String userPhoneNumber = readOctetString(buffer, SgipLength.USER_NUMBER);
			if (userPhoneNumber != null) {
				if (userPhoneNumber.startsWith("+86")) {
					setUserNumber(userPhoneNumber.substring(3));
				}else if (userPhoneNumber.startsWith("86")) {
					setUserNumber(userPhoneNumber.substring(2));
				}else{
					setUserNumber("0");
				}
			}else{
				setUserNumber("0");
			}
			setsPNumber(readOctetString(buffer, SgipLength.SP_NUMBER));
			setTP_pid(readInt(buffer, SgipLength.TP_PID));
			setTP_udhi(readInt(buffer, SgipLength.TP_UDHI));
			setMessageCoding(readInt(buffer, SgipLength.MESSAGE_CODING));
			setMessageLength(readInt(buffer, SgipLength.MESSAGE_LENGTH));
			if (getTP_udhi() == 1) {
				ChannelBuffer contentBuffer = buffer.readBytes(getMessageLength());
				int firstIndex = contentBuffer.readerIndex();
				int longSmsLength = contentBuffer.readByte() & 0xff;
				
				contentBuffer.writerIndex(firstIndex);
				setTp_udhi_protocol(Common.toHex(contentBuffer.readBytes(longSmsLength + 1).array()));
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
			null != userNumber && 
			null != sPNumber
		;
		
		return valid;
	}

	
	/**
	 * @return the userNumber
	 */
	public String getUserNumber() {
		return userNumber;
	}

	/**
	 * @param userNumber the userNumber to set
	 */
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	/**
	 * @return the sPNumber
	 */
	public String getsPNumber() {
		return sPNumber;
	}

	/**
	 * @param sPNumber the sPNumber to set
	 */
	public void setsPNumber(String sPNumber) {
		this.sPNumber = sPNumber;
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
	 * @return the updateInfoCount
	 */
	public int getUpdateInfoCount() {
		return updateInfoCount;
	}

	/**
	 * @param updateInfoCount the updateInfoCount to set
	 */
	public void setUpdateInfoCount(int updateInfoCount) {
		this.updateInfoCount += updateInfoCount;
	}

	
	
	/**
	 * @return the isPush
	 */
	public boolean isPush() {
		return isPush;
	}

	/**
	 * @param isPush the isPush to set
	 */
	public void setPush(boolean isPush) {
		this.isPush = isPush;
	}
	
	

	/**
	 * @return the isSave
	 */
	public boolean isSave() {
		return isSave;
	}

	/**
	 * @param isSave the isSave to set
	 */
	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "SgipDeliver [userNumber=" + userNumber + ", sPNumber=" + sPNumber + ", TP_pid="
				+ TP_pid + ", TP_udhi=" + TP_udhi + ", messageCoding=" + messageCoding
				+ ", messageLength=" + messageLength + ", messageContent=" + messageContent
				+ ", reserve=" + reserve + ", tp_udhi_protocol=" + tp_udhi_protocol
				+ ", updateInfoCount=" + updateInfoCount + ", isPush=" + isPush + ", isSave="
				+ isSave + "]";
	}

	

}
