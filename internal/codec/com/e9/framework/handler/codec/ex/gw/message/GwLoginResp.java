package com.e9.framework.handler.codec.ex.gw.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.handler.codec.ex.gw.property.GwLoginStatus;
import com.e9.framework.util.Common;

/**
 * 登陆应答，见《短信通道协议开发包V3.06(内部使用)》3.2.5.2章节
 * @date 2013-8-29
 * @author Jason
 */
public class GwLoginResp extends AbstractGwMessage {

	public GwLoginResp() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.LOGIN_RESP);
		setSyncMessage(true);
	}
	
	public GwLoginResp(int status) {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.LOGIN_RESP);
		this.status = status;
		setSyncMessage(true);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4842634187637455516L;
	/**
	 * 登录结果
	 */
	private int status = GwLoginStatus.SUCCESSFUL.ordinal();
	/**
	 * 本次会话使用的密码
	 */
	private String cPwd = "";
	

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
			//buffer.skipBytes(GwLength.CPASS);
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
			writeOctetString(buffer, getcPwd(), GwLength.CPASS);
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

	/**
	 * @return the cPwd
	 */
	public String getcPwd() {
		return cPwd;
	}

	/**
	 * @param cPwd the cPwd to set
	 */
	public void setcPwd(String cPwd) {
		this.cPwd = cPwd;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "GwLoginResp [status=0x" + Integer.toHexString(status) + ", cPwd=" + cPwd + "]";
	}
	
	

}
