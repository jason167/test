/** Date:2013-1-25上午10:48:41 Copyright (c) 2013, 三三得玖 All Rights Reserved.
 */
package com.e9.tcp.framework.utils;


import java.util.UUID;
/**
 * 简述:批次号生成器 <br/>
 * 详细描述: (无)<br/>
 * date: 2013-1-25 上午10:48:41 <br/>
 *
 * @author Administrator
 * @version 
 * @since JDK 1.6
 */
public class BatchNoGenerator {
    public static String getBatchNo(){
        UUID uuid = UUID.randomUUID();
        String no = uuid.toString().replaceAll("-", "");
        return no.substring(12);
    }
}

