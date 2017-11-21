package com.client.api;

import java.io.FileInputStream;
import java.util.Properties;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 네이버번역 API
 * @author gawon
 */
public class NaverTranslate {
	
	private HttpHeaders headers;
	private RestTemplate restTemplate;
	private String url;
	
	private static NaverTranslate translateApi = new NaverTranslate();
	
	private NaverTranslate() { }
	
	public static NaverTranslate getInstance() {
		return translateApi;
	}
	
	{
		
		try {
			headers = new HttpHeaders();
			Properties props = new Properties();
		
			props.load(new FileInputStream("C:/projects/spring-web/movie-client/src/main/resources/META-INF/properties/key.properties"));

		 
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.add("X-Naver-Client-Id", props.getProperty("naver.client-id"));
			headers.add("X-Naver-Client-Secret", props.getProperty("naver.client-secret"));
			this.url = props.getProperty("naver.translate-host");
			
			this.restTemplate = new RestTemplate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 번역
	 * @param source
	 * @param target
	 * @param text
	 * @return
	 */
	public String translate(String source, String target, String text) {
		
		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.add("source", source);
		bodyMap.add("target", target);
		bodyMap.add("text", text);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(bodyMap, headers);
	
		
		return restTemplate.postForObject(url, request, String.class);
	}
	
	/**
	 * 한국어로 번역
	 * @param text
	 * @return
	 */
	public String toKorean(String text) {
		return this.translate("en", "ko", text);
	}
	
	/**
	 * 영어로 번역
	 * @param text
	 * @return
	 */
	public String toEnglish(String text) {
		return this.translate("ko", "en", text);
	}
}
