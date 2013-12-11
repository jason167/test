package com.e9.constant;

/**
 * /config/conf.properties对应的key ，每添加一个属性，都需要在这里进行补充对应的key。
 * @project E9Framework
 * @date 2012-12-8
 * @version 1.0
 * @company 33e9
 * @author Jason
 * 
 * @review_history
 * [2012-12-8] create by Jason
 */
public interface ConfigKey {
	
	
	/**
	 * 冻结资金重试次数
	 */
	public String RETRYFREEZE = "retryFreeze";
	
	/**
     * 移动号段
     */
	public String YDPHONEPRE = "ydPhonePre";
	
	/**
     * 联通号段
     */
    public String LTPHONEPRE = "ltPhonePre";
    
    /**
     * 电信号段
     */
    public String DXPHONEPRE = "dxPhonePre";
    
    /**
     * UMC host
     */
    public String UMC_HOST = "umc_host";
    
    /**
     * UMC port
     */
    public String UMC_PORT = "umc_port";
    
    /**
     * 审计内容标记，在缓存中的有效期（单位：分钟）
     */
    public String AUDIT_CONTENT_TTL = "audit_content_ttl";
    
    /**
     * 缓存容量
     */
    //public String CACHE_CAPACITY = "cacheCapacity";
    
    /**
     * CMPP协议，本地监听端口号
     */
    public String CMPP_LISTENER_PORT = "cmpp_listener_port";
    
    /**
     * UMC APP listener port
     */
    public String UMC_LISTENER_PORT = "umc_listener_port";
    
    /**
     * ADC APP listener port
     */
    public String ADC_LISTENER_PORT = "adc_listener_port";
    
    /**
     * preferredTestQuery
     */
    public String PREFERREDTESTQUERY = "preferredTestQuery";
    
    /**
     * ADC host
     */
    public String ADC_HOST = "adc_host";
    
    /**
     * ADC port
     */
    public String ADC_PORT = "adc_port";
    
    /**
     * data dir
     */
    public String DATA_DIR = "data_dir";
}
