package com.e9.framework.channel;

import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.DefaultChannelFuture;

import com.e9.framework.channel.i.Message;
import com.e9.framework.channel.i.Session;

/**
 * @project E9Framework
 * @date 2012-12-20
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-20] create by Jason
 */
public class DefaultSession implements Session {
	
	private Channel channel;
//	private AtomicBoolean connectState = new AtomicBoolean(false); 
	private final ConcurrentHashMap<Object, Object> attachment;
	private final Object sync = new Object();
	private String channelCode;
	
	/**
	 * 是否已认证(登录)
	 */
	private AtomicBoolean authenticated = new AtomicBoolean(false); 
	private boolean keepAliveTesting = false;

	/**
	 * 
	 */
	public DefaultSession(Channel channel) {
		// TODO Auto-generated constructor stub
		this.channel = channel;
		attachment = new ConcurrentHashMap<Object, Object>();
	}
	
	
	/**
	 * @return the authenticated
	 */
	public boolean isAuthenticated() {
		return authenticated.get();
	}

	/**
	 * @param authenticated the authenticated to set
	 */
	public void setAuthenticated(boolean authenticated) {
		this.authenticated.set(authenticated);
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#isConnected()
	 */
	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		if (this.channel == null) {
			return false;
		}
		return this.channel.isConnected();
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#disconnect()
	 */
	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		if (this.channel != null && this.channel.isConnected()) {
			this.channel.disconnect();
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#getLocalAddress()
	 */
	@Override
	public SocketAddress getLocalAddress() {
		// TODO Auto-generated method stub
		if (this.channel != null) {
			
			return this.channel.getLocalAddress();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#getRemoteAddress()
	 */
	@Override
	public SocketAddress getRemoteAddress() {
		// TODO Auto-generated method stub
		if (this.channel != null) {
			
			return this.channel.getRemoteAddress();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#send(com.e9.tcp.framework.Message)
	 */
	@Override
	public boolean send(Message message) {
		// TODO Auto-generated method stub
		return this.send(message, 10);
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.e9.framework.channel.i.Session#send(com.e9.framework.channel.i.Message, long)
	 */
	@Override
	public boolean send(Message message, long timeout) {
		// TODO Auto-generated method stub
		if (this.channel != null && this.channel.isConnected() && this.channel.isWritable()) {
			return this.channel.write(message).awaitUninterruptibly(timeout, TimeUnit.SECONDS);
		}
		return false;
	}


	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#submit(com.e9.tcp.framework.Message, java.util.concurrent.TimeUnit, long)
	 */
	@Override
	public Message submit(Message message, long timeout) {
		Message msg = null;
		if (this.channel != null && this.channel.isConnected() && this.channel.isWritable()) {
			
			boolean uninterruptibly = this.channel.write(message).awaitUninterruptibly(timeout, TimeUnit.SECONDS);
			if (uninterruptibly) {
				
				@SuppressWarnings("unused")
				boolean interrupted = false;
				try {
					
					msg = (Message) this.removeAttachment(getChannel());
					if (msg != null) {
//						System.out.println("yes");
						return msg;
					}
					
					lock(timeout * 1000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					interrupted = true;
				}
				
//				if (!interrupted) {
					
					msg = (Message) this.removeAttachment(getChannel());
//				}
			}
		}
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#submit(com.e9.tcp.framework.Message)
	 */
	@Override
	public Message submit(Message message) {
		// TODO Auto-generated method stub
		return this.submit(message, 10);
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#putAttachment(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void putAttachment(Object key, Object value) {
		// TODO Auto-generated method stub
		attachment.put(key, value);
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#getAttachment(java.lang.Object)
	 */
	@Override
	public Object getAttachment(Object key) {
		// TODO Auto-generated method stub
		return attachment.get(key);
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#removeAttachment(java.lang.Object)
	 */
	@Override
	public Object removeAttachment(Object key) {
		// TODO Auto-generated method stub
		return attachment.remove(key);
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#getId()
	 */
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		if (this.channel != null) {
			
			return this.channel.getId();
		}
		return -1;
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (this.channel == null) {
			
			return super.toString();
		}
		return this.channel.toString();
	}

	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		if (this.attachment != null) {
			
			this.attachment.clear();
		}
		if (this.channel != null && this.channel.isOpen()) {
			this.channel.close().awaitUninterruptibly(5, TimeUnit.SECONDS);
		}
	}


	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#lock(long)
	 */
	@Override
	public void lock(long timeout) throws InterruptedException {
		// TODO Auto-generated method stub
		while(true){
			synchronized(sync){
				sync.wait(timeout);
				break;
			}
		}
	}


	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#unlock()
	 */
	@Override
	public void unlock() {
		// TODO Auto-generated method stub
		synchronized(sync){
			sync.notifyAll();
		}
	}


	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#getChannelCode()
	 */
	@Override
	public String getChannelCode() {
		// TODO Auto-generated method stub
		return this.channelCode;
	}


	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#setChannelCode(java.lang.String)
	 */
	@Override
	public void setChannelCode(String channelCode) {
		// TODO Auto-generated method stub
		this.channelCode = channelCode;
	}


	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#isKeepAliveTesting()
	 */
	@Override
	public boolean isKeepAliveTesting() {
		// TODO Auto-generated method stub
		return this.keepAliveTesting;
	}


	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#setKeepAliveTesting(boolean)
	 */
	@Override
	public void setKeepAliveTesting(boolean keepAliveTesting) {
		// TODO Auto-generated method stub
		this.keepAliveTesting = keepAliveTesting;
	}


	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#setChannel(org.jboss.netty.channel.Channel)
	 */
	@Override
	public void setChannel(Channel channel) {
		// TODO Auto-generated method stub
		this.channel = channel;
	}


	/* (non-Javadoc)
	 * @see com.e9.tcp.framework.Session#getChannel()
	 */
	@Override
	public Channel getChannel() {
		// TODO Auto-generated method stub
		return this.channel;
	}


}
