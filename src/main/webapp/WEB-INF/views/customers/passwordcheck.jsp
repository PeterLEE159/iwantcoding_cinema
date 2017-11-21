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
	
	
	<div class="container" style="margin-top: 52px;">
		<div class="col-md-3">
			<c:set var="customer_side" value="update" />
			<%@ include file="/WEB-INF/views/customers/customer_side.jsp" %>
		</div>
		<div class="col-md-7 white-box" style="padding: 24px 24px;">
			
			<form action="/customer/myupdate" class="form-horizontal" method="post">
				<h2 class="page-header"><spring:message code="infocheck" /></h2>
				<div class="form-group">
					<label class="col-md-2 control-label"><spring:message code="id" /></label>
					<div class="col-md-9">
						<input type="text" class="form-control" name="id" required="required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label"><spring:message code="password" /></label>
					<div class="col-md-9">
						<input type="password" class="form-control" name="password" required="required"/>
					</div>
				</div>
				
				<div class="form-group top-margin">
					<div class="col-md-offset-2 col-md-9">
						<button class="btn btn-primary btn-block"><i class="fa fa-check fa-fw"></i><spring:message code="submit" /></button>		
					</div>
				</div>
			</form>
		</div>
	</div>

	
</body>
	
<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
<script>
	var deny = '${param.access}';
	if(deny != null && deny == 'deny') {
		fAlert('<spring:message code="alert" />', '<spring:message code="login.fail" />');
	}
</script>
</html>