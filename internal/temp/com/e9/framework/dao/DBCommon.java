package com.e9.framework.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.e9.constant.ConfigKey;
import com.e9.tcp.framework.utils.ConfigUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * DB Connection Manager
 * @project EskReport
 * @date 2012-12-6
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-6] create by Jason
 */
public final class DBCommon {

	private static ComboPooledDataSource dataSource;
	private final static String DBConfigPath = "db.xml";
	private static Logger log = LoggerFactory.getLogger(DBCommon.class);
	
	public static Connection getConnection() throws SQLException{
		if (dataSource != null) {
			return dataSource.getConnection();
		}
		return null;
	}
	
	public static void close(Connection conn) throws SQLException{
		if (conn != null) {
			conn.close();
		}
	}
	
	public static void beginTransaction(Connection conn) throws SQLException{
		if (conn != null) {
			conn.setAutoCommit(false);
		}
	}
	
	public static void commit(Connection conn) throws SQLException{
		if (conn != null) {
			conn.commit();
		}
	}
	
	public static void rollback(Connection conn) throws SQLException{
        if (conn != null) {
            conn.rollback();
        }
    }
	
	public static void testDesConnection() throws Exception{
		long beginTime = System.currentTimeMillis();  
		DBCommon.init();
		long endTime = System.currentTimeMillis();  
		
		int i = 0;
		while(true){
			beginTime = System.currentTimeMillis(); 
			
			Connection conn = null;
			PreparedStatement statement = null;
			try{
				conn = DBCommon.getConnection();
				DBCommon.beginTransaction(conn);
				System.out.println(conn);
				String sql = "select sysdate from dual";
				statement = conn.prepareStatement(sql);
				ResultSet result = statement.executeQuery();
				if(result.next()){
					System.out.println(result.getString(1));
				}
				DBCommon.commit(conn);
			}catch(SQLException e){
				log.error("",e);
			}
			finally{
				if (statement != null) {
					statement.close();
				}
				if (conn != null) {
					
					DBCommon.close(conn);
				}
			}
			endTime = System.currentTimeMillis();  
			System.out.println("第" + (i + 1) + "次执行花费时间为:" + (endTime - beginTime));  
			
			TimeUnit.SECONDS.sleep(5);
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("Starting...");
		testDesConnection();
		
	}
	
	public static void basicTest() throws Exception{
		long beginTime = System.currentTimeMillis();  
		DBCommon.init();
		long endTime = System.currentTimeMillis();  
		System.out.println("DBCommon init 花费时间为:" + (endTime - beginTime));  
		
		for (int i = 0; i < 20; i++) {
			beginTime = System.currentTimeMillis(); 
			
			Connection conn = null;
			try{
				conn = DBCommon.getConnection();
				System.out.println(conn);
				
				//PreparedStatement statement = conn.prepareStatement(SQL.getStatement("saveUser")); // "saveUser" from *_statement.xml file.
				
				// statement set to params
				// ...
				
				DBCommon.beginTransaction(conn);
				
				//int state = statement.executeUpdate();
				// ....
				
				DBCommon.commit(conn);
			}catch(SQLException e){
				log.error("",e);
			}
			finally{
				if (conn != null) {
					
					DBCommon.close(conn);
				}
			}
			endTime = System.currentTimeMillis();  
			System.out.println("第" + (i + 1) + "次执行花费时间为:" + (endTime - beginTime));  
		}
		
		DBCommon.destroy();
		
//		System.gc();
	}
	
	public static void init() throws Exception{
		
		if (dataSource != null) {
			return;
		}
		long beginTime = System.currentTimeMillis(); 
		initConfig();
		Connection conn = getConnection();
		log.info("Connection:{0}", conn);
		if (conn != null) {
			conn.close();
		}
		
		long endTime = System.currentTimeMillis();  
		log.info("init DB pool Manager Successful, use time:{0} ms", (endTime - beginTime));
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	private DBCommon() throws Exception {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @throws Exception 
	 * 
	 */
	private static void initConfig() throws Exception {
		// TODO Auto-generated constructor stub
		String path = null;
		try {
			path = ClassLoader.getSystemClassLoader().getSystemResource(DBConfigPath).toURI().getPath();
		} catch (NullPointerException e) {
			// TODO: handle exception
			path = System.getProperty("user.dir")+"/"+DBConfigPath;
		}
		
		DBConfig config = new DBConfig(path);
		
		dataSource = new ComboPooledDataSource();
		
		dataSource.setDriverClass(config.getDriverClass());
		dataSource.setJdbcUrl(config.getUrl());
		dataSource.setUser(config.getUsername());
		dataSource.setPassword(config.getPassword());
		
		dataSource.setInitialPoolSize(1);
		dataSource.setMinPoolSize(config.getMinConnection());
		dataSource.setMaxPoolSize(config.getMaxConnection());
		dataSource.setMaxIdleTime(60 * 60);
		dataSource.setNumHelperThreads(3);
		
		dataSource.setAcquireIncrement(3);
		dataSource.setAcquireRetryAttempts(3);
		dataSource.setAcquireRetryDelay(1000);
		dataSource.setTestConnectionOnCheckin(false);
		
//		dataSource.setAutomaticTestTable("e9test");
		dataSource.setPreferredTestQuery(ConfigUtil.getStringValue(ConfigKey.PREFERREDTESTQUERY));
		dataSource.setIdleConnectionTestPeriod(10 * 60);	//s
		dataSource.setCheckoutTimeout(10 * 1000); 	// ms
//		dataSource.setMaxStatements(20);
//		dataSource.setMaxStatementsPerConnection(20);
	}
	
	public static void destroy(){
		if (dataSource != null) {
			try {
				dataSource.close();
			} catch (Exception e) {
				// TODO: handle exception
				log.error("",e);
			}
			finally{
				
				dataSource = null;
			}
		}
		
	}
	
}
