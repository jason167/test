package test.com.e9.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date 2013-7-19
 * @author Jason
 */
public class LogTest {

	final static Logger logger =
            LoggerFactory.getLogger(LogTest.class);
	
//	static com.e9.log.Logger e9logger = com.e9.log.LoggerFactory.getLogger();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int count = 20000;
		while(count > 0){
			count--;
//			logger.trace("count:"+count);
			logger.info("count:{},{}， 中文哦。。。。", count, count);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			e9logger.debug("count:"+count);
		}
	}

}
