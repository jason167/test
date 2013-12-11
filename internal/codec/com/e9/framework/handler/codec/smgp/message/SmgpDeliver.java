package com.e9.framework.handler.codec.smgp.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.MsgFormat;
import com.e9.framework.handler.codec.smgp.property.SmgpCommandId;
import com.e9.framework.handler.codec.smgp.property.SmgpIntegerBoolean;
import com.e9.framework.handler.codec.smgp.property.SmgpLength;
import com.e9.framework.handler.codec.smgp.property.SmgpNeedReport;
import com.e9.framework.handler.codec.smgp.property.SmgpSubmitMsgType;
import com.e9.framework.handler.codec.smgp.property.SmgpTag;
import com.e9.framework.handler.codec.smgp.property.SmgpTermType;
import com.e9.framework.util.Common;


/**
 * Deliver,见《中国电信CTMP开发接口－中国电信SMGP协议》5.2.2.3.1章节
 * @date 2013-11-12
 * @author Jason
 */
public class SmgpDeliver extends AbstractMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8249475184578818783L;
	
	/**
	 * 
	 */
	public SmgpDeliver() {
		// TODO Auto-generated constructor stub
		super.setCommandId(SmgpCommandId.DELIVER);
	}
	
	/**
	 * 短消息流水号，用来唯一标识一条短消息。
	 * 该字段在短消息的转发处理流程中保持唯一。
	 */
	private String msgidSmgw;
	private String msgidTime;
	private Integer msgidSequence;
	private String bossMsgid;
	
	/**
	 * 是否为状态报告。
	 * @see SmgpIntegerBoolean
	 */
	private Integer isReport;
	
	/**
	 * 短消息内容体的编码格式。
	 * @see MsgFormat
	 */
	private Integer msgFormat = 15;
	
	/**
	 * SMGW接收到短消息的时间。格式为YYYYMMDDHHMMSS（年年年年月月日日时时分分秒秒）。
	 */
	private String recvTime = "";
	
	/**
	 * 短消息发送方号码。
	 * 对于MT消息，SrcTermID格式为“118＋SP服务代码＋其它（可选）”，例如SP服务代码为1234时，SrcTermID可以为1181234或118123456等。
	 * 对于MO消息，固定网中SrcTermID格式为“区号+号码（区号前添零）”，例如02087310323，07558780808，移动网中SrcTermID格式为MSISDN号码格式。
	 * 对于固定网点对点消息，主叫号码为普通终端时，SrcTermID格式为“区号+号码（区号前添零）”；主叫号码为爱因平台时，SrcTermID格式为“10631＋区号+号码（区号前添零）”。
	 */
	private String srcTermId;
	
	/**
	 * 短消息接收号码。
	 * 对于MT消息，DestTermID连续存储DestTermIDCount个号码，每一个接收方号码为21位，固定网中DestTermID格式为“区号+号码（区号前添零）”，移动网中DestTermID格式为MSISDN号码格式，不足21位时应左对齐，右补0x00。
	 * 对于MO消息，DestTermID格式为“118＋SP服务代码＋其它（可选）”。对于点对点短消息，DestTermID格式为“区号+号码（区号前添零）” ，不足21位时应左对齐，右补0x00。
	 * 对于固定网点对点消息，被叫号码为普通终端时，SrcTermID格式为“区号+号码（区号前添零）”；被叫号码为爱因平台时，SrcTermID格式为“10631＋区号+号码（区号前添零）”。
	 */
	private String destTermId;
	
	/**
	 * 短消息内容。
	 * 当IsReport＝1时，MsgContent中内容为状态报告，其格式遵循《中国电信CTMP开发接口－中国电信SMGP协议》6.2.39节描述。
	 */
	private String msgContent = "";
	
	/**
	 * 保留字段。
	 */
	private String reserve = "";
	
	// ================== 以下属性为TLV可选参数 ==================
	
	/**
	 * GSM协议类型。详细解释请参考GSM03.40中的9.2.3.9。
	 */
	private Integer tpPid;
	
	/**
	 * GSM协议类型。详细解释请参考GSM03.40中的9.2.3.23,仅使用1位，右对齐。
	 */
	private Integer tpUdhi;
	
	/**
	 * 交易标识，用于唯一标识一次交易
	 */
	private String linkId;
	
	/**
	 * 短消息发送方的号码类型。
	 * @see SmgpTermType
	 */
	private Integer srcTermType;
	
	/**
	 * 短消息发送方的伪码
	 */
	private String srcTermPseudo;
	
	/**
	 * SP发送的消息类型。
	 * @see SmgpSubmitMsgType
	 */
	private Integer submitMsgType;
	
	/**
	 * SP对消息的处理结果
	 * 该字段在SubmitMsgType为0时无效。
	 * @see SmgpIntegerBoolean
	 */
	private Integer spDealResult;

	private int msgLength;
	private String sub = "001";
    private String Dlvrd = "001";
    private String Submit_date = "";
    private String done_date = "";
    private String Stat = "";
    private String Err = "";
    private String Txt = "";
	private String tp_udhi_protocol = "050003000101"; // 06 08 00 00 11 01 01
	private int updateInfoCount = 1;
	private boolean isPush = false;
	private boolean isSave = false;
	
	
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
	 * @return the msgidSmgw
	 */
	public String getMsgidSmgw() {
		return msgidSmgw;
	}

	/**
	 * @param msgidSmgw the msgidSmgw to set
	 */
	public void setMsgidSmgw(String msgidSmgw) {
		this.msgidSmgw = msgidSmgw;
	}

	/**
	 * @return the msgidTime
	 */
	public String getMsgidTime() {
		return msgidTime;
	}

	/**
	 * @param msgidTime the msgidTime to set
	 */
	public void setMsgidTime(String msgidTime) {
		this.msgidTime = msgidTime;
	}

	/**
	 * @return the msgidSequence
	 */
	public Integer getMsgidSequence() {
		return msgidSequence;
	}

	/**
	 * @param msgidSequence the msgidSequence to set
	 */
	public void setMsgidSequence(Integer msgidSequence) {
		this.msgidSequence = msgidSequence;
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
	 * @return the sub
	 */
	public String getSub() {
		return sub;
	}

	/**
	 * @param sub the sub to set
	 */
	public void setSub(String sub) {
		this.sub = sub;
	}

	/**
	 * @return the dlvrd
	 */
	public String getDlvrd() {
		return Dlvrd;
	}

	/**
	 * @param dlvrd the dlvrd to set
	 */
	public void setDlvrd(String dlvrd) {
		Dlvrd = dlvrd;
	}

	/**
	 * @return the submit_date
	 */
	public String getSubmit_date() {
		return Submit_date;
	}

	/**
	 * @param submit_date the submit_date to set
	 */
	public void setSubmit_date(String submit_date) {
		Submit_date = submit_date;
	}

	/**
	 * @return the done_date
	 */
	public String getDone_date() {
		return done_date;
	}

	/**
	 * @param done_date the done_date to set
	 */
	public void setDone_date(String done_date) {
		this.done_date = done_date;
	}

	/**
	 * @return the stat
	 */
	public String getStat() {
		return Stat;
	}

	/**
	 * @param stat the stat to set
	 */
	public void setStat(String stat) {
		Stat = stat;
	}

	/**
	 * @return the err
	 */
	public String getErr() {
		return Err;
	}

	/**
	 * @param err the err to set
	 */
	public void setErr(String err) {
		Err = err;
	}

	/**
	 * @return the txt
	 */
	public String getTxt() {
		return Txt;
	}

	/**
	 * @param txt the txt to set
	 */
	public void setTxt(String txt) {
		Txt = txt;
	}

	/**
	 * @return the isReport
	 */
	public Integer getIsReport() {
		return isReport;
	}

	/**
	 * @param isReport the isReport to set
	 */
	public void setIsReport(Integer isReport) {
		this.isReport = isReport;
	}

	/**
	 * @return the msgFormat
	 */
	public Integer getMsgFormat() {
		return msgFormat;
	}

	/**
	 * @param msgFormat the msgFormat to set
	 */
	public void setMsgFormat(Integer msgFormat) {
		this.msgFormat = msgFormat;
	}

	/**
	 * @return the recvTime
	 */
	public String getRecvTime() {
		return recvTime;
	}

	/**
	 * @param recvTime the recvTime to set
	 */
	public void setRecvTime(String recvTime) {
		this.recvTime = recvTime;
	}

	/**
	 * @return the srcTermId
	 */
	public String getSrcTermId() {
		return srcTermId;
	}

	/**
	 * @param srcTermId the srcTermId to set
	 */
	public void setSrcTermId(String srcTermId) {
		this.srcTermId = srcTermId;
	}

	/**
	 * @return the destTermId
	 */
	public String getDestTermId() {
		return destTermId;
	}

	/**
	 * @param destTermId the destTermId to set
	 */
	public void setDestTermId(String destTermId) {
		this.destTermId = destTermId;
	}

	/**
	 * @return the msgLength
	 */
	public Integer getMsgLength() {
		return this.msgLength;
	}
	
	public void setMsgLength(int msgLength){
		this.msgLength = msgLength;
	}

	/**
	 * @return the msgContent
	 */
	public String getMsgContent() {
		return msgContent;
	}

	/**
	 * @param msgContent the msgContent to set
	 */
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
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
	 * @return the tpPid
	 */
	public Integer getTpPid() {
		return tpPid;
	}

	/**
	 * @param tpPid the tpPid to set
	 */
	public void setTpPid(Integer tpPid) {
		this.tpPid = tpPid;
	}

	/**
	 * @return the tpUdhi
	 */
	public Integer getTpUdhi() {
		return tpUdhi;
	}

	/**
	 * @param tpUdhi the tpUdhi to set
	 */
	public void setTpUdhi(Integer tpUdhi) {
		this.tpUdhi = tpUdhi;
	}

	/**
	 * @return the linkId
	 */
	public String getLinkId() {
		return linkId;
	}

	/**
	 * @param linkId the linkId to set
	 */
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	/**
	 * @return the srcTermType
	 */
	public Integer getSrcTermType() {
		return srcTermType;
	}

	/**
	 * @param srcTermType the srcTermType to set
	 */
	public void setSrcTermType(Integer srcTermType) {
		this.srcTermType = srcTermType;
	}

	/**
	 * @return the srcTermPseudo
	 */
	public String getSrcTermPseudo() {
		return srcTermPseudo;
	}

	/**
	 * @param srcTermPseudo the srcTermPseudo to set
	 */
	public void setSrcTermPseudo(String srcTermPseudo) {
		this.srcTermPseudo = srcTermPseudo;
	}

	/**
	 * @return the submitMsgType
	 */
	public Integer getSubmitMsgType() {
		return submitMsgType;
	}

	/**
	 * @param submitMsgType the submitMsgType to set
	 */
	public void setSubmitMsgType(Integer submitMsgType) {
		this.submitMsgType = submitMsgType;
	}

	/**
	 * @return the spDealResult
	 */
	public Integer getSpDealResult() {
		return spDealResult;
	}

	/**
	 * @param spDealResult the spDealResult to set
	 */
	public void setSpDealResult(Integer spDealResult) {
		this.spDealResult = spDealResult;
	}

	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		try {
			if(!validateParameters()){
				throw new RuntimeException("Invalid parameters");
			}
//			if (getMsgId() == null || getMsgId().length() < SmgpLength.MSG_ID) {
//				throw new RuntimeException("Smgp deliver msgid length less than 10 or msgid is null");
//			}
			ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanWrite(buffer);
			
			int beginIndex = buffer.writerIndex();
			buffer.writerIndex(beginIndex + SmgpLength.SMGP_HEADER);
			
			buffer.writeBytes(Common.hex2Bytes(getMsgidSmgw()));
			buffer.writeBytes(Common.hex2Bytes(getMsgidTime()));
			buffer.writeMedium(getMsgidSequence());
//			writeOctetString(buffer, getMsgId(), SmgpLength.MSG_ID);
			writeInt(buffer, getIsReport(), SmgpLength.IS_REPORT);
			writeInt(buffer, getMsgFormat(), SmgpLength.MSG_FORMAT);
			writeOctetString(buffer, getRecvTime(), SmgpLength.RECV_TIME);
			writeOctetString(buffer, getSrcTermId(), SmgpLength.SRC_TERM_ID);
			writeOctetString(buffer, getDestTermId(), SmgpLength.DEST_TERM_ID);
			
			if (getIsReport() == SmgpNeedReport.YES) {
				setMsgLength(122);
				int firstIndex = buffer.writerIndex();
				buffer.writerIndex(firstIndex + 1);
				
				buffer.writeBytes("id:".getBytes());
				buffer.writeBytes(Common.hex2Bytes(getMsgidSmgw()));
				buffer.writeBytes(Common.hex2Bytes(getMsgidTime()));
				buffer.writeMedium(getMsgidSequence());
				buffer.writeBytes(" ".getBytes());
				
				StringBuilder sf = new StringBuilder();
				sf.append("sub:").append(this.getSub()).append(" ");
				sf.append("dlvrd:").append(this.getDlvrd()).append(" ");
				sf.append("Submit date:").append(this.getSubmit_date()).append(" ");
				sf.append("done date:").append(this.getDone_date()).append(" ");
				sf.append("stat:").append(this.getStat()).append(" ");
				sf.append("err:").append(this.getErr()).append(" ");
				sf.append("Text:").append(this.getTxt());
				
				buffer.writeBytes(sf.toString().getBytes());
				
				int lastIndex = buffer.writerIndex();
				buffer.writerIndex(firstIndex);
				buffer.writeByte(getMsgLength());
				
				buffer.writerIndex(lastIndex);
				
			}else{
				writeInt(buffer, getMsgLength(), SmgpLength.MSG_LENGTH);
				if (getTpUdhi() == 1) {
					if(getMsgLength() > SmgpLength.MSG_CONTENT_MAX_LENGTH){
						throw new RuntimeException("MsgContent length too big, tp_udhi_protocol:["+getTp_udhi_protocol()+"], msgcontent:[" + getMsgContent() + "]");
					}
					buffer.writeBytes(Common.hex2Bytes(getTp_udhi_protocol()));
					writeOctetString(buffer, getMsgContent(), getMsgLength() - (getTp_udhi_protocol().length() / 2), getMsgFormat());
				}else{
					if(getMsgLength() > SmgpLength.MSG_CONTENT_MAX_LENGTH){
						throw new RuntimeException("MsgContent length too big,[" + getMsgContent() + "]");
					}
					writeOctetString(buffer, getMsgContent(), getMsgLength(), getMsgFormat());
				}
			}
			writeOctetString(buffer, getReserve(), SmgpLength.RESERVE);
			
			// ========== TLV字段处理 ==========
			writeTLV(buffer, SmgpTag.TP_PID, SmgpLength.TLV_V_TP_PID, getTpPid());
			writeTLV(buffer, SmgpTag.TP_UDHI, SmgpLength.TLV_V_TP_UDHI, getTpUdhi());
			writeTLV(buffer, SmgpTag.LINK_ID, SmgpLength.TLV_V_LINK_ID, getLinkId());
			writeTLV(buffer, SmgpTag.SRC_TERM_TYPE, SmgpLength.TLV_V_SRC_TERM_TYPE, getSrcTermType());
			writeTLV(buffer, SmgpTag.SRC_TERM_PSEUDO, -1, getSrcTermPseudo());
			writeTLV(buffer, SmgpTag.SUBMIT_MSG_TYPE, SmgpLength.TLV_V_SUBMIT_MSG_TYPE, getSubmitMsgType());
			writeTLV(buffer, SmgpTag.SP_DEAL_RESULT, -1, getSpDealResult());
			
			int endIndex = buffer.writerIndex();
			setPacketLength(endIndex - beginIndex);
			
			buffer.writerIndex(beginIndex);
			headEncode(buffer);
			
			buffer.writerIndex(endIndex);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(this.toString(), e);
			
		}
	}

	@Override
	public void decode(IoBuffer ioBuffer) throws Exception {
		ChannelBuffer buffer = null;
		try {
			buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanRead(buffer);
			headDecode(buffer);
			
			setMsgidSmgw(Common.toHex(buffer.readBytes(3).array()));
			setMsgidTime(Common.toHex(buffer.readBytes(4).array()));
			setMsgidSequence(buffer.readUnsignedMedium());
			setBossMsgid(getMsgidSmgw() + getMsgidTime() + getSequenceId());
			
			setIsReport(readInt(buffer, SmgpLength.IS_REPORT));
			setMsgFormat(readInt(buffer, SmgpLength.MSG_FORMAT));
			setRecvTime(readOctetString(buffer, SmgpLength.RECV_TIME));
			setSrcTermId(readOctetString(buffer, SmgpLength.SRC_TERM_ID));
			setDestTermId(readOctetString(buffer, SmgpLength.DEST_TERM_ID));
			
			int msgLen = readInt(buffer, SmgpLength.MSG_LENGTH);
			setMsgLength(msgLen);
			ChannelBuffer msgContentBuffer = null;
			if (getIsReport() == SmgpNeedReport.YES) {
				// Report
	//id:3762211713 sub:001 dlvrd:001 Submit date:1305101650 done date:1305101650 stat:DELIVRD err:000 Text:00000000000000000000			
				buffer.skipBytes(3); //id:
//				setMsgId(Common.toHex(buffer.readBytes(SmgpLength.MSG_ID).array()));
				setMsgidSmgw(Common.toHex(buffer.readBytes(3).array()));
				setMsgidTime(Common.toHex(buffer.readBytes(4).array()));
				setMsgidSequence(buffer.readUnsignedMedium());
				
				buffer.skipBytes(31); // sub:001 dlvrd:001 Submit date:
				setSubmit_date(readOctetString(buffer, SmgpLength.Submit_date));
				
				buffer.skipBytes(11); // done date:
				setDone_date(readOctetString(buffer, SmgpLength.done_date));
				
				buffer.skipBytes(6); // stat:
				setStat(readOctetString(buffer, SmgpLength.Stat));
				
				buffer.skipBytes(5); // err:
				setErr(readOctetString(buffer, SmgpLength.Err));
				
				buffer.skipBytes(26); // Text:00000000000000000000
			}else{
				// Deliver
//				setMsgContent(readOctetString(buffer, msgLen,getMsgFormat()));
				msgContentBuffer = buffer.readBytes(msgLen);
			}
			setReserve(readOctetString(buffer, SmgpLength.RESERVE));

			// ========== TLV字段处理 ==========
			int tag;
			int length;
			ChannelBuffer valueBuffer;
			while(buffer.readable()){
				tag = readInt(buffer, SmgpLength.TLV_TAG);
				length = readInt(buffer, SmgpLength.TLV_LENGTH);
				ensureTlvLengthValid(buffer, tag, length);
				valueBuffer = buffer.readBytes(length);
				
				switch(tag) {
				case SmgpTag.TP_PID:
					setTpPid(readInt(valueBuffer, SmgpLength.TLV_V_TP_PID));
					break;
				case SmgpTag.TP_UDHI:
					setTpUdhi(readInt(valueBuffer, SmgpLength.TLV_V_TP_PID));
					break;
				case SmgpTag.LINK_ID:
					setLinkId(readOctetString(valueBuffer, SmgpLength.TLV_V_LINK_ID));
					break;
				case SmgpTag.SRC_TERM_TYPE:
					setSrcTermType(readInt(valueBuffer, SmgpLength.TLV_V_SRC_TERM_TYPE));
					break;
				case SmgpTag.SRC_TERM_PSEUDO:
					setSrcTermPseudo(readOctetString(valueBuffer, length));
					break;
				case SmgpTag.SUBMIT_MSG_TYPE:
					setSubmitMsgType(readInt(valueBuffer, SmgpLength.TLV_V_SUBMIT_MSG_TYPE));
					break;
				case SmgpTag.SP_DEAL_RESULT:
					setSpDealResult(readInt(valueBuffer,SmgpLength.TLV_V_SP_DEAL_RESULT));
					break;
				default:
				}
			}
			
			if (getIsReport() == SmgpNeedReport.NO) {
				if (getTpUdhi() == 0) {
					// 普通Deliver
					setMsgContent(readOctetString(msgContentBuffer, getMsgLength(), getMsgFormat()));
				}else{
					// 长短信Deliver
					if (msgContentBuffer != null) {
						int firstIndex = msgContentBuffer.readerIndex();
						
						int longSmsLength = msgContentBuffer.readByte() & 0xff;
						msgContentBuffer.writerIndex(firstIndex);
						
						// get a long sms content:
						setTp_udhi_protocol(Common.toHex(msgContentBuffer.readBytes(longSmsLength + 1).array()));
						setMsgContent(readOctetString(msgContentBuffer, getMsgLength() - (longSmsLength + 1), getMsgFormat()));
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(buffer == null ? "" : Common.toHex(buffer.array()), e);
		}
	}

	@Override
	protected boolean validateParameters() {
		boolean valid = false;
		
		// 必选参数检查
		valid = 
			null != msgidSmgw && msgidSmgw.length() == 6 &&
			null != msgidTime && msgidTime.length() == 8 &&
			null != msgidSequence && msgidSequence < 1000000 &&
			null != srcTermId && 
			null != destTermId
		;
		
		return valid;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "SmgpDeliver [msgidSmgw=" + msgidSmgw + ", msgidTime=" + msgidTime
				+ ", msgidSequence=" + msgidSequence + ", isReport=" + isReport + ", msgFormat="
				+ msgFormat + ", recvTime=" + recvTime + ", srcTermId=" + srcTermId
				+ ", destTermId=" + destTermId + ", msgContent=" + msgContent + ", reserve="
				+ reserve + ", tpPid=" + tpPid + ", tpUdhi=" + tpUdhi + ", linkId=" + linkId
				+ ", srcTermType=" + srcTermType + ", srcTermPseudo=" + srcTermPseudo
				+ ", submitMsgType=" + submitMsgType + ", spDealResult=" + spDealResult
				+ ", msgLength=" + msgLength + ", sub=" + sub + ", Dlvrd=" + Dlvrd
				+ ", Submit_date=" + Submit_date + ", done_date=" + done_date + ", Stat=" + Stat
				+ ", Err=" + Err + ", Txt=" + Txt + ", tp_udhi_protocol=" + tp_udhi_protocol
				+ ", updateInfoCount=" + updateInfoCount + ", isPush=" + isPush + ", isSave="
				+ isSave + "]";
	}


	

	
}
