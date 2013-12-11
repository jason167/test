package com.e9.framework.handler.codec.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.jboss.netty.buffer.ChannelBuffer;

import com.e9.framework.handler.codec.MsgFormat;
import com.e9.framework.handler.codec.smgp.property.SmgpLength;
import com.e9.framework.util.Common;


/**
 * 编解码辅助类
 * @project E9Framework
 * @date 2012-12-27
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-27] create by Jason
 */
public class MessageHelper {
	
	
	/**
	 * 根据给定字节数组生成字符串
	 * @param data
	 * @return
	 */
	protected String octetString(byte [] data){
		return octetString(data, MsgFormat.GBK);
	}
	
	/**
	 * 根据给定字节数组生成字符串
	 * @param source
	 * @param msgFormat {@link MsgFormat}
	 * @return
	 */
	protected String octetString(byte [] data,int msgFormat){
		if(null == data) {
			return null;
		}
		
		String string;
		switch (msgFormat) {
		case MsgFormat.ASCII:
			string = new String(data,Charset.forName("ASCII"));
			break;
		case MsgFormat.GBK:
			string = new String(data,Charset.forName("GBK"));
			break;
		case MsgFormat.BINARY_MESSAGE:
			string = Common.toHex(data);
			break;
		case MsgFormat.MESSAGE_WRITES_CARD:
		case MsgFormat.SIM:
		case MsgFormat.UCS2:
			string = new String(data, Charset.forName("UTF-16BE")); // UnicodeBigUnmarked
			break;
		case MsgFormat.GSM17:
			string = new String(data, Charset.forName("UTF-16BE")); // UnicodeBigUnmarked
			break;
		case MsgFormat.GSM25:
			string = new String(data, Charset.forName("UTF-16BE")); // UnicodeBigUnmarked
			break;
		default:
			string = new String(data);
		}
		
		string = string.trim();
		if (string == null || string.equals("")) {
			return string;
		}
		
		while(string.charAt(string.length() - 1) == '\0'){
			string = string.substring(0, string.length() - 1);
		}
		
		return string;
	}
	
	/**
	 * 生成符合SMGP协议要求的Octet String字节数组
	 * @param source
	 * @return
	 */
	protected byte[] octetStringByteArray(String source){
		return octetStringByteArray(source, -1, MsgFormat.GBK);
	}
	
	/**
	 * 生成符合SMGP协议要求的Octet String字节数组
	 * @param source
	 * @param length
	 * @return
	 */
	protected byte[] octetStringByteArray(String source,int length){
		return octetStringByteArray(source, length, MsgFormat.GBK);
	}
	
	/**
	 * 生成符合SMGP协议要求的Octet String字节数组
	 * @param source
	 * @param length
	 * @param msgFormat {@link MsgFormat}
	 * @return
	 */
	protected byte[] octetStringByteArray(String source,int length,int msgFormat){
		if(null == source) {
			return new byte[]{};
		}
		
		byte [] bytes = null;
		try {
			switch (msgFormat) {
			case MsgFormat.ASCII:
				bytes = source.getBytes("ASCII");
				break;
			case MsgFormat.GBK:
				bytes = source.getBytes("GBK");
				break;
			case MsgFormat.BINARY_MESSAGE:
				bytes = Common.hex2Bytes(source);
				break;
			case MsgFormat.MESSAGE_WRITES_CARD:
			case MsgFormat.SIM:
			case MsgFormat.UCS2:
				bytes = source.getBytes("UTF-16BE"); // UnicodeBigUnmarked
				break;
			case MsgFormat.GSM17:
				bytes = source.getBytes("UTF-16BE"); // UnicodeBigUnmarked
				break;
			case MsgFormat.GSM25:
				bytes = source.getBytes("UTF-16BE"); // UnicodeBigUnmarked
				break;
			default:
				bytes = source.getBytes();
			}
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException(ex);
		}
		
		if(length < 0){
			length = bytes.length;
		}
		
		if(bytes.length > length) {
			throw new IllegalArgumentException(
				"OctetString[" + source + "] too long;Max bytes length:[" + length + "]");
		}
		
		if(bytes.length == length) {
			return bytes;
		}
		
		return Arrays.copyOf(bytes, length);
	}
	
	/**
	 * 将指定字节数组写入给定buffer
	 * @param buffer
	 * @param source
	 * @param length
	 */
	protected void writeBytes(ChannelBuffer buffer,byte[] bytes) {
		if(null == bytes){
			throw new NullPointerException("Bytes can not be null!");
		}
		ensureChannelBufferCanWrite(buffer, bytes.length);
		buffer.writeBytes(bytes);
	}
	
	/**
	 * 根据字段长度从给定buffer中读取符合SMGP协议要求的Octet String字符串
	 * @param buffer
	 * @param length
	 */
	protected String readOctetString(ChannelBuffer buffer, int length) {
		ensureChannelBufferCanRead(buffer, length);
		byte [] data = buffer.readBytes(length).array();
		return octetString(data);
	}
	
	/**
	 * 根据字段长度从给定buffer中读取符合SMGP协议要求的Octet String字符串
	 * @param buffer
	 * @param length
	 * @param msgFormat {@link MsgFormat}
	 */
	protected String readOctetString(ChannelBuffer buffer, int length, int msgFormat) {
		ensureChannelBufferCanRead(buffer, length);
		byte [] data = buffer.readBytes(length).array();
		return octetString(data, msgFormat);
	}
	
	/**
	 * 将指定长度的字符串写入给定buffer
	 * @param buffer
	 * @param source
	 */
	protected void writeOctetString(ChannelBuffer buffer,String source) {
		byte[] data = octetStringByteArray(source);
		
		ensureChannelBufferCanWrite(buffer, data.length);
		buffer.writeBytes(data);
	}
	
	/**
	 * 将指定长度的字符串写入给定buffer
	 * @param buffer
	 * @param source
	 * @param length
	 */
	protected void writeOctetString(ChannelBuffer buffer,String source,int length) {
		byte[] data = octetStringByteArray(source, length);
		
		ensureChannelBufferCanWrite(buffer, data.length);
		buffer.writeBytes(data);
	}
	
	/**
	 * 将指定长度的字符串写入给定buffer
	 * @param buffer
	 * @param source
	 * @param length
	 * @param msgFormat {@link MsgFormat}
	 */
	protected void writeOctetString(ChannelBuffer buffer,String source,int length,int msgFormat) {
		byte[] data = octetStringByteArray(source, length, msgFormat);
		
		ensureChannelBufferCanWrite(buffer, data.length);
		buffer.writeBytes(data);
	}
	
	
	/**
	 * 根据字段长度从给定buffer中读取整型值
	 * @param buffer
	 * @param length
	 * @return
	 */
	protected Integer readInt(ChannelBuffer buffer,int length){
		switch (length) {
		case SmgpLength.INT1:
			ensureChannelBufferCanRead(buffer,SmgpLength.INT1);
			return Short.valueOf(buffer.readUnsignedByte()).intValue();
		case SmgpLength.INT2:
			ensureChannelBufferCanRead(buffer,SmgpLength.INT2);
			return buffer.readUnsignedShort();
		case SmgpLength.INT4:
		default:
			ensureChannelBufferCanRead(buffer,SmgpLength.INT4);
			return buffer.readInt();
		}
	}
	
	/**
	 * 将指定长度的整数写入给定buffer
	 * @param buffer
	 * @param length
	 * @return
	 */
	protected void writeInt(ChannelBuffer buffer,int value,int length){
		switch (length) {
		case SmgpLength.INT1:
			ensureChannelBufferCanWrite(buffer,SmgpLength.INT1);
			// value &= 0x000000FF;
			buffer.writeByte(value);
			break;
		case SmgpLength.INT2:
			ensureChannelBufferCanWrite(buffer,SmgpLength.INT2);
			// value &= 0x0000FFFF;
			buffer.writeShort(value);
			break;
		case SmgpLength.INT4:
		default:
			ensureChannelBufferCanWrite(buffer,SmgpLength.INT4);
			buffer.writeInt(value);
		}
	}
	
	/**
	 * 写入TLV字段
	 * @param buffer
	 * @param t
	 * @param l
	 * @param value
	 */
	protected void writeTLV(ChannelBuffer buffer,int t,int l,Object value){
		if(null == value){
			return;
		}
		
		if(value instanceof Integer) {
			if( l != SmgpLength.INT1 && l != SmgpLength.INT2 && l != SmgpLength.INT4 ){
				// throw new IllegalArgumentException("Invalid Integer Length:[" + l + "]");
				return;
			}
			
			Integer v = (Integer )value;
			writeInt(buffer, t, SmgpLength.TLV_TAG); // T
			writeInt(buffer, l, SmgpLength.TLV_LENGTH); // L
			writeInt(buffer, v, l); // V
		} else if(value instanceof String) {
			String v = (String)value;
			if(v.isEmpty()){
				return;
			}
			
			writeInt(buffer, t, SmgpLength.TLV_TAG); // T
			
			// L + V
			if(l < 0){
				byte[] data = octetStringByteArray(v);
				writeInt(buffer, data.length, SmgpLength.TLV_LENGTH); // L
				ensureChannelBufferCanWrite(buffer, data.length);
				buffer.writeBytes(data); // V
			} else {
				writeInt(buffer, l, SmgpLength.TLV_LENGTH); // L
				writeOctetString(buffer, v, SmgpLength.TLV_LENGTH); // V
			}
		}
	}
	
	/**
	 * 确保TLV参数的长度有效
	 * @param buffer
	 * @param tlvParamTag
	 * @param tlvParamLen
	 * @return
	 */
	protected void ensureTlvLengthValid(ChannelBuffer buffer,Integer tlvParamTag,int tlvParamLen) throws IllegalArgumentException{
		if(tlvParamLen < 0 || tlvParamLen > buffer.readableBytes()) {
			throw new IllegalArgumentException(
				"Invalid length of a TLV parameter:" + 
				( null != tlvParamTag ? ("tag=" + tlvParamTag + ";") : "") +
				"length=" + tlvParamLen + ";" +
				"readable length=" + buffer.readableBytes()
			);
		}
	}
	
	/**
	 * 确保TLV参数的长度有效
	 * @param buffer
	 * @param tlvParamLen
	 * @return
	 */
	protected void ensureTlvLengthValid(ChannelBuffer buffer,int tlvParamLen) throws IllegalArgumentException{
		ensureTlvLengthValid(buffer, null, tlvParamLen);
	}
	
	/**
	 * 确保Buffer可写
	 * @param buffer
	 */
	protected void ensureChannelBufferCanWrite(ChannelBuffer buffer){
		if(null == buffer) {
			throw new NullPointerException("ChannelBuffer can not be null!");
		}
		
		if(!buffer.writable()){
			throw new IllegalArgumentException("ChannelBuffer can not be wrote!");
		}
	}
	
	/**
	 * 确保Buffer可写,且可写字节数大于指定长度
	 * @param buffer
	 */
	protected void ensureChannelBufferCanWrite(ChannelBuffer buffer,int writableBytes){
//		if(null == buffer) {
//			throw new NullPointerException("ChannelBuffer can not be null!");
//		}
//		
//		if(!buffer.writable()){
//			throw new IllegalArgumentException("ChannelBuffer can not be wrote!");
//		}
//		
//		if(buffer.writableBytes() < writableBytes) {
//			throw new IllegalArgumentException("Writable bytes of ChannelBuffer is less than [" + writableBytes + "]!");
//		}
	}
	
	/**
	 * 确保Buffer可读
	 * @param buffer
	 */
	protected void ensureChannelBufferCanRead(ChannelBuffer buffer){
		if(null == buffer) {
			throw new NullPointerException("ChannelBuffer can not be null!");
		}
		
		if(!buffer.readable()){
			throw new IllegalArgumentException("ChannelBuffer can not be read!");
		}
	}
	
	/**
	 * 确保Buffer可读,且可读字节数大于指定长度
	 * @param buffer
	 * @param readableBytes
	 */
	protected void ensureChannelBufferCanRead(ChannelBuffer buffer,int readableBytes){
//		if(null == buffer) {
//			throw new NullPointerException("ChannelBuffer can not be null!");
//		}
//		
//		if(!buffer.readable()){
//			throw new IllegalArgumentException("ChannelBuffer can not be read!");
//		}
//		
//		if(buffer.readableBytes() < readableBytes) {
//			throw new IllegalArgumentException("Readable bytes of ChannelBuffer is less than [" + readableBytes + "]!");
//		}
	}

}
