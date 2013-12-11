package com.e9.framework.handler.codec.smgp.message;

import java.util.LinkedList;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.smgp.property.SmgpCommandId;
import com.e9.framework.handler.codec.smgp.property.SmgpLength;
import com.e9.framework.handler.codec.smgp.property.SmgpPriority;
import com.e9.framework.handler.codec.smgp.property.SmgpTag;
import com.e9.framework.util.Common;

/**
 * Submit,见《中国电信CTMP开发接口－中国电信SMGP协议》5.2.2.2.1章节
 * @date 2013-11-14
 * @author Jason
 */
public class SmgpSubmit extends AbstractMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4737199589294340261L;

	/**
	 * 
	 */
	public SmgpSubmit() {
		// TODO Auto-generated constructor stub
		super.setCommandId(SmgpCommandId.SUBMIT);
	}
	
	private String msgidSmgw;
	private String msgidTime;
	private Integer msgidSequence;
	private String uid;
	
	/**
	 * 短消息类型。
	 * 对于回执消息该字段无效；对于文本短消息，该字段表示短消息的消息流向：
	 * @see SmgpMsgType
	 */
	private Integer msgType = 6;
	
	/**
	 * 是否要求返回状态报告。
	 * @see SmgpIntegerBoolean
	 */
	private Integer needReport = 1;
	
	/**
	 * 短消息发送优先级。
	 * @see SmgpPriority
	 */
	private Integer priority = SmgpPriority.NORMAL;
	
	/**
	 * 业务代码，用于固定网业务。
	 * 对于MO消息或点对点短消息，该字段无效；
	 * 对于MT消息，该字段表示业务代码，是该条短消息所属的业务类别，由数字、字母和符号组合而成。
	 * 对于从WEB上发送的点对点短消息，要求业务代码为 “PC2P”，其它业务代码由SP自定义。
	 */
	private String serviceId = "0";
	
	/**
	 * 对计费用户采取的收费类型。
	 * 对于MO消息或点对点短消息，该字段无效。
	 * 对于MT消息，该字段用法见：{@link SmgpFeeType}
	 */
	private String feeType = "00";
	
	/**
	 * 每条短消息费率，单位为“分”。
	 * 对于MO消息或点对点短消息，该字段无效；
	 * 对于MT消息，该字段具体使用方法参见{@link #feeType}。
	 */
	private String feeCode = "000";
	
	/**
	 * 短消息的包月费/封顶费，单位为“分”。
	 * 对于MO消息或点对点短消息，该字段无效；对于MT消息，该字段具体使用方法参见{@link #feeType}。
	 */
	private String fixedFee = "000";
	
	/**
	 * 短消息内容体的编码格式。
	 * @see {@link MsgFormat}
	 */
	private Integer msgFormat = 15;
	
	/**
	 * 短消息有效时间，格式遵循SMPP3.3以上版本协议。
	 * 短消息有效时间在转发过程中保持不变。
	 */
	private String validTime = "";
	
	/**
	 * 短消息定时发送时间，格式遵循SMPP3.3以上版本协议。
	 * 短消息定时发送时间在转发过程中保持不变。
	 */
	private String atTime = "";
	
	/**
	 * 短消息发送方号码。
	 * 对于MT消息，SrcTermID格式为“118＋SP服务代码＋其它（可选）”，例如SP服务代码为1234时，SrcTermID可以为1181234或118123456等。
	 * 对于MO消息，固定网中SrcTermID格式为“区号+号码（区号前添零）”，例如02087310323，07558780808，移动网中SrcTermID格式为MSISDN号码格式。
	 * 对于固定网点对点消息，主叫号码为普通终端时，SrcTermID格式为“区号+号码（区号前添零）”；主叫号码为爱因平台时，SrcTermID格式为“10631＋区号+号码（区号前添零）”。
	 */
	private String srcTermId = "";
	
	/**
	 * 计费用户号码。
	 * ChargeTermID为空时，如果是MT消息，则表示对被叫用户号码计费；如果是MO或点对点消息，则表示对主叫用户号码计费。
	 * ChargeTermID为非空时，表示对计费用户号码计费。
	 */
	private String chargeTermId = "";
	
//	/**
//	 * 短消息接收号码总数（≤100），用于SP实现群发短消息。
//	 */
//	private Integer destTermIdCount;
	
	/**
	 * 短消息 接收号码列表
	 * 
	 * 短消息接收号码。
	 * 对于MT消息，DestTermID连续存储DestTermIDCount个号码，每一个接收方号码为21位，固定网中DestTermID格式为“区号+号码（区号前添零）”，移动网中DestTermID格式为MSISDN号码格式，不足21位时应左对齐，右补0x00。
	 * 对于MO消息，DestTermID格式为“118＋SP服务代码＋其它（可选）”。对于点对点短消息，DestTermID格式为“区号+号码（区号前添零）” ，不足21位时应左对齐，右补0x00。
	 * 对于固定网点对点消息，被叫号码为普通终端时，SrcTermID格式为“区号+号码（区号前添零）”；被叫号码为爱因平台时，SrcTermID格式为“10631＋区号+号码（区号前添零）”。
	 */
	private final List<String> destTermIdList = new LinkedList<String>();
	
	/**
	 * 短消息长度。指MsgContent域的长度，取值大于或等于0。
	 * 对于MT消息，取值应小于或等于140。
	 */
	private Integer msgLength;
	
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
	 * 信息内容的来源。
	 * 在固定网短消息业务中，MsgSrc填写SP的服务代码。
	 * 在移动网短消息业务中，MsgSrc填写SP的企业代码。
	 */
	private String msgSrc;
	
	/**
	 * 计费用户类型。
	 * @see SmgpChargeUserType
	 */
	private Integer chargeUserType;
	
	/**
	 * 计费用户的号码类型。
	 * @see SmgpTermType
	 */
	private Integer chargeTermType;
	
	/**
	 * 计费用户的伪码
	 */
	private String chargeTermPseudo;
	
	/**
	 * 短消息接收方的号码类型。
	 * @see SmgpTermType
	 */
	private Integer destTermType;
	
	/**
	 * 短消息接收方伪码列表
	 * 短消息接收方的伪码，当有多个接收方伪码时，要求每个接收方伪码的长度一样。
	 */
	private List<String> destTermPseudoList = new LinkedList<String>();
	
	/**
	 * 相同Msg_Id的消息总条数。
	 */
	private Integer pkTotal;
	
	/**
	 * 相同Msg_Id的消息序号，从1开始。
	 */
	private Integer pkNumber;
	
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
	
	/**
	 * 业务代码。
	 * 用于移动网业务。
	 * 要求填写产品ID（Productid,PID）。
	 * Serviceid和Productid是包含关系，一个Serviceid可以有多个Productid，
	 * 电信仅向CP/SP开放Productid，Serviceid用于内部管理使用，Serviceid不开放给CP/SP使用。
	 * 
	 * 目前SMGW和CP/SP的SMGP接口只有Serviceid字段而没有productid字段，
	 * 为继承以前版本，要求CP/SP在和引擎接口的Serviceid 字段里填写Productid内容，
	 * 引擎透传此内容到ISMAP接口的PID字段里，ISMP获取到的是Productid内容。
	 */
	private String mServiceId;

	

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
	 * @return the msgType
	 */
	public Integer getMsgType() {
		return msgType;
	}

	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	/**
	 * @return the needReport
	 */
	public Integer getNeedReport() {
		return needReport;
	}

	/**
	 * @param needReport the needReport to set
	 */
	public void setNeedReport(Integer needReport) {
		this.needReport = needReport;
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
	 * @return the serviceId
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
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
	 * @return the feeCode
	 */
	public String getFeeCode() {
		return feeCode;
	}

	/**
	 * @param feeCode the feeCode to set
	 */
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	/**
	 * @return the fixedFee
	 */
	public String getFixedFee() {
		return fixedFee;
	}

	/**
	 * @param fixedFee the fixedFee to set
	 */
	public void setFixedFee(String fixedFee) {
		this.fixedFee = fixedFee;
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
	 * @return the validTime
	 */
	public String getValidTime() {
		return validTime;
	}

	/**
	 * @param validTime the validTime to set
	 */
	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	/**
	 * @return the atTime
	 */
	public String getAtTime() {
		return atTime;
	}

	/**
	 * @param atTime the atTime to set
	 */
	public void setAtTime(String atTime) {
		this.atTime = atTime;
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
	 * @return the chargeTermId
	 */
	public String getChargeTermId() {
		return chargeTermId;
	}

	/**
	 * @param chargeTermId the chargeTermId to set
	 */
	public void setChargeTermId(String chargeTermId) {
		this.chargeTermId = chargeTermId;
	}

	/**
	 * @return the length of {@link #destTermIdList}
	 */
	public Integer getDestTermIdCount() {
		// return destTermIdCount;
		return destTermIdList.size();
	}

//	/**
//	 * @param destTermIdCount the destTermIdCount to set
//	 */
//	public void setDestTermIdCount(Integer destTermIdCount) {
//		this.destTermIdCount = destTermIdCount;
//	}

	/**
	 * @return the msgLength
	 */
	public Integer getMsgLength() {
		// return msgLength;
		return octetStringByteArray(getMsgContent(),-1,getMsgFormat()).length;
	}

	/**
	 * @param msgLength the msgLength to set
	 */
	public void setMsgLength(Integer msgLength) {
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
	 * @return the destTermIdList
	 */
	public List<String> getDestTermIdList() {
		return destTermIdList;
	}
	
	public void setDestTermId(String destTermId){
		this.destTermIdList.add(destTermId);
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
	 * @return the msgSrc
	 */
	public String getMsgSrc() {
		return msgSrc;
	}

	/**
	 * @param msgSrc the msgSrc to set
	 */
	public void setMsgSrc(String msgSrc) {
		this.msgSrc = msgSrc;
	}

	/**
	 * @return the chargeUserType
	 */
	public Integer getChargeUserType() {
		return chargeUserType;
	}

	/**
	 * @param chargeUserType the chargeUserType to set
	 */
	public void setChargeUserType(Integer chargeUserType) {
		this.chargeUserType = chargeUserType;
	}

	/**
	 * @return the chargeTermType
	 */
	public Integer getChargeTermType() {
		return chargeTermType;
	}

	/**
	 * @param chargeTermType the chargeTermType to set
	 */
	public void setChargeTermType(Integer chargeTermType) {
		this.chargeTermType = chargeTermType;
	}

	/**
	 * @return the chargeTermPseudo
	 */
	public String getChargeTermPseudo() {
		return chargeTermPseudo;
	}

	/**
	 * @param chargeTermPseudo the chargeTermPseudo to set
	 */
	public void setChargeTermPseudo(String chargeTermPseudo) {
		this.chargeTermPseudo = chargeTermPseudo;
	}

	/**
	 * @return the destTermType
	 */
	public Integer getDestTermType() {
		return destTermType;
	}

	/**
	 * @param destTermType the destTermType to set
	 */
	public void setDestTermType(Integer destTermType) {
		this.destTermType = destTermType;
	}

	/**
	 * @return the destTermPseudoList
	 */
	public List<String> getDestTermPseudoList() {
		return destTermPseudoList;
	}

	/**
	 * @param destTermPseudoList the destTermPseudoList to set
	 */
	public void setDestTermPseudoList(List<String> destTermPseudoList) {
		this.destTermPseudoList = destTermPseudoList;
	}

	/**
	 * @return the pkTotal
	 */
	public Integer getPkTotal() {
		return pkTotal;
	}

	/**
	 * @param pkTotal the pkTotal to set
	 */
	public void setPkTotal(Integer pkTotal) {
		this.pkTotal = pkTotal;
	}

	/**
	 * @return the pkNumber
	 */
	public Integer getPkNumber() {
		return pkNumber;
	}

	/**
	 * @param pkNumber the pkNumber to set
	 */
	public void setPkNumber(Integer pkNumber) {
		this.pkNumber = pkNumber;
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

	/**
	 * @return the mServiceId
	 */
	public String getmServiceId() {
		return mServiceId;
	}

	/**
	 * @param mServiceId the mServiceId to set
	 */
	public void setmServiceId(String mServiceId) {
		this.mServiceId = mServiceId;
	}

	/**
	 * @return the mServiceId
	 */
	public String getMServiceId() {
		return getmServiceId();
	}

	/**
	 * @param mServiceId the mServiceId to set
	 */
	public void setMServiceId(String mServiceId) {
		setmServiceId(mServiceId);
	}

	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		try {
			if(!validateParameters()){
				throw new RuntimeException("Invalid parameters");
			}
			
			ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanWrite(buffer);
			int beginIndex = buffer.writerIndex();
			buffer.writerIndex(beginIndex + SmgpLength.SMGP_HEADER);
			
			writeInt(buffer, getMsgType(), SmgpLength.MSG_TYPE);
			writeInt(buffer, getNeedReport(), SmgpLength.NEED_REPORT);
			writeInt(buffer, getPriority(), SmgpLength.PRIORITY);
			writeOctetString(buffer, getServiceId(), SmgpLength.SERVICE_ID);
			writeOctetString(buffer, getFeeType(), SmgpLength.FEE_TYPE);
			writeOctetString(buffer, getFeeCode(), SmgpLength.FEE_CODE);
			writeOctetString(buffer, getFixedFee(), SmgpLength.FIXED_FEE);
			writeInt(buffer, getMsgFormat(), SmgpLength.MSG_FORMAT);
			writeOctetString(buffer, getValidTime(), SmgpLength.VALID_TIME);
			writeOctetString(buffer, getAtTime(), SmgpLength.AT_TIME);
			writeOctetString(buffer, getSrcTermId(), SmgpLength.SRC_TERM_ID);
			writeOctetString(buffer, getChargeTermId(), SmgpLength.CHARGE_TERM_ID);
			writeInt(buffer, getDestTermIdCount(), SmgpLength.DEST_TERM_ID_COUNT);
			
			for (String destTermId : getDestTermIdList()) {
				writeOctetString(buffer, destTermId, SmgpLength.DEST_TERM_ID);
			}
			
			int msgLen = getMsgLength();
			if(msgLen > SmgpLength.MSG_CONTENT_MAX_LENGTH){ 
				throw new RuntimeException("MsgContent length too big,[" + getMsgContent() + "]");
			}
			writeInt(buffer, msgLen, SmgpLength.MSG_LENGTH);
			writeOctetString(buffer, getMsgContent(), msgLen, getMsgFormat());
			writeOctetString(buffer, getReserve(), SmgpLength.RESERVE);
			
			// ========== TLV字段处理 ==========
			
			writeTLV(buffer, SmgpTag.TP_PID, SmgpLength.TLV_V_TP_PID, getTpPid());
			writeTLV(buffer, SmgpTag.TP_UDHI, SmgpLength.TLV_V_TP_UDHI, getTpUdhi());
			writeTLV(buffer, SmgpTag.LINK_ID, SmgpLength.TLV_V_LINK_ID, getLinkId());
			writeTLV(buffer, SmgpTag.MSG_SRC, SmgpLength.TLV_V_MSG_SRC, getMsgSrc());
			writeTLV(buffer, SmgpTag.CHARGE_USER_TYPE, SmgpLength.TLV_V_CHARGE_USER_TYPE, getChargeUserType());
			writeTLV(buffer, SmgpTag.CHARGE_TERM_TYPE, SmgpLength.TLV_V_CHARGE_TERM_TYPE, getChargeTermType());
			writeTLV(buffer, SmgpTag.CHARGE_TERM_PSEUDO, -1, getChargeTermPseudo());
			writeTLV(buffer, SmgpTag.DEST_TERM_TYPE, SmgpLength.TLV_V_DEST_TERM_TYPE, getDestTermType());
			for (String destTermPseudo : getDestTermPseudoList()) {
				writeTLV(buffer, SmgpTag.DEST_TERM_PSEUDO, -1, destTermPseudo);
			}
			writeTLV(buffer, SmgpTag.PK_TOTAL, SmgpLength.TLV_V_PK_TOTAL, getPkTotal());
			writeTLV(buffer, SmgpTag.PK_NUMBER, SmgpLength.TLV_V_PK_NUMBER, getPkNumber());
			writeTLV(buffer, SmgpTag.SUBMIT_MSG_TYPE, SmgpLength.TLV_V_SUBMIT_MSG_TYPE, getSubmitMsgType());
			writeTLV(buffer, SmgpTag.SP_DEAL_RESULT, SmgpLength.TLV_V_SP_DEAL_RESULT, getSpDealResult());
			writeTLV(buffer, SmgpTag.M_SERVICE_ID, SmgpLength.TLV_V_M_SERVICE_ID, getmServiceId());
			
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
			
			setMsgType(readInt(buffer, SmgpLength.MSG_TYPE));
			setNeedReport(readInt(buffer, SmgpLength.NEED_REPORT));
			setPriority(readInt(buffer, SmgpLength.PRIORITY));
			setServiceId(readOctetString(buffer, SmgpLength.SERVICE_ID));
			setFeeType(readOctetString(buffer, SmgpLength.FEE_TYPE));
			setFeeCode(readOctetString(buffer, SmgpLength.FEE_CODE));
			setFixedFee(readOctetString(buffer, SmgpLength.FIXED_FEE));
			setMsgFormat(readInt(buffer, SmgpLength.MSG_FORMAT));
			setValidTime(readOctetString(buffer, SmgpLength.VALID_TIME));
			setAtTime(readOctetString(buffer, SmgpLength.AT_TIME));
			setSrcTermId(readOctetString(buffer, SmgpLength.SRC_TERM_ID));
			setChargeTermId(readOctetString(buffer, SmgpLength.CHARGE_TERM_ID));
			
			int destTermIdCount = readInt(buffer, SmgpLength.DEST_TERM_ID_COUNT);
			for(int i = 0; i < destTermIdCount; i++){
				destTermIdList.add(readOctetString(buffer, SmgpLength.DEST_TERM_ID));
			}
			
			int msgLength = readInt(buffer, SmgpLength.MSG_LENGTH);
			setMsgLength(msgLength);
			setMsgContent(readOctetString(buffer, msgLength, getMsgFormat()));
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
				case SmgpTag.MSG_SRC:
					setMsgSrc(readOctetString(valueBuffer, SmgpLength.TLV_V_MSG_SRC));
					break;
				case SmgpTag.CHARGE_USER_TYPE:
					setChargeUserType(readInt(valueBuffer, SmgpLength.TLV_V_CHARGE_USER_TYPE));
					break;
				case SmgpTag.CHARGE_TERM_TYPE:
					setChargeTermType(readInt(valueBuffer, SmgpLength.TLV_V_CHARGE_TERM_TYPE));
					break;
				case SmgpTag.CHARGE_TERM_PSEUDO:
					setChargeTermPseudo(readOctetString(valueBuffer, length));
					break;
				case SmgpTag.DEST_TERM_TYPE:
					setDestTermType(readInt(valueBuffer, SmgpLength.TLV_V_DEST_TERM_TYPE));
					break;
				case SmgpTag.DEST_TERM_PSEUDO:
					destTermPseudoList.add(readOctetString(valueBuffer, length));
					break;
				case SmgpTag.PK_TOTAL:
					setPkTotal(readInt(valueBuffer, SmgpLength.TLV_V_PK_TOTAL));
					break;
				case SmgpTag.PK_NUMBER:
					setPkNumber(readInt(valueBuffer, SmgpLength.TLV_V_PK_NUMBER));
					break;
				case SmgpTag.SUBMIT_MSG_TYPE:
					setSubmitMsgType(readInt(valueBuffer, SmgpLength.TLV_V_SUBMIT_MSG_TYPE));
					break;
				case SmgpTag.SP_DEAL_RESULT:
					setSpDealResult(readInt(valueBuffer,SmgpLength.TLV_V_SP_DEAL_RESULT));
					break;
				case SmgpTag.M_SERVICE_ID:
					setmServiceId(readOctetString(valueBuffer, SmgpLength.TLV_V_M_SERVICE_ID));
					break;
				default:
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
			null != msgType && 
			null != needReport && 
			null != priority && 
			null != serviceId && 
			null != feeType && 
//			null != feeCode && 
//			null != fixedFee && 
			null != msgFormat && 
			null != validTime && 
//			null != atTime && 
			null != srcTermId && 
			null != chargeTermId && 
			null != msgContent  
//			null != reserve
		;
		
		return valid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SmgpSubmit [msgidSmgw=" + msgidSmgw + ", msgidTime=" + msgidTime
				+ ", msgidSequence=" + msgidSequence + ", uid=" + uid + ", msgType=" + msgType
				+ ", needReport=" + needReport + ", priority=" + priority + ", serviceId="
				+ serviceId + ", feeType=" + feeType + ", feeCode=" + feeCode + ", fixedFee="
				+ fixedFee + ", msgFormat=" + msgFormat + ", validTime=" + validTime + ", atTime="
				+ atTime + ", srcTermId=" + srcTermId + ", chargeTermId=" + chargeTermId
				+ ", destTermIdList=" + destTermIdList + ", msgLength=" + msgLength
				+ ", msgContent=" + msgContent + ", reserve=" + reserve + ", tpPid=" + tpPid
				+ ", tpUdhi=" + tpUdhi + ", linkId=" + linkId + ", msgSrc=" + msgSrc
				+ ", chargeUserType=" + chargeUserType + ", chargeTermType=" + chargeTermType
				+ ", chargeTermPseudo=" + chargeTermPseudo + ", destTermType=" + destTermType
				+ ", destTermPseudoList=" + destTermPseudoList + ", pkTotal=" + pkTotal
				+ ", pkNumber=" + pkNumber + ", submitMsgType=" + submitMsgType + ", spDealResult="
				+ spDealResult + ", mServiceId=" + mServiceId + "]";
	}

}
