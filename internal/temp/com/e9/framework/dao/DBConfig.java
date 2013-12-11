package com.e9.framework.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.e9.framework.dao.util.SQLHelper;

/**
 * DB pojo
 * @project EskReport
 * @date 2012-12-6
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-6] create by Jason
 */
public class DBConfig {
	
	private String driverClass;
	private String url;
	private String username;
	private String password;
	private int maxConnection = 20;
	private int minConnection = 1;
	private final static Pattern numberRegex = Pattern.compile("\\d+");
	
	/**
	 * @return the minConnection
	 */
	public int getMinConnection() {
		return minConnection;
	}

	/**
	 * @param minConnection the minConnection to set
	 */
	public void setMinConnection(int minConnection) {
		this.minConnection = minConnection;
	}

	/**
	 * @return the maxConnection
	 */
	public int getMaxConnection() {
		return maxConnection;
	}

	/**
	 * @param maxConnection the maxConnection to set
	 */
	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}

	private Logger log = LoggerFactory.getLogger(DBConfig.class);
	
	/**
	 * 
	 */
	public DBConfig() {
		// TODO Auto-generated constructor stub
	}
	
	public DBConfig(String path) throws Exception{
		new DBXmlToConfig(path).loadXmlConfig();
		if (this.driverClass == null || this.driverClass.equals("")
				|| this.url == null || this.url.equals("")
				|| this.username == null || this.username.equals("")
				|| this.password == null || this.password.equals("")) {
			throw new IllegalArgumentException("'db.xml' arguments error!!");
		}
	}
	
	/**
	 * @return the driverClass
	 */
	public String getDriverClass() {
		return driverClass;
	}
	/**
	 * @param driverClass the driverClass to set
	 */
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	class DBXmlToConfig{
		
		private String path;
		private final int ORACLE = 0;
		private final int MYSQL = 1;
		private final int SYBASE = 2;
		private final int MSSQLSERVER = 3;
		/**
		 * 
		 */
		public DBXmlToConfig(String path) {
			// TODO Auto-generated constructor stub
			this.path = path;
		}
		
		public void loadXmlConfig() throws Exception{
			FileInputStream inputStream = null;
			SAXReader reader = null;
			Document document = null;
			try {
				inputStream = new FileInputStream(new File(path));
				reader = new SAXReader();
				document = reader.read(inputStream);
				
				Element root = document.getRootElement();
				int dbtype = Integer.parseInt(root.elementText("dbtype"));
				
				Element dbConf = null;
				switch (dbtype) {
				case MYSQL:
					dbConf = getElement(root,"mysql");
					SQLHelper.init("jdbc/mysql_statement.xml");
					break;
				case SYBASE:
					dbConf = getElement(root,"sybase");
					SQLHelper.init("jdbc/sybase_statement.xml");
					break;
				case MSSQLSERVER:
					dbConf = getElement(root,"mssqlserver");
					SQLHelper.init("jdbc/mssqlserver_statement.xml");
					break;
				default:
					// ORACLE:
					dbConf = getElement(root,"oracle");
					SQLHelper.init("jdbc/oracle_statement.xml");
					break;
				}
				
				driverClass = dbConf.elementText("driverClass");
				url = dbConf.elementText("jdbcUrl");
				username = dbConf.elementText("user");
				password = dbConf.elementText("password");
				String maxConnectionString = root.elementText("maxConnection");
				if (!numberRegex.matcher(maxConnectionString).matches()) {
					throw new NumberFormatException("maxConnection property is not a Number");
				}
				String minConnectionString = root.elementText("minConnection");
				if (!numberRegex.matcher(minConnectionString).matches()) {
					throw new NumberFormatException("minConnection property is not a Number");
				}
				minConnection = Integer.parseInt(minConnectionString);
				maxConnection = Integer.parseInt(maxConnectionString);
				
			} catch (Exception e) {
				// TODO: handle exception
				log.error("",e);
				throw e;
			}
			finally{
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {}
				}
				
				document = null;
				reader = null;
			}
			
		}
		
		private Element getElement(Element e, String tagname){
			return e.element(tagname);
		}
	}
	
}
