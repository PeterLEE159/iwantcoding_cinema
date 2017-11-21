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
		<div class="col-md-9 white-box" style="padding: 24px 52px;">
			<div class="row text-center"><h2 class="page-header"><spring:message code="myupdate" /></h2></div>
			<form id="form-update" class="form-horizontal" action="/customer/update" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label class="control-label"><spring:message code="id" /></label>
					<p>${LOGIN_CUSTOMER.username }</p>
				</div>
				
				<div class="form-group">
					<label class="control-label"><spring:message code="password" /></label>
					<input id="input-password" type="password" class="form-control" name="password">
					<span id="span-password" class="help-block"></span>
				</div>
				
				<div class="form-group">
					<label class="control-label"><spring:message code="password.confirm" /></label>
					<input id="input-confirm-password" type="password" class="form-control">
					<span id="span-confirm-password" class="help-block"></span>
				</div>
				
				<div class="form-group">
					<label class="control-label"><spring:message code="name" /></label>
					<input type="text" class="form-control" name="name" value="${LOGIN_CUSTOMER.name }">
					<span id="span-name" class="help-block"></span>
				</div>
				
				<div class="form-group">
					<label class="control-label"><spring:message code="signup.phone" /></label>
					<div class="row">
						<div class="col-md-4">
							<select name="firstPhone" class="form-control" value=${firstPhone }>
								<option value="010">010</option>
								<option value="02">02</option>
								<option value="031">031</option>
								<option value="033">033</option>
								<option value="051">051</option>
								<option value="053">053</option>
								<option value="054">054</option>
								<option value="016">016</option>
								<option value="019">019</option>
							</select>
						</div>
						<div class="col-md-4"><input type="number" class="form-control" name="secondPhone" value="${secondPhone }"></div>
						<div class="col-md-4"><input type="number" class="form-control" name="thirdPhone" value="${thirdPhone }"></div>
					</div>
					<span id="span-phone" class="help-block"></span>
				</div>
				
				<div class="form-group">
					<label class="control-label"><spring:message code="signup.email" /></label>
					<input type="email" class="form-control" name="email" value="${LOGIN_CUSTOMER.email }">
					<span id="span-email" class="help-block"></span>
				</div>
				
				<div class="form-group">
					<label class="control-label"><spring:message code="gender" /></label><br />
					<input type="radio" class="btn-input" name="gender" value="1" content="<spring:message code="male" />" ${LOGIN_CUSTOMER.gender == 1 ? 'checked' : '' }>
					<input type="radio" class="btn-input" name="gender" value="2" content="<spring:message code="female" />" ${LOGIN_CUSTOMER.gender == 2 ? 'checked' : '' }>
				</div>
				
				<div class="form-group">
					<label class="control-label"><spring:message code="signup.image" /></label><br />
					<input type="file" class="btn-input-file" name="file" content="<spring:message code="imageUpload" />">
				</div>
				<div class="row">
					<img id="img-placeholder">
				</div>
				<div class="row" style="margin-top: 52px;">
					<button class="btn btn-primary btn-block" type="submit"><i class="fa fa-user-plus fa-fw"></i><spring:message code="myupdate" /></button>
				</div>
			</form>
		</div>
	</div>
</body>
	
<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
<script>
	$(function() {
		// 인풋
		var $inputPassword = $(':input[name=password]'),
		$inputConfirmPassword = $(':input#input-confirm-password'),
		$inputName = $(':input[name=name]'),
		$selectFirstPhone = $('select[name=firstPhone]'),
		$inputSecondPhone = $(':input[name=secondPhone]'),
		$inputThirdPhone = $(':input[name=thirdPhone]'),
		$inputEmail = $(':input[name=email]');
		
		// 경고창
		$spanPassword = $('#span-password'),
		$spanConfirmPassword = $('#span-confirm-password'),
		$spanName = $('#span-name'),
		$spanPhone = $('#span-phone'),
		$spanEmail = $('#span-email');
		
		// 패턴
		var passwordPattern = /^[\d\D]{8,30}$/,
		namePattern = /^\D{2,50}$/,
		phonePattern = /^\d{3,4}$/,
		emailPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		
		$('#form-update').submit(function() {
			var pwd = $inputPassword.val();
			if(pwd && pwd != '') {
				$inputPassword.removeAttr('disabled');
				
				if(!passwordPattern.test(pwd)) {
					message($spanPassword, '<spring:message code="signup.passwordcheck" />', false);
					return false;
				} else $spanPassword.text('');
				
				if(pwd !== $inputConfirmPassword.val()) {
					message($spanConfirmPassword, '<spring:message code="signup.passwordnotconfirm" />', false);
					return false;
				} else $spanConfirmPassword.text('');
			} else $inputPassword.attr('disabled', 'disabled');
			
			if($spanEmail.val() && !emailPattern.test($spanEmail.val())) {
				message($spanPassword, '<spring:message code="signup.emailcheck" />', false);
				return false;
			} else $spanEmail.text('');
			
			var phone = $selectFirstPhone.val()+'-'+$inputSecondPhone.val()+'-'+$inputThirdPhone.val();
			
			if(phone.length > 9) {
				var inputPhone = "<input type='hidden' name='phone' value='"+phone+"'/>";
				$(this).append(inputPhone);
			}
			
			return true;
		});
		
		
		$(":input[type=file]").change(function() {
			  showImage(this, $('#img-placeholder'));
		});
		
		function message($span, msg, success) {
			$span.text(msg);
			if(success) $span.css('color', '#66AA66');
			else $span.css('color', '#AA6666');
		}
	});
</script>
</html>