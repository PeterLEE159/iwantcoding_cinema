package com.client.util;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class DateUtil {
	
	private static final SimpleDateFormat YYYY = new SimpleDateFormat("yyyy");
	private static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy.MM.dd");
	private static final SimpleDateFormat YYYYMMDDHHmmSS = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	
	
	
	public static int period(Date before, Date after) {
		return (int)( (after.getTime() - before.getTime())  / (1000 * 60 * 60 * 24) );
	}
	
	public static String yyyymmdd(Date date) {
		return YYYYMMDD.format(date);
	}
	
	public static Date yyyymmdd(String date) {
		try {
			return YYYYMMDD.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date yyyymmddhhmmss(String date) {
		try {
			return YYYYMMDDHHmmSS.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String yyyy(Date date) {
		return YYYY.format(date);
	}
	
	public static String calcPastTime(Date date, String lang) {
		Date pastDate = new Date(System.currentTimeMillis() - date.getTime());
		Calendar cal = Calendar.getInstance();
		cal.setTime(pastDate);
		boolean isEng = "en".equals(lang) ? true : false; 
				
		int past = 0;
		if((past = cal.get(Calendar.YEAR) - 1970) > 0) 
			return String.format("%d%s", past, isEng ? " years ago" : " 년 전");
		else if((past = cal.get(Calendar.MONTH)) > 0) 
			return String.format("%d%s", past, isEng ? " months ago" : " 개월 전");
		else if((past = cal.get(Calendar.DATE) - 1) > 0) 
			return String.format("%d%s", past, isEng ? " days ago" : " 일 전");
		else if((past = cal.get(Calendar.HOUR) - 9) > 0) 
			return String.format("%d%s", past, isEng ? " hours ago" : " 시간 전");
		else if((past = cal.get(Calendar.MINUTE)) > 0)
			return String.format("%d%s", past, isEng ? " minutes ago" : " 분 전");
		else {
			past = cal.get(Calendar.SECOND);
			return String.format("%d%s", past, isEng ? " seconds ago" : " 초 전");
		}
	}
	
	
}
