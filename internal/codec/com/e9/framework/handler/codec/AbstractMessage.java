package com.e9.framework.handler.codec;


import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.channel.i.Message;
import com.e9.framework.handler.codec.smgp.property.SmgpLength;
import com.e9.framework.handler.codec.util.MessageHelper;

/**
 * 基础协议编解码器的超类
 * @project E9Framework
 * @date 2013-4-19
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-19] create by Jason
 */
public abstract class AbstractMessage extends MessageHelper implements Message,java.io.Serializable {
	
	
	private static final long serialVersionUID = -8904909725455309970L;

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
	private Integer sequenceId;
	protected boolean syncMessage = false;
	
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

	/*
	 * (non-Javadoc)
	 * @see com.e9.tcp.framework.Message#getSequenceId()
	 */
	@Override
	public Integer getSequenceId() {
		return sequenceId;
	}

	/*
	 * (non-Javadoc)
	 * @see com.e9.tcp.framework.Message#setSequenceId(java.lang.Integer)
	 */
	@Override
	public void setSequenceId(Integer sequenceId) {
		this.sequenceId = sequenceId;
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
	 * @see com.e9.tcp.framework.Message#getCommandId()
	 */
	@Override
	public Integer getCommandId() {
		// TODO Auto-generated method stub
		return this.commandId;
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
	 * @see com.e9.tcp.framework.Message#isSyncMessage()
	 */
	@Override
	public boolean isSyncMessage() {
		// TODO Auto-generated method stub
		return syncMessage;
	}
	
	protected void headEncode(ChannelBuffer buffer){
		if( null == packetLength || 
			null == commandId ||
			null == sequenceId 
		){
			throw new NullPointerException("PacketLength/RequestId/SequenceId is null!");
		}
		writeInt(buffer, packetLength, SmgpLength.INT4);
		writeInt(buffer, commandId, SmgpLength.INT4);
		writeInt(buffer, sequenceId, SmgpLength.INT4);
	}
	
	protected void headDecode(ChannelBuffer buffer){
		setPacketLength(readInt(buffer,SmgpLength.INT4));
		setCommandId(readInt(buffer,SmgpLength.INT4));
		setSequenceId(readInt(buffer,SmgpLength.INT4));
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Message#encode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public abstract void encode(IoBuffer ioBuffer) throws Exception;

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Message#decode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public abstract void decode(IoBuffer ioBuffer) throws Exception;
	
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
		return "MessageHeader [packetLength=" + packetLength + ", commandId=0x" + (commandId == null ? 0 : Integer.toHexString(commandId))
				+ ", sequenceId=" + sequenceId + "]";
	}
	
	

}
