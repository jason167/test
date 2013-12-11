package com.e9.framework.handler.codec.cmpp.message;

import java.util.LinkedList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.MsgFormat;
import com.e9.framework.handler.codec.cmpp.property.CmppCommandId;
import com.e9.framework.handler.codec.cmpp.property.CmppFee_UserType;
import com.e9.framework.handler.codec.cmpp.property.CmppLength;
import com.e9.framework.handler.codec.cmpp.property.CmppMsgFormat;
import com.e9.framework.handler.codec.cmpp.property.CmppMsg_level;
import com.e9.framework.handler.codec.cmpp.property.CmppRegistered_Delivery;
import com.e9.framework.util.Common;

/**
 * Submit,见CMPP v2.0 7.4.3.1章节
 * @project E9Framework
 * @date 2013-1-24
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-24] create by Jason
 */
public class CmppSubmit extends AbstractMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -947058649403861902L;

	/**
	 * 
	 */
	public CmppSubmit() {
		// TODO Auto-generated constructor stub
		super.setCommandId(CmppCommandId.SUBMIT);
	}
	
	/**
	 * 信息标识，由SP侧短信网关本身产生，本处填空。
	 */
	private Long Msg_Id = 0l;
	private String msgidPrefixHex;
	private Integer msgidSequence;
	private String uid;
	
	/**
	 * 相同Msg_Id的信息总条数，从1开始
	 */
	private Integer Pk_total;
	
	/**
	 * 相同Msg_Id的信息序号，从1开始
	 */
	private Integer Pk_number;
	
	/**
	 * 是否要求返回状态确认报告：
		0：不需要
		1：需要
		2：产生SMC话单
		 （该类型短信仅供网关计费使用，不发送给目的终端)
	 */
	private Integer Registered_Delivery = CmppRegistered_Delivery.YES;
	
	/**
	 * 信息级别
	 */
	private Integer Msg_level = CmppMsg_level.NORMAL;
	
	/**
	 * 业务类型，是数字、字母和符号的组合。
	 */
	private String Service_Id = "0";
	
	/**
	 * 计费用户类型字段
		0：对目的终端MSISDN计费；
		1：对源终端MSISDN计费；
		2：对SP计费;
		3：表示本字段无效，对谁计费参见Fee_terminal_Id字段。
	 */
	private Integer Fee_UserType = 0;
	
	/**
	 * 被计费用户的号码（如本字节填空，则表示本字段无效，对谁计费参见Fee_UserType字段，本字段与Fee_UserType字段互斥）
	 */
	private String Fee_terminal_Id = "";
	
	private Integer TP_pId = 0;
	private Integer TP_udhi = 0;
	
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
	 * 信息内容来源(SP_Id)
	 */
	private String Msg_src;
	
	/**
	 * 资费类别
		01：对“计费用户号码”免费
		02：对“计费用户号码”按条计信息费
		03：对“计费用户号码”按包月收取信息费
		04：对“计费用户号码”的信息费封顶
		05：对“计费用户号码”的收费是由SP实现
	 */
	private String FeeType = "00";
	
	/**
	 * 资费代码（以分为单位）
	 */
	private String FeeCode = "000000";
	
	/**
	 * 存活有效期
	 */
	private String ValId_Time = "";
	
	/**
	 * 定时发送时间
	 */
	private String At_Time = "";
	
	/**
	 * 源号码
	 */
	private String Src_Id;
	
	/**
	 * 接收信息的用户数量(小于100个用户)
	 */
	private Integer DestUsr_tl;
	
	/**
	 * 接收短信的MSISDN号码
	 */
	private List<String> Dest_terminal_Id = new LinkedList<String>();
	
	/**
	 * 信息长度(Msg_Fmt值为0时：<160个字节；其它<=140个字节)
	 */
	private Integer Msg_Length;
	
	/**
	 * 信息内容
	 */
	private String Msg_Content = "";
	
	/**
	 * 保留
	 */
	private String Reserve = "";
	
	private boolean lastSmsForLongSms = false;
//	private int tp_udhi_protocol_length = 6;
	private String tp_udhi_protocol;
	private String userid;
	private String channelCode;
	
	
	
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
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the channelCode
	 */
	public String getChannelCode() {
		return channelCode;
	}

	/**
	 * @param channelCode the channelCode to set
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}


	/**
	 * @return the lastSmsForLongSms
	 */
	public boolean isLastSmsForLongSms() {
		return lastSmsForLongSms;
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
			
			int dest_terminal_id_size = getDest_terminal_Id().size();
			
			buffer.writeLong(0);
			writeInt(buffer, getPk_total(), CmppLength.PK_TOTAL);
			writeInt(buffer, getPk_number(), CmppLength.PK_NUMBER);
			writeInt(buffer, getRegistered_Delivery(), CmppLength.Registered_Delivery);
			writeInt(buffer, getMsg_level() > 3 ? 3 : getMsg_level(), CmppLength.MSG_LEVEL);
			writeOctetString(buffer, getService_Id(), CmppLength.SERVICE_ID);
			writeInt(buffer, getFee_UserType(), CmppLength.FEE_USERTYPE);
			writeOctetString(buffer, getFee_terminal_Id(), CmppLength.FEE_TERMINAL_ID);
			writeInt(buffer, getTP_pId(), CmppLength.TP_PID);
			writeInt(buffer, getTP_udhi(), CmppLength.TP_UDHI);
			writeInt(buffer, getMsg_Fmt(), CmppLength.MSG_FMT);
			writeOctetString(buffer, getMsg_src(), CmppLength.Msg_src);
			writeOctetString(buffer, getFeeType(), CmppLength.FEE_TYPE);
			if (getFeeCode().length() != 6) {
				setFeeCode("000000");
			}
			writeOctetString(buffer, getFeeCode(), CmppLength.FEE_CODE);
			writeOctetString(buffer, getValId_Time(), CmppLength.VALID_TIME);
			writeOctetString(buffer, getAt_Time(), CmppLength.AT_TIME);
			writeOctetString(buffer, getSrc_Id(), CmppLength.SRC_ID);
			writeInt(buffer, dest_terminal_id_size, CmppLength.DESTUSR_TL);
			for(String dest_phone_number : getDest_terminal_Id()){
				writeOctetString(buffer, dest_phone_number, CmppLength.Dest_terminal_Id);
			}
			
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
			
			writeInt(buffer, getMsg_Length(), CmppLength.MSG_LENGTH);
			if (getTP_udhi() == 1) {
				buffer.writeBytes(Common.hex2Bytes(getTp_udhi_protocol()));
				writeOctetString(buffer, getMsg_Content(), getMsg_Length() - (getTp_udhi_protocol().length() / 2), getMsg_Fmt());
			}else{
				
				writeOctetString(buffer, getMsg_Content(), getMsg_Length(), getMsg_Fmt());
			}
			writeOctetString(buffer, getReserve(), CmppLength.RESERVE);
			
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
	public void decode(IoBuffer ioBuffer)  throws Exception{
		// TODO Auto-generated method stub
		ChannelBuffer buffer = null;
		try {
			buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanRead(buffer);
			headDecode(buffer);
			
			setMsg_Id(buffer.readLong());
			setPk_total(readInt(buffer, CmppLength.PK_TOTAL));
			setPk_number(readInt(buffer, CmppLength.PK_NUMBER));
			setRegistered_Delivery(readInt(buffer, CmppLength.Registered_Delivery));
			setMsg_level(readInt(buffer, CmppLength.MSG_LEVEL));
			setService_Id(readOctetString(buffer, CmppLength.SERVICE_ID));
			setFee_UserType(readInt(buffer, CmppLength.FEE_USERTYPE));
			setFee_terminal_Id(readOctetString(buffer, CmppLength.FEE_TERMINAL_ID));
			setTP_pId(readInt(buffer, CmppLength.TP_PID));
			setTP_udhi(readInt(buffer, CmppLength.TP_UDHI));
			setMsg_Fmt(readInt(buffer, CmppLength.MSG_FMT));
			setMsg_src(readOctetString(buffer, CmppLength.Msg_src));
			setFeeType(readOctetString(buffer, CmppLength.FEE_TYPE));
			setFeeCode(readOctetString(buffer, CmppLength.FEE_CODE));
			setValId_Time(readOctetString(buffer, CmppLength.VALID_TIME));
			setAt_Time(readOctetString(buffer, CmppLength.AT_TIME));
			setSrc_Id(readOctetString(buffer, CmppLength.SRC_ID));
			setDestUsr_tl(readInt(buffer, CmppLength.DESTUSR_TL));
			
			for(int i = 0; i < getDestUsr_tl(); i++){
				getDest_terminal_Id().add(readOctetString(buffer, CmppLength.Dest_terminal_Id));
			}
			
			setMsg_Length(readInt(buffer, CmppLength.MSG_LENGTH));
			
			if (getTP_udhi() == 1) {
				
				//  05 00 03 00 01 01
				//	06 08 04 00 11 01 01
				ChannelBuffer contentBuffer = buffer.readBytes(getMsg_Length());
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
				setMsg_Content(readOctetString(contentBuffer, getMsg_Length() - (longSmsLength + 1), getMsg_Fmt()));
			}else{
				
				setMsg_Content(readOctetString(buffer, getMsg_Length(), getMsg_Fmt()));
			}
			setReserve(readOctetString(buffer, CmppLength.RESERVE));
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
				null != Pk_number &&
				(Fee_UserType == CmppFee_UserType.IGNORE ? null != Fee_terminal_Id : true) &&
				null != Msg_src &&
				null != Src_Id &&
				Dest_terminal_Id.size() > 0 ;
		
		return valid;
	}

	/**
	 * @return the msg_Id
	 */
	public Long getMsg_Id() {
		return Msg_Id;
	}

	/**
	 * @param msg_Id the msg_Id to set
	 */
	public void setMsg_Id(Long msg_Id) {
		Msg_Id = msg_Id;
	}

	/**
	 * @return the pk_total
	 */
	public Integer getPk_total() {
		return Pk_total;
	}

	/**
	 * @param pk_total the pk_total to set
	 */
	public void setPk_total(Integer pk_total) {
		Pk_total = pk_total;
	}

	/**
	 * @return the pk_number
	 */
	public Integer getPk_number() {
		return Pk_number;
	}

	/**
	 * @param pk_number the pk_number to set
	 */
	public void setPk_number(Integer pk_number) {
		Pk_number = pk_number;
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
	 * @return the msg_level
	 */
	public Integer getMsg_level() {
		return Msg_level;
	}

	/**
	 * @param msg_level the msg_level to set
	 */
	public void setMsg_level(Integer msg_level) {
		Msg_level = msg_level;
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
	 * @return the fee_UserType
	 */
	public Integer getFee_UserType() {
		return Fee_UserType;
	}

	/**
	 * @param fee_UserType the fee_UserType to set
	 */
	public void setFee_UserType(Integer fee_UserType) {
		Fee_UserType = fee_UserType;
	}

	/**
	 * @return the fee_terminal_Id
	 */
	public String getFee_terminal_Id() {
		return Fee_terminal_Id;
	}

	/**
	 * @param fee_terminal_Id the fee_terminal_Id to set
	 */
	public void setFee_terminal_Id(String fee_terminal_Id) {
		Fee_terminal_Id = fee_terminal_Id;
	}

	/**
	 * @return the tP_pId
	 */
	public Integer getTP_pId() {
		return TP_pId;
	}

	/**
	 * @param tP_pId the tP_pId to set
	 */
	public void setTP_pId(Integer tP_pId) {
		TP_pId = tP_pId;
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
	 * @return the msg_src
	 */
	public String getMsg_src() {
		return Msg_src;
	}

	/**
	 * @param msg_src the msg_src to set
	 */
	public void setMsg_src(String msg_src) {
		Msg_src = msg_src;
	}

	/**
	 * @return the feeType
	 */
	public String getFeeType() {
		return FeeType;
	}

	/**
	 * @param feeType the feeType to set
	 */
	public void setFeeType(String feeType) {
		FeeType = feeType;
	}

	/**
	 * @return the feeCode
	 */
	public String getFeeCode() {
		return FeeCode;
	}

	/**
	 * @param feeCode the feeCode to set
	 */
	public void setFeeCode(String feeCode) {
		FeeCode = feeCode;
	}

	/**
	 * @return the valId_Time
	 */
	public String getValId_Time() {
		return ValId_Time;
	}

	/**
	 * @param valId_Time the valId_Time to set
	 */
	public void setValId_Time(String valId_Time) {
		ValId_Time = valId_Time;
	}

	/**
	 * @return the at_Time
	 */
	public String getAt_Time() {
		return At_Time;
	}

	/**
	 * @param at_Time the at_Time to set
	 */
	public void setAt_Time(String at_Time) {
		At_Time = at_Time;
	}

	/**
	 * @return the src_Id
	 */
	public String getSrc_Id() {
		return Src_Id;
	}

	/**
	 * @param src_Id the src_Id to set
	 */
	public void setSrc_Id(String src_Id) {
		Src_Id = src_Id;
	}

	/**
	 * @return the destUsr_tl
	 */
	public Integer getDestUsr_tl() {
		return DestUsr_tl;
	}

	/**
	 * @param destUsr_tl the destUsr_tl to set
	 */
	public void setDestUsr_tl(Integer destUsr_tl) {
		DestUsr_tl = destUsr_tl;
	}

	/**
	 * @return the dest_terminal_Id
	 */
	public List<String> getDest_terminal_Id() {
		return Dest_terminal_Id;
	}

	/**
	 * @param dest_terminal_Id the dest_terminal_Id to set
	 */
	public void setDest_terminal_Id(List<String> dest_terminal_Id) {
		Dest_terminal_Id = dest_terminal_Id;
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
	 * @return the reserve
	 */
	public String getReserve() {
		return Reserve;
	}

	/**
	 * @param reserve the reserve to set
	 */
	public void setReserve(String reserve) {
		Reserve = reserve;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CmppSubmit [Msg_Id=" + Msg_Id + ", msgidPrefixHex=" + msgidPrefixHex
				+ ", msgidSequence=" + msgidSequence + ", uid=" + uid + ", Pk_total=" + Pk_total
				+ ", Pk_number=" + Pk_number + ", Registered_Delivery=" + Registered_Delivery
				+ ", Msg_level=" + Msg_level + ", Service_Id=" + Service_Id + ", Fee_UserType="
				+ Fee_UserType + ", Fee_terminal_Id=" + Fee_terminal_Id + ", TP_pId=" + TP_pId
				+ ", TP_udhi=" + TP_udhi + ", Msg_Fmt=" + Msg_Fmt + ", Msg_src=" + Msg_src
				+ ", FeeType=" + FeeType + ", FeeCode=" + FeeCode + ", ValId_Time=" + ValId_Time
				+ ", At_Time=" + At_Time + ", Src_Id=" + Src_Id + ", DestUsr_tl=" + DestUsr_tl
				+ ", Dest_terminal_Id=" + Dest_terminal_Id + ", Msg_Length=" + Msg_Length
				+ ", Msg_Content=" + Msg_Content + ", Reserve=" + Reserve + ", lastSmsForLongSms="
				+ lastSmsForLongSms + ", tp_udhi_protocol=" + tp_udhi_protocol + ", userid="
				+ userid + ", channelCode=" + channelCode + "]";
	}

	
}
