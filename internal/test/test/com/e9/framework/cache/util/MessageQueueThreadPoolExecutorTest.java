package test.com.e9.framework.cache.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9.framework.cache.E9BlockingQueue;
import com.e9.framework.cache.IQueueSupport;
import com.e9.framework.cache.MessageLevel;
import com.e9.framework.cache.impl.MessageQueueSupport;
import com.e9.framework.cache.util.MessageQueuePriority;
import com.e9.framework.cache.util.MessageQueueThreadPoolExecutor;
import com.e9.framework.channel.i.Message;
import com.e9.framework.handler.codec.string.message.Hello;

/**
 * @date 2013-8-1
 * @author Jason
 */
public class MessageQueueThreadPoolExecutorTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		testPool();
	}
	
	
	public static void testPool() throws Exception{
		final MessageQueueThreadPoolExecutor executorService = new MessageQueueThreadPoolExecutor(1);
		
		IQueueSupport<Message> support = new MessageQueueSupport<Message>(null) {

			Logger logger = LoggerFactory.getLogger(getClass());
			@Override
			public boolean persistentOnCacheFailed(Message e, MessageLevel level) {
				// TODO Auto-generated method stub
				return getMessagePersistentUtil().persistentOnCacheFailed(e, level);
			}

			@Override
			public void persistentOnDestory(E9BlockingQueue<Message>[] queues) {
				// TODO Auto-generated method stub
				getMessagePersistentUtil().persistentOnDestory(queues);
			}

			@Override
			public void doRun() {
				// TODO Auto-generated method stub

				try {
					state = USING;
					int delayTimes = 0;
					int executeTaskCount = 0;
					long taskStartTime = System.currentTimeMillis();
					
					executeTaskCount++;
					long firstTime = System.currentTimeMillis();
					
					Message message = poll();
					if (message == null) {
						return;
					}
					
					boolean sent = getSession().send(message);
					if (!sent) {
						offer(message, MessageLevel.THREE, true);
					}
					long useTime = System.currentTimeMillis() - firstTime;
					if (useTime > getAllowDelayTime()) {
						logger.debug("getAllowDelayTime");
						delayTimes++;
						System.out.println(delayTimes);
						if (delayTimes > getAllowDelayTimes()) {
							logger.debug("getAllowDelayTimes");
							MessageQueuePriority priority = MessageQueuePriority.getPriority(getPriority().ordinal() + 1);
							if (priority == null) {
								setPriority(MessageQueuePriority.NINE);
							}else{
								setPriority(priority);
							}
							toSleep();
							return; // 结束本次任务
						}
					}
					
					// 根据本次执行任务的使用时间和本次执行任务的数量，得到本次任务消耗的平均时间
					// 根据这个消耗时间设定当前任务的优先级
					long totalUseTime = System.currentTimeMillis() - taskStartTime;
					long avgUseTime = totalUseTime / executeTaskCount;
					if (avgUseTime > getAllowUpgradeTime()) {
						
						MessageQueuePriority priority = MessageQueuePriority.getPriority(getPriority().ordinal() + 1);
						if (priority == null) {
							setPriority(MessageQueuePriority.NINE);
						}else{
							setPriority(priority);
						}
					}else{
						if (getPriority().ordinal() > MessageQueuePriority.FIVE.ordinal()) {
							setPriority(MessageQueuePriority.FIVE);
						}
					}
				}catch(Exception e){
					logger.error("", e);
				}
				finally{
					if (state != PAUSED) {
						state = UNUSED;
					}
				}
			}

		};
		
		support.offer(new Hello(), MessageLevel.ONE);
		for (int i = 0; i < 2; i++) {
			System.out.println(i+1);
			executorService.execute(support);
		}
		
	}

}
