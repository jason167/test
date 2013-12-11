package com.e9.framework.handler.codec.cmpp.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.cmpp.property.CmppCommandId;
import com.e9.framework.handler.codec.cmpp.property.CmppLength;

/**
 * ActiveTestResp,见CMPP v2.0 7.4.7.2章节
 * @project E9Framework
 * @date 2013-1-24
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-24] create by Jason
 */
public class CmppActiveTestResp extends AbstractMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7856390860967852263L;
	private byte reserved = 0x01;
	/**
	 * 
	 */
	public CmppActiveTestResp() {
		// TODO Auto-generated constructor stub
		super.setCommandId(CmppCommandId.ACTIVE_TEST_RESP);
		this.syncMessage = true;
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.cmpp.AbstractCmppMessage#encode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception{
		// TODO Auto-generated method stub
		try {
			ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanWrite(buffer);
			
			setPacketLength(CmppLength.CMPP_HEADER + 1);
			headEncode(buffer);
			buffer.writeByte(reserved);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.cmpp.AbstractCmppMessage#decode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer ioBuffer) throws Exception{
		// TODO Auto-generated method stub
		try {
			ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanRead(buffer);
			
			headDecode(buffer);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.cmpp.AbstractCmppMessage#validateParameters()
	 */
	@Override
	protected boolean validateParameters() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "ActiveTestResp [reserved=" + reserved + "]";
	}
	

}
