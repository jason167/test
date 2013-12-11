package com.e9.framework.handler.codec.sgip.message;


import com.e9.framework.channel.i.IoBuffer;
import com.e9.framework.handler.codec.sgip.AbstractSgipMessage;

/**
 * QueryRoute命令，见SGIP 1.21版4.2.3.9.1章节
 * @date 2013-8-26
 * @author Jason
 */
public class SgipQueryRoute extends AbstractSgipMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4799945284280256325L;
	
	/**
	 * <pre>
		请求类型
		0:全部路由表信息
		1:根据SP接入号码查找SMG
		2:根据手机号码段查找SMG
		3:根据SP接入号码和业务代码查找SMG
		4:根据SMG节点编号查找该SMG所对应全部路由信息
	 * </pre>
	 */
	private Integer queryType;
	
	/**
	 * <pre>
		请求类型为
		     0-忽略
		     1和3-SP接入号码
		　　2-手机号码段
		　　4-SMG节点编号
		左对齐，剩余部分填’\0’
	 * </pre>
	 */
	private String number;
	
	/**
	 * 业务代码，该字段为空时不考虑业务代码，请求类型为0、1、2时，该字段无效。
	 */
	private String serviceTag;
	private String reserve;

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#encode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void encode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Message#decode(com.e9.framework.channel.i.IoBuffer)
	 */
	@Override
	public void decode(IoBuffer ioBuffer) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.e9.framework.handler.codec.sgip.AbstractSgipMessage#validateParameters()
	 */
	@Override
	protected boolean validateParameters() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "SgipQueryRoute [queryType=" + queryType + ", number=" + number + ", serviceTag="
				+ serviceTag + ", reserve=" + reserve + "]";
	}
	
	

}
