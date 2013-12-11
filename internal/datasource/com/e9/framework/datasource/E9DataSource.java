package com.e9.framework.datasource;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * @date 2013-7-23
 * @author Jason
 */
public interface E9DataSource {
	
//	public boolean initDataSource();
	
	public Connection getConnection() throws SQLException;
	
	public void close(Connection conn) throws SQLException;
	
	public void beginTransaction(Connection conn) throws SQLException;
	
	public void commit(Connection conn) throws SQLException;
	
	public void rollback(Connection conn) throws SQLException;
	
	public void destoryForDataSource();
}
