package com.e9.framework.handler.codec.sgip.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.sgip.AbstractSgipMessage;
import com.e9.framework.handler.codec.sgip.property.SgipCommandId;
import com.e9.framework.handler.codec.sgip.property.SgipLength;
import com.e9.framework.handler.codec.sgip.property.SgipReportType;
import com.e9.framework.util.Common;

/**
 * Report命令，见SGIP 1.21版4.2.3.5.1章节
 * @date 2013-8-26
 * @author Jason
 */
public class SgipReport extends AbstractSgipMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1267916923474483401L;
	
	/**
	 * 该命令所涉及的Submit或deliver命令的序列号
	 */
	private Integer submitNodeCode;
	private Integer submitIntDate;
	private Integer submitSequenceId;
	private String submitHexSequenceNumber;
	private String bossMsgid;
	
	/**
	 * <pre>
		Report命令类型
		0：对先前一条Submit命令的状态报告
		1：对先前一条前转Deliver命令的状态报告
	 * </pre>
	 */
	private Integer reportType = SgipReportType.PRE_FIRST_SUBMIT;
	
	/**
	 * 接收短消息的手机号，手机号码前加“86”国别标志
	 */
	private String userNumber;
	
	/**
	 * <pre>
		该命令所涉及的短消息的当前执行状态
		0：发送成功
		1：等待发送
		2：发送失败
	 * </pre>
	 */
	private Integer state;
	
	/**
	 * 当State=2时为错误码值，否则为0
	 */
	private Integer errorCode;
	
	/**
	 * 保留，扩展用
	 */
	private String reserve = "";
	private int updateInfoCount = 1;
	private boolean isPush = false;
	private boolean isSave = false;
	
	/**
	 * 
	 */
	public SgipReport() {
		// TODO Auto-generated constructor stub
		setCommandId(SgipCommandId.REPORT);
	}
	
	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#encode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
		try {
			ChannelBuffer buffer = beginEncode(ioBuffer);
//			buffer.writeBytes(Common.hex2Bytes(getSubmitSequenceNumber()));
			buffer.writeInt(getSubmitNodeCode());
			buffer.writeInt(getSubmitIntDate());
			buffer.writeInt(getSubmitSequenceId());
			writeInt(buffer, getReportType(), SgipLength.REPORT_TYPE);
			writeOctetString(buffer, "86"+getUserNumber(), SgipLength.USER_NUMBER);
			writeInt(buffer, getState(), SgipLength.STATE);
			writeInt(buffer, getErrorCode(), SgipLength.ERROR_CODE);
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
			//setSubmitSequenceNumber(Common.toHex(buffer.readBytes(SgipLength.SEQUENCE_ID).array()));
			
			setSubmitNodeCode(getNodeCode());
			setSubmitIntDate(getIntDate());
			setSubmitSequenceId(getSequenceId());
			setSubmitHexSequenceNumber(getHexSequenceNumber());
			setBossMsgid(String.valueOf(getNodeCode()) + getIntDate() + getSequenceId());
			
			setReportType(readInt(buffer, SgipLength.REPORT_TYPE));
			String userPhoneNumber = readOctetString(buffer, SgipLength.USER_NUMBER);
			if (userPhoneNumber != null) {
				if (userPhoneNumber.startsWith("+86")) {
					setUserNumber(userPhoneNumber.substring(3));
				}else if(userPhoneNumber.startsWith("86")){
					setUserNumber(userPhoneNumber.substring(2));
				}else{
					setUserNumber("0");
				}
			}else{
				setUserNumber("0");
			}
			setState(readInt(buffer, SgipLength.STATE));
			setErrorCode(readInt(buffer, SgipLength.ERROR_CODE));
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
			null != submitIntDate && 
			null != submitNodeCode && 
			null != submitSequenceId && 
			null != userNumber
		;
		
		return valid;
	}

	/**
	 * @return the reportType
	 */
	public Integer getReportType() {
		return reportType;
	}

	/**
	 * @param reportType the reportType to set
	 */
	public void setReportType(Integer reportType) {
		this.reportType = reportType;
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
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return the errorCode
	 */
	public Integer getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
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
	 * @return the submitNodeCode
	 */
	public Integer getSubmitNodeCode() {
		return submitNodeCode;
	}

	/**
	 * @param submitNodeCode the submitNodeCode to set
	 */
	public void setSubmitNodeCode(Integer submitNodeCode) {
		this.submitNodeCode = submitNodeCode;
	}

	/**
	 * @return the submitIntDate
	 */
	public Integer getSubmitIntDate() {
		return submitIntDate;
	}

	/**
	 * @param submitIntDate the submitIntDate to set
	 */
	public void setSubmitIntDate(Integer submitIntDate) {
		this.submitIntDate = submitIntDate;
	}

	/**
	 * @return the submitSequenceId
	 */
	public Integer getSubmitSequenceId() {
		return submitSequenceId;
	}

	/**
	 * @param submitSequenceId the submitSequenceId to set
	 */
	public void setSubmitSequenceId(Integer submitSequenceId) {
		this.submitSequenceId = submitSequenceId;
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
	
	

	/**
	 * @return the submitHexSequenceNumber
	 */
	public String getSubmitHexSequenceNumber() {
		return submitHexSequenceNumber;
	}

	/**
	 * @param submitHexSequenceNumber the submitHexSequenceNumber to set
	 */
	public void setSubmitHexSequenceNumber(String submitHexSequenceNumber) {
		this.submitHexSequenceNumber = submitHexSequenceNumber;
	}
	
	

	/**
	 * @return the bossMsgid
	 */
	public String getBossMsgid() {
		return bossMsgid;
	}

	/**
	 * @param bossMsgid the bossMsgid to set
	 */
	public void setBossMsgid(String bossMsgid) {
		this.bossMsgid = bossMsgid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SgipReport [submitNodeCode=" + submitNodeCode + ", submitIntDate=" + submitIntDate
				+ ", submitSequenceId=" + submitSequenceId + ", submitHexSequenceNumber="
				+ submitHexSequenceNumber + ", reportType=" + reportType + ", userNumber="
				+ userNumber + ", state=" + state + ", errorCode=" + errorCode + ", reserve="
				+ reserve + ", updateInfoCount=" + updateInfoCount + ", isPush=" + isPush
				+ ", isSave=" + isSave + "]";
	}

}
