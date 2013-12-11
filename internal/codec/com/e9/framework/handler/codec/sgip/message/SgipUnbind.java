package com.e9.framework.handler.codec.sgip.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.sgip.AbstractSgipMessage;
import com.e9.framework.handler.codec.sgip.property.SgipCommandId;
import com.e9.framework.handler.codec.sgip.property.SgipLength;

/**
 * unbind命令，见SGIP 1.21版4.2.3.2.1章节
 * @date 2013-8-24
 * @author Jason
 */
public class SgipUnbind extends AbstractSgipMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9106467159287696296L;
	
	/**
	 * 
	 */
	public SgipUnbind() {
		// TODO Auto-generated constructor stub
		setCommandId(SgipCommandId.UNBIND);
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#encode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
		try {
			ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanWrite(buffer);
			setPacketLength(SgipLength.SGIP_HEADER);
			headEncode(buffer);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(this.toString(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#decode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
		try {
			beginDecode(ioBuffer);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.handler.codec.sgip.AbstractSgipMessage#validateParameters()
	 */
	@Override
	protected boolean validateParameters() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "SgipUnbind []";
	}

	
}
