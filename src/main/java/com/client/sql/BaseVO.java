package com.client.sql;

import java.util.Date;

import com.client.util.DateUtil;
import com.client.util.StringUtil;

public abstract class BaseVO {
	public abstract BaseVO newInstance();
	public boolean isNumericFalse(int value) {
		return value == 1;
	}
	
	public String getFullImageURI(String directory, String uri) {
		if(uri.startsWith("http"))
			return uri;
		
		return "/resources/images/" + (directory != null ? directory + "/" : "") + uri;
	}
	public String getFullImageURI(String uri) {
		return this.getFullImageURI(null, uri);
	}
	public String getFullMovieURI(String uri) {
		return this.getFullImageURI("movie", uri);
	}
	public String getFullCustomerURI(String uri) {
		return this.getFullImageURI("customer", uri);
	}
	public String cutoffString(String text, int length) {
		return StringUtil.cutoffString(text, length);
	}
	public boolean isExpired(Date date) {
		return new Date().getTime() >= date.getTime();
	}
	public String dateFormat(Date date) {
		return DateUtil.yyyymmdd(date);
	}
	public String calcPast(Date when, String lang) {
		return DateUtil.calcPastTime(when, lang);
	}
}