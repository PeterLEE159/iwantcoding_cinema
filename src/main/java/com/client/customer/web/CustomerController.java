package com.client.customer.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.client.api.ThirdAppAuth;
import com.client.service.SQLService;
import com.client.vo.Customer;
import com.client.vo.CustomerRank;
import com.client.vo.CustomerType;
import com.client.vo.MovieReview;
import com.client.vo.MovieReviewTranslation;
import com.client.vo.MovieTimetable;
import com.client.vo.MovieTranslation;
import com.client.vo.Pagination;
import com.client.vo.Screen;
import com.client.vo.ScreenMovie;
import com.client.vo.Seat;
import com.client.vo.Ticket;
import com.client.vo.TicketReceipt;

@Controller
public class CustomerController {
	@Autowired
	private SQLService service;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${dir.customer}")
	private String customerImageDir;
	
	@Value("${mail.id}")
	private String fromEmail;
	
	private List<CustomerType> cachedCustomerType;
	private List<CustomerRank> cachedCustomerRank;
	private MimeMessage message;
	private MimeMessageHelper messageHelper;
	
	{
		this.cachedCustomerType = new ArrayList<CustomerType>();
		this.cachedCustomerRank = new ArrayList<CustomerRank>();
	}
	public JavaMailSender getMailSender() {
		return mailSender;
	}


	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}


	/**
	 * 고객이 구매한 영수증 정보 읽어오기
	 * 영수증 	RID, price, 구매방식, 할인가격, 구매날짜 ID
	 * @param model
	 * @param lang
	 * @param session
	 * @return
	 */
	@GetMapping(value= {"/index", "/", "/mybooking"})
	public String myBooking(Model model, String lang, HttpSession session) {
		Customer loginCustomer = (Customer) session.getAttribute("LOGIN_CUSTOMER");
		List<TicketReceipt> receipts = this.service.table(TicketReceipt.class)
				.columns("ID", "RID", "PRICE", "PURCHASE_TYPE", "DISCOUNTED_PRICE", "PURCHASE_DATE")
				.where("CUSTOMER_ID", loginCustomer.getId())
				.orderby(false, "PURCHASE_DATE")
				.commitSelect();
		
		
		model.addAttribute("receipts", receipts);
		
		return "mybooking";
	}
	
	
	@GetMapping("/mypost")
	public String myPost(Model model, Pagination pagination, String lang, HttpSession session) {
		Customer loginCustomer = (Customer) session.getAttribute("LOGIN_CUSTOMER");
		
		model.addAttribute("reviews", this.fetchReviews(pagination, lang, loginCustomer));
		return "mypost";
	}
	
	/**
	 * 영화받기
	 * @param pagination
	 * @param lang
	 * @return
	 */
	@GetMapping("/more_review")
	@ResponseBody
	public List<MovieReviewTranslation> ajaxReviews(Pagination pagination, String lang, HttpSession session) {
		Customer loginCustomer = (Customer) session.getAttribute("LOGIN_CUSTOMER");
		return this.fetchReviews(pagination, lang, loginCustomer);
	}
	
	/**
	 * 스크린 	이름
	 * 좌석 		ID, 좌석이름
	 * 영화시간표   시작시간
	 * 영화 		ID 이름
	 * @param lang
	 * @param receiptId
	 * @return
	 */
	@GetMapping("/receipt_detail")
	@ResponseBody
	public List<MovieTimetable> myReceiptDetail(String lang, int receiptId) {
		if(lang == null || lang.equals("")) lang = "ko";
		TicketReceipt receipt = this.service.table(TicketReceipt.class)
				.columns("ID")
				.where("ID", receiptId)
				.commitSelectSingle();
		List<Ticket> tickets = this.service.table(Ticket.class)
					.where("TICKET_RECEIPT_ID", receipt.getId())
					.commitSelect();
		List<MovieTimetable> timetables = new ArrayList();
		for(Ticket ticket : tickets) {
			
			MovieTimetable timetable = this.service.table(MovieTimetable.class)
					.columns("STARTED_AT", "SCREEN_MOVIE_ID")
					.where("ID", ticket.getMovietimetableId())
					.commitSelectSingle();
			timetable.setSeat(this.service.table(Seat.class)
					.columns("NAME")
					.where("ID", ticket.getSeatId())
					.commitSelectSingle());
			if(timetable != null) {
				ScreenMovie screenMovie = this.service.directSelect(ScreenMovie.class, timetable.getScreenMovieId());
				
				
				if(screenMovie != null) {
					timetable.setMovie(this.service.table(MovieTranslation.class)
							.columns("ID", "NAME", "MOVIE_ID")
							.where("LANG_ISO", lang)
							.where("MOVIE_ID", screenMovie.getMovieId())
							.commitSelectSingle());
					timetable.setScreen(this.service.table(Screen.class)
							.columns("NAME")
							.where("ID", screenMovie.getScreenId())
							.commitSelectSingle());
					
					timetables.add(timetable);
					
				}
			}
			
		}
		return timetables;
	}
	
	
	@PostMapping("/login")
	public String login(HttpSession session, Customer customer, String redirectURI, String lang) {
		
		Customer foundCustomer = this.service.table(Customer.class)
				.where("USERNAME", customer.getUsername())
				.andIsNull(true, "THIRD_PARTY")
				.commitSelectSingle();
		
		if(foundCustomer == null || !foundCustomer.getPassword().equals(DigestUtils.md5DigestAsHex(customer.getPassword().getBytes())))
			return "redirect:"+redirectURI + "?login=fail";
		
		foundCustomer.setType(this.service.directSelect(CustomerType.class, foundCustomer.getCustomerTypeId()));
		foundCustomer.setRank(this.service.directSelect(CustomerRank.class, foundCustomer.getCustomerRankId()));
		
		session.setAttribute("LOGIN_CUSTOMER", foundCustomer);
		String successRedirect = String.valueOf(session.getAttribute("REDIRECT_IN_CASE_SUCCESS_LOGIN"));
		if(successRedirect != null) {
			session.removeAttribute("REDIRECT_IN_CASE_SUCCESS_LOGIN");
			return "redirect:"+successRedirect+"?lang="+lang;
		}
		
		return "redirect:/movie/index?lang="+lang;
	}
	
	/**
	 * 제 3자 앱인증을 통한 로그인
	 * @param customer
	 * @return
	 */
	@PostMapping("/third_login")
	@ResponseBody
	public Customer thirdSignup(Customer customer, HttpSession session) {
		if(customer.getThirdParty().equals("F")) {
			customer = this.fetchFBCustomer(customer);
			return null;
		}
			
		
		customer.setPassword(DigestUtils.md5DigestAsHex(customer.getThirdPartyValidationId().getBytes()));
		Customer foundCustomer = this.service.table(Customer.class)
				.where("USERNAME", customer.getUsername())
				.commitSelectSingle();
		if(foundCustomer == null) {
			customer.setCustomerRankId(this.fetchCustomerRank());
			customer.setCustomerTypeId(1);
			this.service.directInsert(customer);
			
			foundCustomer = this.service.table(Customer.class)
					.where("USERNAME", customer.getUsername())
					.commitSelectSingle();
		}
		foundCustomer.setType(this.service.directSelect(CustomerType.class, foundCustomer.getCustomerTypeId()));
		foundCustomer.setRank(this.service.directSelect(CustomerRank.class, foundCustomer.getCustomerRankId()));
		
		
		session.setAttribute("LOGIN_CUSTOMER", foundCustomer);
		
		return foundCustomer;
	}
	
	/**
	 * facebook 고객정보 받기
	 * @param customer
	 * @return
	 */
	private Customer fetchFBCustomer(Customer customer) {
		String accessToken = customer.getUsername();
		return ThirdAppAuth.getInstance().fbAuth(accessToken);
	}


	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if(session != null) session.invalidate();
		return "redirect:/movie/index?lang=";
	}
	
	/**
	 * 회원가입 폼
	 */
	@GetMapping("/signupform")
	public String signup(Model model) {
		model.addAttribute("types", this.fetchCustomerType());
		return "signup";
	}
	
	

	@PostMapping("/idcheck")
	@ResponseBody
	public String idcheck(String username) {
		Customer customer = this.service.table(Customer.class)
				.where("USERNAME", username)
				.commitSelectSingle();
		return customer == null ? "success" : "duplicated";
	}
	/**
	 * 회원가입
	 * @param customer
	 * @return
	 */
	@PostMapping("/signup")
	public String signup(Customer customer, MultipartFile file) {
		try {
			Customer foundCustomer = this.service.table(Customer.class)
					.where("USERNAME", customer.getUsername())
					.commitSelectSingle();
			if(foundCustomer != null)
				return "redirect:/customer/signupform?signup=error";
			
			if(!file.isEmpty()) {
				String filename = System.currentTimeMillis() + "_" +file.getOriginalFilename();
				customer.setImageURI(filename);
				FileCopyUtils.copy(file.getBytes(), new File(customerImageDir, filename));
			}
			customer.setPassword(DigestUtils.md5DigestAsHex(customer.getPassword().getBytes()));
			customer.setCustomerRankId(this.fetchCustomerRank());
			
			this.service.directInsert(customer);
		} catch (IOException e) {
			e.printStackTrace();
		
		}
		
		return "redirect:/movie/index?login=signup";
	}
	
	
	@GetMapping("/myupdate")
	public String myupdate() {		
		return "passwordcheck";
	}
	
	/**
	 * 비밀번호 확인
	 * @param id
	 * @param password
	 * @param session
	 * @param model
	 * @return
	 */
	@PostMapping("/myupdate")
	public String passwordcheck(String id, String password, HttpSession session, Model model, @RequestParam(required=false) String lang) {
		Customer loginCustomer = (Customer) session.getAttribute("LOGIN_CUSTOMER");
		if(!id.equals(loginCustomer.getUsername()))
			return "redirect:/customer/myupdate?access=deny&lang="+lang;
		String hashedPwd = DigestUtils.md5DigestAsHex(password.getBytes()); 
		if(!hashedPwd.equals(loginCustomer.getPassword()))
			return "redirect:/customer/myupdate?access=deny&lang="+lang;
		
		String phone = loginCustomer.getPhone();
		if(phone != null && !phone.equals("")) {
			String[] phones = phone.split("-");
			if(phones.length == 3) {
				model.addAttribute("firstPhone", phones[0]);
				model.addAttribute("secondPhone", phones[1]);
				model.addAttribute("thirdPhone", phones[2]);
			}
		}
		
		return "myupdate";
	}
	
	@PostMapping("/update")
	public String update(Customer customer, MultipartFile file, HttpSession session) throws IOException {
		Customer loginCustomer = (Customer)session.getAttribute("LOGIN_CUSTOMER");
		
		if(customer.getPassword() != null) loginCustomer.setPassword(DigestUtils.md5DigestAsHex(customer.getPassword().getBytes()));
		
		if(customer.getPhone() != null) loginCustomer.setPhone(customer.getPhone());	
		if(customer.getEmail() != null) loginCustomer.setEmail(customer.getEmail());
		
		if(!file.isEmpty()) {
			String filename = System.currentTimeMillis() + "_" +file.getOriginalFilename();
			
			loginCustomer.setImageURI(filename);
			FileCopyUtils.copy(file.getBytes(), new File(customerImageDir, filename));
		}
		
		loginCustomer.setName(customer.getName());
		loginCustomer.setGender(customer.getGender());
		
		this.service.directUpdate(loginCustomer);
		
		return "redirect:/movie/index?alert=update";
	}
	
	@GetMapping("/find")
	public String findIdentification() {
		return "find";
	}
	
	/**
	 * 유저에게 아이디 및 비밀번호 전송
	 * @param target
	 * @param name
	 * @param email
	 * @return
	 * @throws MessagingException 
	 */
	@PostMapping("/find")
	public String findIdentification(String target, @NotEmpty String name, @Email String email, String lang) throws MessagingException {
		if(lang == null || lang.equals("")) lang = "ko";
		Customer foundCustomer = this.service.table(Customer.class)
				.where("EMAIL", email)
				.and("NAME", name)
				.commitSelectSingle();
		
		if(foundCustomer != null) {
			if(this.message == null) this.setMailFormat();
			
			this.messageHelper.setTo(email);
			
			if("id".equals(target)) {
				String username = foundCustomer.getUsername();
				this.messageHelper.setSubject(lang.equals("ko") ? "아이디 찾기" : "Find Your ID");
				this.messageHelper.setText(lang.equals("ko") ? "아이디: " +username : "New password: "+username);
			} else if("pwd".equals(target)){
				String newPwd = String.valueOf(System.currentTimeMillis());
				foundCustomer.setPassword(DigestUtils.md5DigestAsHex(newPwd.getBytes()));
				this.service.directUpdate(foundCustomer);
				
				this.messageHelper.setSubject(lang.equals("ko") ? "비밀번호 찾기" : "Find Your Password");
				this.messageHelper.setText(lang.equals("ko") ? "새로운 비밀번호: " + newPwd : "New password: "+newPwd);
			}
			this.mailSender.send(this.message);
		}
		
		return "redirect:/customer/find?result=sent&target="+target;
		
	}
	
	
	
	private void setMailFormat() {
		this.message = this.mailSender.createMimeMessage();
		try {
			this.messageHelper = new MimeMessageHelper(this.message, false, "UTF-8");
			this.messageHelper.setFrom(this.fromEmail);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 모든 고객별 타입 받기
	 * @return
	 */
	private List<CustomerType> fetchCustomerType() {
		if(!this.cachedCustomerType.isEmpty())
			return this.cachedCustomerType;
		
		List<CustomerType> types = this.service.table(CustomerType.class)
				.commitSelect();
		this.cachedCustomerType.addAll(types);
		return types;
	}
	
	/**
	 * 고객 등급정보 받기
	 * @return
	 */
	private int fetchCustomerRank() {
		int lowest = Integer.MAX_VALUE;
		if(this.cachedCustomerRank.isEmpty()) 
			this.cachedCustomerRank.addAll(this.service.table(CustomerRank.class)
					.commitSelect());
			
		for(CustomerRank rank : this.cachedCustomerRank) {
			int newRank = rank.getPriority();
			if(newRank < lowest)
				lowest = newRank;
		}
			
		return lowest;
	}
	
	/**
	 * 리뷰 요청
	 * @param pagination
	 * @param lang
	 * @return
	 */
	private List<MovieReviewTranslation> fetchReviews(Pagination pagination, String lang, Customer customer) {
		if(lang == null || lang.isEmpty() ) lang = "ko";
		if(pagination.getPage() == 0) pagination.setPage(1);
		
		
		
		List<MovieReview> commons = this.service.table(MovieReview.class)
							.where("CUSTOMER_ID", customer.getId())
							.between(pagination.getBeginIndex(), pagination.getEndIndex())
							.orderby(false, "CREATED_AT")
							.commitSelect();
		
		List<MovieReviewTranslation> reviews = new ArrayList();
		for(MovieReview common : commons) {
			
			MovieReviewTranslation review = this.service.table(MovieReviewTranslation.class)
					.columns("REVIEW")
					.where("MOVIE_REVIEW_ID", common.getId())
					.and("LANG_ISO", lang)
					.commitSelectSingle();
			if(review != null) {
				review.setCommon(common);
				
				review.setCustomer(customer);
				
				review.setMovie(this.service.table(MovieTranslation.class)
						.columns("MOVIE_ID", "NAME")
						.where("MOVIE_ID", common.getMovieId())
						.and("LANG_ISO", lang)
						.commitSelectSingle());
				reviews.add(review);
			}
		}
		
		return reviews;
	}
}
