package com.e9.framework.cache.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Message queue pools
 * @date 2013-8-1
 * @author Jason
 */
public class MessageQueueThreadPoolExecutor{

	private List<IQueueRunnable> tasks = new Vector<IQueueRunnable>();
	private ConcurrentHashMap<Object, IQueueRunnable> m = new ConcurrentHashMap<Object, IQueueRunnable>();
	private boolean isHoldTask = false; 
	private ScheduledExecutorService scheduledExecutorService;
	private Logger logger = LoggerFactory.getLogger(MessageQueueThreadPoolExecutor.class);
	private ExecutorCompletionService<IQueueRunnable> completionService;
	private MessageQueueTaskSingleThread taskSingleThread;
	private MessageQueueTaskSchedule messageQueueTaskSchedule;
	private ThreadPoolExecutor executorService;
	
	
	public MessageQueueThreadPoolExecutor() throws Exception{
		this(Runtime.getRuntime().availableProcessors() * 2);
	}
	/**
	 * 默认：持有任务句柄和任务排序，支持回收空闲线程功能
	 * @param corePoolSize
	 * @throws Exception
	 */
	public MessageQueueThreadPoolExecutor(int corePoolSize) throws Exception {
		// TODO Auto-generated constructor stub
		this(corePoolSize, 60, TimeUnit.SECONDS);
	}
	
	/**
	 * 
	 * @param corePoolSize
	 * @param maximumPoolSize
	 * @param keepAliveTime
	 * @param unit
	 * @throws Exception 
	 */
	public MessageQueueThreadPoolExecutor(int corePoolSize, long keepAliveTime, TimeUnit unit) throws Exception {
		// TODO Auto-generated constructor stub
		this(corePoolSize, keepAliveTime, unit, true, true);
	}
	
	/**
	 * 
	 * @param corePoolSize
	 * @param maximumPoolSize
	 * @param keepAliveTime
	 * @param unit
	 * @param isHoldTask
	 * @throws Exception 
	 */
	public MessageQueueThreadPoolExecutor(int corePoolSize, long keepAliveTime, TimeUnit unit, boolean isHoldTask, boolean isRank) throws Exception {
		// TODO Auto-generated constructor stub
		this(corePoolSize, corePoolSize, keepAliveTime, unit, true, isHoldTask, isRank);
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	public MessageQueueThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, boolean allowCoreThreadTimeOut, 
			boolean isHoldTask, boolean isRank) throws Exception {
		// TODO Auto-generated constructor stub
//		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<Runnable>(), new QueueNamingThreadFactory("MessageQueueThreadPoolExecutor"));
		executorService = new ThreadPoolExecutor(corePoolSize, corePoolSize, keepAliveTime, unit, new LinkedBlockingQueue<Runnable>(), new QueueNamingThreadFactory("MessageQueueThreadPoolExecutor"));
		executorService.allowCoreThreadTimeOut(allowCoreThreadTimeOut);
		
		this.isHoldTask = isHoldTask;
		if (isHoldTask) {
			if (isRank) {
				scheduledExecutorService = Executors.newScheduledThreadPool(1, new QueueNamingThreadFactory("MessageQueueRankSchedule"));
				scheduledExecutorService.scheduleAtFixedRate(new MessageQueueRankSchedule(tasks), 2, 10, TimeUnit.SECONDS);
			}
			
			createTaskThread(corePoolSize);
		}
		logger.info("线程[MessageQueueThreadPoolExecutor]启动成功");
	}
	
	private void createTaskThread(int corePoolSize)throws Exception{
		this.completionService = new ExecutorCompletionService<IQueueRunnable>(executorService);
		MessageQueueTaskThreadListener taskThreadListener = new MessageQueueTaskThreadListener();
		AtomicInteger concurrentNum = new AtomicInteger(corePoolSize);
		
		taskSingleThread = new MessageQueueTaskSingleThread(concurrentNum, completionService);
		taskSingleThread.addObserver(taskThreadListener);
		new Thread(taskSingleThread, MessageQueueTaskSingleThread.class.getName()).start();
		
		messageQueueTaskSchedule = new MessageQueueTaskSchedule(tasks, completionService, concurrentNum);
		messageQueueTaskSchedule.addObserver(taskThreadListener);
		new Thread(messageQueueTaskSchedule, MessageQueueTaskSchedule.class.getName()).start();
		
	}
	
	public void execute(IQueueRunnable task) {
		// TODO Auto-generated method stub
		if (task == null) {
			return;
		}
		if (isHoldTask) {
			if (m.putIfAbsent(hash(task.hashCode()), task) == null) {
				tasks.add(task);
				logger.debug("Added a task {}", task);
			}else{
				logger.warn("Repeat add tasks, Task:{}", task);
			}
		}else{
			doExecute(task);
		}
	}
	
	
	public void removeTask(IQueueRunnable task){
		IQueueRunnable oldTask = m.remove(hash(task.hashCode()));
		if (oldTask != null) {
			tasks.remove(task);
			logger.debug("Removed a task {}", task);
		}
		
	}
	

	protected void doExecute(IQueueRunnable task) {
	    doUnorderedExecute(task);
	}
	
	
	protected final void doUnorderedExecute(Runnable task) {
		executorService.execute(task);
	}
	
	
	public void shutdown() {
		// TODO Auto-generated method stub
		logger.debug("Shutdown ...");
		try{
			if (executorService != null) {
				executorService.shutdown();
			}
		}
		finally{
			destoryTask();
		}
	}
	
	public List<Runnable> shutdownNow() {
		// TODO Auto-generated method stub
		logger.debug("Shutdown now ...");
		List<Runnable> notExecuteTask = null;
		 try{
			 if (executorService != null) {
				 notExecuteTask = executorService.shutdownNow();
			 }
		 }
		 finally{
			 destoryTask();
		 }
		 return notExecuteTask;
	}
	
	private void destoryTask(){
		logger.debug("Destory task and thread ...");
		
		executorService = null;
		try {
			if (scheduledExecutorService != null) {
				scheduledExecutorService.shutdownNow();
				scheduledExecutorService = null;
			}
		} catch (Exception e) { }
		
		try {
			if (messageQueueTaskSchedule != null) {
				messageQueueTaskSchedule.stop();
				messageQueueTaskSchedule = null;
			}
			if (taskSingleThread != null) {
				taskSingleThread.stop();
				taskSingleThread = null;
			}
		} catch (Exception e) { }
		
		completionService = null;
		
		if (tasks != null) {
			this.tasks.clear();
			this.tasks = null;
		}
		if (m != null) {
			this.m.clear();
			this.m = null;
		}
		
	}
	
	
	private static int hash(int h) {
        // Spread bits to regularize both segment and index locations,
        // using variant of single-word Wang/Jenkins hash.
        h += (h <<  15) ^ 0xffffcd7d;
        h ^= (h >>> 10);
        h += (h <<   3);
        h ^= (h >>>  6);
        h += (h <<   2) + (h << 14);
        return h ^ (h >>> 16);
    }
	
	
	/**
	 * TaskThread监控组件
	 * @date 2013-8-15
	 * @author Jason
	 */
	class MessageQueueTaskThreadListener implements Observer{

		private Logger logger = LoggerFactory.getLogger(MessageQueueTaskThreadListener.class);
		/* (non-Javadoc)
		 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
		 */
		@Override
		public void update(Observable task, Object arg) {
			// TODO Auto-generated method stub
			String threadName = task.getClass().getName();
			logger.warn("线程[{}]终止，正在重启...", threadName);
			task.addObserver(this);
			Thread t = new Thread((Runnable)task, threadName);
			t.start();
			
		}
		
	}
	
	
	/**
	 * <pre>
	 * 同MessageQueueTaskSchedule任务调度配合使用，该类用于标记完成任务的线程。
	 * </pre>
	 * @date 2013-8-15
	 * @author Jason
	 */
	class MessageQueueTaskSingleThread extends Observable implements Runnable{

		private AtomicInteger concurrentNum;
		private ExecutorCompletionService<IQueueRunnable> completionService;
		private final AtomicBoolean runState = new AtomicBoolean();
		private volatile boolean isReRun = true;
		private Logger logger = LoggerFactory.getLogger(MessageQueueTaskSingleThread.class);
		/**
		 * 
		 */
		public MessageQueueTaskSingleThread(AtomicInteger concurrentNum, ExecutorCompletionService<IQueueRunnable> completionService) {
			// TODO Auto-generated constructor stub
			if (completionService == null) {
				throw new IllegalArgumentException("ExecutorCompletionService instance can't be null");
			}
			this.concurrentNum = concurrentNum;
			this.completionService = completionService;
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if (!runState.compareAndSet(false, true)) {
				logger.warn("线程[{}]重复启动，已被阻止", Thread.currentThread().getName());
				return;
			}
			logger.info("线程[{}]启动成功", Thread.currentThread().getName());
			try {
				while(runState.get() && !Thread.currentThread().isInterrupted()){
					try {
						if (completionService != null) {
							this.completionService.take();
						}else{
							logger.error("completionService is null !! will to sleep tow seconds");
							TimeUnit.SECONDS.sleep(1);
							break;
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						logger.error("", e);
						break;
					}
					finally{
						this.concurrentNum.incrementAndGet();
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("", e);
			}
			finally{
				runState.set(false);
				reRun();
			}
		}
		
		private void reRun(){
			if (!isReRun) {
				return;
			}
			this.setChanged();
			this.notifyObservers(this);
		}
		
		public void stop(){
			isReRun = false;
			runState.set(false);
			logger.info("线程[{}]已被主动终止", Thread.currentThread().getName());
		}
		
		
	}
	
	/**
	 * <pre>
	 * 1、任务调度，对TaskList进行分发。
	 * 2、分发的速度依赖于被执行的任务处理速度，任务逻辑要简单、快速。
	 * 3、并行任务的数量依赖于concurrentNum。
	 * </pre>
	 * @date 2013-8-15
	 * @author Jason
	 */
	class MessageQueueTaskSchedule extends Observable implements Runnable{

		private final List<IQueueRunnable> tasks;
		private final ExecutorCompletionService<IQueueRunnable> completionService;
		private final AtomicInteger concurrentNum;
		private final AtomicBoolean runState = new AtomicBoolean();
		private final AtomicInteger taskIndex = new AtomicInteger();
		private volatile boolean isReRun = true;
		private Logger logger = LoggerFactory.getLogger(MessageQueueTaskSchedule.class);
		
		/**
		 * 
		 */
		public MessageQueueTaskSchedule(List<IQueueRunnable> tasks, ExecutorCompletionService<IQueueRunnable> completionService, AtomicInteger concurrentNum) {
			// TODO Auto-generated constructor stub
			if (completionService == null) {
				throw new IllegalArgumentException("ExecutorCompletionService instance can't be null");
			}
			this.tasks = tasks;
			this.completionService = completionService;
			this.concurrentNum = concurrentNum;
		}
		
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (!runState.compareAndSet(false, true)) {
				logger.warn("线程[{}]重复启动，已被阻止", Thread.currentThread().getName());
				return;
			}
			logger.info("线程[{}]启动成功", Thread.currentThread().getName());
			int counter = 0;
			try {
				while(runState.get() && !Thread.currentThread().isInterrupted()){
					boolean interrupted = false;
					while(tasks.isEmpty()){
						try {
							logger.debug("Task list is empty, will be sleep two seconds ...");
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							interrupted = true;
							break;
						}
					}
					if (interrupted) {
						break;
					}
					
					while(concurrentNum.get() <= 0){
						try {
							logger.debug("ConcurrentNum is zero, will be sleep two seconds ...");
							TimeUnit.SECONDS.sleep(2);
						} catch (InterruptedException e) {
							interrupted = true;
							break;
						}
					}
					if (interrupted) {
						break;
					}
					
					taskIndex.compareAndSet(tasks.size(), 0);
					
					IQueueRunnable task = null;
					try {
						task = tasks.get(taskIndex.getAndIncrement());
					} catch (Exception e) {
						// TODO: handle exception
						logger.error("", e);
					}
					
					if (task != null) {
						
						if (task.getState() <= IQueueRunnable.NONE) {
							counter++;
							
							/**
							 * 任务状态小于等于NONE时，计数器加1。
							 * 当计数器连续累加并达到任务列表的最大值时（表明任务列表内的对象（容器），无可执行任务），暂停分发任务N秒。
							 * N秒钟结束后，重置计数器，继续扫描任务列表并进行分发。
							 */
							if (counter == tasks.size()) {
								
								String stateString = task.getState() == IQueueRunnable.NONE ? "NONE" : "PAUSED";
								logger.debug("Task state is [{}], Thread [{}] will sleep two seconds ...  task:{}",
										new Object[]{stateString, Thread.currentThread().getName(), task});
								
								TimeUnit.SECONDS.sleep(2);
								counter = 0;
							}
							
							if(task.getState() == IQueueRunnable.PAUSED){
								continue;
							}
						}else{
							
							/**
							 * 计数器累加期间，当有任何一个任务的状态大于NONE时，将初始化计数器
							 */
							counter = 0;
						}
						
						if (completionService != null) {
							
							/**
							 * 当前的任务调度对状态为只读权限。
							 * 无论当前任务状态是否为NONE，都会执行该任务，移交task的控制权限，让任务自身来检查是否需要将状态改为非NONE值。
							 */
							if (task != null) {
								concurrentNum.decrementAndGet();
								completionService.submit(task, null);
							}
						}else{
							break;
						}
					}else{
						try {
							tasks.remove(taskIndex.get() - 1);
						} catch (Exception e) {
							// TODO: handle exception
							logger.error("", e);
							TimeUnit.SECONDS.sleep(1);
						}
					}
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("", e);
			}
			finally{
				runState.set(false);
				reRun();
			}
			
		}

		
		private void reRun(){
			if (!isReRun) {
				return;
			}
			this.setChanged();
			this.notifyObservers(this);
		}
		
		public void stop(){
			isReRun = false;
			runState.set(false);
			logger.info("线程[{}]已被主动终止", Thread.currentThread().getName());
		}
		
	}
	
	
	/**
	 * 按一定间隔时间对TaskList进行排序
	 * @date 2013-8-15
	 * @author Jason
	 */
	class MessageQueueRankSchedule implements Runnable{

		@SuppressWarnings("rawtypes")
		private final Comparator comparator;
		private List<IQueueRunnable> taskList;
		private Logger logger = LoggerFactory.getLogger(MessageQueueRankSchedule.class);
		
		/**
		 * 
		 */
		public MessageQueueRankSchedule(List<IQueueRunnable> taskList) {
			// TODO Auto-generated constructor stub
			comparator = new MessageQueueComparator();
			this.taskList = taskList;
			logger.info("线程[MessageQueueRankSchedule]启动成功");
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (taskList != null && !taskList.isEmpty()) {
				try {
					
					Collections.sort(taskList, comparator);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("", e);
				}
			}
		}
		
	}
	

}

class QueueNamingThreadFactory implements ThreadFactory {
	final ThreadGroup group;
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;
	final int priority;
	final boolean daemon;
	
	public QueueNamingThreadFactory(String namePrefix){
		this(namePrefix,Thread.NORM_PRIORITY, false);
	}
	
	/**
	 * 
	 */
	public QueueNamingThreadFactory(String namePrefix, boolean daemon){
		this(namePrefix,Thread.NORM_PRIORITY, daemon);
	}
	
	public QueueNamingThreadFactory(String namePrefix,int priority, boolean daemon){
		SecurityManager s = System.getSecurityManager();
		group = (s != null)? s.getThreadGroup() :
							 Thread.currentThread().getThreadGroup();
		this.namePrefix = namePrefix + "-";
		this.priority = priority;
		this.daemon = daemon;
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r,
							  namePrefix + threadNumber.getAndIncrement(),
							  0);
		t.setDaemon(daemon);
		t.setPriority(priority);
		return t;
	}
	
}


