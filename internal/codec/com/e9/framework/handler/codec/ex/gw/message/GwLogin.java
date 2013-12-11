package com.e9.framework.handler.codec.ex.gw.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.handler.codec.ex.gw.property.GwLoginType;
import com.e9.framework.util.Common;

/**
 * 登陆请求，见《短信通道协议开发包V3.06(内部使用)》3.2.5.1章节
 * @date 2013-8-29
 * @author Jason
 */
public class GwLogin extends AbstractGwMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7146675592986141025L;
	private String pwd;
	/**
	 * <pre>
		当cmethod为空串时，表示不使用加密算法，
		当使用加密算法时仅加密数据体部分数据，
		如加密后长度发生了变化，则TotalLength数值应作改变。
	 * </pre>
	 */
	private String cmethod = "";
	
	/**
	 * 1支持MT, 2支持MT和MO,3 支持缓存方式MO(默认为2)
	 */
	private int type = GwLoginType.MT_AND_MO;
	
	/**
	 * 产品编号
	 */
	private String productid = "";
	
	/**
	 * (默认为空串)
	 */
	private String agentList = "";
	
	/**
	 * 
	 */
	public GwLogin() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.LOGIN);
	}
	
	/**
	 * 
	 */
	public GwLogin(String pwd) {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.LOGIN);
		this.pwd = pwd;
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
			setPwd(readOctetString(buffer, GwLength.PASSWORD));
			setCmethod(readOctetString(buffer, GwLength.CMETHOD));
			setType(readInt(buffer, GwLength.TYPE));
			setProductid(readOctetString(buffer, GwLength.PRODUCTID));
			setAgentList(readOctetString(buffer, GwLength.AGENT_LIST));
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
			writeOctetString(buffer, getPwd(), GwLength.PASSWORD);
			writeOctetString(buffer, getCmethod(), GwLength.CMETHOD);
			writeInt(buffer, getType(), GwLength.TYPE);
			writeOctetString(buffer, getProductid(), GwLength.PRODUCTID);
			writeOctetString(buffer, getAgentList(), GwLength.AGENT_LIST);
			endEncode(buffer);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(this.toString(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.eap.codec.ex.gw.message.AbstractGwMessage#validateParameters()
	 */
	@Override
	protected boolean validateParameters() {
		// TODO Auto-generated method stub
		return null != pwd;
	}

	/**
	 * @return the pwd
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * @param pwd the pwd to set
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * @return the cmethod
	 */
	public String getCmethod() {
		return cmethod;
	}

	/**
	 * @param cmethod the cmethod to set
	 */
	public void setCmethod(String cmethod) {
		this.cmethod = cmethod;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the productid
	 */
	public String getProductid() {
		return productid;
	}

	/**
	 * @param productid the productid to set
	 */
	public void setProductid(String productid) {
		this.productid = productid;
	}

	/**
	 * @return the agentList
	 */
	public String getAgentList() {
		return agentList;
	}

	/**
	 * @param agentList the agentList to set
	 */
	public void setAgentList(String agentList) {
		this.agentList = agentList;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "GwLogin [pwd=" + pwd + ", cmethod=" + cmethod + ", type=" + type + ", productid="
				+ productid + ", agentList=" + agentList + "]";
	}

	
}
