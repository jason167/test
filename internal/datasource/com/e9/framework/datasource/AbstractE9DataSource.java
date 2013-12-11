package com.e9.framework.datasource;

import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * <p>DataSource properties and default value:</p>
	* <p>driverClass</p>
	* <p>jdbcUrl</p>
	* <p>uid</p>
	* <p>pwd</p>
	* <p>initialPoolSize = 1;</p>
	* <p>minPoolSize = 1</p>
	* <p>maxPoolSize = 5</p>
	* <p>maxIdleTime = 60; // s</p>
	* <p>numHelperThreads = 3;</p>
	* <p>acquireIncrement = 3;</p>
	* <p>acquireRetryAttempts = 3;</p>
	* <p>acquireRetryDelay = 1000; // ms</p>
	* <p>testConnectionOnCheckin = false;</p>
	* <p>preferredTestQuery = "select 1";</p>
	* <p>idleConnectionTestPeriod = 10; // s</p>
	* <p>checkoutTimeout = 10 * 1000; // ms</p>
	* <p>maxStatements = 0;</p>
	* <p>maxStatementsPerConnection = 0;</p>
 * @date 2013-7-23
 * @author Jason
 */
public abstract class AbstractE9DataSource implements E9DataSource {

	private Logger logger = LoggerFactory.getLogger(AbstractE9DataSource.class);
	private ComboPooledDataSource dataSource;
	private String driverClass;
	private String jdbcUrl;
	private String uid;
	private String pwd;
	private int initialPoolSize = 1;
	private int minPoolSize = 1;
	private int maxPoolSize = 5;
	private int maxIdleTime = 60; // s
	private int numHelperThreads = 3;
	private int acquireIncrement = 3;
	private int acquireRetryAttempts = 3;
	private int acquireRetryDelay = 1000; // ms
	private boolean testConnectionOnCheckin = false;
	private String preferredTestQuery = "select 1";
	private int idleConnectionTestPeriod = 10; // s
	private int checkoutTimeout = 10 * 1000; // ms
	private int maxStatements = 0;
	private int maxStatementsPerConnection = 0;
	
	
	protected void initDataSource(Properties properties) throws PropertyVetoException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		if (properties == null || properties.isEmpty()) {
			throw new IllegalArgumentException("Params Properties is null");
		}
		
		Iterator<Object> keys = properties.keySet().iterator();
		while(keys.hasNext()){
			String key = (String) keys.next();
			String value = (String) properties.get(key);
			if (value == null || value.equals("")) {
				continue;
			}
			String methodName = "set".concat(key.substring(0, 1).toUpperCase().concat(key.substring(1)));
			try {
				Method method = AbstractE9DataSource.class.getDeclaredMethod(methodName, String.class);
				method.setAccessible(true);
				method.invoke(this, value);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				throw e;
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				throw e;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				throw e;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				throw e;
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				throw e;
			}
			
		}
		
		initC3p0DataSourceProperty();
	}
	
	private void initC3p0DataSourceProperty() throws PropertyVetoException{
		if (dataSource != null) {
			dataSource.close();
			dataSource = null;
		}
		dataSource = new ComboPooledDataSource();
		
		try {
			dataSource.setDriverClass(getDriverClass());
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			throw e;
		}
		dataSource.setJdbcUrl(getJdbcUrl());
		dataSource.setUser(getUid());
		dataSource.setPassword(getPwd());
		
		dataSource.setInitialPoolSize(getInitialPoolSize());
		dataSource.setMinPoolSize(getMinPoolSize());
		dataSource.setMaxPoolSize(getMaxPoolSize());
		dataSource.setMaxIdleTime(getMaxIdleTime());
		dataSource.setNumHelperThreads(getNumHelperThreads());
		
		dataSource.setAcquireIncrement(getAcquireIncrement());
		dataSource.setAcquireRetryAttempts(getAcquireRetryAttempts());
		dataSource.setAcquireRetryDelay(getAcquireRetryDelay());
		dataSource.setTestConnectionOnCheckin(isTestConnectionOnCheckin());
		
		dataSource.setPreferredTestQuery(getPreferredTestQuery());
		dataSource.setIdleConnectionTestPeriod(getIdleConnectionTestPeriod());
		dataSource.setCheckoutTimeout(getCheckoutTimeout());
		dataSource.setMaxStatements(getMaxStatements());
		dataSource.setMaxStatementsPerConnection(getMaxStatementsPerConnection());
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.e9.framework.dao.E9DataSource#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		if (dataSource != null) {
			return dataSource.getConnection();
		}
		return null;
	}
	
	

	/* (non-Javadoc)
	 * @see com.e9.framework.dao.E9DataSource#destoryForDataSource()
	 */
	@Override
	public void destoryForDataSource() {
		// TODO Auto-generated method stub
		logger.info("Destory for dataSource...");
		if (dataSource != null) {
			dataSource.close();
		}
	}

	/**
	 * @return the driverClass
	 */
	private String getDriverClass() {
		return driverClass;
	}
	/**
	 * @param driverClass the driverClass to set
	 */
	private void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	/**
	 * @return the jdbcUrl
	 */
	private String getJdbcUrl() {
		return jdbcUrl;
	}
	/**
	 * @param jdbcUrl the jdbcUrl to set
	 */
	private void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}
	/**
	 * @return the uid
	 */
	private String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	private void setUid(String uid) {
		this.uid = uid;
	}
	/**
	 * @return the pwd
	 */
	private String getPwd() {
		return pwd;
	}
	/**
	 * @param pwd the pwd to set
	 */
	private void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * @return the initialPoolSize
	 */
	private int getInitialPoolSize() {
		return initialPoolSize;
	}
	/**
	 * @param initialPoolSize the initialPoolSize to set
	 */
	private void setInitialPoolSize(String initialPoolSize) {
		this.initialPoolSize = Integer.parseInt(initialPoolSize);
	}
	/**
	 * @return the minPoolSize
	 */
	private int getMinPoolSize() {
		return minPoolSize;
	}
	/**
	 * @param minPoolSize the minPoolSize to set
	 */
	private void setMinPoolSize(String minPoolSize) {
		this.minPoolSize = Integer.parseInt(minPoolSize);
	}
	/**
	 * @return the maxPoolSize
	 */
	private int getMaxPoolSize() {
		return maxPoolSize;
	}
	/**
	 * @param maxPoolSize the maxPoolSize to set
	 */
	private void setMaxPoolSize(String maxPoolSize) {
		this.maxPoolSize = Integer.parseInt(maxPoolSize);
	}
	/**
	 * @return the maxIdleTime
	 */
	private int getMaxIdleTime() {
		return maxIdleTime;
	}
	/**
	 *  Time unit is seconds
	 * @param maxIdleTime the maxIdleTime to set
	 */
	private void setMaxIdleTime(String maxIdleTime) {
		this.maxIdleTime = Integer.parseInt(maxIdleTime);
	}
	/**
	 * @return the numHelperThreads
	 */
	private int getNumHelperThreads() {
		return numHelperThreads;
	}
	/**
	 * @param numHelperThreads the numHelperThreads to set
	 */
	private void setNumHelperThreads(String numHelperThreads) {
		this.numHelperThreads = Integer.parseInt(numHelperThreads);
	}
	/**
	 * @return the acquireIncrement
	 */
	private int getAcquireIncrement() {
		return acquireIncrement;
	}
	/**
	 * @param acquireIncrement the acquireIncrement to set
	 */
	private void setAcquireIncrement(String acquireIncrement) {
		this.acquireIncrement = Integer.parseInt(acquireIncrement);
	}
	/**
	 * @return the acquireRetryAttempts
	 */
	private int getAcquireRetryAttempts() {
		return acquireRetryAttempts;
	}
	/**
	 * @param acquireRetryAttempts the acquireRetryAttempts to set
	 */
	private void setAcquireRetryAttempts(String acquireRetryAttempts) {
		this.acquireRetryAttempts = Integer.parseInt(acquireRetryAttempts);
	}
	/**
	 * @return the acquireRetryDelay
	 */
	private int getAcquireRetryDelay() {
		return acquireRetryDelay;
	}
	/**
	 * Time unit is milliseconds
	 * @param acquireRetryDelay the acquireRetryDelay to set
	 */
	private void setAcquireRetryDelay(String acquireRetryDelay) {
		this.acquireRetryDelay = Integer.parseInt(acquireRetryDelay);
	}
	/**
	 * @return the testConnectionOnCheckin
	 */
	private boolean isTestConnectionOnCheckin() {
		return testConnectionOnCheckin;
	}
	/**
	 * @param testConnectionOnCheckin the testConnectionOnCheckin to set
	 */
	private void setTestConnectionOnCheckin(String testConnectionOnCheckin) {
		this.testConnectionOnCheckin = Boolean.parseBoolean(testConnectionOnCheckin);
	}
	/**
	 * @return the preferredTestQuery
	 */
	private String getPreferredTestQuery() {
		return preferredTestQuery;
	}
	/**
	 * @param preferredTestQuery the preferredTestQuery to set
	 */
	private void setPreferredTestQuery(String preferredTestQuery) {
		this.preferredTestQuery = preferredTestQuery;
	}
	/**
	 * @return the idleConnectionTestPeriod
	 */
	private int getIdleConnectionTestPeriod() {
		return idleConnectionTestPeriod;
	}
	/**
	 * Time unit is seconds
	 * @param idleConnectionTestPeriod the idleConnectionTestPeriod to set
	 */
	private void setIdleConnectionTestPeriod(String idleConnectionTestPeriod) {
		this.idleConnectionTestPeriod = Integer.parseInt(idleConnectionTestPeriod);
	}
	/**
	 * @return the checkoutTimeout
	 */
	private int getCheckoutTimeout() {
		return checkoutTimeout;
	}
	/**
	 * Time unit is milliseconds
	 * @param checkoutTimeout the checkoutTimeout to set
	 */
	private void setCheckoutTimeout(String checkoutTimeout) {
		this.checkoutTimeout = Integer.parseInt(checkoutTimeout);
	}
	/**
	 * @return the maxStatements
	 */
	private int getMaxStatements() {
		return maxStatements;
	}
	/**
	 * @param maxStatements the maxStatements to set
	 */
	private void setMaxStatements(String maxStatements) {
		this.maxStatements = Integer.parseInt(maxStatements);
	}
	/**
	 * @return the maxStatementsPerConnection
	 */
	private int getMaxStatementsPerConnection() {
		return maxStatementsPerConnection;
	}
	/**
	 * @param maxStatementsPerConnection the maxStatementsPerConnection to set
	 */
	private void setMaxStatementsPerConnection(String maxStatementsPerConnection) {
		this.maxStatementsPerConnection = Integer.parseInt(maxStatementsPerConnection);
	}
	
	
}
