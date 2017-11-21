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
	<c:set var="nav_active" value="post" />
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<%@ include file="/WEB-INF/views/common/common_views.jsp" %>
	
	<div class="col-md-4 col-md-offset-4 white-box top-margin">
		<c:set var="furiLocation" value="movie"/>
		<%@ include file="/WEB-INF/views/movies/review_segment.jsp" %>
	</div>
</body>
	
<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
</html>