package com.e9.framework.handler.codec.ex.gw.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.util.Common;

/**
 * 链路检测命令，见《短信通道协议开发包V3.06(内部使用)》3.2.5.7章节
 * @date 2013-8-29
 * @author Jason
 */
public class GwAliveTest extends AbstractGwMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7423198081508807379L;
	/**
	 * 
	 */
	public GwAliveTest() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.ALIVETEST);
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#decode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer arg0) throws Exception {
		// TODO Auto-generated method stub
		ChannelBuffer buffer = null;
		try{
			buffer = beginDecode(arg0);
		}catch(Exception e){
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
			endEncode(beginEncode(arg0));
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "GwAliveTest []";
	}
	
	

}
