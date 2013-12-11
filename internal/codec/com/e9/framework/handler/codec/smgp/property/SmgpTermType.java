package com.e9.framework.handler.codec.smgp.property;

/**
 * 号码类型,
 * 见《中国电信CTMP开发接口－中国电信SMGP协议》
 * 	6.3.6章节
 * 	6.3.9章节
 * 	6.3.14章节
 * @project 33e9
 * @date 2012-7-26
 * @version 1.0
 * @author zengweizhi
 * 
 * @review_history
 * [2012-7-26] create by zengweizhi
 */
public interface SmgpTermType {
	int REAL = 0;
	
	int PSEUDO = 1;
}
