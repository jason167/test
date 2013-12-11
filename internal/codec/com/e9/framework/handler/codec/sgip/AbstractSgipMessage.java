package com.e9.framework.handler.codec.sgip;

import java.io.Serializable;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.sgip.property.SgipLength;
import com.e9.framework.handler.codec.smgp.property.SmgpLength;
import com.e9.framework.handler.codec.util.MessageHelper;
import com.e9.framework.util.Common;

/**
 * @date 2013-8-24
 * @author Jason
 */
public abstract class AbstractSgipMessage extends MessageHelper implements SgipMessage,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2195096803043429687L;

	/**
	 * 数据包长度
	 */
	private Integer packetLength;
	
	/**
	 * 请求标识
	 */
	private Integer commandId;
	
	/**
	 * 消息流水号
	 */
	private String hexSequenceNumber;
	private Integer nodeCode;
	private Integer intDate;
	private Integer sequenceId;
	private boolean syncMessage = false;
	private int beginIndex;
	
	
	protected void headEncode(ChannelBuffer buffer){
		if( null == packetLength || 
			null == commandId 
		){
			throw new NullPointerException("PacketLength/RequestId is null!");
		}
		writeInt(buffer, packetLength, SmgpLength.PACKET_LENGTH);
		writeInt(buffer, commandId, SmgpLength.REQUEST_ID);
		buffer.writeInt(nodeCode);
		buffer.writeInt(intDate);
		buffer.writeInt(sequenceId);
	}
	
	protected void headDecode(ChannelBuffer buffer){
		setPacketLength(readInt(buffer,SmgpLength.PACKET_LENGTH));
		setCommandId(readInt(buffer,SmgpLength.REQUEST_ID));
		
		int firstIndex = buffer.readerIndex();
		setNodeCode(readInt(buffer, SgipLength.INT4));
		setIntDate(readInt(buffer, SgipLength.INT4));
		setSequenceId(readInt(buffer, SgipLength.INT4));
		
		buffer.writerIndex(firstIndex);
		setHexSequenceNumber(Common.toHex(buffer.readBytes(SgipLength.SEQUENCE_ID).array()));
	}
	
	protected ChannelBuffer beginEncode(IoBuffer ioBuffer){
		if(!validateParameters()){
			throw new RuntimeException("Invalid parameters:"+this.toString());
		}
		ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
		ensureChannelBufferCanWrite(buffer);

		beginIndex = buffer.writerIndex();
		buffer.writerIndex(beginIndex + SgipLength.SGIP_HEADER);
		
		return buffer;
	}
	
	protected void endEncode(ChannelBuffer buffer){
		// 设置数据包长度
		int endIndex = buffer.writerIndex();
		setPacketLength(endIndex - beginIndex);
		
		// 进行消息头编码
		buffer.writerIndex(beginIndex);
		headEncode(buffer);
		
		buffer.writerIndex(endIndex);
	}
	
	protected ChannelBuffer beginDecode(IoBuffer ioBuffer){
		ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
		ensureChannelBufferCanRead(buffer);
		headDecode(buffer);
		
		return buffer;
	}
	
	/**
	 * @return the packetLength
	 */
	protected Integer getPacketLength() {
		return packetLength;
	}

	/**
	 * @param packetLength the packetLength to set
	 */
	protected void setPacketLength(Integer packetLength) {
		this.packetLength = packetLength;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#setCommandId(java.lang.Integer)
	 */
	@Override
	public void setCommandId(Integer commandid) {
		// TODO Auto-generated method stub
		this.commandId = commandid;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#getCommandId()
	 */
	@Override
	public Integer getCommandId() {
		// TODO Auto-generated method stub
		return commandId;
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#setSyncMessage(boolean)
	 */
	@Override
	public void setSyncMessage(boolean sync) {
		// TODO Auto-generated method stub
		this.syncMessage = sync;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#isSyncMessage()
	 */
	@Override
	public boolean isSyncMessage() {
		// TODO Auto-generated method stub
		return this.syncMessage;
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.handler.codec.sgip.SgipMessage#setHexSequenceNumber(java.lang.String)
	 */
	@Override
	public void setHexSequenceNumber(String hexSequenceNumber) {
		// TODO Auto-generated method stub
		this.hexSequenceNumber = hexSequenceNumber;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.framework.handler.codec.sgip.SgipMessage#getHexSequenceNumber()
	 */
	@Override
	public String getHexSequenceNumber() {
		// TODO Auto-generated method stub
		return hexSequenceNumber;
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#getSequenceId()
	 */
	@Override
	public Integer getSequenceId() {
		// TODO Auto-generated method stub
		return this.sequenceId;
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#setSequenceId(java.lang.Integer)
	 */
	@Override
	public void setSequenceId(Integer sequenceId) {
		// TODO Auto-generated method stub
		this.sequenceId = sequenceId;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.framework.handler.codec.sgip.SgipMessage#setIntDate(java.lang.Integer)
	 */
	@Override
	public void setIntDate(Integer intDate) {
		// TODO Auto-generated method stub
		this.intDate = intDate;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.framework.handler.codec.sgip.SgipMessage#getIntDate()
	 */
	@Override
	public Integer getIntDate() {
		// TODO Auto-generated method stub
		return intDate;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.framework.handler.codec.sgip.SgipMessage#setNodeCode(java.lang.Integer)
	 */
	@Override
	public void setNodeCode(Integer nodeCode) {
		// TODO Auto-generated method stub
		this.nodeCode = nodeCode;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.framework.handler.codec.sgip.SgipMessage#getNodeCode()
	 */
	@Override
	public Integer getNodeCode() {
		// TODO Auto-generated method stub
		return nodeCode;
	}
	
	/**
	 * @return the beginIndex
	 */
	public int getBeginIndex() {
		return beginIndex;
	}

	/**
	 * @param beginIndex the beginIndex to set
	 */
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	/**
	 * 验证参数有效性
	 * @return
	 */
	protected abstract boolean validateParameters();

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AbstractSgipMessage [packetLength=" + packetLength + ", commandId=" + commandId
				+ ", hexSequenceNumber=" + hexSequenceNumber + ", nodeCode=" + nodeCode
				+ ", intDate=" + intDate + ", sequenceId=" + sequenceId + ", syncMessage="
				+ syncMessage + "]";
	}

	
}
