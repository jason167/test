package com.e9.tcp.framework.utils;

import java.util.HashMap;

import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 简述:流量管理器，控制指定的session发送sms不能超过每秒指定的数量，当超过时sendSms reurn false <br/>
 * 详细描述: 使用方法<br/>
 * 
 * <pre>
 * 1)首先通过configLimit 注册需要流量控制的session和流量数
 * 2)每次发送记录
 * </pre>
 * 
 * date: 2013-2-2 上午11:36:10 <br/>
 * 
 * @author LinZhiXian
 * @version
 * @since JDK 1.6
 * 
 * @review history
 * [2013-3-25] modify by Jason
 * 将map key由String 修改为 jboss.channel。
 */
public class ThroughputControlUtil {

    private static HashMap<Channel, Counter> counterMaps = new HashMap<Channel, Counter>();
    private final static Logger logger =
            LoggerFactory.getLogger(ThroughputControlUtil.class);
    /**
     * 
     * 描述:配置需要管理流量id和流量限制. <br/>
     * 
     * @author lzx
     * @param channel
     * @param countPerSecond
     *            每秒多少条
     */
    public static void configLimit(Channel channel, int countPerSecond)
    {
        Counter counter = counterMaps.get(channel);
        if (counter == null) {
            counter = new Counter();
            counterMaps.put(channel, counter);
        }
        counter.countPerSecond = countPerSecond;
        logger.debug("ThroughputControlUtil.speed:[{}]", counter.countPerSecond);
    }

    /**
     * 
     * 描述:发送一条sms流量记录. <br/>
     * 
     * @author lzx
     * @param channel
     * @return true 可以发送，false 表示流量超过
     */
    public static boolean isSendSms(Channel channel)
    {
        return isSendSms(channel, 1);
    }

    /**
     * 
     * 描述:发送N条sms流量记录. <br/>
     * 
     * @author lzx
     * @param channel
     * @param sms_count
     *            发送数量
     * @return true 可以发送，false 表示流量超过
     */
    public static boolean isSendSms(Channel channel, int sms_count)
    {
        Counter counter = counterMaps.get(channel);
        if (counter == null)
            throw new RuntimeException("Error:no config countPerSecond for sesion:" + channel);

        return !counter.isExceedLimit(sms_count);
    }

    /**
     * 
     * 描述:取消流量控制的session，释放内存. <br/>
     * 
     * @author Administrator
     * @param channel
     */
    public static void destroy(Channel channel)
    {
        counterMaps.remove(channel);
    }
    public static String getSessionInfo(Channel channel) {
        Counter counter=counterMaps.get(channel);
        if(counter!=null) {
            return "timeDiff:"+(System.currentTimeMillis()-counter.currentSecondStartTime)+",currentCount="+counter.currentSecondCount+",limit="+counter.countPerSecond;
        } else {
            return null;
        }
    }
}

class Counter {

    public int countPerSecond;
    public long currentSecondStartTime = 0;
    public int currentSecondCount = 0;

    public void reset()
    {
        currentSecondCount = 0;
        currentSecondStartTime=currentSecondStartTime+((System.currentTimeMillis()-currentSecondStartTime)/1000)*1000;
    }

    public synchronized boolean isExceedLimit(int sms_count)
    {
        if (isUpperLimit(sms_count))
            return true;

        if (currentSecondStartTime == 0) {
            currentSecondStartTime = System.currentTimeMillis();
            currentSecondCount = currentSecondCount + sms_count;
            return false;
        }
        if ((System.currentTimeMillis() - currentSecondStartTime) > 1000) {
            reset();
            currentSecondCount = sms_count;
            return false;
        } else {
            if (this.currentSecondCount < countPerSecond) {
                if (isUpperLimit(currentSecondCount + sms_count)) {
                    return true;
                } else {
                    currentSecondCount = currentSecondCount + sms_count;
                    return false;
                }
            } else {
                return true;
            }
        }
    }    
    public boolean isUpperLimit(int sms_count)
    {
        return sms_count > countPerSecond;
    }
}
