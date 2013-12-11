package com.e9.framework.util;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * SMS任务调度器
 * @project E9Framework
 * @date 2012-10-15
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-10-15] create by Jason
 * 
 * [2012-12-8] modify by Jason
 * 重构任务调度器，使之成为通用
 * 
 * [2013-3-12] modify by Jason
 * 支持多任务
 */
public class SmsQuartz{
	
	private Scheduler scheduler;
	private static AtomicInteger count = new AtomicInteger();
	private final Logger logger =
            LoggerFactory.getLogger(SmsQuartz.class);
	private SmsQuartz(){}
	private static SmsQuartz sq = new SmsQuartz();
	public static SmsQuartz getInstance(){return sq;};
	
	public void addPlanTask(Class jobClass, String taskPlanTime) throws SchedulerException, ParseException{
		this.addPlanTask(jobClass, taskPlanTime, "SmsJobName_"+count.incrementAndGet(), "SmsTriggerName_"+count.incrementAndGet());
	}
	
	public void addPlanTask(Class jobClass, String taskPlanTime, String jobName, String triggerName) throws SchedulerException, ParseException{
		 try {
        	 this.scheduler = createScheduler();
             this.start();
             scheduleJob(scheduler, jobName, triggerName, taskPlanTime, jobClass);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
        	logger.error("", e);
           throw e;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
        	logger.error("", e);
            throw e;
        }
	}
	

	/**
	 * 启动任务调度器
	 * @throws SchedulerException
	 */
	public void start() throws SchedulerException{
		if (!this.scheduler.isStarted()) {
			this.scheduler.start();
		}
	}
	
	/**
	 * 关闭任务调度器
	 * @throws SchedulerException
	 */
	public void shutdown() throws SchedulerException{
		if (!this.scheduler.isShutdown()) {
			this.scheduler.shutdown(true);
		}
	}
	
	private Scheduler createScheduler() throws SchedulerException 
    {     
        return StdSchedulerFactory.getDefaultScheduler();
    }
	
	private void scheduleJob(Scheduler scheduler, String jobName, String triggerName, String taskPlanTime, Class jobClass) throws SchedulerException, ParseException 
    {         
        // Create a JobDetail for the Job       
       JobDetail job = new JobDetail(jobName,Scheduler.DEFAULT_GROUP, jobClass);         
       String t_taskPlanTime = taskPlanTime;
       if (null==t_taskPlanTime || "".equals(t_taskPlanTime)){
    	   t_taskPlanTime = "59 59 23 * * ? ";
       }
       CronTrigger trigger = new CronTrigger(triggerName, Scheduler.DEFAULT_GROUP, jobName,Scheduler.DEFAULT_GROUP, t_taskPlanTime);
       trigger.setStartTime(new Date());         
       scheduler.scheduleJob(job, trigger); 
  }  
	
}
