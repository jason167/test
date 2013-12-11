package com.e9.framework.handler.codec.cmpp.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.cmpp.property.CmppCommandId;
import com.e9.framework.handler.codec.cmpp.property.CmppLength;
import com.e9.framework.util.Common;
import com.e9.framework.util.algorithm.MD5;

/**
 * ConnectResp,见CMPP v2.0 7.4.1.2章节
 * @project E9Framework
 * @date 2013-1-24
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-24] create by Jason
 */
public class CmppConnectResp extends AbstractMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6795365045478050904L;

	/**
	 * 
	 */
	public CmppConnectResp() {
		// TODO Auto-generated constructor stub
		super.setCommandId(CmppCommandId.CONNECT_RESP);
		this.syncMessage = true;
	}

	/**
	 * 状态
			0：正确
			1：消息结构错
		 	2：非法源地址
		 	3：认证错
		 	4：版本太高
		  	5~ ：其他错误
	 */
	private Integer Status;
	
	/**
	 * ISMG认证码，用于鉴别ISMG。
		其值通过单向MD5 hash计算得出，表示如下：
		AuthenticatorISMG =MD5（Status+AuthenticatorSource+shared secret），Shared secret 由中国移动与源地址实体事先商定，AuthenticatorSource为源地址实体发送给ISMG的对应消息CMPP_Connect中的值。
		 认证出错时，此项为空。
	 */
	private byte[] AuthenticatorISMG;
	
	/**
	 * 服务器支持的最高版本号
	 */
	private Integer Version;
	
	/**
	 * 
	 * @param authenticatorSource
	 * @param password
	 * @return bytes
	 * @throws Exception
	 */
	public byte[] password2Bytes(String authenticatorSource, String password) throws Exception{
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			out.write(getStatus());
			out.write(Common.hex2Bytes(authenticatorSource));
			out.write(password.getBytes());
			return MD5.encode(out.toByteArray());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.cmpp.AbstractCmppMessage#encode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception{
		// TODO Auto-generated method stub
		try {
			if(!validateParameters()){
				throw new RuntimeException("Invalid parameters:"+this.toString());
			}
			
			ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanWrite(buffer);
			int beginIndex = buffer.writerIndex();
			buffer.writerIndex(beginIndex + CmppLength.CMPP_HEADER);
			
			writeInt(buffer, getStatus(), CmppLength.STATUS);
			writeBytes(buffer, getAuthenticatorISMG());
			writeInt(buffer, getVersion(), CmppLength.VERSION);
			
			int endIndex = buffer.writerIndex();
			setPacketLength(endIndex - beginIndex);
			
			buffer.writerIndex(beginIndex);
			headEncode(buffer);
			
			buffer.writerIndex(endIndex);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(this.toString(), e);
		}

	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.cmpp.AbstractCmppMessage#decode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
		ChannelBuffer buffer = null;
		try {
			buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanRead(buffer);
			headDecode(buffer);
			
			setStatus(readInt(buffer, CmppLength.STATUS));
			setAuthenticatorISMG(buffer.readBytes(CmppLength.AUTHENTICATORISMG).array());
			setVersion(readInt(buffer, CmppLength.VERSION));
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(buffer == null ? "" : Common.toHex(buffer.array()), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.cmpp.AbstractCmppMessage#validateParameters()
	 */
	@Override
	protected boolean validateParameters() {
		// TODO Auto-generated method stub
		boolean valid = false;
		
		// 必选参数检查
		valid = 
			null != Status && 
			null != AuthenticatorISMG && 
			null != Version 
		;
		
		return valid;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return Status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		Status = status;
	}

	/**
	 * @return the authenticatorISMG
	 */
	public byte[] getAuthenticatorISMG() {
		return AuthenticatorISMG;
	}

	/**
	 * @param authenticatorISMG the authenticatorISMG to set
	 */
	public void setAuthenticatorISMG(byte[] authenticatorISMG) {
		AuthenticatorISMG = authenticatorISMG;
	}

	/**
	 * @return the version
	 */
	public Integer getVersion() {
		return Version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(Integer version) {
		Version = version;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " ConnectResp [Status=" + Status + ", AuthenticatorISMG="
				+ Arrays.toString(AuthenticatorISMG) + ", Version=" + Version + "]";
	}
	

	
}
