package com.client.web.editor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateEditor extends PropertyEditorSupport {

	SimpleDateFormat yyyymmdd = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat yyyymmddhh = new SimpleDateFormat("yyyy-MM-dd HH");
	SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	// 폼입력값을 변수에 담을 때 실행된다.
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			setValue(yyyymmddhhmmss.parse(text));
		} catch (ParseException e) {
			try {
				setValue(yyyymmddhh.parse(text));
			} catch (ParseException e1) {
				try {
					setValue(yyyymmdd.parse(text));
				} catch (ParseException e2) {
					setValue(null);
				}
			}
			
			
		}
	}
	
	// 변수에 들어있는 값을 꺼낼 때 실행된다.
	public String getAsText() {
		String text = "";
		if (getValue() != null) {
			text = yyyymmdd.format(getValue());
		}
		return text;
	}
}




