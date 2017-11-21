package com.client.web;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.client.service.SQLService;
import com.client.util.DateUtil;
import com.client.util.RestUtils;
import com.client.vo.Coupon;
import com.client.vo.Customer;
import com.client.vo.CustomerRank;
import com.client.vo.CustomerType;
import com.client.vo.Discount;
import com.client.vo.Genre;
import com.client.vo.Gift;
import com.client.vo.Movie;
import com.client.vo.MovieGenre;
import com.client.vo.MovieGenreTranslation;
import com.client.vo.MovieImage;
import com.client.vo.MovieReview;
import com.client.vo.MovieReviewTranslation;
import com.client.vo.MovieTimetable;
import com.client.vo.MovieTranslation;
import com.client.vo.Screen;
import com.client.vo.ScreenMovie;
import com.client.vo.Seat;
import com.client.vo.SeatDisabled;


@Controller
public class HomeController {
	@Autowired
	private SQLService service;
	
	@GetMapping(value= {"/", "/index", "/home"})
	public String index(Model model) {
		return "index";
	}
	
	@GetMapping("sql")
	public String insert(Model model) {
		
		Random r = new Random();
		int movieSize  = 30;
		Date until  = DateUtil.yyyymmdd("2020.01.01");
		Date dStarted  = DateUtil.yyyymmdd("2015.01.01");
		
		Discount discountVO = new Discount();
		discountVO.setEndedDate(until);
		discountVO.setStartedDate(dStarted);
		discountVO.setDiscountType("P");
		
		discountVO.setName("모든 고객 대상 할인행사");
		discountVO.setDetail("여러분들 힘내세요 ! 우리 영화관이 특별 행사 10% 할인을 진행합니다 ");
		discountVO.setDiscountPercent(10);
		this.service.directInsert(discountVO);
		discountVO.setName("모든 장애인 고객 대상 할인행사");
		discountVO.setDetail("현대를 살아가는 장애우분들 힘내세요 우리가 특별히 할인행사를 지원합니다");
		discountVO.setDiscountPercent(20);
		this.service.directInsert(discountVO);
		
		Coupon coupon = new Coupon();
		Gift gift = new Gift();
		coupon.setDistributeUntil(until);
		coupon.setDistributeType("I");
		gift.setDistributeType("D");
		gift.setDistributeUntil(until);
		
		coupon.setName("신규고객님 환영 쿠폰");
		coupon.setReason("신규고객님들을 위해 준비한 우리들의 특별한 선물입니다");
		coupon.setCustomerRankId(4);
		this.service.directInsert(coupon);
		
		coupon.setName("설립 1주년 기념");
		coupon.setReason("우리 영화관이 개장을 한지 1년이 지났습니다. 응원해주신 모든 고객분들을 위하여 저희가 특별한 선물을 준비했습니다 즐겨주세요");
		coupon.setCustomerRankId(4);
		this.service.directInsert(coupon);
		
		coupon.setName("김기덕 감독님의 새로운 영화 출시 기념");
		coupon.setReason("김기덕 감독님이 새로운 영화를 출시했습니다. 이를 기념하기 위해 모든 VIP 고객님께 저희가 특별한 선물을 준비했습니다");
		coupon.setCustomerRankId(3);
		this.service.directInsert(coupon);
		
		coupon.setName("우리모두 VVVIP");
		coupon.setReason("저희 영화관을 여러번 내방해주신 모든 VVIP 고객님들을 위해 준비했습니다");
		coupon.setCustomerRankId(2);
		this.service.directInsert(coupon);
		
		coupon.setName("당신이 영화계의 챔피언");
		coupon.setReason("우리 영화관 죽돌이 분들을 위해 준비한 특별 쿠폰입니다");
		coupon.setCustomerRankId(1);
		this.service.directInsert(coupon);
		
		gift.setName("만년필");
		gift.setReason("모든 고객님들을 위한 우리들의 선물입니다");
		gift.setCustomerRankId(4);
		this.service.directInsert(gift);
		
		gift.setName("1주년 기념 초콜렛");
		gift.setReason("우리 영화관이 개장한지 1년이 지났습니다. 기념 초콜릿을 드시고 내방해주세요");
		gift.setCustomerRankId(4);
		this.service.directInsert(gift);
		// 고객
		Customer c = new Customer();
		Date birth = DateUtil.yyyymmdd("1990.04.02");
		String[] firstNames = new String[] {"진희", "진주", "효연", "여민", "래선", "희태", "주성", "연강", "소희", "하늘", "명보",
				"희창", "은영", "청웅", "찬주", "대문", "민영", "혜미", "희경", "찬호", "태겸", "라예", "미려", "성오", "수천", "경도", "종흠",
				"현우", "요훈", "찬우", "보미", "주상", "경우", "선문", "태현", "상민", "민아"};
		String[] lastNames = new String [] {"이", "함", "송", "반", "임", "명", "천", "예", "채", "임", "장", "민", "정",
				"마", "진", "노", "구", "선", "명", "최", "천", "윤", "소", "민"};
		String[] emails = new String[] {
			"sgart3938@hanmail.net", "hanal2000@naver.com", "yi0303@naver.com", "ysj0946@naver.com", "buen33@hanmail.net",
			"marionon593@gmail.com", "tomanderson@naver.com", "kjo505@naver.com", "jimmy123123@naver.com",
			"lmp595@gmail.com", "cw909@hotmail.com", "jwk999@naver.com", "magarin@naver.com",
			"tompson101@gmail.com", "kinder10123@naver.com", "jackson15@naver.com", "chinny505@naver.com",
			"seoulme505@naver.com", "navinavi505@naver.com", "hereiam505@naver.com", "chiabattalover123@naver.com"
		};
		
		int size = 100;
		String[] cGender = new String[] {"F", "M"};
		for(int i=0; i<size ; i++) {
			String name = lastNames[r.nextInt(lastNames.length)]+firstNames[r.nextInt(firstNames.length)];
			c.setName(name);
			String email = emails[r.nextInt(emails.length)];
			String username = email.replace(email.substring(email.indexOf("@")), "");
			c.setEmail(email);
			c.setGender(r.nextInt(2) + 1);
			c.setImageURI("customer_"+((i % 5) + 1 )+".jpg");
			c.setMiliege(r.nextInt(100000));
			c.setPassword(DigestUtils.md5DigestAsHex(username.getBytes()));
			c.setBirth(new Date(birth.getTime() + r.nextInt(100000)));
			String firstPhone = String.format("%4s", String.valueOf(r.nextInt(9999))).replace(" ", "0");
			String lastPhone = String.format("%4s", String.valueOf(r.nextInt(9999))).replace(" ", "0");
			c.setPhone("010-"+firstPhone +"-"+ lastPhone);
			Date ccreated = DateUtil.yyyymmdd("" + 201 + r.nextInt(8)+ "."+String.format("2%s", r.nextInt(12)).replace(" ", "0") + "."+String.format("2%s", r.nextInt(30)).replace(" ", "0"));
			c.setUsername(username);
			c.setCreatedAt(ccreated);
			c.setUpdatedAt(ccreated);
			c.setCustomerRankId(r.nextInt(4) + 1);
			c.setCustomerTypeId(r.nextInt(4) + 1);
			this.service.directInsert(c);
		}
		
		
		//영화이미지
		String base = "movie_image";
		MovieImage mImage = new MovieImage();
		for(int i=1 ; i <31 ; i++) {
			mImage.setMovieId(i);
			for(int j=1; j< 3; j++) {
				mImage.setUri(base + (2*(i-1)+j)  + ".jpg");
				this.service.directInsert(mImage);
			}
		}
		
		// 고객 별타입과 가격정산
		String[] customerTypeNames = new String[] {
			"성인", "청소년", "어린이", "장애인"
		};
		// 고객 별타입과 가격정산
		String[] customerTypeDetails = new String[] {
			"성인요금", "청소년요금", "어린이 요금", "장애인 할인 요금"
		};
		int[] customerTypePrice = new int[] {
			10000, 8000, 6000, 4000
		};
		
		String[] customerRankType = new String[] {
			"VVVIP",
			"VVIP",
			"VIP",
			"WELCOME"
		};
		int[] customerRankPriority = new int[] {
				1, 2, 3, 4
		};
		
		CustomerType customerType= new CustomerType();
		CustomerRank customerRank = new CustomerRank();
		
		for(int i=0; i < 4 ; i++) {
			customerType.setName(customerTypeNames[i]);
			customerType.setPrice(customerTypePrice[i]);
			customerType.setDetila(customerTypeDetails[i]);
			customerRank.setName(customerRankType[i]);
			customerRank.setPriority(customerRankPriority[i]);
			
			this.service.directInsert(customerType);
			this.service.directInsert(customerRank);
		}
		
		// 시트, 영화 스크린, 영화 타임테이블
		String[] seatdetail = new String[] {"어린이용 의자", "보통의자", "보통의자", "보통의자", "보통의자", "보통의자", "보통의자"};
		String[] seatStatus = new String[] {"Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y",
				"Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y","Y",
				"N","N","A","A","A","A","A","A","A","C","C","C","C","C","C","C","C"};
		
		String[] sSeatName = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"};
		int count = 1;
		Screen screen = new Screen();
		Seat seat = new Seat();
		SeatDisabled disable = new SeatDisabled();
		ScreenMovie screenMovie = new ScreenMovie();
		
//		this.addMovieTimetable();
		for(int i =0 ; i < 11 ; i++) {
			int seatCount = r.nextInt(30) + 60;
			screen.setName((i+1) + "관");
			screen.setSeatCount(seatCount);
			int screenId = this.service.directInsert(screen);
			seat.setScreenId(screenId);
			screenMovie.setScreenId(screenId);
			for(int j=0; j < seatCount;j++) {
				seat.setDetail(seatdetail[r.nextInt(seatdetail.length)]);
				seat.setStatus(seatStatus[r.nextInt(seatStatus.length)]);
				if(count == 11 ) count = 1;
				seat.setName(sSeatName[j  / 10] + (count++));
				int seatId = this.service.directInsert(seat);
				disable.setSeatId(seatId);
				if(seat.getStatus().equals("A") || seat.getStatus().equals("C")) {
					disable.setTimeLimit(r.nextInt(4) + 3);
					this.service.directInsert(disable);
				}
			}
			
			for(int k=(i * 3) + 1; k < 3*i + 4 ; k ++) {
				screenMovie.setMovieId(k);
				this.service.directInsert(screenMovie);
			}
		}
		
		// 리뷰
		MovieReview mReview = new MovieReview();
		MovieReviewTranslation mReviewT = new MovieReviewTranslation();
		String[] reviews = new String[] {
			"제목 홈 어게인을 러브 어게인으로 바꿔서 로맨틱 코미디로 둔갑. 진짜 왜그러냐? 제목때문에 보고 나서 영화 평가 절하 됨. 홈어게인으로 보면 괜찮은 영화이나 러브어게인은 아니다. 진짜 국내 배급사인지 뭔지 홍보 드럽게 한다",
			"기대보다 더 괜찮았슴. 내인생 남은 날의 첫날은 오늘이란 말.. 너무 와닿음",
			"우리는 잊고 있었다. 토르가 ‘망치’의 신이 아니고 ‘천둥’의 신이라는걸",
			"토르 머리 짧아지니까 진짜 잘생겼다는..깨알 스트레인지 멋있었고 전체적으로 재밌었어요 마블영화는 개봉첫날 바로 봐줘야 제맛이죠",
			"배우분들 연기 좋았어요. 연출부분에서 별로인점도 있었지만 뻔하지 않은 한국영화 본거 같아서 개인적으로는 괜찮았습니다",
			"갑질하는 사람은 따로 있는데 아무도 안나오고 을끼리 지지고볶고 너무 현실적이라 더 웃펐습니다ㅜㅜ 배우들 연기 좋고 연출이 너무 세련돼서 시간가는 줄 모르고 봤어요 오랜만에 잘만든 블랙코미디 영화가 보고싶다면 추천! 꼭 보세요",
			"화려하던 압구정 로데오와 현재는 사양사업인 DVD방, 그리고 학자금 대출로 빚이있는 대학생. 너무 현실적인 인물들의 고군분투가 짠하고 또 웃지 못하는 상황에 웃음을 유발하는, 두 등장인물간의 눈치싸움과 그 긴장감이 넘치는 영화",
			"블랙코미디라는 장르를 처음 접해보았는데 정말 재미있더라구요! 두 주연 배우들의 연기도 정말 훌륭했습니다!",
			"영화는 뻔했고 설정은 억지였다 하지만 그걸 살려낸 배우들이 대단한영화. 그중심엔 류승룡이있었다",
			"예승이가 감옥으로 들어오는 설정부터가 조금은 억지가아닌가 싶었지만, 영화 특유의 따뜻한 느낌이 너무좋았습니다. 류승룡씨의 명연기도 잘 봤습니다. 다만 아쉬운점은 한국영화 특유의 눈물 쥐어짜기에서 상업영화의 한계를 보았습니다",
			"이번에 7번방의 류승룡의 연기를 보고 다시 한번 느꼈다. 이래서 숀펜이구나 하는걸",
			
		};
		int rsize = reviews.length;
		String[] rtranslated = new String[rsize];
		
		for(int i=0; i < rsize ; i++)
			rtranslated[i] = RestUtils.toEnglish(reviews[i]);
		
		
		for(int i=0; i < 90; i++) {
			mReview.setCreatedAt(DateUtil.yyyymmdd("" + 201 + r.nextInt(8)+ "."+String.format("2%s", r.nextInt(12)).replace(" ", "0") + "."+String.format("2%s", r.nextInt(30)).replace(" ", "0")));
			mReview.setCustomerId(r.nextInt(100) + 1);
			mReview.setMovieId(r.nextInt(30));
			mReview.setRatingPoint(r.nextInt(5));
			this.service.directInsert(mReview);
			int reviewId = r.nextInt(reviews.length);
			String review = reviews[reviewId];
			String treview = rtranslated[reviewId];
			mReviewT.setMovieReviewId(i+1);
			for(int j=0; j < 2 ; j++) {
				mReviewT.setReview(j == 1 ? treview : review);
				mReviewT.setLangDefault(j);
				mReviewT.setLangIso(j == 0 ? "ko" : "en");
				this.service.directInsert(mReviewT);
			}
		}
		
		// 장르
		Genre g = new Genre();
		int genreSize = 20;
		MovieGenre mgenre = new MovieGenre();
		for(int i=0; i < genreSize; i ++) {
			this.service.directInsert(g);
		}
		for(int i=0; i < 50; i++) {
			mgenre.setMovieId(i%movieSize);
			mgenre.setGenreId(i%genreSize);
			this.service.directInsert(mgenre);
		}
		MovieGenreTranslation mgt = new MovieGenreTranslation();
		String[] genres = new String[] {
				"로맨스", "코메디", "호러", "느와르", "범죄", "좀비", "재난", "로드무비", "역사", "다큐멘터리", "청춘", "판타지", 
				"뮤지컬", "반전", "종교", "전쟁", "인권", "실험", "서부극", "SF"
		};
		String[] translatedGenre = new String[] {
				"ROMANCE", "COMEDY", "HORROR", "REVENGE", "CRIME", "ZOMBIE", "DISASTER", "ROAD MOVIE", "HISTORY", "DOCUMENTARY", "YOUNG", "FANTASY", 
				"MUSICAL", "REHEARSAL", "RELIGION", "WAR", "HUMANITY", "EXPERIMENT", "WESTERN", "SF"
		};
		for(int i=0; i < 20; i ++) {
			mgt.setGenreId(i + 1);
			for(int j=0; j < 2; j++) {
				if(j == 0 ) {
					mgt.setLangDefault(1);
					mgt.setLangIso("ko");
					mgt.setType(genres[i]);
				} else {
					mgt.setLangDefault(0);
					mgt.setLangIso("en");
					mgt.setType(translatedGenre[i]);
				}
				this.service.directInsert(mgt);
			}
			
		}


		// MOVIES TABLE
		
		Movie movie = new Movie();
		int[] ages = new int[] {0, 12, 15, 18};
		Date openDate = DateUtil.yyyymmdd("2017.11.10");
		Date closeDate = DateUtil.yyyymmdd("2020.02.01");
		Date publishDate = DateUtil.yyyymmdd("2017.02.01");
		for(int i=0; i < movieSize; i++) {
			movie.setAgeLimit(ages[r.nextInt(ages.length)]);
			movie.setOpenDate(openDate);
			movie.setCloseDate(new Date(closeDate.getTime() + r.nextInt(100000)));
			movie.setDubbed(r.nextInt(2));
			movie.setPublishDate(new Date(publishDate.getTime() - r.nextInt(100000)));
			this.service.directInsert(movie);
		}
		MovieTranslation mTrans = new MovieTranslation();
		String[] movieNames = new String[] {
			"비 사이드", "멈춘 심장", "심장마비", "잘가", "밀실살인사건", "케이지다이브", "오픈워터", "상어", "히틀러의 아내", "켈리포니아", "너의 이름을 불러줘",
			"카메라", "킹콩", "참새아가씨", "붉은머리", "랜드라인", "1974", "마지막 달라이라마", "라스트 페이스", "노르마딕", "비트 러버", "케네디를 사랑해",
			"러브송", "저질 라이더", "럭키", "맨프람어스", "크리스마스를 개발한 사나이", "프라임 마조", "넛츠", "넛츠2", "무시", "베니스", "베니스2", "베니스3",
			"너에게로 1마일", "우리중 하나", "뉴요커 소년", "뉴요커 소년2", "뉴욕 카사노바", "용기", "용기2", "용기3", "평범한 사나이", "오토만 제국", "팩맨",
			"파리의 기다림", "파리의 기다림2", "파리의 기다림3", "벨", "걸스 나이트", "토르", "라이드", "안전사가", "샘의 공간", "친절", "망할 닭", "달콤한 사랑",
			"달콤한 버지니아", "수영팀", "날려버려", "날려버려2", "서핑", "불납", "기댈수 없는 그대", "마우핀", "불관용", "뼈의 계곡", "볼트", "베가스 베이베", "지옥 끝까지"
		};
		String[] moviewNamesTrans = new String[movieNames.length];
		for(int i=0; i < movieNames.length ; i++)
			moviewNamesTrans[i] = RestUtils.toEnglish(movieNames[i]);
		String[] description = new String[] {
			"“오늘 밤, 다 쓸어버린다!”" + 
			"2004년 서울…" + 
			" 하얼빈에서 넘어와 단숨에 기존 조직들을 장악하고" + 
			" 가장 강력한 세력인 춘식이파 보스 ‘황사장(조재윤 분)’까지 위협하며 " + 
			" 도시 일대의 최강자로 급부상한 신흥범죄조직의 악랄한 보스 ‘장첸(윤계상 분)’." + 
			" 대한민국을 뒤흔든 ‘장첸(윤계상 분)’ 일당을 잡기 위해 " + 
			" 오직 주먹 한방으로 도시의 평화를 유지해 온 괴물형사 ‘마석도(마동석 분)’와 " + 
			" 인간미 넘치는 든든한 리더 ‘전일만(최귀화 분)’ 반장이 이끄는 강력반은" + 
			" 나쁜 놈들을 한방에 쓸어버릴 끝.짱.나.는. 작전을 세우는데…" + 
			" 통쾌하고! 화끈하고! 살벌하게!" + 
			" 나쁜 놈들 때려잡는 강력반 형사들의 ‘조폭소탕작전’이 시작된다!",
			" 맨발로 교정을 거니는 괴짜, 자유로운 영혼의 히피였던 젊은 시절의 잡스.",
			"대학을 자퇴하고 절친 스티브 워즈니악과 자신의 집 차고에서 ‘애플’을 설립해 세계 최초로 개인용 컴퓨터를 세상에 내놓는다.",
			" 그 후 남다른 안목과 시대를 앞선 사업가적 기질로 애플을 업계 최고의 회사로 만들며 세계적으로 주목 받는CEO로 승승장구한다", 
			"자신이 만든 회사에서 내쫓기게 되면서 인생에서 가장 큰 좌절감에 사로잡힌다.", 
			" 그리고 11년 뒤, 스티브 잡스 퇴임 후 하락세를 걷던 애플을 구원하기 위해 돌아온 잡스는 다시 한번 세상을 뒤흔들 혁신을 준비한다",
			"생일날 반복되는 죽음이라는 특별한 선물을 받은 여대생의 끝나지 않는 파티",
			"인류의 수호자인 슈퍼맨이 사라진 틈을 노리고 ‘마더박스’를 차지하기 위해 빌런 스테픈울프가 악마군단을 이끌고 지구에 온다." + 
			" 마더박스는 시간과 공간, 에너지, 중력을 통제하는 범우주적인 능력으로 행성의 파괴마저도 초래하는 물체로 " + 
			" 이 강력한 힘을 통제하기 위해 고대부터 총 3개로 분리되어 보관되고 있던 것. " + 
			" 인류에 대한 믿음을 되찾고 슈퍼맨의 희생 정신에 마음이 움직인 브루스 웨인은 " + 
			" 새로운 동료인 다이애나 프린스에게 도움을 청해 이 거대한 적에 맞서기로 한다. " + 
			" 배트맨과 원더 우먼은 새로이 등장한 위협에 맞서기 위해 아쿠아맨, 사이보그, 플래시를 찾아 신속히 팀을 꾸린다." + 
			" 이들 슈퍼히어로 완전체는 스테픈울프로부터 마더박스를 지키기 위해 지구의 운명을 건 전투를 벌인다",
			"서울의 망해가는 DVD방 사장 두식(신하균)" + 
			" 학자금 빚을 갚으려 DVD방에서 일하는 알바생 태정(도경수)" + 
			" 팔리지도 않던 가게에 기적처럼 매수자가 나타난 바로 그 때!" + 
			" 예상치 못한 사고가 일어나고, 두식은 시체를 7호실에 숨겨 봉쇄한다." + 
			" 한편, 빚을 해결해주는 조건으로 마약을 7호실에 잠시 감춰놨던 태정은 " + 
			" 늘 열려있던 그 방의 문을 두식이 갑자기 잠가버리자 당황하는데…" + 
			" 닫아야 사는 사장 vs 열어야 사는 알바생!" + 
			" 두 남자의 생존이 걸린 문제의 방 ‘7호실’",
			"모든 것이 꼬여만 가는 하루하루들.." + 
			"남편과 헤어진 앨리스는 두 딸과 함께 LA로 이사를 오게 되고 " + 
			" 그 곳에서 인테리어 디자이너로 재기하려 하지만 무시 당하기 일쑤다. " + 
			" 어느덧 다가온 40세 생일날, 모처럼 친구들과 파티를 가진 앨리스는 " + 
			" 우연히 세 남자 해리, 테디, 조지를 만나 즐거운 시간을 보내게 되고 " + 
			" 우여곡절 끝에 그들은 앨리스 집에 잠시 머물기로 한다. " + 
			" 평범한 일상에 끼어든 낯선 세 남자가 불편한 앨리스, " + 
			" 하지만 바쁜 자신을 대신해 아이들을 돌봐주는 것은 물론 때론 친구처럼 연인처럼 " + 
			" 빈틈 있던 그녀의 삶을 채워주는 그들의 존재에 자신도 모르게 의지하게 되는데… " + 
			" 나 다시 시작할 수 있을까? " + 
			" 사랑도 인생도 지금부터 !",
			
			"지적 장애로 7살의 지능밖에 갖지 못한 샘(숀 팬)은 버스정류장 옆 커피 전문점에서 일한다. 그날, 황망하게 가게를 나온 샘은 병원으로 향하고, 레베카와의 사이에서 태어난 자신의 딸과 첫 대면을 하게 된다. 그러나 병원을 나서자 레베카는 샘과 딸을 두고 사라져버린다. 혼자 남겨진 샘은 좋아하는 가수 비틀즈의 노래에서 따온 루시 다이아몬드를 딸의 이름으로 짓고 둘만의 생활을 시작한다. 그러나 외출공포증으로 집안에서 피아노만 연주하는 이웃집 애니(다이앤 위스트)와 샘과 같은 장애를 갖고 있으면서도 언제나 밝은 친구 이프티와 로버트 같은 주변의 따뜻하고 친절한 도움이 없었다면 루시(다코타 패닝)가 그렇게 건강하고 밝게 자라기 힘들었을 것. 수요일에는 레스토랑에, 목요일에는 비디오 나이트에, 금요일에는 노래방에 함께 다니는 것이 이들 부녀의 작은 행복. 남들이 보기에는 정상적이지 못하지만 그들은 가장 즐거운 시간을 함께 하며 행복한 가정을 이루고 있다." + 
			"  그런데 루시가 7살이 되면서 아빠의 지능을 추월해버리는 것을 두려워한 나머지 학교 수업을 일부러 게을리하게 되고, 이로 인해 사회복지기관에서 샘의 가정을 방문한다. 그리고 샘은 아빠로서 양육 능력이 없다는 선고를 받게 된다. 결국 루시는 시설로 옮겨지고, 샘은 주 2회의 면회만을 허락받게 된다. 세상에서 가장 사랑하는 딸과의 행복한 날들을 빼앗기고 실의에 빠진 샘. 그는 법정에서 싸워 루시를 되찾을 결심을 굳히고, 승승장구하는 엘리트 변호사 리타 해리슨(미셸 파이퍼)의 사무실을 찾아간다. 정력적이고 자아 도취적인 변호사 리타는 동료들에게 자신의 능력을 과시하기 위해 무료로 샘의 변호를 맡겠다고 공언하고 샘과의 도저히 어울리지 않을 것 같은 연대를 맺게 된다. 그러나 아무리 생각해도 샘에게는 불리한 재판으로 그가 양육권을 인정받을 가능성은 낮았다. 샘이 훌륭한 아빠라는 것을 인정해줄 친구들은 재판에서는 증언조차 불가능하다. 음악 대학을 수석졸업, 유일하게 법정에 설 수 있는 애니 역시 어렵게 외출 공포증을 극복하고 증언대에 서지만, 상대 변호사의 추긍받으면서 답변을 하지 못하게 된다. 과연 샘은 루시의 훌륭한 아빠라는 것을 증명할 수 있을까...?"
		};
		String[] movieDescTrans = new String[description.length];
		for(int i=0; i < description.length ; i++)
			movieDescTrans[i] = RestUtils.toEnglish(description[i]);
		
		String[] countries = new String[] {"한국", "한국", "한국", "한국", "한국", "한국", "미국", "미국", "미국", "일본", "미국", "중국", "인도" };
		String[] countriesTrans = new String[countries.length];
		for(int i=0; i < countries.length ; i++)
			countriesTrans[i] = RestUtils.toEnglish(countries[i]);
		
		for(int i=0; i < movieSize; i++) {
			int cidx = r.nextInt(countries.length);
			int didx = r.nextInt(description.length);
			
			String country = countries[cidx];
			String countryTrans = countriesTrans[cidx];
			
			String name = movieNames[i];
			String tname = moviewNamesTrans[i];
			
			String sDesc = description[didx];
			String tdesc = movieDescTrans[didx];
			
			for(int j=0; j < 2;j++) {
				mTrans.setName(j== 0 ? name : tname);
				mTrans.setDescription(j== 0 ? sDesc : tdesc);
				mTrans.setLangDefault(j == 0 ? 1 : 0);
				mTrans.setLangIso(j == 0 ? "ko" : "en");
				mTrans.setPublishCountry(j== 0 ? country : countryTrans);
				mTrans.setMovieId(i+1);
				this.service.directInsert(mTrans);
			}
		}
		
		
		return "sql";
	}

	private void addMovieTimetable() {
		MovieTimetable movieTime =  new MovieTimetable();
		Random r = new Random();
		
		long twoHour = 1000 * 60 * 60 * 2;
		Date today = new Date();
		
		
		for(int i=1; i <= 30; i++ ) {
			movieTime.setScreenMovieId(i);
			
			today = new Date(today.getTime());
			Date movieStartedAt = today;
			for(int k=0; k < 30; k++) {
				movieTime.setOffline(r.nextInt(5) % 10 == 0 ? 0 : 1);
//				movieTime.setOnline(r.nextInt(5) % 10 == 0 ? 0 : 1);
				movieTime.setOnline(1);
				movieTime.setStartedAt(movieStartedAt);
				movieStartedAt = new Date(movieStartedAt.getTime() + twoHour);
				movieTime.setEndedAt(movieStartedAt);
				this.service.directInsert(movieTime);
			}
		}
	}
	
	@GetMapping("/sqladd")
	public String addTimetable() {
		return "sqladd";
	}
	
	@GetMapping("/addtimetable")
	@ResponseBody
	public String addTime() {
		this.addMovieTimetable();
		return "good";
	}
	
}
