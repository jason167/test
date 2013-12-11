package com.e9.framework.handler.codec.sgip.property;

/**
 * 见SGIP 1.21版4.2.3.9.1章节
 * @date 2013-8-26
 * @author Jason
 */
public interface SgipQueryType {

	int ALL = 0;
	int SP_CODE = 1;
	int PHONE_NUMBER = 2;
	int SP_CODE_AND_SERVICE_ID =3;
	int SMG_CODE = 4;
}
