package com.e9.framework.channel.util;


/**
 * ChannelConfig参数的时间单位都是秒
 * @project E9Framework
 * @date 2012-12-19
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-19] create by Jason
 */
public class ChannelConfig {
	
	private String host;
	private int port;
	private int conncet_timeout = 10;
	private int response_timeout = 10;
	private int keepalive_idle = 30;
	private int keepalive_retry = 3;
	private int cacheCapacity = 5000;
	private String channelCode;
	private int mt_speed = 200;
	private String version;
	



	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}


	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}


	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}


	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}


	/**
	 * @param conncet_timeout the conncet_timeout to set
	 */
	public void setConncet_timeout(int conncet_timeout) {
		this.conncet_timeout = conncet_timeout;
	}


	/**
	 * @param response_timeout the response_timeout to set
	 */
	public void setResponse_timeout(int response_timeout) {
		this.response_timeout = response_timeout;
	}


	/**
	 * @param keepalive_idle the keepalive_idle to set
	 */
	public void setKeepalive_idle(int keepalive_idle) {
		this.keepalive_idle = keepalive_idle;
	}


	/**
	 * @param keepalive_retry the keepalive_retry to set
	 */
	public void setKeepalive_retry(int keepalive_retry) {
		this.keepalive_retry = keepalive_retry;
	}


	/**
	 * 
	 */
	private ChannelConfig() {}


	/**
	 * @param port
	 */
	public ChannelConfig(int port) {
		super();
		this.port = port;
	}


	/**
	 * @param host
	 * @param port
	 */
	public ChannelConfig(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	
	
	/**
	 * @param host
	 * @param port
	 * @param channelCode
	 */
	public ChannelConfig(String host, int port, String channelCode) {
		super();
		this.host = host;
		this.port = port;
		this.channelCode = channelCode;
	}
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param channelCode
	 * @param mt_speed
	 * @param version
	 */
	public ChannelConfig(String host, int port, String channelCode, int mt_speed, String version) {
		super();
		this.host = host;
		this.port = port;
		this.channelCode = channelCode;
		this.mt_speed = mt_speed;
		this.version = version;
	}
	
	/**
	 * 
	 * @param host
	 * @param port
	 * @param channelCode
	 * @param mt_speed
	 */
	public ChannelConfig(String host, int port, String channelCode, int mt_speed) {
		super();
		this.host = host;
		this.port = port;
		this.channelCode = channelCode;
		this.mt_speed = mt_speed;
	}


	/**
	 * @return the mt_speed
	 */
	public int getMt_speed() {
		return mt_speed;
	}


	/**
	 * @param mt_speed the mt_speed to set
	 */
	public void setMt_speed(int mt_speed) {
		this.mt_speed = mt_speed;
	}


	/**
	 * @return the channelCode
	 */
	public String getChannelCode() {
		return channelCode;
	}


	/**
	 * @param channelCode the channelCode to set
	 */
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}


	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @return the conncet_timeout
	 */
	public int getConncet_timeout() {
		return conncet_timeout;
	}
	/**
	 * @return the response_timeout
	 */
	public int getResponse_timeout() {
		return response_timeout;
	}
	/**
	 * @return the keepalive_idle
	 */
	public int getKeepalive_idle() {
		return keepalive_idle;
	}
	/**
	 * @return the keepalive_retry
	 */
	public int getKeepalive_retry() {
		return keepalive_retry;
	}
	/**
	 * @return the cacheCapacity
	 */
	public int getCacheCapacity() {
		return cacheCapacity;
	}
	/**
	 * @param cacheCapacity the cacheCapacity to set
	 */
	public void setCacheCapacity(int cacheCapacity) {
		this.cacheCapacity = cacheCapacity;
	}

	
}
