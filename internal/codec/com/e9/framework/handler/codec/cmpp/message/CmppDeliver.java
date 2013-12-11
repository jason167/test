package com.e9.framework.handler.codec.cmpp.message;

import java.util.Date;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.MsgFormat;
import com.e9.framework.handler.codec.cmpp.property.CmppCommandId;
import com.e9.framework.handler.codec.cmpp.property.CmppLength;
import com.e9.framework.handler.codec.cmpp.property.CmppMsgFormat;
import com.e9.framework.handler.codec.cmpp.property.CmppRegistered_Delivery;
import com.e9.framework.handler.codec.smgp.property.SmgpLength;
import com.e9.framework.util.Common;

/**
 * Deliver,见CMPP v2.0 7.4.5.1章节
 * @date 2013-1-24
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-24] create by Jason
 */
public class CmppDeliver extends AbstractMessage implements Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3353615071651488635L;

	/**
	 * 
	 */
	public CmppDeliver() {
		// TODO Auto-generated constructor stub
		super.setCommandId(CmppCommandId.DELIVER);
	}
	
	/**
	 * 信息标识
	        生成算法如下：
			采用64位（8字节）的整数：
			时间（格式为MMDDHHMMSS，即月日时分秒）：bit64~bit39，其中
			bit64~bit61：月份的二进制表示；
			bit60~bit56：日的二进制表示；
			bit55~bit51：小时的二进制表示；
			bit50~bit45：分的二进制表示；
			bit44~bit39：秒的二进制表示；
			短信网关代码：bit38~bit17，把短信网关的代码转换为整数填写到该字段中。
			序列号：bit16~bit1，顺序增加，步长为1，循环使用。
			各部分如不能填满，左补零，右对齐。
	 */
	private String msgidPrefixHex;
	private Integer msgidSequence;
	private String bossMsgid;
	
	/**
	 * 目的号码
	 */
	private String Dest_Id;
	
	/**
	 * 业务类型，是数字、字母和符号的组合。
	 */
	private String Service_Id = "0";
	
	private Integer TP_pid;
	private Integer TP_udhi;
	
	/**
	 * 信息格式
		  0：ASCII串
		  3：短信写卡操作
		  4：二进制信息
		  8：UCS2编码
		  15：含GB汉字
	 */
	private Integer Msg_Fmt = MsgFormat.GBK;
	
	/**
	 * 源终端MSISDN号码（状态报告时填为CMPP_SUBMIT消息的目的终端号码）
	 */
	private String Src_terminal_Id;
	
	/**
	 * 是否为状态报告
			0：非状态报告
			1：状态报告
	 */
	private Integer Registered_Delivery = CmppRegistered_Delivery.YES;
	
	/**
	 * 消息长度
	 */
	private Integer Msg_Length;
	
	/**
	 * 消息内容
	 */
	private String Msg_Content = "";
	
	/**
	 * 保留项
	 */
	private String Reserved = "";
	
	
	// ===========Message Content=========
	/**
	 * 发送短信的应答结果，含义与SMPP协议要求中stat字段定义相同，详见表一。SP根据该字段确定CMPP_SUBMIT消息的处理状态。
	 */
	private String Stat;
	
	private String Submit_time;
	private String Done_time;
	
	/**
	 * 目的终端MSISDN号码(SP发送CMPP_SUBMIT消息的目标终端)
	 */
	private String Dest_terminal_Id;
	
	/**
	 * 取自SMSC发送状态报告的消息体中的消息标识。
	 */
	private Integer SMSC_sequence;
	
	private long timeout_ttl = 0l;
	private int updateInfoCount = 1;
	private long deliverResp_ttl = 0l;
	private String tp_udhi_protocol;
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
	 * @return the deliverResp_ttl
	 */
	public long getDeliverResp_ttl() {
		return deliverResp_ttl;
	}

	/**
	 * @param deliverResp_ttl the deliverResp_ttl to set
	 */
	public void setDeliverResp_ttl(long deliverResp_ttl) {
		this.deliverResp_ttl = deliverResp_ttl;
	}

	/**
	 * @return the timeout_ttl
	 */
	public long getTimeout_ttl() {
		return timeout_ttl;
	}

	/**
	 * @param timeout_ttl the timeout_ttl to set
	 */
	public void setTimeout_ttl(long timeout_ttl) {
		this.timeout_ttl = timeout_ttl;
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

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.cmpp.AbstractCmppMessage#encode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception{
		// TODO Auto-generated method stub
		try {
			if(!validateParameters()){
				throw new RuntimeException("Invalid parameters:"+this.toString());
			}
			
			ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanWrite(buffer);
			
			int beginIndex = buffer.writerIndex();
			buffer.writerIndex(beginIndex + CmppLength.CMPP_HEADER);
			
//			buffer.writeLong(getMsg_Id());
			buffer.writeBytes(Common.hex2Bytes(getMsgidPrefixHex()));
			buffer.writeShort(getMsgidSequence());
			writeOctetString(buffer, getDest_Id(), CmppLength.DEST_ID);
			writeOctetString(buffer, getService_Id(), CmppLength.SERVICE_ID);
			writeInt(buffer, getTP_pid(), CmppLength.TP_PID);
			writeInt(buffer, getTP_udhi(), CmppLength.TP_UDHI);
			writeInt(buffer, getMsg_Fmt(), CmppLength.MSG_FMT);
			writeOctetString(buffer, getSrc_terminal_Id(), CmppLength.SRC_TERMINAL_ID);
			writeInt(buffer, getRegistered_Delivery(), CmppLength.Registered_Delivery);
			
			
			if (getRegistered_Delivery() == CmppRegistered_Delivery.NO) {
				if (getMsg_Fmt() == CmppMsgFormat.ASCII) {
					if (getMsg_Length() > CmppLength.MSG_CONTENT_ASCII_MAX_LENGTH) {
						throw new RuntimeException("Ascii MsgContent length too big,[" + getMsg_Content() + "]");
					}
				}else{
					// other msg format
					if (getMsg_Length() > CmppLength.MSG_CONTENT_MAX_LENGTH) {
						throw new RuntimeException("Not Ascii, MsgContent length too big,[" + getMsg_Content() + "]");
					}
				}
				
				if (getTP_udhi() == 1) {
					if(getMsg_Length() > SmgpLength.MSG_CONTENT_MAX_LENGTH){
						throw new RuntimeException("MsgContent length too big, tp_udhi_protocol:["+getTp_udhi_protocol()+"], msgcontent:[" + getMsg_Content() + "]");
					}
					buffer.writeBytes(Common.hex2Bytes(getTp_udhi_protocol()));
					writeOctetString(buffer, getMsg_Content(), getMsg_Length() - (getTp_udhi_protocol().length() / 2), getMsg_Fmt());
				}else{
					writeInt(buffer, getMsg_Length(), CmppLength.MSG_LENGTH);
					writeOctetString(buffer, getMsg_Content(), getMsg_Length(), getMsg_Fmt());
				}
			}else{
				
				writeInt(buffer, getMsg_Length(), CmppLength.MSG_LENGTH);
				
//				buffer.writeLong(getMsg_Id());
				buffer.writeBytes(Common.hex2Bytes(getMsgidPrefixHex()));
				buffer.writeShort(getMsgidSequence());
				writeOctetString(buffer, getStat(), CmppLength.STAT);
				writeOctetString(buffer, getSubmit_time(), CmppLength.SUBMIT_TIME);
				writeOctetString(buffer, getDone_time(), CmppLength.DONE_TIME);
				writeOctetString(buffer, getDest_terminal_Id(), CmppLength.Dest_terminal_Id);
				writeInt(buffer, getSMSC_sequence(), CmppLength.SMSC_sequence);
			}
			writeOctetString(buffer, getReserved(), CmppLength.RESERVE);
			
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

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.cmpp.AbstractCmppMessage#decode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer ioBuffer) throws Exception{
		// TODO Auto-generated method stub
		ChannelBuffer buffer = null;
		try {
			buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanRead(buffer);
			this.setDeliverResp_ttl(new Date().getTime());
			headDecode(buffer);
			
//			setMsg_Id(buffer.readLong());
			setMsgidPrefixHex(Common.toHex(buffer.readBytes(6).array()));
			setMsgidSequence(buffer.readUnsignedShort());
			setBossMsgid(getMsgidPrefixHex() + getMsgidSequence());
			setDest_Id(readOctetString(buffer, CmppLength.DEST_ID));
			setService_Id(readOctetString(buffer, CmppLength.SERVICE_ID));
			setTP_pid(readInt(buffer, CmppLength.TP_PID));
			setTP_udhi(readInt(buffer, CmppLength.TP_UDHI));
			setMsg_Fmt(readInt(buffer, CmppLength.MSG_FMT));
			
			setSrc_terminal_Id(readOctetString(buffer, CmppLength.SRC_TERMINAL_ID));
			if (getSrc_terminal_Id().startsWith("+86")) {
				setSrc_terminal_Id(getSrc_terminal_Id().substring(3));
			}else if(getSrc_terminal_Id().startsWith("86")){
				setSrc_terminal_Id(getSrc_terminal_Id().substring(2));
			}
			
			setRegistered_Delivery(readInt(buffer, CmppLength.Registered_Delivery));
			setMsg_Length(readInt(buffer, CmppLength.MSG_LENGTH));
			
			if (getRegistered_Delivery() == CmppRegistered_Delivery.NO) {
				// MO
				if (getTP_udhi() == 1) {
					ChannelBuffer contentBuffer = buffer.readBytes(getMsg_Length());
					int firstIndex = contentBuffer.readerIndex();
					int longSmsLength = contentBuffer.readByte() & 0xff;
					
					contentBuffer.writerIndex(firstIndex);
					setTp_udhi_protocol(Common.toHex(contentBuffer.readBytes(longSmsLength + 1).array()));
					setMsg_Content(readOctetString(contentBuffer, getMsg_Length() - (longSmsLength + 1), getMsg_Fmt()));
				}else{
					setMsg_Content(readOctetString(buffer, getMsg_Length(), getMsg_Fmt()));
				}
			}else{
				
//				setMsg_Id(buffer.readLong());
				setMsgidPrefixHex(Common.toHex(buffer.readBytes(6).array()));
				setMsgidSequence(buffer.readUnsignedShort());
				setStat(readOctetString(buffer, CmppLength.STAT));
				setSubmit_time(readOctetString(buffer, CmppLength.SUBMIT_TIME));
				setDone_time(readOctetString(buffer, CmppLength.DONE_TIME));
				setDest_terminal_Id(readOctetString(buffer, CmppLength.Dest_terminal_Id));
				if (getDest_terminal_Id().startsWith("+86")) {
					setDest_terminal_Id(getDest_terminal_Id().substring(3));
				}else if(getDest_terminal_Id().startsWith("86")){
					setDest_terminal_Id(getDest_terminal_Id().substring(2));
				}
				setSMSC_sequence(readInt(buffer, CmppLength.SMSC_sequence));
			}
			
			setReserved(readOctetString(buffer, CmppLength.RESERVE));
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(buffer == null ? "" : Common.toHex(buffer.array()), e);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.cmpp.AbstractCmppMessage#validateParameters()
	 */
	@Override
	protected boolean validateParameters() {
		// TODO Auto-generated method stub
		boolean valid = false;
		
		valid = 
				null != msgidPrefixHex && msgidPrefixHex.length() == 12 &&
				null != msgidSequence && msgidSequence < Short.MAX_VALUE &&
				null != Dest_Id &&
				null != Src_terminal_Id;
		
		return valid;
	}


	/**
	 * @return the dest_Id
	 */
	public String getDest_Id() {
		return Dest_Id;
	}

	/**
	 * @param dest_Id the dest_Id to set
	 */
	public void setDest_Id(String dest_Id) {
		Dest_Id = dest_Id;
	}

	/**
	 * @return the service_Id
	 */
	public String getService_Id() {
		return Service_Id;
	}

	/**
	 * @param service_Id the service_Id to set
	 */
	public void setService_Id(String service_Id) {
		if (service_Id == null || service_Id.equals("")) {
			return;
		}
		Service_Id = service_Id;
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
	 * @return the msg_Fmt
	 */
	public Integer getMsg_Fmt() {
		return Msg_Fmt;
	}

	/**
	 * @param msg_Fmt the msg_Fmt to set
	 */
	public void setMsg_Fmt(Integer msg_Fmt) {
		Msg_Fmt = msg_Fmt;
	}

	/**
	 * @return the src_terminal_Id
	 */
	public String getSrc_terminal_Id() {
		return Src_terminal_Id;
	}

	/**
	 * @param src_terminal_Id the src_terminal_Id to set
	 */
	public void setSrc_terminal_Id(String src_terminal_Id) {
		Src_terminal_Id = src_terminal_Id;
	}

	/**
	 * @return the registered_Delivery
	 */
	public Integer getRegistered_Delivery() {
		return Registered_Delivery;
	}

	/**
	 * @param registered_Delivery the registered_Delivery to set
	 */
	public void setRegistered_Delivery(Integer registered_Delivery) {
		Registered_Delivery = registered_Delivery;
	}

	/**
	 * @return the msg_Length
	 */
	public Integer getMsg_Length() {
		return Msg_Length;
	}

	/**
	 * @param msg_Length the msg_Length to set
	 */
	public void setMsg_Length(Integer msg_Length) {
		Msg_Length = msg_Length;
	}

	/**
	 * @return the msg_Content
	 */
	public String getMsg_Content() {
		return Msg_Content;
	}

	/**
	 * @param msg_Content the msg_Content to set
	 */
	public void setMsg_Content(String msg_Content) {
		Msg_Content = (msg_Content == null ? "" : msg_Content);
	}

	/**
	 * @return the reserved
	 */
	public String getReserved() {
		return Reserved;
	}

	/**
	 * @param reserved the reserved to set
	 */
	public void setReserved(String reserved) {
		Reserved = reserved;
	}

	/**
	 * @return the stat
	 */
	public String getStat() {
		return Stat == null ? "" : Stat;
	}

	/**
	 * @param stat the stat to set
	 */
	public void setStat(String stat) {
		Stat = stat;
	}

	/**
	 * @return the submit_time
	 */
	public String getSubmit_time() {
		return Submit_time == null ? "" : Submit_time;
	}

	/**
	 * @param submit_time the submit_time to set
	 */
	public void setSubmit_time(String submit_time) {
		Submit_time = submit_time;
	}

	/**
	 * @return the done_time
	 */
	public String getDone_time() {
		return Done_time == null ? "" : Done_time;
	}

	/**
	 * @param done_time the done_time to set
	 */
	public void setDone_time(String done_time) {
		Done_time = done_time;
	}

	/**
	 * @return the dest_terminal_Id
	 */
	public String getDest_terminal_Id() {
		return Dest_terminal_Id == null ? "" : Dest_terminal_Id;
	}

	/**
	 * @param dest_terminal_Id the dest_terminal_Id to set
	 */
	public void setDest_terminal_Id(String dest_terminal_Id) {
		Dest_terminal_Id = dest_terminal_Id;
	}

	/**
	 * @return the sMSC_sequence
	 */
	public Integer getSMSC_sequence() {
		return SMSC_sequence == null ? 0 : SMSC_sequence;
	}

	/**
	 * @param sMSC_sequence the sMSC_sequence to set
	 */
	public void setSMSC_sequence(Integer sMSC_sequence) {
		SMSC_sequence = sMSC_sequence;
	}


	
	/**
	 * @return the msgidPrefixHex
	 */
	public String getMsgidPrefixHex() {
		return msgidPrefixHex;
	}

	/**
	 * @param msgidPrefixHex the msgidPrefixHex to set
	 */
	public void setMsgidPrefixHex(String msgidPrefixHex) {
		this.msgidPrefixHex = msgidPrefixHex;
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

	public CmppDeliver clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (CmppDeliver) super.clone();
	}

	private Integer id;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param sequenceId
	 * @param msg_Id
	 * @param dest_Id
	 * @param service_Id
	 * @param tP_pid
	 * @param tP_udhi
	 * @param msg_Fmt
	 * @param src_terminal_Id
	 * @param registered_Delivery
	 * @param msg_Length
	 * @param msg_Content
	 * @param stat
	 * @param submit_time
	 * @param done_time
	 * @param dest_terminal_Id
	 * @param sMSC_sequence
	 */
	public CmppDeliver(Integer id, Integer sequenceId, String msgidPrrfixHex, Integer msgidSequence, String dest_Id, String service_Id, Integer tP_pid, Integer tP_udhi,
			Integer msg_Fmt, String src_terminal_Id, Integer registered_Delivery,
			Integer msg_Length, String msg_Content, String stat, String submit_time,
			String done_time, String dest_terminal_Id, Integer sMSC_sequence) {
		super.setCommandId(CmppCommandId.DELIVER);
		super.setSequenceId(sequenceId);
		this.id = id;
		this.msgidPrefixHex = msgidPrrfixHex;
		this.msgidSequence = msgidSequence;
		Dest_Id = dest_Id;
		Service_Id = service_Id;
		TP_pid = tP_pid;
		TP_udhi = tP_udhi;
		Msg_Fmt = msg_Fmt;
		Src_terminal_Id = src_terminal_Id;
		Registered_Delivery = registered_Delivery;
		Msg_Length = msg_Length;
		Msg_Content = msg_Content;
		Stat = stat;
		Submit_time = submit_time;
		Done_time = done_time;
		Dest_terminal_Id = dest_terminal_Id;
		SMSC_sequence = sMSC_sequence;
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
		return super.toString() + "CmppDeliver [msgidPrefixHex=" + msgidPrefixHex + ", msgidSequence=" + msgidSequence
				+ ", Dest_Id=" + Dest_Id + ", Service_Id=" + Service_Id + ", TP_pid=" + TP_pid
				+ ", TP_udhi=" + TP_udhi + ", Msg_Fmt=" + Msg_Fmt + ", Src_terminal_Id="
				+ Src_terminal_Id + ", Registered_Delivery=" + Registered_Delivery
				+ ", Msg_Length=" + Msg_Length + ", Msg_Content=" + Msg_Content + ", Reserved="
				+ Reserved + ", Stat=" + Stat + ", Submit_time=" + Submit_time + ", Done_time="
				+ Done_time + ", Dest_terminal_Id=" + Dest_terminal_Id + ", SMSC_sequence="
				+ SMSC_sequence + ", timeout_ttl=" + timeout_ttl + ", updateInfoCount="
				+ updateInfoCount + ", deliverResp_ttl=" + deliverResp_ttl + ", tp_udhi_protocol="
				+ tp_udhi_protocol + ", isPush=" + isPush + ", isSave=" + isSave + ", id=" + id
				+ "]";
	}

	
}
