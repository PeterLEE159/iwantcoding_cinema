<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html>
<head>
	<title><spring:message code="coupon" /></title>
	<%@ include file="/WEB-INF/views/common/head.jsp" %>
	<style>
		.float-left {
			float: left;
		}
		.white-box {
			padding-top: 24px;
		}
		
		.ul-horizontal li {
			padding: 52px !important;
		}
		.small-top-margin {
			top-margin: 6px;
		}
	</style>
</head>
<body>
	<c:set var="nav_active" value="mypage" />
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<%@ include file="/WEB-INF/views/common/common_views.jsp" %>
	
	
	<div class="container" style="margin-top: 52px;">
		<div class="col-md-3">
			<c:set var="customer_side" value="post" />
			<%@ include file="/WEB-INF/views/customers/customer_side.jsp" %>
		</div>
		<div class="col-md-9 white-box" style="padding: 24px 0px;">
			<c:set var="furiLocation" value="customer"/>
			<%@ include file="/WEB-INF/views/movies/review_segment.jsp" %>
			
		</div>
		
	</div>

	
</body>
	
<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
</html>