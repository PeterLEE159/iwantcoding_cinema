package com.client.intercepter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 로그인 페이지 접근 미인증시 거부
 * @author peter
 *
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
	private Set<String> loginPageSet;
	public void setLoginPageSet(Set<String> loginPageSet) {
		this.loginPageSet = loginPageSet;
	}
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();
		
		if(!this.loginPageSet.contains(requestURI))
			return true;
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			session = request.getSession(true);
			session.setAttribute("REDIRECT_IN_CASE_SUCCESS_LOGIN", requestURI);
			return this.deny(response, requestURI);
		}
			
		Object customer = session.getAttribute("LOGIN_CUSTOMER");
		if(customer == null) {
			session.setAttribute("REDIRECT_IN_CASE_SUCCESS_LOGIN", requestURI);
			return this.deny(response, requestURI);
		}
		
		
		return true;
	}
		
	public boolean deny(HttpServletResponse response, String requestURI) throws IOException {
		response.sendRedirect("/movie/index?login=deny&redirectURI=" + requestURI);
		return false;
	}
}
