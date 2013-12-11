package com.e9.framework.handler.codec.ex.gw.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.MsgFormat;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.handler.codec.ex.gw.property.GwStatus;
import com.e9.framework.util.Common;

/**
 * 短信群发应答命令，见《短信通道协议开发包V3.06(内部使用)》3.2.5.4章节
 * @date 2013-9-2
 * @author Jason
 */
public class GwSubmitGroupResp extends AbstractGwMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7933264084802092071L;
	
	/**
	 * 
	 */
	public GwSubmitGroupResp() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.SUBMIT_GROUP_RESP);
	}
	/**
	 * 请求ID
	 */
	private String reqID;
	
	/**
	 * 发送短消息时使用的Agent 
	 */
	private String agentID = "";
	
	/**
	 * 短消息提交结果
	 */
	private int status = GwStatus.SUCCESSFUL.ordinal();
	
	/**
	 * 短消息提交结果描述
	 */
	private String msg = "";
	/**
	 * 
	 */

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#decode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer arg0) throws Exception {
		// TODO Auto-generated method stub
		ChannelBuffer buffer = null;
		try {
			buffer = beginDecode(arg0);
			setReqID(readOctetString(buffer, GwLength.REQ_ID));
			setAgentID(readOctetString(buffer, GwLength.AGENT_ID));
			setStatus(readInt(buffer, GwLength.STATUS));
			setMsg(readOctetString(buffer, getPacketLength() - buffer.writerIndex(), MsgFormat.GBK));
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
			writeOctetString(buffer, getReqID(), GwLength.REQ_ID);
			writeOctetString(buffer, getAgentID(), GwLength.AGENT_ID);
			writeInt(buffer, getStatus(), GwLength.STATUS);
			writeOctetString(buffer, getMsg());
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
		return true;
	}

	
	/**
	 * @return the reqID
	 */
	public String getReqID() {
		return reqID;
	}

	/**
	 * @param reqID the reqID to set
	 */
	public void setReqID(String reqID) {
		this.reqID = reqID;
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
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "GwSubmitGroupResp [reqID=" + reqID + ", agentID=" + agentID + ", status=0x" + Integer.toHexString(status)
				+ ", msg=" + msg + "]";
	}
	
	

}
