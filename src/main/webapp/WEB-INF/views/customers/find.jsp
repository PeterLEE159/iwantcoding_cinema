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
	
	
	<div class="row top-margin">
		<div class="col-md-4 col-md-offset-4 white-box">
			<spring:message code="findID" var="fid" />
			<spring:message code="findPassword" var="fpwd" />
			<h2 class="page-header">
				${param.target == 'id' ? fid : fpwd }
			</h2>
			<form id="form-check" action="/customer/find?target=${param.target }&lang=${param.lang}" method="post">
				<div class="form-group">
					<label><spring:message code="name" /></label>
					<input type="text" class="form-control" name="name" />
					<label><spring:message code="email" /></label>
					<input type="email" class="form-control" name="email" />
					<button class="btn btn-primary top-margin"><i class="fa fa-check fa-fw"></i><spring:message code="submit" /></button>
				</div>
			</form>
		</div>
	</div>
</body>
	
<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
<script>
	var result = '${param.result}';
	if(result == 'sent')
		fAlert('<spring:message code="alert" />', '<spring:message code="sendEmail" />');
	
	//인풋
	var $inputName = $('input[name=name]'),
	$inputEmail = $('input[name=email]');
	
	// 패턴
	var namePattern = /^\D{2,50}$/,
	emailPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

	
	
	
	$('#form-check').submit(function() {
		if(!namePattern.test($inputName.val())) {
			fAlert('<spring:message code="alert" />', '<spring:message code="signup.namecheck" />');
			return false;
		}
		if(!emailPattern.test($inputEmail.val())) {
			fAlert('<spring:message code="alert" />', '<spring:message code="signup.emailcheck" />');
			return false;
		}
		pageForwardAnim();
		return true;
	});
	
</script>
</html>