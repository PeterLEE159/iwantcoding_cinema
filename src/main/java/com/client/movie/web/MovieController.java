package com.client.movie.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.client.service.SQLService;
import com.client.sql.SQLBuilder;
import com.client.util.RestUtils;
import com.client.vo.Coupon;
import com.client.vo.CouponCustomer;
import com.client.vo.Customer;
import com.client.vo.CustomerRank;
import com.client.vo.Discount;
import com.client.vo.DiscountTicket;
import com.client.vo.Genre;
import com.client.vo.Gift;
import com.client.vo.GiftCustomer;
import com.client.vo.Movie;
import com.client.vo.MovieGenre;
import com.client.vo.MovieGenreTranslation;
import com.client.vo.MovieImage;
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
import com.client.vo.form.TicketForm;
import com.client.vo.form.TicketForm.EachTicketForm;

//@SessionAttributes(names={"ticketForm"})
@Controller
public class MovieController {
	
	@Autowired
	private SQLService service;
	
	private Map<String, List<MovieGenreTranslation>> cachedGenreMap;
	
	
	{
		this.cachedGenreMap = new HashMap<String, List<MovieGenreTranslation>>();
		
	}
	
	/**
	 * 영화조회 페이지
	 * @param pagination
	 * @param model
	 * @param lang
	 * @return
	 */
	@GetMapping(value= {"/index", "/home", "/"})
	public String index(Model model, Pagination pagination, Integer[] genre, Integer ageLimit, String lang) {
		
		model.addAttribute("genres", this.fetchGenre(lang));
		model.addAttribute("movies", this.fetchMovies(pagination, genre, ageLimit, lang));
		return "index";
	}
	
	
	
	@PostMapping("/ajaxmovie")
	@ResponseBody
	public List<MovieTranslation> ajaxMovie(@RequestBody Map<String, Object> params) {
		Pagination pagination = new Pagination();
		String keyword = (String)params.get("keyword");
		String opt = (String)params.get("opt");
		if(keyword != null && keyword.equals("")) keyword = null;
		if(opt != null && opt.equals("")) opt = null;
		
		
		pagination.setPage((Integer) params.get("page"));
		pagination.setKeyword(keyword);
		pagination.setOpt(opt);
		
		List<Integer> genreList = (ArrayList<Integer>) params.get("genre");
		int size = genreList.size();
		Integer[] genres = new Integer[size];
		
		for(int i=0; i < size ; i++)
			genres[i] = genreList.get(i);
		
		Integer ageLimit = (Integer)params.get("ageLimit");
		String lang = (String)params.get("lang");
		
		
		
		return this.fetchMovies(pagination, genres, ageLimit, lang);
	}
	
	
	
	
	/**
	 * 결제하기 중 결제하려는 쿠폰 세션에 저장하기
	 * @param ticketForm
	 * @param lang
	 * @return
	 */
	@PostMapping("/select_coupon")
	public String selectCoupon(TicketForm ticketForm, HttpSession session, String lang) {
		if(lang == null || lang.isEmpty() ) lang = "ko";
		
		session.setAttribute("TICKETS", ticketForm);
		
		List<EachTicketForm> tickets = ticketForm.getTickets();
		tickets.removeIf(new Predicate<EachTicketForm>() {
			@Override
			public boolean test(EachTicketForm ticket) {
				return ticket.getTimetableId() == 0;
			}
		});
		return "redirect:/movie/select_coupon?lang="+lang;
	}
	

	
	
	
	/**
	 * 쿠폰 선택하기
	 * @param model
	 * @param ticketForm
	 * @param lang
	 * @param session
	 * @return
	 */
	@GetMapping("/select_coupon")
	public String selectCoupon(Model model, String lang, HttpSession session) {
		TicketForm ticketForm = (TicketForm) session.getAttribute("TICKETS");
		
		Customer loginCustomer = (Customer) session.getAttribute("LOGIN_CUSTOMER");
		final int customerId = loginCustomer.getId();
		Date today = new Date();
		
		
		
		List<CouponCustomer> customerCoupones = this.service.table(CouponCustomer.class)
				.columns("ID", "USED", "EXPIRED_AT", "COUPON_ID")
				.where("CUSTOMER_ID", loginCustomer.getId())
				.and("EXPIRED_AT", today, ">=")
				.and("USED", 0)
				.commitSelect();
		
		for(CouponCustomer eachCoupon : customerCoupones) {
			Coupon coupon = this.service.table(Coupon.class)
					.columns("NAME")
					.where("ID", eachCoupon.getCouponId())
					.commitSelectSingle();
			if(coupon != null)
				eachCoupon.setCoupon(coupon);
		}
		List<Discount> discounts = this.service.table(Discount.class)
				.columns("NAME", "ID", "DETAIL", "DISCOUNT_PERCENT", "STARTED_DATE", "END_DATE")
				.where("STARTED_DATE", today, "<=")
				.and("END_DATE", today, ">=")
				.commitSelect();
		model.addAttribute("tickets", ticketForm.getTickets());
		model.addAttribute("coupones", customerCoupones);
		model.addAttribute("discounts", discounts);
		
		return "select_coupon";
	}
	
	/**
	 * 결제하기
	 * 영수증, 티켓, 쿠폰사용도, 할인 사용도
	 * @param ticketForm
	 * @param receipt
	 * @param lang
	 * @param couponCustomerId
	 * @param discountId
	 * @param session
	 * @param status
	 * @return
	 */
	@PostMapping("/payment")
	public String payment(TicketReceipt receipt, String lang, int couponCustomerId, int discountId, HttpSession session) {
		if(lang == null || lang.isEmpty() ) lang = "ko";
		TicketForm ticketForm = (TicketForm) session.getAttribute("TICKETS");
		
		Customer loginCustomer = (Customer) session.getAttribute("LOGIN_CUSTOMER");
		
		receipt.setRid(DigestUtils.md5DigestAsHex(String.valueOf(System.currentTimeMillis() + loginCustomer.getName()).getBytes()));
		receipt.setPurchaseType("C");
		receipt.setCustomerId(loginCustomer.getId());
		int receiptId = this.service.directInsert(receipt);
		
		List<EachTicketForm> tickets = ticketForm.getTickets();
		Ticket ticket = new Ticket();
		ticket.setCustomerTypeId(loginCustomer.getType().getId());
		ticket.setTicketReceiptId(receiptId);
		for(EachTicketForm eachTicket : tickets) {
			ticket.setSeatId(eachTicket.getSeatId());
			ticket.setMovietimetableId(eachTicket.getTimetableId());
			this.service.directInsert(ticket);
		}
		if(couponCustomerId != 0) {
			this.service.table(CouponCustomer.class)
				.where("ID", couponCustomerId)
				.set("USED", 1)
				.commitUpdate();	
		}
		if(discountId != 0) {
			DiscountTicket discountTicket = new DiscountTicket();
			discountTicket.setDiscountId(discountId);
			discountTicket.setReceiptId(receiptId);
			this.service.directInsert(discountTicket);
		}
		
		session.removeAttribute("TICKETS");
		return "redirect:/customer/index?lang="+lang+"&result=success";
	}
	
	/**
	 * 영화예약
	 * @param model
	 * @param id
	 * @return
	 */
	@GetMapping("/booking")
	public String booking(Model model, String lang, Date when) {
		// 호출 영화정보
		if(when == null)
			when = new Date();
		
		List<MovieTranslation> movies = new ArrayList();
		List<Movie> commons = this.service.table(Movie.class)
				.where("CLOSE_DATE", when, ">=")
				.and("OPEN_DATE", when, "<=")
				.commitSelect();
		
		
		for(Movie common : commons) {
			MovieTranslation movie = this.service.table(MovieTranslation.class)
					.where("MOVIE_ID", common.getId())
					.and("LANG_ISO", "en".equals(lang) ? "en" : "ko")
					.commitSelectSingle();
			
			if(movie == null) continue;
			
			movie.setCommon(common);
			movies.add(movie);
		}
		
		model.addAttribute("movies", movies);
		
		
		return "booking";
	}
	
	@ResponseBody
	@GetMapping("/schedule")
	public List<Screen> schedule(int mid, String lang, Date date) {
		List<ScreenMovie> sms = this.service.table(ScreenMovie.class)
				.where("MOVIE_ID", mid)
				.commitSelect();
		
		List<Screen> screens = new ArrayList();
		List<MovieTimetable> timetables = new ArrayList();
		for(ScreenMovie sm : sms) {
			Screen screen = this.service.directSelect(Screen.class, sm.getScreenId());
			if(screen == null) continue;
			
			
			List<MovieTimetable> eachTimetable = this.service.table(MovieTimetable.class)
					.where("SCREEN_MOVIE_ID", sm.getId())
					.andDate("STARTED_AT", date, "hours")
					.commitSelect();
			
			List<Seat> seats = this.service.table(Seat.class)
					.where("SCREEN_ID", sm.getScreenId())
					.orderby("ID")
					.commitSelect();
			
			for(MovieTimetable timetable : eachTimetable) {
				timetable.setTickets(this.service.table(Ticket.class)
						.where("MOVIE_TIMETABLE_ID", timetable.getId())
						.commitSelect());
			}
			screen.setSeats(seats);
			screen.setTimetables(eachTimetable);
			screens.add(screen);
		}
		return screens;
	}
	
	
	/**
	 * 영화 상세정보 화면
	 * @param model
	 * @param mid
	 * @param lang
	 * @return
	 */
	@GetMapping("/detail")
	public String detail(Model model, int mid, String lang) {
		model.addAttribute("movie", this.fetchMovie(mid, lang));
		return "detail";
	}
	
	
	/**
	 * 영화리뷰 호출하기
	 * 고객아이디, 고객이름, 고객이미지, 리뷰 내용, 평점, 리뷰 생성시간, 대상영화 이름, 대상영화 아이디  
	 * @param model
	 * @param pagination
	 * @param lang
	 * @return
	 */
	@GetMapping("/reviews")
	public String reviews(Model model, Pagination pagination, String lang, @RequestParam(required=false) Integer cid) {
		
		model.addAttribute("reviews", this.fetchReviews(pagination, lang, cid == null ? 0 : cid));
		return "reviews";
	}
	
	/**
	 * 영화받기
	 * @param pagination
	 * @param lang
	 * @return
	 */
	@GetMapping("/more_review")
	@ResponseBody
	public List<MovieReviewTranslation> ajaxReviews(Pagination pagination, String lang, @RequestParam(required=false) Integer cid) {
		return this.fetchReviews(pagination, lang, cid == null ? 0 : cid);
	}
	
	


	/**
	 * 쿠폰 & 사은품 지급받기 화면
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/coupon")
	public String coupon(Model model, HttpSession session) {
		Customer loginCustomer = (Customer)session.getAttribute("LOGIN_CUSTOMER");
		Date today = new Date();
		
		CustomerRank rank = loginCustomer.getRank();
		List<Gift> gifts = this.service.table(Gift.class)
				.where("CUSTOMER_RANK_ID", rank.getId(), ">=")
				.and("DISTRIBUTE_UNTIL", today, ">=")
				.commitSelect();
		List<Coupon> coupones = this.service.table(Coupon.class)
				.where("CUSTOMER_RANK_ID", rank.getId(), ">=")
				.and("DISTRIBUTE_UNTIL", today, ">=")
				.commitSelect(); 
		
		model.addAttribute("gifts", gifts);
		model.addAttribute("coupones", coupones);
		
		return "coupon";
	}
	
	
	
	/**
	 * 리뷰 업로드
	 * @param review
	 * @param content
	 * @return
	 */
	@PostMapping("/insert_review")
	@ResponseBody
	public MovieReviewTranslation insertReview(MovieReview common, String content, String lang, HttpSession session) {
		lang = "en".equals(lang) ? "en" : "ko";
		MovieReviewTranslation review = new MovieReviewTranslation();
		Customer loginCustomer = (Customer)session.getAttribute("LOGIN_CUSTOMER");
		
		common.setCustomerId(loginCustomer.getId());
		review.setMovieReviewId( this.service.directInsert(common));
		
		review.setLangDefault(lang.equals("ko") ? 1 : 0);
		review.setLangIso(lang);
		review.setReview(content);
		this.service.directInsert(review);
		
		 
		review.setLangDefault(lang.equals("ko") ? 0 : 1);
		review.setLangIso("en".equals(lang) ? "ko" : "en");
		review.setReview("en".equals(lang) ? RestUtils.toKorean(content) : RestUtils.toEnglish(content));
		this.service.directInsert(review);
		
		review.setReview(content);
		review.setCustomer(loginCustomer);
		review.setCommon(common);
		return review;
	}
	
	
	/**
	 * 쿠폰 발급받기
	 * @param coupon
	 * @param session
	 * @return
	 */
	@PostMapping("/issue_coupon")
	@ResponseBody
	public String issueCoupon(CouponCustomer coupon, HttpSession session) {
		Date today = new Date();
		Date oneWeekAfter = new Date(today.getTime() + (1000 * 60 * 60 * 24 * 7));
		
		Customer loginCustomer = (Customer)session.getAttribute("LOGIN_CUSTOMER");
		
		CouponCustomer foundCoupon = this.service.table(CouponCustomer.class)
				.where("CUSTOMER_ID", loginCustomer.getId())
				.and("COUPON_ID", coupon.getCouponId())
				.commitSelectSingle();
		if(foundCoupon != null) return "failure";
		
		coupon.setCustomerId(loginCustomer.getId());
		coupon.setReceivedAt(today);
		coupon.setExpiredAt(oneWeekAfter);
		coupon.setUsed(0);
		coupon.setReceived(1);
		
		this.service.directInsert(coupon);
		
		return "success";
	}
	
	/**
	 * 사은품 발급받기
	 * @param gift
	 * @param session
	 * @return
	 */
	@PostMapping("/issue_gift")
	@ResponseBody
	public String issueGift(GiftCustomer gift, HttpSession session) {
		Date today = new Date();
		
		Customer loginCustomer = (Customer)session.getAttribute("LOGIN_CUSTOMER");
		
		GiftCustomer foundGift = this.service.table(GiftCustomer.class)
				.where("CUSTOMER_ID", loginCustomer.getId())
				.and("GIFT_ID", gift.getGiftId())
				.commitSelectSingle();
		if(foundGift != null) return "failure";
		
		gift.setCustomerId(loginCustomer.getId());
		gift.setReceivedAt(today);
		gift.setReceived(1);
		
		this.service.directInsert(gift);
		
		return "success";
	}
	
	
	/**
	 * 단일 영화정보 읽어오기
	 * 영화정보, 평점, 리뷰 최대 15개, 모든 이미지 , 장르
	 * @param mid
	 * @param lang
	 * @return
	 */
	private MovieTranslation fetchMovie(int mid, String lang) {
		if(lang == null || lang.isEmpty()) lang = "ko";
		Movie common = this.service.directSelect(Movie.class, mid);
		MovieTranslation movie = this.service.table(MovieTranslation.class)
				.where("MOVIE_ID", mid)
				.and("LANG_ISO", lang)
				.commitSelectSingle();
		
		movie.setCommon(common);
		movie.setRatingPoint(this.service.table(MovieReview.class)
				.where("MOVIE_ID", movie.getMovieId())
				.commitMethod("RATING_POINT", "avg"));
		
		// 리뷰
		List<MovieReviewTranslation> reviews = new ArrayList();
		List<MovieReview> commonReviews = this.service.table(MovieReview.class)
				.where("MOVIE_ID", mid)
				.between(1, 15)
				.commitSelect();
		for(MovieReview eachReview : commonReviews) {
			MovieReviewTranslation review = this.service.table(MovieReviewTranslation.class)
					.where("MOVIE_REVIEW_ID", eachReview.getId())
					.and("LANG_ISO", lang)
					.commitSelectSingle();
			if(review != null) {
				Customer customer = this.service.table(Customer.class)
						.columns("ID", "NAME", "IMAGE_URI")
						.where("ID", eachReview.getCustomerId())
						.commitSelectSingle();
				review.setCommon(eachReview);
				review.setCustomer(customer);
				reviews.add(review);
			}
		}
		
		// 장르
		List<MovieGenreTranslation> genres = new ArrayList();
		List<MovieGenre> commonGenres = this.service.table(MovieGenre.class)
				.where("MOVIE_ID", mid)
				.commitSelect();
		for(MovieGenre commonGenre : commonGenres) { 
			MovieGenreTranslation genre = this.service.table(MovieGenreTranslation.class)
					.where("GENRE_ID", commonGenre.getGenreId())
					.and("LANG_ISO", lang)
					.commitSelectSingle();
			if(genre != null) genres.add(genre);
		}
		
		List<MovieImage> images = this.service.table(MovieImage.class)
				.where("MOVIE_ID", mid)
				.commitSelect();
		movie.setRatingCount(this.service.table(MovieReview.class)
				.where("MOVIE_ID", mid)
				.commitCount());
		
		movie.setReviews(reviews);
		movie.setImages(images);
		movie.setGenres(genres);
		return movie;
	}
	
	
	
	/**
	 * 영화를 데이터베이스에서 조회한다
	 * @param pagination
	 * @param lang
	 * @return
	 */
	private List<MovieTranslation> fetchMovies(Pagination pagination, Integer[] genre, Integer ageLimit, String lang) {
		if(pagination.getPage() == 0) 
			pagination.setPage(1);
		
		
		
		lang = "en".equals(lang) ? "en" : "ko";
		SQLBuilder builder = this.service.table(MovieTranslation.class)
								.where("LANG_ISO", lang)
								.between(pagination.getBeginIndex(), pagination.getEndIndex());
		
		String opt = pagination.getOpt();
		
		
		// 검색조건이 있음
		if((opt != null && !opt.equals("") )|| (genre != null && genre.length > 0 ) || (ageLimit != null && ageLimit > 0 )) {
			String keyword = pagination.getKeyword();
			if(opt != null && keyword != null && !keyword.equals(""))
				builder = builder.andLike(opt, keyword+"%");
			
			boolean isGenreExist = genre != null && genre.length > 0;
			boolean isAgeLimitExist = ageLimit != null && ageLimit > 0;
			List<Integer> movieIds = new ArrayList();
			
			if(isGenreExist && isAgeLimitExist) {		// 장르제한과 나이제한이 걸린경우
				
				
				List<MovieGenre> mgList = this.service.table(MovieGenre.class)
						.whereIn("GENRE_ID", genre)
						.commitSelect();
				
				
				List<Movie> mList = this.service.table(Movie.class)
						.where("AGE_LIMIT", ageLimit, "<=")
						.and("AGE_LIMIT", 0, ">")
						.commitSelect();
				
				List<Integer> mgMovieIds = new ArrayList();
				for(MovieGenre mg : mgList) {
					mgMovieIds.add(mg.getMovieId());
				}
				for(Movie m : mList) {
					for(Integer mgMovieId : mgMovieIds) {
						if(mgMovieId == m.getId()) {
							movieIds.add(mgMovieId);
							break;
						}
					}
				}

				
			} else if (isGenreExist) { 		// 장르제한만 있을 경우
				
				List<MovieGenre> mgList = this.service.table(MovieGenre.class)
						.whereIn("GENRE_ID", genre)
						.commitSelect();
				for(MovieGenre mg : mgList) {
					if(mg.getMovieId() != 0)
						movieIds.add(mg.getMovieId());
						
				}
			} else if (isAgeLimitExist) { 	// 나이제한만 있을 경우
				List<Movie> mList = this.service.table(Movie.class)
						.where("AGE_LIMIT", ageLimit, "<=")
						.and("AGE_LIMIT", 0, ">")
						.commitSelect();
				for(Movie m : mList) {
					if(m.getId() !=0)
						movieIds.add(m.getId());
				}
			}
			
			if((isGenreExist || isAgeLimitExist)) {
				int mSize = movieIds.size();
				
				if(mSize > 0) {
					Integer[] ids = new Integer[mSize];
					
					for(int i=0; i < mSize; i++) {
						ids[i] = movieIds.get(i);
					}
					builder = builder.andIn("movie_id", ids);
				} else
					return new ArrayList<MovieTranslation>();
			}
		}
		
		
		
		
		
		List<MovieTranslation> movies = builder.commitSelect();
		
		for(MovieTranslation movie : movies ) {
			Movie common = this.service.directSelect(Movie.class, movie.getMovieId());
			if(common != null) movie.setCommon(common);
			movie.setImages(this.service.table(MovieImage.class)
					.where("MOVIE_ID", movie.getMovieId())
					.commitSelect());
			
			movie.setRatingPoint(this.service.table(MovieReview.class)
					.where("MOVIE_ID", movie.getMovieId())
					.commitMethod("RATING_POINT", "avg"));
			
			List<MovieGenre> movieGenres = this.service.table(MovieGenre.class)
					.where("MOVIE_ID", movie.getMovieId())
					.commitSelect();
			
			int genreSize = movieGenres.size();
			if(genreSize > 0) {
				Integer[] genreIds = new Integer[genreSize]; 
				for(int i=0; i < genreSize; i++) {
					genreIds[i] = movieGenres.get(i).getGenreId();
				}
				movie.setGenres(this.service.table(MovieGenreTranslation.class)
						.whereIn("GENRE_ID", genreIds)
						.and("LANG_ISO", "en".equals(lang) ? "en" : "ko")
						.commitSelect());
			}
		}
		
		
		return movies;
	}
	
	/**
	 * 장르 리스트 읽어오기
	 * @param lang
	 * @return
	 */
	private List<MovieGenreTranslation> fetchGenre(String lang) {
		List<Genre> genres =  this.service.table(Genre.class)
				.commitSelect();
		
		if(cachedGenreMap.containsKey(lang))
			return cachedGenreMap.get(lang);
		else {
			List<MovieGenreTranslation> genreList = new ArrayList();
			for(Genre genre : genres) {
				
				MovieGenreTranslation mgt = this.service.table(MovieGenreTranslation.class)
						.where("GENRE_ID", genre.getId())
						.and("LANG_ISO", "en".equals(lang) ? "en" : "ko")
						.commitSelectSingle();
				if(mgt != null)
					genreList.add(mgt);
			}
			cachedGenreMap.put(("en".equals(lang) ? "en" : "ko"), genreList);
			return genreList;
		}
	}
	
	/**
	 * 리뷰 요청
	 * @param pagination
	 * @param lang
	 * @return
	 */
	private List<MovieReviewTranslation> fetchReviews(Pagination pagination, String lang, int customerId) {
		if(lang == null || lang.isEmpty() ) lang = "ko";
		if(pagination.getPage() == 0) pagination.setPage(1);
		
		SQLBuilder<MovieReview> builder = this.service.table(MovieReview.class).where()
							.between(pagination.getBeginIndex(), pagination.getEndIndex());
		
		if(customerId != 0) builder = builder.and("CUSTOMER_ID", customerId);
		
		List<MovieReview> commons = builder.orderby(true, "CREATED_AT")
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
				
				review.setCustomer(this.service.table(Customer.class)
						.columns("ID", "NAME", "IMAGE_URI")
						.where("ID", common.getCustomerId())
						.commitSelectSingle());
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
