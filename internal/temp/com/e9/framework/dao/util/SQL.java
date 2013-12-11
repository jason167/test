package com.e9.framework.dao.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据db.xml中的dbtype，存储对应库的statement，便于不同库的脚本调用。
 * @date 2012-12-17
 * @author Jason
 */
public class SQL{

	private static Map<String, String> statements = new HashMap<String, String>();

	public static String getStatement(String key){
		return statements.get(key);
	}
	
	public static void setStatement(String key, String sql){
		statements.put(key, sql);
	}
	
	
}
