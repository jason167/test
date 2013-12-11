package com.e9.framework.handler.codec.ex.gw.message;

import java.util.LinkedList;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.handler.codec.ex.gw.property.GwStatus;
import com.e9.framework.util.Common;

/**
 * 查询可用通道列表应答，见《短信通道协议开发包V3.06(内部使用)》3.2.5.8章节
 * @date 2013-9-2
 * @author Jason
 */
public class GwQueryChannelListResp extends AbstractGwMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -819466975186970647L;
	/**
	 * 
	 */
	public GwQueryChannelListResp() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.QUERY_CHANNEL_LIST_RESP);
		setSyncMessage(true);
	}
	/**
	 * 执行状态
	 */
	private int status = GwStatus.SUCCESSFUL.ordinal();
	
	/**
	 * 可用的SMSC数目
	 */
	private int numSMSC;
	
	/**
	 * SMSC/Agetn ID
	 */
	private LinkedList<String> channelList = new LinkedList<String>();

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#decode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer arg0) throws Exception {
		// TODO Auto-generated method stub
		ChannelBuffer buffer = null;
		try {
			buffer = beginDecode(arg0);
			setStatus(readInt(buffer, GwLength.STATUS));
			setNumSMSC(readInt(buffer, GwLength.NUM_SMSC));
			if (getNumSMSC() <= 0) {
				return;
			}
			for (int i = 0; i < getNumSMSC(); i++) {
				setChannelList(readOctetString(buffer, GwLength.CHANNEL_LIST));
			}
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
			writeInt(buffer, getStatus(), GwLength.STATUS);
			writeInt(buffer, getNumSMSC(), GwLength.NUM_SMSC);
			for (String channel : channelList) {
				writeOctetString(buffer, channel, GwLength.CHANNEL_LIST);
			}
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
		valid =
				!channelList.isEmpty() &&
				numSMSC == channelList.size();
		
		return valid;
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
	 * @return the numSMSC
	 */
	public int getNumSMSC() {
		return numSMSC;
	}

	/**
	 * @param numSMSC the numSMSC to set
	 */
	public void setNumSMSC(int numSMSC) {
		this.numSMSC = numSMSC;
	}

	/**
	 * @return the channelList
	 */
	public LinkedList<String> getChannelList() {
		return channelList;
	}

	/**
	 * @param channelList the channelList to set
	 */
	public void setChannelList(LinkedList<String> channelList) {
		this.channelList.addAll(channelList);
	}
	
	public void setChannelList(String channel){
		this.channelList.add(channel);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "GwQueryChannelListResp [status=0x" + Integer.toHexString(status) + ", numSMSC=" + numSMSC
				+ ", channelList=" + channelList + "]";
	}
	
	

}
