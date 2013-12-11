package com.e9.framework.handler.codec.sgip.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.sgip.AbstractSgipMessage;
import com.e9.framework.handler.codec.sgip.property.SgipCommandId;
import com.e9.framework.handler.codec.sgip.property.SgipLength;
import com.e9.framework.handler.codec.sgip.property.SgipLoginType;
import com.e9.framework.util.Common;

/**
 * bind命令，见SGIP 1.21版4.2.3.1.1章节
 * @date 2013-8-24
 * @author Jason
 */
public class SgipBind extends AbstractSgipMessage {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3553998009269214463L;
	/**
	 * <pre>
		登录类型。
		1：SP向SMG建立的连接，用于发送命令
		2：SMG向SP建立的连接，用于发送命令
		3：SMG之间建立的连接，用于转发命令
		4：SMG向GNS建立的连接，用于路由表的检索和维护
		5：GNS向SMG建立的连接，用于路由表的更新
		6：主备GNS之间建立的连接，用于主备路由表的一致性
		11：SP与SMG以及SMG之间建立的测试连接，用于跟踪测试
		其它：保留
	 * </pre>
	 */
	private Integer loginType = SgipLoginType.SP_2_SMG;
	
	/**
	 * 服务器端给客户端分配的登录名
	 */
	private String loginName;
	
	/**
	 * 服务器端和Login Name对应的密码
	 */
	private String loginPasword;
	
	/**
	 * 保留，扩展用
	 */
	private String reserve = "";
	
	
	/**
	 * 
	 */
	public SgipBind() {
		// TODO Auto-generated constructor stub
		setCommandId(SgipCommandId.BIND);
	}
	
	/**
	 * 
	 */
	public SgipBind(String loginName, String loginPassword) {
		// TODO Auto-generated constructor stub
		setCommandId(SgipCommandId.BIND);
		this.loginName = loginName;
		this.loginPasword = loginPassword;
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#encode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub

		try {
			ChannelBuffer buffer = beginEncode(ioBuffer);
			writeInt(buffer, getLoginType(), SgipLength.LOGIN_TYPE);
			writeOctetString(buffer, getLoginName(), SgipLength.LOGIN_NAME);
			writeOctetString(buffer, getLoginPasword(), SgipLength.LOGIN_PASSWORD);
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
			setLoginType(readInt(buffer, SgipLength.LOGIN_TYPE));
			setLoginName(readOctetString(buffer, SgipLength.LOGIN_NAME));
			setLoginPasword(readOctetString(buffer, SgipLength.LOGIN_PASSWORD));
			setReserve(readOctetString(buffer, SgipLength.RESERVE));
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(buffer == null ? "" : Common.toHex(buffer.array()), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.handler.codec.sgip.AbstractSgipMessage#validateParameters()
	 */
	@Override
	protected boolean validateParameters() {
		// TODO Auto-generated method stub
		boolean valid = false;
		
		// 必选参数检查
		valid = 
			null != loginName && 
			null != loginPasword
		;
		
		return valid;
	}
	
	

	/**
	 * @return the loginType
	 */
	public Integer getLoginType() {
		return loginType;
	}

	/**
	 * @param loginType the loginType to set
	 */
	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the loginPasword
	 */
	public String getLoginPasword() {
		return loginPasword;
	}

	/**
	 * @param loginPasword the loginPasword to set
	 */
	public void setLoginPasword(String loginPasword) {
		this.loginPasword = loginPasword;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "SgipBind [loginType=" + loginType + ", loginName=" + loginName + ", loginPasword="
				+ loginPasword + ", reserve=" + reserve + "]";
	}

	
}
