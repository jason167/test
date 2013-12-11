package com.e9.framework.datasource;

import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @date 2013-7-23
 * @author Jason
 */
public class E9DataSourceImpl extends AbstractE9DataSource {

	private Properties properties;
	
	/**
	 * <p>DataSource properties and default value:</p>
		* <p>driverClass</p>
		* <p>jdbcUrl</p>
		* <p>uid</p>
		* <p>pwd</p>
		* <p>initialPoolSize = 1;</p>
		* <p>minPoolSize = 1</p>
		* <p>maxPoolSize = 5</p>
		* <p>maxIdleTime = 60 * 60; // s</p>
		* <p>numHelperThreads = 3;</p>
		* <p>acquireIncrement = 3;</p>
		* <p>acquireRetryAttempts = 3;</p>
		* <p>acquireRetryDelay = 1000; // ms</p>
		* <p>testConnectionOnCheckin = false;</p>
		* <p>preferredTestQuery = "select 1";</p>
		* <p>idleConnectionTestPeriod = 10 * 60; // s</p>
		* <p>checkoutTimeout = 10 * 1000; // ms</p>
		* <p>maxStatements = 0;</p>
		* <p>maxStatementsPerConnection = 0;</p>
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws PropertyVetoException 
	 */
	public E9DataSourceImpl(Properties properties) throws PropertyVetoException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated constructor stub
		super();
		this.properties = properties;
		initDataSource();
	}
	
	private void initDataSource() throws PropertyVetoException, NoSuchMethodException, IllegalAccessException, InvocationTargetException{
		super.initDataSource(properties);
	}
	
	public void close(Connection conn) throws SQLException{
		if (conn != null) {
			conn.close();
		}
	}
	
	public void beginTransaction(Connection conn) throws SQLException{
		if (conn != null) {
			conn.setAutoCommit(false);
		}
	}
	
	public void commit(Connection conn) throws SQLException{
		if (conn != null) {
			conn.commit();
		}
	}
	
	public void rollback(Connection conn) throws SQLException{
        if (conn != null) {
            conn.rollback();
        }
    }
	
}
