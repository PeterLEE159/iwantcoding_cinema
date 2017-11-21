<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html>
<head>
	<title><spring:message code="coupon" /></title>
	<%@ include file="/WEB-INF/views/common/head.jsp" %>
</head>
<body>
	<c:set var="nav_active" value="mypage" />
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<%@ include file="/WEB-INF/views/common/common_views.jsp" %>
	
	<a class="btn btn-default hover" id="btn-add">추가</a>
</body>
	
<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
<script>
	$('#btn-add').click(function() {
		fConfirm('alert', 'do you want to add more timetable ? ', function() {
			$.ajax({
				url: '/addtimetable',
				type: 'get',
				success: function(result) {
					fAlert('success');
				},
			});	
		});
	});
</script>
</html>