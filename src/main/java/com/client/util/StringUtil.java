package com.client.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	public static String nullToBlank(String str) {
		if(str == null) {
			return "";
		}
		return str.trim();
	}
	
	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty() || "null".equals(str);
	}
	
	/**
	 * @param strIds
	 * @return
	 */
	public static List<Integer> convertStringToIntList(String strIds) {
		
		List<Integer> ids = null;
		try {
			char[] chars = strIds.toCharArray();
			int size = chars.length;
			
			for(int i=0; i< size; i++) {
				if(i ==0) ids = new ArrayList();
				ids.add(Integer.parseInt(String.valueOf(chars[i])));
			}
		} catch(NumberFormatException ex) {
			ex.printStackTrace();
		}
		return ids;
	}
	
	public static int strToNumber(String str) {
		return strToNumber(str, 0);
	}
	
	public static int strToNumber(String str, int defaultValue) {
		if(str == null) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	public static String cutoffString(String str, int length) {
		if(str == null || str.isEmpty()) 
			return "";
		if(str.length() < length) 
			return str; 
		
		return String.format("%s ...", str.substring(0, length));
	}
}
