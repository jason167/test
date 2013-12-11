package com.e9.framework.handler.codec.ex.gw.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.handler.codec.ex.gw.property.GwStatus;
import com.e9.framework.util.Common;

/**
 * 查询费用应答，见《短信通道协议开发包V3.06(内部使用)》3.2.5.6章节
 * @date 2013-9-2
 * @author Jason
 */
public class GwQueryFeeResp extends AbstractGwMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2282485838836517031L;
	
	/**
	 * 状态
	 */
	private int status = GwStatus.SUCCESSFUL.ordinal();
	
	/**
	 * 余额
	 */
	private String balance;
	
	/**
	 * 发送一条信息要扣的通道费
	 */
	private String mtFee;
	
	/**
	 * 个种状态的记录数（暂时不提供）
	 */
	private int numRec;
	
	/**
	 * 状态1代码（暂时不提供）
	 */
	private int Code1;
	
	/**
	 * 状态1记录数（暂时不提供）
	 */
	private int numCode;
	
	/**
	 * 
	 */
	public GwQueryFeeResp() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.QUERY_FEE_RESP);
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
			setBalance(readOctetString(buffer, GwLength.BALANCE));
			setMtFee(readOctetString(buffer, GwLength.MT_FEE));
			setNumRec(readInt(buffer, GwLength.NUM_REC));
			setCode1(readInt(buffer, GwLength.CODE_1));
			setNumCode(readInt(buffer, GwLength.NUM_CODE));
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
			writeOctetString(buffer, getBalance(), GwLength.BALANCE);
			writeOctetString(buffer, getMtFee(), GwLength.MT_FEE);
			writeInt(buffer, getNumRec(), GwLength.NUM_REC);
			writeInt(buffer, getCode1(), GwLength.CODE_1);
			writeInt(buffer, getNumCode(), GwLength.NUM_CODE);
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
		boolean valid = false;
		valid = 
				balance != null &&
				mtFee != null;
		
		return valid;
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
	 * @return the balance
	 */
	public String getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	public void setBalance(String balance) {
		this.balance = balance;
	}

	/**
	 * @return the mtFee
	 */
	public String getMtFee() {
		return mtFee;
	}

	/**
	 * @param mtFee the mtFee to set
	 */
	public void setMtFee(String mtFee) {
		this.mtFee = mtFee;
	}

	/**
	 * @return the numRec
	 */
	public int getNumRec() {
		return numRec;
	}

	/**
	 * @param numRec the numRec to set
	 */
	public void setNumRec(int numRec) {
		this.numRec = numRec;
	}

	/**
	 * @return the code1
	 */
	public int getCode1() {
		return Code1;
	}

	/**
	 * @param code1 the code1 to set
	 */
	public void setCode1(int code1) {
		Code1 = code1;
	}

	/**
	 * @return the numCode
	 */
	public int getNumCode() {
		return numCode;
	}

	/**
	 * @param numCode the numCode to set
	 */
	public void setNumCode(int numCode) {
		this.numCode = numCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "GwQueryFeeResp [status=" + status + ", balance=" + balance + ", mtFee=" + mtFee
				+ ", numRec=" + numRec + ", Code1=" + Code1 + ", numCode=" + numCode + "]";
	}
	
	

	
}
