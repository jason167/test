package com.e9.framework.handler.codec.smgp.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.smgp.property.SmgpCommandId;
import com.e9.framework.handler.codec.smgp.property.SmgpLength;
import com.e9.framework.util.Common;

/**
 * Deliver_Resp,见《中国电信CTMP开发接口－中国电信SMGP协议》5.2.2.3.2章节
 * @project E9Framework
 * @date 2013-1-18
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-18] create by Jason
 */
public class SmgpDeliverResp extends AbstractMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5329745525169314064L;

	/**
	 * 
	 */
	public SmgpDeliverResp() {
		// TODO Auto-generated constructor stub
		super.setCommandId(SmgpCommandId.DELIVER_RESP);
	}
	
	/**
	 * 短消息流水号
	 */
	private String msgidSmgw;
	private String msgidTime;
	private Integer msgidSequence;
	
	/**
	 * 请求返回结果
	 */
	private Integer status; 
	


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
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.smgp.AbstractSmgpMessage#encode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
		try {
			if (!this.validateParameters()) {
				throw new RuntimeException("Invalid parameters");
			}
//			if (getMsgId() == null || getMsgId().length() < SmgpLength.MSG_ID) {
//				throw new RuntimeException("Smgp deliverResp msgid length less than 10 or msgid is null");
//			}
			ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanWrite(buffer);
			
			int beginIndex = buffer.writerIndex();
			buffer.writerIndex(beginIndex + SmgpLength.SMGP_HEADER);
			
//			writeOctetString(buffer, getMsgId(), SmgpLength.MSG_ID);
			buffer.writeBytes(Common.hex2Bytes(getMsgidSmgw()));
			buffer.writeBytes(Common.hex2Bytes(getMsgidTime()));
			buffer.writeMedium(getMsgidSequence());
			writeInt(buffer, getStatus(), SmgpLength.STATUS);
			
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
	 * @see com.e9.tcp.gateway.smgp.AbstractSmgpMessage#decode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
		ChannelBuffer buffer = null;
		try {
			buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanRead(buffer);
			headDecode(buffer);
			
			setMsgidSmgw(Common.toHex(buffer.readBytes(3).array()));
			setMsgidTime(Common.toHex(buffer.readBytes(4).array()));
			setMsgidSequence(buffer.readUnsignedMedium());
			setStatus(readInt(buffer, SmgpLength.STATUS));
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(buffer == null ? "" : Common.toHex(buffer.array()), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.smgp.AbstractSmgpMessage#validateParameters()
	 */
	@Override
	protected boolean validateParameters() {
		// TODO Auto-generated method stub
		boolean valid = false;
		
		valid = 
				null != msgidSmgw && msgidSmgw.length() == 6 &&
				null != msgidTime && msgidTime.length() == 8 &&
				null != msgidSequence && msgidSequence < 1000000 &&
				null != status;
		
		return valid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "SmgpDeliverResp [msgidSmgw=" + msgidSmgw + ", msgidTime=" + msgidTime
				+ ", msgidSequence=" + msgidSequence + ", status=" + status + "]";
	}

	

}

