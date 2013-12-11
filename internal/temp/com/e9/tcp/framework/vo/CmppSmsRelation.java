package com.e9.tcp.framework.vo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @project E9Framework
 * @date 2013-3-23
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2013-3-23] create by Jason
 */
public class CmppSmsRelation implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3468756226211057847L;
	
	private AtomicInteger deliverCounter = new AtomicInteger(0);
	private Integer smsSequenceId;
	private Long userMsgId;
	private Long bossMsgId;
	private Integer smsNum;
	
	/**
	 * @param smsSequenceId
	 * @param userMsgId
	 */
	public CmppSmsRelation(Integer smsSequenceId, Long userMsgId, int smsNum) {
		super();
		this.smsSequenceId = smsSequenceId;
		this.userMsgId = userMsgId;
		this.smsNum = smsNum;
	}
	
	public boolean isLastSms(){
		return deliverCounter.get() >= smsNum ? true : false;
	}
	
	public void incrementSms(){
		this.deliverCounter.incrementAndGet();
	}
	
	public synchronized int getCurrentSmsCount(){
		return this.deliverCounter.get();
	}
	/**
	 * @return the smsSequenceId
	 */
	public Integer getSmsSequenceId() {
		return smsSequenceId;
	}
	/**
	 * @param smsSequenceId the smsSequenceId to set
	 */
	public void setSmsSequenceId(Integer smsSequenceId) {
		this.smsSequenceId = smsSequenceId;
	}
	/**
	 * @return the userMsgId
	 */
	public Long getUserMsgId() {
		return userMsgId;
	}
	/**
	 * @param userMsgId the userMsgId to set
	 */
	public void setUserMsgId(Long userMsgId) {
		this.userMsgId = userMsgId;
	}
	/**
	 * @return the bossMsgId
	 */
	public Long getBossMsgId() {
		return bossMsgId;
	}
	/**
	 * @param bossMsgId the bossMsgId to set
	 */
	public void setBossMsgId(Long bossMsgId) {
		this.bossMsgId = bossMsgId;
	}
	

}
