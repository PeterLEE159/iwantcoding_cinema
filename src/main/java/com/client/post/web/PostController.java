package com.client.post.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.client.service.MySQLServiceImpl;
import com.client.vo.Customer;

@Controller
public class PostController {
	@Autowired
	private MySQLServiceImpl service;
	
	@PostMapping("/login")
	public String login(HttpSession session, Customer customer, String redirectURI) {
		
		Customer foundCustomer = this.service.table(Customer.class)
				.where("USERNAME", customer.getUsername())
				.commitSelectSingle();
		
		if(foundCustomer == null || !foundCustomer.getPassword().equals(DigestUtils.md5DigestAsHex(customer.getPassword().getBytes())))
			return "redirect:"+redirectURI + "?login=fail";
		
		session.setAttribute("LOGIN_USER", foundCustomer);
		
		return "redirect:" + redirectURI;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if(session != null) session.invalidate();
		return "redirect:/";
	}
}
