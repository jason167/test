package com.e9.framework.handler.codec.string.message;


import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.string.property.StringCommandId;
import com.e9.framework.handler.codec.string.property.StringLength;
import com.e9.framework.util.Common;

/**
 * @project E9Framework
 * @date 2013-4-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-20] create by Jason
 */
public class Hello extends AbstractMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7016965382909284998L;
	private String msg;
	
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

	/**
	 * 
	 */
	public Hello() {
		// TODO Auto-generated constructor stub
		setCommandId(StringCommandId.HELLO);
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.AbstractMessage#encode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
		ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
		
		int beginIndex = buffer.writerIndex();
		buffer.writerIndex(beginIndex + StringLength.STRING_HEADER);
		
		int length = getMsg().getBytes("gbk").length;
		writeInt(buffer, length, StringLength.INT1);
		writeOctetString(buffer, getMsg(), length);
		
		int endIndex = buffer.writerIndex();
		setPacketLength(endIndex - beginIndex);
		
		buffer.writerIndex(beginIndex);
		headEncode(buffer);
		
		buffer.writerIndex(endIndex);
		
	}
	
	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.AbstractMessage#decode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
		ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
		
		System.out.println(Common.toHex(buffer.array()));
		headDecode(buffer);
		int length = readInt(buffer, StringLength.INT1);
		setMsg(readOctetString(buffer, length));
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.AbstractMessage#validateParameters()
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
		return super.toString() + "Hello [msg=" + msg + "]";
	}

	
}
