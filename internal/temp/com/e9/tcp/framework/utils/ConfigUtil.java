package com.e9.tcp.framework.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 用于加载/config/conf.properties文件的内容的工具类;
 * @project E9Framework
 * @date 2012-12-8
 * @version 1.0
 * @author Jason
 * 
 * @review_history
 * [2012-12-8] create by Jason
 */
public class ConfigUtil {
	
	private static Properties pro = new Properties();
	private final static String PATH = "conf.properties";
	private final static Logger logger =
            LoggerFactory.getLogger(ConfigUtil.class);
	
	private ConfigUtil() { }
	
	public static boolean initConfig() throws URISyntaxException{
		String confPath = null;
		try {
			confPath= ClassLoader.getSystemClassLoader().getSystemResource(PATH).toURI().getPath();
		} catch (NullPointerException e) {
			// TODO: handle exception
			confPath = System.getProperty("user.dir")+"/"+PATH;
		}
		
		FileInputStream inputStream = null;
		try {
			if (pro == null) {
				pro = new Properties();
			}
			inputStream = new FileInputStream(new File(confPath));
			pro.load(inputStream);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			if (pro != null) {
				pro.clear();
			}
			logger.error("", e);
			return false;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if (pro != null) {
				pro.clear();
			}
			logger.error("", e);
			return false;
			
		}
		finally{
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {}
			}
		}
		
		return true;
		
	}
	
	public static String getStringValue(String key){
		return pro.getProperty(key);
	}
	
	public static Integer getIntgerValue(String key){
		String v = pro.getProperty(key);
		if (v == null) {
			return null;
		}
		return Integer.valueOf(v);
	}

}
