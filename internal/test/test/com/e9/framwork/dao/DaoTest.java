package test.com.e9.framwork.dao;

import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.e9.framework.datasource.E9DataSource;
import com.e9.framework.datasource.E9DataSourceImpl;

/**
 * @date 2013-7-24
 * @author Jason
 */
public class DaoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Properties pro = new Properties();
		pro.setProperty("driverClass", "com.mysql.jdbc.Driver");
		pro.setProperty("jdbcUrl", "jdbc:mysql://localhost:8889/sms");
		pro.setProperty("uid", "root");
		pro.setProperty("pwd", "root");
//		pro.setProperty("driverClass", "oracle.jdbc.OracleDriver");
//		pro.setProperty("jdbcUrl", "jdbc:oracle:thin:@192.168.0.84:1521:devdb");
//		pro.setProperty("uid", "osp");
//		pro.setProperty("pwd", "osp");
		
		E9DataSource dao = null;
		try {
			dao = new E9DataSourceImpl(pro);
		} catch (PropertyVetoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		pro.clear();
		pro = null;
		
		Connection conn = null;
		try {
			conn = dao.getConnection();
			System.out.println(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				dao.close(conn);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dao.destoryForDataSource();
		}
	}

}
