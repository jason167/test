package com.e9.framework.channel.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 可自定义命名线程的线程工厂
 * @date 2013-7-24
 * @author Jason
 */
public class NamingThreadFactory implements ThreadFactory {
	final ThreadGroup group;
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;
	final int priority;
	
	public NamingThreadFactory(String namePrefix){
		this(namePrefix,Thread.NORM_PRIORITY);
	}
	
	public NamingThreadFactory(String namePrefix,int priority){
		SecurityManager s = System.getSecurityManager();
		group = (s != null)? s.getThreadGroup() :
							 Thread.currentThread().getThreadGroup();
		this.namePrefix = namePrefix + "-";
		this.priority = priority;
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r,
							  namePrefix + threadNumber.getAndIncrement(),
							  0);
		if (t.isDaemon())
			t.setDaemon(false);
		t.setPriority(priority);
		return t;
	}
	
}

