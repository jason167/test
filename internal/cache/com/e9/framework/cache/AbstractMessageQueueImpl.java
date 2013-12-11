package com.e9.framework.cache;

import java.util.concurrent.TimeUnit;



/**
 * 
 * @date 2013-8-1
 * @author Jason
 */
public abstract class AbstractMessageQueueImpl<E> implements IQueue<E> {

    /**
	 * 
	 */
    private final int messageQueueSize = MessageLevel.getMaxValue();
	@SuppressWarnings("unchecked")
	private E9BlockingQueue<E>[] messageQueue = new E9BlockingQueue[messageQueueSize];

    /**
	 * 
	 */
    public AbstractMessageQueueImpl() {
        // TODO Auto-generated constructor stub
        this(-1);
    }

    /**
	 * 
	 */
    public AbstractMessageQueueImpl(int _capacity) {
        // TODO Auto-generated constructor stub
    	int capacity = _capacity;
    	if (capacity <= 0) {
    		capacity = Integer.MAX_VALUE;
		}
    	for (int i = 0; i < messageQueueSize; i++) {
            messageQueue[i] = new E9BlockingQueue<E>(capacity);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.e9.cache.IQueue#offer(java.lang.Object, com.e9.cache.QueueLevel)
     */
    @Override
    public boolean offer(E e, MessageLevel level)
    {
        // TODO Auto-generated method stub
        return messageQueue[level.getLevel()].offer(e);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.e9.cache.IQueue#offer(java.lang.Object, long,
     * java.util.concurrent.TimeUnit, com.e9.cache.QueueLevel)
     */
    @Override
    public boolean offer(E e, long timeout, TimeUnit unit, MessageLevel level)
    {
        // TODO Auto-generated method stub
        try {
            return messageQueue[level.getLevel()].offer(e, timeout, unit);
        } catch (InterruptedException ex) {
            // TODO Auto-generated catch block
            return false;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.e9.cache.IQueue#poll()
     */
    @Override
    public E poll()
    {
        // TODO Auto-generated method stub
        for (int i = 0; i < this.messageQueueSize; i++) {
        	E e = messageQueue[i].poll();
            if (e != null) {
                return e;
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.e9.cache.IQueue#poll(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public E poll(long timeout, TimeUnit unit)
    {
        // TODO Auto-generated method stub

        try {
            for (int i = 0; i < this.messageQueueSize; i++) {

               E e = messageQueue[i].poll(timeout, unit);
                if (e != null) {

                    return e;
                }
            }
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            return null;
        }

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.e9.cache.IQueue#put(java.lang.Object, com.e9.cache.QueueLevel)
     */
    @Override
    public boolean put(E e, MessageLevel level)
    {
        // TODO Auto-generated method stub

        try {
            messageQueue[level.getLevel()].put(e);
        } catch (InterruptedException ex) {
            // TODO Auto-generated catch block
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.e9.cache.IQueue#take()
     */
    @Override
    public E take()
    {
        // TODO Auto-generated method stub
        try {
            while (true) {

                for (int i = 0; i < this.messageQueueSize; i++) {
                    E e = messageQueue[i].poll();
                    if (e != null) {
                        return e;
                    }
                }

                E e = messageQueue[MessageLevel.ZERO.getLevel()].poll(2, TimeUnit.SECONDS);
                if (e == null) {
                    continue;
                } else {
                    return e;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.e9.cache.IQueue#offer(java.lang.Object, com.e9.cache.QueueLevel,
     * boolean)
     */
    @Override
    public boolean offer(E e, MessageLevel level, boolean persistentOnCacheFailed)
    {
        // TODO Auto-generated method stub
    	if (!this.offer(e, level)) {
    		if (persistentOnCacheFailed) {
    			return persistentOnCacheFailed(e, level);
			}else{
				return false;
			}
		}else{
			return true;
		}
    }
    

	/*
     * (non-Javadoc)
     * 
     * @see com.e9.cache.IQueue#poll(com.e9.cache.QueueLevel)
     */
    @Override
    public E poll(MessageLevel level)
    {
        // TODO Auto-generated method stub
        return messageQueue[level.getLevel()].poll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.e9.cache.IQueue#poll(com.e9.cache.QueueLevel, long,
     * java.util.concurrent.TimeUnit)
     */
    @Override
    public E poll(MessageLevel level, long timeout, TimeUnit unit)
    {
        // TODO Auto-generated method stub
        try {
            return messageQueue[level.getLevel()].poll(timeout, unit);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.e9.cache.IQueue#destory()
     */
    @Override
    public void destory()
    {
        // TODO Auto-generated method stub
    	try{
    		persistentOnDestory(messageQueue);
    	}
    	finally{
    		if (messageQueue == null || messageQueue.length == 0) {
                return;
            }

           for (E9BlockingQueue<E> q : messageQueue) {
        	   if (q != null) {
        		   q.clear();
        	   }
           }
    	}
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.e9.cache.IQueue#take(com.e9.cache.QueueLevel)
     */
    @Override
    public E take(MessageLevel level)
    {
        // TODO Auto-generated method stub
        try {
            return messageQueue[level.getLevel()].take();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.e9.cache.IQueue#size(com.e9.cache.QueueLevel)
     */
    @Override
    public int size(MessageLevel level)
    {
        return messageQueue[level.getLevel()].size();
    }

    @Override
    public int size()
    {
        int size=0;
        for(E9BlockingQueue<E> q : messageQueue) {
            if(q!=null)
            	size += q.size();
        }
        return size;
    }
}
