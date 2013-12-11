package com.e9.framework.logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.MessageFormat;

import org.slf4j.LoggerFactory;

/**
 * 默认的日志服务类；封装了Logback的基本日志功能，便于应用日志集中管理。
 * @project E9Framework
 * @date 2012-12-18
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-18] create by Jason
 */
public abstract class DefaultLoggerService implements Logger{

	protected org.slf4j.Logger log = LoggerFactory.getLogger(getClass());
	private LoggerLevel level = LoggerLevel.ALL;

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#debug(java.lang.String)
	 */
	@Override
	public abstract void debug(String s);

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#debug(java.lang.String, java.lang.Object)
	 */
	@Override
	public void debug(String s, Object arg) {
		// TODO Auto-generated method stub
		this.debug(MessageFormat.format(s, arg));
	}

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#debug(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void debug(String s, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		this.debug(MessageFormat.format(s, arg1, arg2));
	}

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#debug(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void debug(String s, Object[] args) {
		// TODO Auto-generated method stub
		this.debug(MessageFormat.format(s, args));
	}

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#info(java.lang.String)
	 */
	@Override
	public abstract void info(String s);

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#info(java.lang.String, java.lang.Object)
	 */
	@Override
	public void info(String s, Object arg) {
		// TODO Auto-generated method stub
		this.info(MessageFormat.format(s, arg));
	}

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#info(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void info(String s, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		this.info(MessageFormat.format(s, arg1, arg2));
	}

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#info(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void info(String s, Object[] args) {
		// TODO Auto-generated method stub
		this.info(MessageFormat.format(s, args));
	}

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#error(java.lang.String)
	 */
	@Override
	public abstract void error(String s);

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#error(java.lang.String, java.lang.Object)
	 */
	@Override
	public void error(String s, Object arg) {
		// TODO Auto-generated method stub
		this.error(MessageFormat.format(s, exception2String(arg)));
	}

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#error(java.lang.String, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void error(String s, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		this.error(MessageFormat.format(s, exception2String(arg1), exception2String(arg2)));
	}

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#error(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void error(String s, Object[] args) {
		// TODO Auto-generated method stub
		
		Object[] t_args = (Object[]) exception2String(args);
		this.error(MessageFormat.format(s, t_args));
	}

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#error(java.lang.Throwable)
	 */
	@Override
	public void error(Throwable e) {
		// TODO Auto-generated method stub
		
		this.error(null, e);
	}

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#error(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(String s, Throwable e) {
		// TODO Auto-generated method stub
		
		if (e == null) {
			this.error(s);
			return;
		}
		
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(out));
			this.error(s+"\n"+out.toString(Charset.forName("utf-8").displayName()));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			this.error(s+"\n"+out.toString());
		}
		finally{
			if (out != null) {
				try {
					out.close();
				} catch (IOException e1) { }
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		log.info("Logger Service is closed!");
	}
	
	/* (non-Javadoc)
	 * @see com.e9.log.Logger#setLevel(com.e9.log.LoggerLevel)
	 */
	@Override
	public void setLevel(LoggerLevel level) {
		// TODO Auto-generated method stub
		this.level = level;
	}

	/* (non-Javadoc)
	 * @see com.e9.log.Logger#getLevel()
	 */
	@Override
	public LoggerLevel getLevel() {
		// TODO Auto-generated method stub
		return level;
	}
	
	private Object exception2String(Object o){
		
		if (o == null) {
			return "";
		}
		
		if (o instanceof Object[]) {
			Object[] args = (Object[]) o;
			for (int i = 0; i <args.length; i++) {
				if (args[i] instanceof Throwable) {
					
					Throwable e = (Throwable) args[i];
					ByteArrayOutputStream out = null;
					try {
						out = new ByteArrayOutputStream();
						e.printStackTrace(new PrintStream(out));
						try {
							args[i] = "\n"+out.toString(Charset.forName("utf-8").displayName());
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							args[i] = "\n"+out.toString();
						}
					} catch (Exception e2) { }
					finally{
						if (out != null) {
							try {
								out.close();
							} catch (IOException e1) { }
						}
					}
					
				}
			}
			return args;
		}else{
			if (o instanceof Throwable) {
				
				Throwable e = (Throwable) o;
				ByteArrayOutputStream out = null;
				try {
					out = new ByteArrayOutputStream();
					e.printStackTrace(new PrintStream(out));
					try {
						return "\n"+out.toString(Charset.forName("utf-8").displayName());
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						return "\n"+out.toString();
					}
				} catch (Exception e2) { }
				finally{
					if (out != null) {
						try {
							out.close();
						} catch (IOException e1) { }
					}
				}
			}
			
			return o;
		}
		
	}

}
