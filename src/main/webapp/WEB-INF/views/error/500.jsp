<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html>
<head>
	<title><spring:message code="coupon" /></title>
	<%@ include file="/WEB-INF/views/common/head.jsp" %>
	<%@ include file="/WEB-INF/views/common/error.jsp" %>
</head>
<body>
	<c:set var="nav_active" value="mypage" />
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<%@ include file="/WEB-INF/views/common/common_views.jsp" %>
	<div class="div-error">
		<img src="/resources/images/error_bg.jpg" />
		<div class="div-error-container">
			<h1>:( 500</h1>
			
			<p><spring:message code="error.505" /></p>
			<p><spring:message code="error.505.intro" /></p>
		</div>
	</div>
</body>
	
<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
</html>