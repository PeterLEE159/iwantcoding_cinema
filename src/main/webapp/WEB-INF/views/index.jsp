<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html>
<head>
	<title>Home</title>
	<%@ include file="/WEB-INF/views/common/head.jsp" %>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullPage.js/2.9.5/jquery.fullpage.min.css" />
	<style>
		.bg-image {
			width: 100%;
 			height: 100%;
 			position: absolute;
 			z-index: -1;
 			left: 0;
 			top: 0;
 		}
 		
 		.navbar-default {
 			background: #444;
 			border: transparent;
 		}
 		.navbar-default a{
 			color: white !important;
 		}
 		.btn-flag {
 			background: #444 !important;
 		}
 		.btn-flag span {
 			color: #fff !important;
 		}
 		span.span-lang {
 			padding-left: 4px; 
 			color: #fff !important;
 		}
 		.font-white {
 			color: #fff !important
 		}
 		
 		#map {
        	height: 400px;
        	background-color: grey;
      	}
 		
</style>
</head>
<body>
	
	<%@ include file="/WEB-INF/views/common/common_views.jsp" %>
	
	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
	    
<!-- 			<div class="navbar-header"> -->
<!-- 				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false"> -->
<!-- 			        <span class="sr-only">Toggle navigation</span> -->
<!-- 			        <span class="icon-bar"></span> -->
<!-- 			        <span class="icon-bar"></span> -->
<!-- 			        <span class="icon-bar"></span> -->
<!-- 				</button> -->
<%-- 		    	<a class="navbar-brand" href="#"><spring:message code="cinema" /></a> --%>
<!-- 		    </div> -->
	
	    	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="${nav_active eq 'booking' ? 'active' :'' }"><a class="btn-forward" href="/movie/booking?lang=${param.lang }"><i class="fa fa-ticket fa-fw"></i> <spring:message code="movie.book" /></a></li>
					<li class="${nav_active eq 'movie' ? 'active' :'' }"><a class="btn-forward" href="/movie/index?lang=${param.lang }"><i class="fa fa-film fa-fw"></i> <spring:message code="movie" /></a></li>
					<li class="${nav_active eq 'post' ? 'active' : ''}"><a class="btn-forward" href="/movie/reviews?page=1&lang=${param.lang }"><i class="fa fa-paper-plane fa-fw"></i> <spring:message code="post" /></a></li>
					<c:if test="${not empty LOGIN_CUSTOMER }">
						<li class="nav-item ${nav_active eq 'coupon' ? 'active' : ''}"><a class="btn-forward" href="/movie/coupon?lang=${param.lang }"><i class="fa fa-tree fa-fw"></i> <spring:message code="event" /></a></li>
						<li class="nav-item ${nav_active eq 'mypage' ? 'active' : ''}"><a class="btn-forward" href="/customer/index?lang=${param.lang }"><i class="fa fa-user fa-fw"></i> <spring:message code="mypage" /></a></li>
					</c:if>
					
					<li><a lang='ko' class="btn btn-default btn-flag btn-forward"><i class="country-flag flag-icon flag-icon-kr"></i><span style="padding-left: 4px">한국어</span></a></li>
					<li><a lang='en' class="btn btn-default btn-flag btn-forward"><i class="country-flag flag-icon flag-icon-us"></i><span style="padding-left: 4px">English</span></a></li>
				</ul>
				<form class="navbar-form navbar-left left-margin" action="/movie/index" method="get">
					<input type="hidden" name="opt" value="name" />
					<div class="form-group">
						<input type="text" name="keyword" class="form-control" placeholder='<spring:message code="movie.search" />'>
					</div>
					<button type="submit" class="btn btn-default"><i class="fa fa-search fa-fw"></i> <spring:message code="search" /></button>
				</form>
				
				<ul class="nav navbar-nav navbar-right">
					
					<c:if test="${empty LOGIN_CUSTOMER }">
						<li><a class="hover" data-toggle="modal" data-target="#div-login"><i class="fa fa-key fa-fw"></i> <spring:message code="login" /></a></li>
					</c:if>
					<c:if test="${not empty LOGIN_CUSTOMER }">
	<!-- 					<li><a class="page-forward hover btn-logout" id="a-logout"><i class="fa fa-sign-out fa-fw"></i> <spring:message code="logout" /></a></li> -->
						<li><a class="page-forward hover btn-logout"><i class="fa fa-sign-out fa-fw"></i> <spring:message code="logout" /></a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</nav>
	
	
	<section id="fullpage" style="margin-top: 50px;">
		<section id="first" class="section">
			<img class="bg-image" src="/resources/images/cinema_background.jpg" />
			<div class="container">
				<h1 class="white-font bold"><spring:message code="intro.title" /></h1>
				<h3 class="white-font bold"><spring:message code="intro.body" /></h3>
				<a href="/movie/booking?lang=${param.lang }" class="btn btn-primary btn-lg top-margin font-bigger"><i class="fa fa-ticket fa-fw"></i> <spring:message code="movie.booking" /></a>
				<a class="btn btn-default btn-lg top-margin left-margin font-bigger hover" data-toggle="modal" data-target="#div-login"><i class="fa fa-key fa-fw"></i> <spring:message code="login" /></a>
			</div>
			
		</section>
		<section id="second" class="section">
			<img class="bg-image blur" src="/resources/images/cinema_background2.jpg" />
			<div class="overlap">
				<div class="row">
					<div class="col-md-6" style="padding: 0px 40px 0px 150px;">
						<h2 class="font-white"><spring:message code="cinema.location" /></h2>
						<div id="map"></div>
					</div>
					
					<div class="col-md-6" style="padding: 0px 150px 0px 40px;">
						
						<h2 class="font-white"><spring:message code="cinema.enquirement" /></h2>
						<div class="form-group top-margin">
							<label class="font-white"><spring:message code="email" /></label>
							<input type="text" class="form-control" name="email"/>
						</div>
						<div class="form-group">
							<label class="font-white"><spring:message code="contents" /></label>
							<textarea rows="10" class="form-control" name="enContent" ></textarea>
						</div>
						<div class="form-group">
							<a id="btn-enquire" class="btn btn-primary top-margin"><i class="fa fa-fw fa-check"></i> <spring:message code="enquire" /></a>
						</div>
					</div>
				</div>
			</div>
		</section>
		
	</section>
</body>
<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fullPage.js/2.9.5/jquery.fullpage.min.js"></script>
<script src="/resources/js/main.js"></script>
<script>
	$(function() {
		$('#fullpage').fullpage();
		
		
		$('#btn-enquire').click(function() {
			fAlert('<spring:message code="alert" />', '<spring:message code="cinema.enquire" />');
			$('.form-control').val('');	
		});
		
		
	});
	
	function initMap() {
		var coordinate = {lat: 37.566144, lng: 126.978036};
        var map = new google.maps.Map(document.getElementById('map'), {
        	center: coordinate,
        	zoom: 15,
        	draggable: false,
        });
        

        var marker = new google.maps.Marker({
            position: coordinate,
            map: map,
            title: '<spring:message code="cinema" />'
        });
    }
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC-clVdv-1G_Fdbf95ruN05dJ7QP_qwXok&callback=initMap" async defer></script>
</html>
