package com.e9.framework.handler.codec.cmpp.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.cmpp.property.CmppCommandId;
import com.e9.framework.handler.codec.cmpp.property.CmppLength;
import com.e9.framework.util.Common;

/**
 * DeliverResp,见CMPP v2.0 7.4.5.2章节
 * @project E9Framework
 * @date 2013-1-24
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-24] create by Jason
 */
public class CmppDeliverResp extends AbstractMessage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 409342112076606798L;
	
	/**
	 * 
	 */
	public CmppDeliverResp() {
		// TODO Auto-generated constructor stub
		super.setCommandId(CmppCommandId.DELIVER_RESP);
	}
	
	/**
	 * 信息标识
	 */
	private String msgidPrefixHex;
	private Integer msgidSequence;
	
	/**
	 * 结果
			0：正确
			1：消息结构错
			 2：命令字错
			 3：消息序号重复
			4：消息长度错
			5：资费代码错
			6：超过最大信息长
			7：业务代码错
			8: 流量控制错
			9~ ：其他错误
	 */
	private Integer Result;

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.cmpp.AbstractCmppMessage#encode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception{
		// TODO Auto-generated method stub
		try {
			if (!this.validateParameters()) {
				throw new RuntimeException("Invalid parameters:"+this.toString());
			}
			ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
			
			ensureChannelBufferCanWrite(buffer);
			
			int beginIndex = buffer.writerIndex();
			buffer.writerIndex(beginIndex + CmppLength.CMPP_HEADER);
			
//			buffer.writeLong(getMsg_Id());
			buffer.writeBytes(Common.hex2Bytes(getMsgidPrefixHex()));
			buffer.writeShort(getMsgidSequence());
			writeInt(buffer, getResult(), CmppLength.RESULT);
			
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
			headDecode(buffer);
			
//			setMsg_Id(buffer.readLong());
			setMsgidPrefixHex(Common.toHex(buffer.readBytes(6).array()));
			setMsgidSequence(buffer.readUnsignedShort());
			setResult(readInt(buffer, CmppLength.RESULT));
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
				null != Result;
		
		return valid;
	}


	/**
	 * @return the result
	 */
	public Integer getResult() {
		return Result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Integer result) {
		Result = result;
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
		return super.toString() + "CmppDeliverResp [msgidPrefixHex=" + msgidPrefixHex + ", msgidSequence="
				+ msgidSequence + ", Result=" + Result + "]";
	}

	
	
}
