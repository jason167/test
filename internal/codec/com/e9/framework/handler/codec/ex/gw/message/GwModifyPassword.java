package com.e9.framework.handler.codec.ex.gw.message;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.ex.AbstractGwMessage;
import com.e9.framework.handler.codec.ex.gw.property.GwCommandId;
import com.e9.framework.handler.codec.ex.gw.property.GwLength;
import com.e9.framework.util.Common;

/**
 * 修改密码，见《短信通道协议开发包V3.06(内部使用)》3.2.5.10章节
 * @date 2013-9-2
 * @author Jason
 */
public class GwModifyPassword extends AbstractGwMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 477253278856451710L;
	private String oldPassword;
	private String newPassword;
	
	/**
	 * 
	 */
	public GwModifyPassword() {
		// TODO Auto-generated constructor stub
		setCommandId(GwCommandId.MODIFY_PASSWORD);
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
			setOldPassword(readOctetString(buffer, GwLength.OLD_PASS));
			setNewPassword(readOctetString(buffer, GwLength.NEW_PASS));
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
			writeOctetString(buffer, getOldPassword(), GwLength.OLD_PASS);
			writeOctetString(buffer, getNewPassword(), GwLength.NEW_PASS);
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
				oldPassword != null &&
				!oldPassword.equals("") &&
				newPassword != null &&
				!newPassword.equals("");
		
		return valid;
	}

	/**
	 * @return the oldPassword
	 */
	public String getOldPassword() {
		return oldPassword;
	}

	/**
	 * @param oldPassword the oldPassword to set
	 */
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}

	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "GwModifyPassword [oldPassword=" + oldPassword + ", newPassword=" + newPassword
				+ "]";
	}
	
	

}
