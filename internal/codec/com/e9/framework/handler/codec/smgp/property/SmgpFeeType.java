package com.e9.framework.handler.codec.smgp.property;

/**
 * 对计费用户采取的收费类型,见《中国电信CTMP开发接口－中国电信SMGP协议》6.2.13章节
 * @project 33e9
 * @date 2012-7-26
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-26] create by zengweizhi
 */
public interface SmgpFeeType {
	/**
	 * 00＝免费，此时FixedFee和FeeCode无效；
	 */
	String FREE = "00";
	
	/**
	 * 01＝按条计信息费，此时FeeCode表示每条费用，FixedFee无效；
	 */
	String BY_COUNT = "01";
	
	/**
	 * 02＝按包月收取信息费，此时FeeCode无效，FixedFee表示包月费用；
	 */
	String BY_MONTH = "02";
	
	/**
	 * 03＝按封顶收取信息费，若按条收费的费用总和达到或超过封顶费后，则按照封顶费用收取信息费；若按条收费的费用总和没有达到封顶费用，则按照每条费用总和收取信息费。FeeCode表示每条费用，FixedFee表示封顶费用。
	 */
	String FIXED = "03";
}

