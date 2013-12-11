package com.e9.framework.logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;



/**
 * 日志服务类
 * @project E9Framework
 * @date 2012-12-18
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-18] create by Jason
 */
public final class LoggerService extends DefaultLoggerService{
	
	private final LinkedBlockingQueue<String> DQ = new LinkedBlockingQueue<String>(100 * 10000);
	private final LinkedBlockingQueue<String> FQ = new LinkedBlockingQueue<String>(100 * 10000);
	private final LinkedBlockingQueue<String> EQ = new LinkedBlockingQueue<String>(100 * 10000);
	
	private AtomicInteger DQNumber = new AtomicInteger(0);
	private AtomicInteger FQNumber = new AtomicInteger(0);
	private AtomicInteger EQNumber = new AtomicInteger(0);
	private AtomicBoolean isShutdown = new AtomicBoolean(false);
	
	private ExecutorService DQService = Executors.newSingleThreadExecutor(new LoggerNamingThreadFactory("DQLoggerThread"));
	private ExecutorService FQService = Executors.newSingleThreadExecutor(new LoggerNamingThreadFactory("FQLoggerThread"));
	private ExecutorService EQService = Executors.newSingleThreadExecutor(new LoggerNamingThreadFactory("EQLoggerThread"));
	
	private static LoggerService logger;
	static{
		logger = new LoggerService();
	}
	
	public static LoggerService getInstance(){
		return logger;
	}
	
	/**
	 * 
	 */
	private LoggerService() {
		// TODO Auto-generated constructor stub
		DQService.execute(new LoggerThread(DQ, LoggerLevel.DEBUG, DQNumber));
		FQService.execute(new LoggerThread(FQ, LoggerLevel.INFO, FQNumber));
		EQService.execute(new LoggerThread(EQ, LoggerLevel.ERROR, EQNumber));
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			
			/* (non-Javadoc)
			 * @see java.lang.Thread#run()
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub
				close();
			}
		});
	}
	

	/* (non-Javadoc)
	 * @see com.e9.log.DefaultLoggerService#debug(java.lang.String)
	 */
	@Override
	public void debug(String s) {
		// TODO Auto-generated method stub
		if (!this.log.isDebugEnabled()) {
			return;
		}
		if (LoggerLevel.DEBUG.getId() >= getLevel().getId()) {
			if (s != null && !s.equals("")) {
				boolean saved = DQ.offer(s);
				if (saved) {
					
					DQNumber.incrementAndGet();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.log.DefaultLoggerService#info(java.lang.String)
	 */
	@Override
	public void info(String s) {
		// TODO Auto-generated method stub
		if (!this.log.isInfoEnabled()) {
			return;
		}
		if (LoggerLevel.INFO.getId() >= getLevel().getId()) {
			if (s != null && !s.equals("")) {
				boolean saved = FQ.offer(s);
				if (saved) {
					
					FQNumber.incrementAndGet();
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.log.DefaultLoggerService#error(java.lang.String)
	 */
	@Override
	public void error(String s) {
		// TODO Auto-generated method stub
		if (!this.log.isErrorEnabled()) {
			return;
		}
		if (LoggerLevel.ERROR.getId() >= getLevel().getId()) {
			if (s != null && !s.equals("")) {
				boolean saved = EQ.offer(s);
				if (saved) {
					
					EQNumber.incrementAndGet();
				}
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.e9.log.DefaultLoggerService#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		log.info("Logger closed");
		isShutdown.set(true);
		
		try {
			if (DQService != null) {
				
				DQService.shutdownNow();
			}
			if (FQService != null) {
				
				FQService.shutdownNow();
			}
			if (EQService != null) {
				
				EQService.shutdownNow();
			}
		}
		finally{
			
			DQService = null;
			FQService = null;
			EQService = null;
		}
		
	}
	
	private class LoggerThread implements Runnable{
		
		LoggerLevel level;
		LinkedBlockingQueue<String> Q;
		AtomicInteger number;
		
		/**
		 * 
		 */
		public LoggerThread(LinkedBlockingQueue<String> Q, LoggerLevel level, AtomicInteger number) {
			// TODO Auto-generated constructor stub
			this.level = level;
			this.Q = Q;
			this.number = number;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				try {
					if(isShutdown.get() && number.get() <= 0){
						Thread.currentThread().interrupt();
						break;
					}
					String s = Q.take();
					number.decrementAndGet();
					
					if (LoggerLevel.INFO.getId() == level.getId()) {
						
						log.info(s);
					}else if(LoggerLevel.DEBUG.getId() == level.getId()){
						
						log.debug(s);
					}else{
						
						log.error(s);
					}
					
				} catch (InterruptedException e) {}
			}
		}
	}
	

	public static void main(String[] args) throws InterruptedException {
		final Logger log = LoggerFactory.getLogger();
		final int count = 10 * 1000; // 10w
		int thread_count = 10;
		
		final CountDownLatch countDownLatch = new CountDownLatch(thread_count);
		final CountDownLatch countDownLatch2 = new CountDownLatch(1);
		
		final String msg = "关于健康的12个真相，那些谚语真的都对吗？";
		List<Thread> t_list = new ArrayList<Thread>();
		while(thread_count != 0){
			Runnable r = new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					try {
						countDownLatch2.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(System.currentTimeMillis());
					for (int i = 0; i < count; i++) {
						if (i % 100 == 0) {
							log.debug(msg);
						}else if (i % 2 == 0) {
							
							log.info(msg);
						}else{
							log.error(msg);
						}
					}
					
					countDownLatch.countDown();
				}
			};
			
			t_list.add(new Thread(r));
			thread_count--;
		}
		
		System.out.println(t_list.size());
		for (Thread t : t_list) {
			t.start();
		}
		
//		Thread.sleep(5000);
		countDownLatch2.countDown();
		
		try {
			System.out.println("await");
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.close();
		System.out.println("Done!");
	}

	
	class LoggerNamingThreadFactory implements ThreadFactory {
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;
		final int priority;
		
		public LoggerNamingThreadFactory(String namePrefix){
			this(namePrefix,Thread.NORM_PRIORITY);
		}
		
		public LoggerNamingThreadFactory(String namePrefix,int priority){
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
}
