package com.client.api;



import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.client.vo.Customer;

public class ThirdAppAuth {
	
	private HttpHeaders headers;
	private RestTemplate restTemplate;
	private String url;
	{
		this.url = "http://graph.facebook.com/me";
		this.headers = new HttpHeaders();
		
		this.headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		this.restTemplate = new RestTemplate();
	}
	
	private static ThirdAppAuth instance = new ThirdAppAuth();
	private ThirdAppAuth() { }
	
	public static ThirdAppAuth getInstance() {
		return instance;
	}
	
	public Customer fbAuth(String accessToken) {
		try {
			accessToken = "Bearer " + accessToken;
			
			URI uri = new URI("https://graph.facebook.com/me");
			uri = new URIBuilder(uri).addParameter("fields", "id,name,email,picture").build();
			
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet httpget = new HttpGet(uri);
			httpget.addHeader("Authorization", accessToken);
			
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity(); 
	    	String content = EntityUtils.toString(entity); //URLEncoder.encode(utf8(value), "UTF-8")
	    	System.out.println(content);
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		
		
//		headers.add("Authorization", accessToken);
		
//		System.out.println(accessToken);
//		System.out.println(url);
//		
//		HttpEntity entity = new HttpEntity(headers);
//		Map<String, String> param = new HashMap<String, String>();
//		param.put("fields", "id,name,email,picture");
//		ResponseEntity<String> response = restTemplate.exchange(this.url, HttpMethod.GET, entity, String.class, param);
//		
//		System.out.println(response.getBody());
		
		Customer customer = new Customer();
		return null;
	}
}
