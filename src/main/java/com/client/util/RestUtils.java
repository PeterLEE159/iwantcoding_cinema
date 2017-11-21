package com.client.util;

import com.client.api.NaverTranslate;

public class RestUtils {
	
	/**
	 * 한국어로 번역
	 * @param text
	 * @return
	 */
	public static String toKorean(String text) {
		String result = NaverTranslate.getInstance().toKorean(text);
		return result.substring(result.indexOf("translatedText")+17, result.indexOf("srcLangType")-3);
	}
	
	/**
	 * 영어로 번역
	 * @param text
	 * @return
	 */
	public static String toEnglish(String text) {
		String result = NaverTranslate.getInstance().toEnglish(text);
		return result.substring(result.indexOf("translatedText")+17, result.indexOf("srcLangType")-3);
	}
}
