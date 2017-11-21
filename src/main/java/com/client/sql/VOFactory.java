package com.client.sql;

import java.util.HashMap;
import java.util.Map;

public class VOFactory {
	private static VOFactory factory = new VOFactory();
	private static Map<String, BaseVO> voMap = new HashMap();
	public VOFactory() { }
	public static VOFactory getFactory() {
		return factory;
	}
	
	public static BaseVO getInstance(String className) {
		if(voMap.containsKey(className))
			return voMap.get(className).newInstance();
		else {
			try {
				BaseVO instance = (BaseVO) Class.forName(className).newInstance();
				voMap.put(className, instance);
				return instance.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}