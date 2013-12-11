package com.e9.framework.handler.codec.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @project E9Framework
 * @date 2013-1-14
 * @version 1.0
 * @author Jason
 * 
 * @review_history [2013-1-14] create by Jason
 */
public class SequenceGenerator {

    private final static AtomicInteger ID = new AtomicInteger(0);

    public static Integer next()
    {

        int t_id = ID.incrementAndGet();
        if (t_id < 1) {
            t_id = ID.addAndGet(1);
        }
        return t_id;
    }
    
}
