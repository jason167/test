package com.e9.framework.handler.codec.smgp.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.smgp.property.SmgpCommandId;
import com.e9.framework.handler.codec.smgp.property.SmgpLength;

/**
 * ActiveTestResp,见《中国电信CTMP开发接口－中国电信SMGP协议》5.2.2.4.2章节
 * @project E9Framework
 * @date 2013-1-14
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-14] create by Jason
 */
public class SmgpActiveTestResp extends AbstractMessage {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3198567573158974740L;

	/**
	 * 
	 */
	public SmgpActiveTestResp() {
		// TODO Auto-generated constructor stub
		super.setCommandId(SmgpCommandId.ACTIVE_TEST_RESP);
		this.syncMessage = true;
	}
	
	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.smgp.AbstractSmgpMessage#encode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
		try {
			ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanWrite(buffer);
			setPacketLength(SmgpLength.SMGP_HEADER);
			headEncode(buffer);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.smgp.AbstractSmgpMessage#decode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer ioBuffer) throws Exception {
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
	 * @see com.e9.tcp.gateway.smgp.AbstractSmgpMessage#validateParameters()
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
		return super.toString() + "SmgpActiveTestResp []";
	}

	
}
