package com.e9.framework.handler.codec.ex;

import java.io.Serializable;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.handler.codec.util.MessageHelper;


/**
 * @date 2013-8-29
 * @author Jason
 */
public abstract class AbstractGwMessage extends MessageHelper implements GwMessage, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8495102824243253357L;

	private Integer packetLength;
	private Integer commandId;
	private boolean syncMessage = false;
	private String uid;
	private String sequenceid;
	private int beginIndex;
	
	protected void headEncode(ChannelBuffer buffer){
		if( null == packetLength || 
			null == commandId 
		){
			throw new NullPointerException("PacketLength/RequestId is null!");
		}
		writeInt(buffer, getPacketLength(), GwLength.PACKET_LENGTH);
		writeInt(buffer, getCommandId(), GwLength.REQUEST_ID);
		writeOctetString(buffer, getUid(), GwLength.USER_ID);
		writeOctetString(buffer, getSequenceString(), GwLength.SEQUENCE_ID);
	}
	
	protected void headDecode(ChannelBuffer buffer){
		setPacketLength(readInt(buffer,GwLength.PACKET_LENGTH));
		setCommandId(readInt(buffer,GwLength.REQUEST_ID));
		setUid(readOctetString(buffer, GwLength.USER_ID));
		setSequenceString(readOctetString(buffer, GwLength.SEQUENCE_ID));
	}
	
	protected ChannelBuffer beginEncode(IoBuffer ioBuffer){
		if(!validateParameters()){
			throw new RuntimeException("Invalid parameters:"+this.toString());
		}
		ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
		ensureChannelBufferCanWrite(buffer);

		beginIndex = buffer.writerIndex();
		buffer.writerIndex(beginIndex + GwLength.GW_HEADER);
		
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
	
	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#getCommandId()
	 */
	@Override
	public Integer getCommandId() {
		// TODO Auto-generated method stub
		return commandId;
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#getSequenceId()
	 */
	@Override
	@Deprecated
	public Integer getSequenceId() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#isSyncMessage()
	 */
	@Override
	public boolean isSyncMessage() {
		// TODO Auto-generated method stub
		return syncMessage;
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#setCommandId(java.lang.Integer)
	 */
	@Override
	public void setCommandId(Integer commandId) {
		// TODO Auto-generated method stub
		this.commandId = commandId;
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#setSequenceId(java.lang.Integer)
	 */
	@Override
	@Deprecated
	public void setSequenceId(Integer arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#setSyncMessage(boolean)
	 */
	@Override
	public void setSyncMessage(boolean syncMessage) {
		// TODO Auto-generated method stub
		this.syncMessage = syncMessage;
	}

	/* (non-Javadoc)
	 * @see com.eap.codec.ex.gw.message.GwMessage#getSequenceString()
	 */
	@Override
	public String getSequenceString() {
		// TODO Auto-generated method stub
		return sequenceid;
	}

	/* (non-Javadoc)
	 * @see com.eap.codec.ex.gw.message.GwMessage#setSequenceString(java.lang.String)
	 */
	@Override
	public void setSequenceString(String sequence) {
		// TODO Auto-generated method stub
		this.sequenceid = sequence;
	}

	/* (non-Javadoc)
	 * @see com.eap.codec.ex.gw.message.GwMessage#getUid()
	 */
	@Override
	public String getUid() {
		// TODO Auto-generated method stub
		return uid;
	}

	/* (non-Javadoc)
	 * @see com.eap.codec.ex.gw.message.GwMessage#setUid(java.lang.String)
	 */
	@Override
	public void setUid(String uid) {
		// TODO Auto-generated method stub
		this.uid = uid;
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
	
	/**
	 * 验证参数有效性
	 * @return
	 */
	protected abstract boolean validateParameters();

}
