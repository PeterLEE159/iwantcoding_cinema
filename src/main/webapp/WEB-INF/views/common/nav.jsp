<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<nav class="navbar navbar-default bg-primary">

	<div class="fluid-container" style="padding: 0px 240px;">

<!-- 		<div class="navbar-header"> -->
<!-- 			<button type="button" class="navbar-toggle collapsed" -->
<!-- 				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" -->
<!-- 				aria-expanded="false"> -->
<!-- 				<span class="sr-only">Toggle navigation</span> <span -->
<!-- 					class="icon-bar"></span> <span class="icon-bar"></span> <span -->
<!-- 					class="icon-bar"></span> -->
<!-- 			</button> -->
<!-- 			<a class="navbar-brand btn-forward" href="/index"><spring:message code="cinema" /></a> -->
<!-- 		</div> -->


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

