package com.e9.framework.handler.codec.ex.gw.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.handler.codec.ex.gw.property.GwStatus;
import com.e9.framework.util.Common;

/**
 * 退出应答命令，见《短信通道协议开发包V3.06(内部使用)》3.2.5.2章节
 * @date 2013-8-29
 * @author Jason
 */
public class GwExitResp extends AbstractGwMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4801840618106326902L;
	private int status = GwStatus.SUCCESSFUL.ordinal();
	/**
	 * 
	 */
	public GwExitResp() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.EXIT_RESP);
		setSyncMessage(true);
	}
	/**
	 * 
	 */
	public GwExitResp(int status) {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.EXIT_RESP);
		this.status = status;
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
			throw new Exception(this.toString(), e);
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
		return super.toString() + "GwExitResp [status=0x" + Integer.toHexString(status) + "]";
	}
	
	

}
