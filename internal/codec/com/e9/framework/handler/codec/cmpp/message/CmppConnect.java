package com.e9.framework.handler.codec.cmpp.message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.AbstractMessage;
import com.e9.framework.handler.codec.cmpp.property.CmppCommandId;
import com.e9.framework.handler.codec.cmpp.property.CmppLength;
import com.e9.framework.handler.codec.util.TimestampUtils;
import com.e9.framework.util.Common;
import com.e9.framework.util.algorithm.MD5;

/**
 * Connect,见CMPP v2.0 7.4.1.1章节
 * @project E9Framework
 * @date 2013-1-24
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-1-24] create by Jason
 */
public class CmppConnect extends AbstractMessage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3191160563565158875L;

	/**
	 * 
	 */
	public CmppConnect() {
		// TODO Auto-generated constructor stub
		super.setCommandId(CmppCommandId.CONNECT);
	}
	
	/**
	 * 源地址，此处为SP_Id，即SP的企业代码。
	 */
	private String Source_Addr;
	
	/**
	 * 用于鉴别源地址。其值通过单向MD5 hash计算得出，表示如下：
		AuthenticatorSource =
		MD5（Source_Addr+9 字节的0 +shared secret+timestamp）
		Shared secret 由中国移动与源地址实体事先商定，timestamp格式为：MMDDHHMMSS，即月日时分秒，10位。
	 */
	private String AuthenticatorSource;
	
	/**
	 * 双方协商的版本号(高位4bit表示主版本号,低位4bit表示次版本号)
	 */
	private Integer Version;
	
	/**
	 * 时间戳的明文,由客户端产生,格式为MMDDHHMMSS，即月日时分秒，10位数字的整型，右对齐 。
	 */
	private Integer Timestamp;
	
	public byte[] password2Bytes(String password, int timestamp) throws Exception{
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			out.write(getSource_Addr().getBytes());
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
			
			writeOctetString(buffer, getSource_Addr(), CmppLength.SOURCE_ADDR);
			writeBytes(buffer, password2Bytes(getAuthenticatorSource(), getTimestamp()));
			writeInt(buffer, getVersion(), CmppLength.VERSION);
			writeInt(buffer, getTimestamp(), CmppLength.TIMESTAMP);
			
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

	/* (non-Javadoc)
	 * @see com.e9.tcp.gateway.cmpp.AbstractCmppMessage#decode(com.e9.tcp.framework.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer ioBuffer) throws Exception{
		// TODO Auto-generated method stub
		ChannelBuffer buffer = null;
		try {
			buffer = (ChannelBuffer) ioBuffer.getBuffer();
			ensureChannelBufferCanRead(buffer);
			headDecode(buffer);
			setSource_Addr(readOctetString(buffer, CmppLength.SOURCE_ADDR));
			setAuthenticatorSource(Common.toHex(buffer.readBytes(CmppLength.AUTHENTICATORSOURCE).array()));
			setVersion(readInt(buffer, CmppLength.VERSION));
			setTimestamp(readInt(buffer, CmppLength.TIMESTAMP));
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
			null != Source_Addr && 
			null != AuthenticatorSource && 
			null != Version && 
			null != Timestamp
		;
		
		return valid;
	}

	/**
	 * @return the source_Addr
	 */
	public String getSource_Addr() {
		return Source_Addr;
	}

	/**
	 * @param source_Addr the source_Addr to set
	 */
	public void setSource_Addr(String source_Addr) {
		Source_Addr = source_Addr;
	}

	/**
	 * @return the authenticatorSource
	 */
	public String getAuthenticatorSource() {
		return AuthenticatorSource;
	}

	/**
	 * @param authenticatorSource the authenticatorSource to set
	 */
	public void setAuthenticatorSource(String authenticatorSource) {
		AuthenticatorSource = authenticatorSource;
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

	/**
	 * @return the timestamp
	 */
	public Integer getTimestamp() {
		return Timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Integer timestamp) {
		Timestamp = timestamp;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + " Connect [Source_Addr=" + Source_Addr + ", AuthenticatorSource="
				+ AuthenticatorSource + ", Version=" + Version + ", Timestamp="
				+ Timestamp + "]";
	}

	
}
