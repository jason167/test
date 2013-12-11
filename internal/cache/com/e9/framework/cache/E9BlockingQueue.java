package com.e9.framework.cache;

import java.util.concurrent.LinkedBlockingQueue;


/**
 * 
 * @date 2013-8-1
 * @author Jason
 */
public class E9BlockingQueue<T> extends LinkedBlockingQueue<T>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4804975274455714833L;

	/**
	 * 
	 */
	public E9BlockingQueue() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public E9BlockingQueue(int capacity){
		super(capacity);
	}
}
