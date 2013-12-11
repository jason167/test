package com.e9.framework.handler.codec.smgp.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.smgp.property.SmgpCommandId;
import com.e9.framework.handler.codec.smgp.property.SmgpLength;
import com.e9.framework.handler.codec.util.TimestampUtils;
import com.e9.framework.util.Common;
import com.e9.framework.util.algorithm.MD5;

/**
 * Login,见《中国电信CTMP开发接口－中国电信SMGP协议》5.2.2.1.1章节
 * @project 33e9
 * @date 2012-7-24
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-24] create by zengweizhi
 */
public class SmgpLogin extends AbstractMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -509230186592326335L;
	/**
	 * 
	 */
	public SmgpLogin() {
		// TODO Auto-generated constructor stub
		super.setCommandId(SmgpCommandId.LOGIN);
	}
	/**
	 * 客户端用来登录服务器端的用户账号。
	 * 
	 * 当客户端为SP时，用户帐号为SP服务代码；
	 * 当客户端为SMGW时，用户帐号为SMGW代码；
	 * 当客户端为GNS时，用户帐号为GNS代码。
	 */
	private String clientId;
	
	/**
	 * 客户端认证码，用来鉴别客户端的合法性。
	 * 其值通过单向MD5 hash计算得出，表示如下：
	 * AuthenticatorClient =MD5（ClientID+7 字节的二进制0（0x00） + Shared secret+Timestamp）
	 * Shared secret 由服务器端与客户端事先商定，最长15字节。
	 * 此处Timestamp格式为：MMDDHHMMSS（月日时分秒），经TimeStamp字段值转换成字符串，转换后右对齐，左补0x30得到。
	 * 例如3月1日0时0分0秒，TimeStamp字段值为0x11F0E540，此处为0301000000。
	 */
	private String authenticatorClient;
	
	public byte[] password2Bytes(String password, int timestamp) throws Exception{
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			out.write(getClientId().getBytes());
			out.write(new byte[9]);
			out.write(password.getBytes());
			out.write(TimestampUtils.getStringTimestamp(timestamp).getBytes());
			
			return MD5.encode(out.toByteArray());
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		finally{
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 保留，企业信使平台全部使用双工模式。即一个连接即可用来发送submit也可用来接收deliver
	 */
	private Integer loginMode = 0x2;
	
	/**
	 * 时间戳。
	 * 例如3月1日0时0分0秒，用于MD5计算时为0301000000，此处转换为整型数即为11F0E540。
	 */
	private Integer timestamp;
	
	/**
	 * 客户端支持的协议版本号。
	 * 高4bit表示主版本号，低4bit表示次版本号。
	 * 例如0x13，表示协议版本号为1.3。
	 */
	private Integer clientVersion = 0x13;
	
	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the authenticatorClient
	 */
	public String getAuthenticatorClient() {
		return authenticatorClient;
	}

	/**
	 * @param authenticatorClient the authenticatorClient to set
	 */
	public void setAuthenticatorClient(String authenticatorClient) {
		this.authenticatorClient = authenticatorClient;
	}

	/**
	 * @return the loginMode
	 */
	public Integer getLoginMode() {
		return loginMode;
	}

	/**
	 * @param loginMode the loginMode to set
	 */
	public void setLoginMode(Integer loginMode) {
		this.loginMode = loginMode;
	}

	/**
	 * @return the timestamp
	 */
	public Integer getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Integer timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the clientVersion
	 */
	public Integer getClientVersion() {
		return clientVersion;
	}

	/**
	 * @param clientVersion the clientVersion to set
	 */
	public void setClientVersion(Integer clientVersion) {
		this.clientVersion = clientVersion;
	}
	
	@Override
	protected boolean validateParameters(){
		boolean valid = false;
		
		// 必选参数检查
		valid = 
			null != clientId && 
			null != authenticatorClient && 
//			null != loginMode && 
			null != timestamp && 
			null != clientVersion
		;
		
		return valid;
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
			// 先跳过消息头,以进行消息体编码
			buffer.writerIndex(beginIndex + SmgpLength.SMGP_HEADER);
			
			writeOctetString(buffer, getClientId(), SmgpLength.CLIENT_ID);
			writeBytes(buffer, password2Bytes(getAuthenticatorClient(), getTimestamp()));
			writeInt(buffer, getLoginMode(), SmgpLength.LOGIN_MODE);
			writeInt(buffer, getTimestamp(), SmgpLength.TIMESTAMP);
			writeInt(buffer, getClientVersion(), SmgpLength.CLIENT_VERSION);
			
			
			// 设置数据包长度
			int endIndex = buffer.writerIndex();
			setPacketLength(endIndex - beginIndex);
			
			// 进行消息头编码
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
			
			// setClientId(new String(buffer.readBytes(Length.CLIENT_ID).array()).trim());
			setClientId(readOctetString(buffer, SmgpLength.CLIENT_ID));
			
			// setAuthenticatorClient(new String(buffer.readBytes(Length.AUTHENTICATOR_CLIENT).array()).trim());
			// setAuthenticatorClient(readOctetString(buffer, Length.AUTHENTICATOR_CLIENT));
			setAuthenticatorClient(Common.toHex(buffer.readBytes(SmgpLength.AUTHENTICATOR_CLIENT).array()));
			
			// setLoginMode(Short.valueOf(buffer.readUnsignedByte()).intValue());
			setLoginMode(readInt(buffer, SmgpLength.LOGIN_MODE));
			
			// setTimestamp(buffer.readInt());
			setTimestamp(readInt(buffer, SmgpLength.TIMESTAMP));
			
			// setClientVersion(Short.valueOf(buffer.readUnsignedByte()).intValue());
			setClientVersion(readInt(buffer, SmgpLength.CLIENT_VERSION));
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
		return super.toString() + "SmgpLogin [clientId=" + clientId + ", authenticatorClient="
				+ authenticatorClient + ", loginMode=" + loginMode
				+ ", timestamp=" + timestamp + ", clientVersion=" + clientVersion + "]";
	}

	
	
	
}
