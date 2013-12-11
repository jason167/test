package com.e9.framework.dao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 用于解析 dbtype_statement.xml
 * @date 2012-12-17
 * @author Jason
 */
public class SQLHelper {
	
	private static Logger log = LoggerFactory.getLogger(SQLHelper.class);
	
	@SuppressWarnings("static-access")
	public static void init(String statement_filename) throws Exception{
		log.info("SqlHelper init db statement xml...");
		String statement_path;
		try {
			statement_path = ClassLoader.getSystemClassLoader().getSystemResource(statement_filename).toURI().getPath();
			
			if (statement_path == null || statement_path.equals("")) {
				statement_path = System.getProperty("user.dir")+"/"+statement_filename;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			statement_path = System.getProperty("user.dir")+"/"+statement_filename;
		}
		
		FileInputStream statement_file = null;
		SAXReader saxReader = null;
		Document document = null;
		
		try{
			statement_file = new FileInputStream(new File(statement_path));
			saxReader = new SAXReader();
			document = saxReader.read(statement_file);
			
			Element root = document.getRootElement(); // statements
			
			@SuppressWarnings("rawtypes")
			Iterator sqls = root.elements().iterator();
			while (sqls.hasNext()) {
			
				Element sql_element = (Element) sqls.next();
				
				SQL.setStatement(sql_element.attributeValue("name"), sql_element.getText());
			}
			
			log.info("SqlHelper init db statement xml is ok");
		}catch(Exception e){
			
			log.error("SqlHelper is error:", e);
			throw e;
		}
		finally{
			if (statement_file != null) {
				try {
					statement_file.close();
				} catch (IOException e) {}
			}
			saxReader = null;
			document = null;
		}
		
	}

}
