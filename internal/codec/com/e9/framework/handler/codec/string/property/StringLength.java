package com.e9.framework.handler.codec.string.property;

/**
 * @project E9Framework
 * @date 2013-4-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-4-20] create by Jason
 */
public interface StringLength {
	
	/**
	 * 无符号Byte型数据的长度
	 */
	int INT1 = 1;
	
	/**
	 * 无符号Short型数据的长度
	 */
	int INT2 = 2;
	
	/**
	 * Integer型数据的长度
	 */
	int INT4 = 4;
	
	/**
	 * 数据包长度
	 */
	int PACKET_LENGTH = INT4;
	
	/**
	 * 请求标识
	 */
	int REQUEST_ID = INT4;
	
	/**
	 * 消息流水号
	 */
	int SEQUENCE_ID = INT4;
	
	/**
	 * 消息头长度:
	 * PacketLength(int4) + 
	 * RequestId(int4) +
	 * SequenceId(int4)
	 */
	int STRING_HEADER = PACKET_LENGTH + REQUEST_ID + SEQUENCE_ID ;

}
