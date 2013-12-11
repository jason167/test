package com.e9.framework.handler.codec.ex.gw.property;

/**
 * 登陆类型，见《短信通道协议开发包V3.06(内部使用).doc》，3.2.5.1章节
 * <pre>
	当type 为3的时候，表示当isp离线时，短信网关会缓存一定数量的mo消息，供isp下次登陆时获取。
	以这种方式获取mo消息，需要与短信网关建立另外一条独立的连接,请向巨澜索取登陆ip及端口。
 * </pre>
 * @date 2013-8-28
 * @author Jason
 */
public interface GwLoginType {

	int MT = 1;
	int MT_AND_MO = 2;
	
	/**
	 * 支持缓存方式MO
	 */
	int CACHE = 3;
}
