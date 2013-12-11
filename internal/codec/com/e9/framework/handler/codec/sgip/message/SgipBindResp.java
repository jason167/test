package com.e9.framework.handler.codec.sgip.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.sgip.AbstractSgipMessage;
import com.e9.framework.handler.codec.sgip.property.SgipCommandId;
import com.e9.framework.handler.codec.sgip.property.SgipLength;
import com.e9.framework.util.Common;

/**
 * bind应答命令，见SGIP 1.21版4.2.3.1.2章节
 * @date 2013-8-24
 * @author Jason
 */
public class SgipBindResp extends AbstractSgipMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -848427238633104767L;
	/**
	 * <pre>
		Bind执行命令是否成功。
		0：执行成功
		其它：错误码
	 * </pre>
	 */
	private Integer result = 0;
	private String reserve = "";
	/**
	 * 
	 */
	public SgipBindResp() {
		// TODO Auto-generated constructor stub
		setCommandId(SgipCommandId.BIND_RESP);
		setSyncMessage(true);
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#encode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
		try {
			ChannelBuffer buffer = beginEncode(ioBuffer);
			writeInt(buffer, getResult(), SgipLength.RESULT);
			writeOctetString(buffer, getReserve(), SgipLength.RESERVE);
			endEncode(buffer);
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
		ChannelBuffer buffer = null;
		try {
			buffer = beginDecode(ioBuffer);
			setResult(readInt(buffer, SgipLength.RESULT));
			setReserve(readOctetString(buffer, SgipLength.RESERVE));
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(buffer == null ? "" : Common.toHex(buffer.array()), e);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "SgipBindResp [result=" + result + ", reserve=" + reserve + "]";
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.handler.codec.sgip.AbstractSgipMessage#validateParameters()
	 */
	@Override
	@Deprecated
	protected boolean validateParameters() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * @return the result
	 */
	public Integer getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(Integer result) {
		this.result = result;
	}

	/**
	 * @return the reserve
	 */
	public String getReserve() {
		return reserve;
	}

	/**
	 * @param reserve the reserve to set
	 */
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	

}
