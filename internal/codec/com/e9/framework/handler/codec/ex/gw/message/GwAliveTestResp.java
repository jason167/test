package com.e9.framework.handler.codec.ex.gw.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.util.Common;

/**
 * 链路检测应答命令，见《短信通道协议开发包V3.06(内部使用)》3.2.5.7章节
 * @date 2013-8-29
 * @author Jason
 */
public class GwAliveTestResp extends AbstractGwMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2864940187537420199L;
	/**
	 * 应答，值恒定为0x01
	 */
	private int status = 0x1;
	/**
	 * 
	 */
	public GwAliveTestResp() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.ALIVETEST_RESP);
		setSyncMessage(true);
	}

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
			endEncode(buffer);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/* (non-Javadoc)
	 * @see com.eap.codec.ex.gw.AbstractGwMessage#validateParameters()
	 */
	@Override
	protected boolean validateParameters() {
		// TODO Auto-generated method stub
		return true;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "GwAliveTestResp [status=0x" + Integer.toHexString(status) + "]";
	}

	
}
