package com.e9.framework.cache.util;

import java.util.Comparator;

import com.e9.framework.cache.IQueueComparator;

/**
 * Message queue比较器的实现类，被比较对象需要实现IQueueComparator接口
 * @date 2013-8-1
 * @author Jason
 */
public class MessageQueueComparator implements Comparator<IQueueComparator> {

	/**
	 * 可用排序方式
	 */
	private final MessageQueueOrder order;
	
	/**
	 * 默认排序方式：MessageQueueOrder.ASC
	 */
	public MessageQueueComparator() {
		// TODO Auto-generated constructor stub
		this(MessageQueueOrder.ASC);
	}
	/**
	 * 
	 */
	public MessageQueueComparator(MessageQueueOrder queueOrder) {
		// TODO Auto-generated constructor stub
		this.order = queueOrder;
	}
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(IQueueComparator o1, IQueueComparator o2) {
		// TODO Auto-generated method stub
		if (this.order == MessageQueueOrder.DESC) {
			return desc(o1, o2);
		}else{
			return asc(o1, o2);
		}
	}
	
	private int asc(IQueueComparator o1, IQueueComparator o2){
		return o1.getPriority().ordinal() > o2.getPriority().ordinal() ? 1 : (o1.getPriority().ordinal() < o2.getPriority().ordinal() ? -1 : 0);
	}
	
	public int desc(IQueueComparator o1, IQueueComparator o2){
		return o1.getPriority().ordinal() > o2.getPriority().ordinal() ? -1 : (o1.getPriority().ordinal() < o2.getPriority().ordinal() ? 1 : 0);
	}


}
