package com.e9.framework.handler.codec.ex.gw.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.util.Common;

/**
 * 查询费用，见《短信通道协议开发包V3.06(内部使用)》3.2.5.6章节
 * @date 2013-9-2
 * @author Jason
 */
public class GwQueryFee extends AbstractGwMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6320000364162980361L;
	
	/**
	 * 使用的Agent ID,为null时查询所有
	 */
	private String agentID = "";
	
	/**
	 * 起始日期(从这天的 零时开始)
	 * yyyymmdd
	 */
	private String fromDate;
	
	/**
	 * 截止日期(到这天的23:59:59秒止)
	 * yyyymmdd
	 */
	private String endDate;
	
	/**
	 * 
	 */
	public GwQueryFee() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.QUERY_FEE);
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
			setFromDate(readOctetString(buffer, GwLength.FROM_DATE));
			setEndDate(readOctetString(buffer, GwLength.END_DATE));
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
			writeOctetString(buffer, getFromDate(), GwLength.FROM_DATE);
			writeOctetString(buffer, getEndDate(), GwLength.END_DATE);
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
			null != fromDate && 
			null != endDate
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
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "QueryFee [agentID=" + agentID + ", fromDate=" + fromDate + ", endDate=" + endDate
				+ "]";
	}
	
	

}
