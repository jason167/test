package com.e9.framework.handler.codec.smgp.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.smgp.property.SmgpCommandId;
import com.e9.framework.handler.codec.smgp.property.SmgpLength;
import com.e9.framework.util.Common;
import com.e9.framework.util.algorithm.MD5;

/**
 * Login_Resp,见《中国电信CTMP开发接口－中国电信SMGP协议》5.2.2.1.2章节
 * @project 33e9
 * @date 2012-7-24
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-24] create by zengweizhi
 */
public class SmgpLoginResp extends AbstractMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7893571228827077089L;

	/**
	 * 
	 */
	public SmgpLoginResp() {
		// TODO Auto-generated constructor stub
		super.setCommandId(SmgpCommandId.LOGIN_RESP);
		this.syncMessage = true;
	}
	
	/**
	 * 请求返回结果。响应包用来向请求包返回成功信息或者失败原因。
	 * @see {@link SmgpStatus}
	 */
	private Integer status;
	
	/**
	 * Login服务器端返回给客户端的认证码，当客户端认证出错时，此项为空。
	 * 其值通过单向MD5 hash计算得出，表示如下：
	 * AuthenticatorServer =MD5（Status+AuthenticatorClient + Shared secret）
	 * Shared secret 由服务器端与客户端事先商定,最长15字节AuthenticatorClient为客户端发送给服务器端的Login中的值。
	 * 参见{@link SmgpLogin#authenticatorClient}。

	 */
	private byte[] authenticatorServer;
	
	/**
	 * 服务器端支持的最高版本号。
	 * 高4位表示主版本号，低4位表示次版本号。
	 * 例如0x13，表示协议版本号1.3。
	 */
	private Integer serverVersion;

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the authenticatorServer
	 */
	public byte[] getAuthenticatorServer() {
		return authenticatorServer;
	}

	/**
	 * @param authenticatorServer the authenticatorServer to set
	 */
	public void setAuthenticatorServer(byte[] authenticatorServer) {
		this.authenticatorServer = authenticatorServer;
	}

	/**
	 * @return the serverVersion
	 */
	public Integer getServerVersion() {
		return serverVersion;
	}

	/**
	 * @param serverVersion the serverVersion to set
	 */
	public void setServerVersion(Integer serverVersion) {
		this.serverVersion = serverVersion;
	}
	
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

	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		
		try {
			if(!validateParameters()){
				throw new RuntimeException("Invalid parameters");
			}
			
			ChannelBuffer buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanWrite(buffer);
			int beginIndex = buffer.writerIndex();
			buffer.writerIndex(beginIndex + SmgpLength.SMGP_HEADER);

			// buffer.writeInt(getStatus());
			writeInt(buffer, getStatus(), SmgpLength.STATUS);
			
			// buffer.writeBytes(octetStringByteArray(getAuthenticatorServer(),Length.AUTHENTICATOR_SERVER));
			// writeOctetString(buffer, getAuthenticatorServer(), Length.AUTHENTICATOR_SERVER);
			writeBytes(buffer, getAuthenticatorServer());
			
			writeInt(buffer, getServerVersion(), SmgpLength.SERVER_VERSION);
//			buffer.writeByte(0x13);
			
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

	@Override
	public void decode(IoBuffer ioBuffer) throws Exception {
		ChannelBuffer buffer = null;
		try {
			buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanRead(buffer);
			headDecode(buffer);
			
			// setStatus(buffer.readInt());
			setStatus(readInt(buffer, SmgpLength.STATUS));
			
			// setAuthenticatorServer(new String(buffer.readBytes(Length.AUTHENTICATOR_SERVER).array()).trim());
			// setAuthenticatorServer(readOctetString(buffer, Length.AUTHENTICATOR_SERVER));
			setAuthenticatorServer(buffer.readBytes(SmgpLength.AUTHENTICATOR_SERVER).array());
			
			// setServerVersion(Short.valueOf(buffer.readUnsignedByte()).intValue());
			setServerVersion(readInt(buffer, SmgpLength.SERVER_VERSION));
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(buffer == null ? "" : Common.toHex(buffer.array()), e);
		}
	}

	@Override
	protected boolean validateParameters() {
		boolean valid = false;
		
		// 必选参数检查
		valid = 
			null != status && 
			null != authenticatorServer && 
			null != serverVersion 
		;
		
		return valid;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "SmgpLoginResp [status=" + status + ", authenticatorServer="
				+ Arrays.toString(authenticatorServer) + ", serverVersion=" + serverVersion + "]";
	}


}
