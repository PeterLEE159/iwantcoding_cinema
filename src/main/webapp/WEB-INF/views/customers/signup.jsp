<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html>
<head>
	<title>Home</title>
	<%@ include file="/WEB-INF/views/common/head.jsp" %>
</head>
<body>
	<c:set var="nav-active" value="movie"></c:set>
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<div class="container">
		<div class="row">
			<div class="col-md-offset-2 col-md-8 white-box" style="padding: 24px 64px;">
				<div class="row text-center"><h2 class="page-header"><spring:message code="signup" /></h2></div>
				<form id="form-signup" class="form-horizontal" action="/customer/signup" method="post" enctype="multipart/form-data">
					<div class="form-group">
						<label class="control-label"><spring:message code="id" />   <small class="red-font">* <spring:message code="signup.compulsoryInfo" /></small></label><a id="a-id-check" class="btn btn-default self-right"><spring:message code="signup.duplicateCheck" /></a>
						<input type="text" class="form-control" name="username" style="margin-top: 12px;" required>
						<span id="span-id" class="help-block"></span>
					</div>
					
					<div class="form-group">
						<label class="control-label"><spring:message code="password" />  <small class="red-font">* <spring:message code="signup.duplicateCheck" /></small></label>
						<input id="input-password" type="password" class="form-control" name="password" required>
						<span id="span-password" class="help-block"></span>
					</div>
					
					<div class="form-group">
						<label class="control-label"><spring:message code="password.confirm" />  <small class="red-font">* <spring:message code="signup.compulsoryInfo" /></small></label>
						<input id="input-confirm-password" type="password" class="form-control" required>
						<span id="span-confirm-password" class="help-block"></span>
					</div>
					
					<div class="form-group">
						<label class="control-label"><spring:message code="name" />  <small class="red-font">* <spring:message code="signup.compulsoryInfo" /></small></label>
						<input type="text" class="form-control" name="name" required>
						<span id="span-name" class="help-block"></span>
					</div>
					
					<div class="form-group">
						<label class="control-label"><spring:message code="signup.phone" />  <small class="red-font">* <spring:message code="signup.compulsoryInfo" /></small></label>
						<div class="row">
							<div class="col-md-4">
								<select name="firstPhone" class="form-control">
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
							<div class="col-md-4"><input type="number" class="form-control" name="secondPhone"></div>
							<div class="col-md-4"><input type="number" class="form-control" name="thirdPhone"></div>
						</div>
						<span id="span-phone" class="help-block"></span>
					</div>
					
					<div class="form-group">
						<label class="control-label"><spring:message code="signup.email" /></label>
						<input type="email" class="form-control" name="email">
						<span id="span-email" class="help-block"></span>
					</div>
					
					<div class="form-group">
						<label class="control-label"><spring:message code="gender" /></label><br />
						<input type="radio" class="btn-input" name="gender" value="1" content="<spring:message code="male" />" checked>
						<input type="radio" class="btn-input" name="gender" value="2" content="<spring:message code="female" />">
					</div>
					
					<div class="form-group">
						<label class="control-label"><spring:message code="signup.customerType" />  <small class="red-font">* <spring:message code="signup.compulsoryInfo" /> (<spring:message code="signup.usedForPricePolicy" />) </small></label><br />
						<c:forEach items="${types }" var="type" varStatus="status">
							<input type="radio" class="btn-input" name="customerTypeId" value="${type.id}" content="${type.name }" 
								<c:if test="${status.index eq 0 }">
									checked
								</c:if>
							/>
						</c:forEach>
					</div>
					
					<div class="form-group">
						<label class="control-label"><spring:message code="signup.image" /></label><br />
						<input type="file" class="btn-input-file" name="file" content="<spring:message code="imageUpload" />">
					</div>
					<div class="row">
						<img id="img-placeholder">
					</div>
					<div class="row" style="margin-top: 52px;">
						<button class="btn btn-primary btn-block" type="submit"><i class="fa fa-user-plus fa-fw"></i><spring:message code="signup" /></button>
					</div>
				</form>
			</div>
		</div>
	</div>
<%@ include file="/WEB-INF/views/common/common_views.jsp" %>
</body>
<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
<script>
	$(function() {
		// 인풋
		var $inputUsername = $(':input[name=username]'),
		$inputPassword = $(':input[name=password]'),
		$inputConfirmPassword = $(':input#input-confirm-password'),
		$inputName = $(':input[name=name]'),
		$selectFirstPhone = $('select[name=firstPhone]'),
		$inputSecondPhone = $(':input[name=secondPhone]'),
		$inputThirdPhone = $(':input[name=thirdPhone]'),
		$inputEmail = $(':input[name=email]'),
		
		
		// 경고창
		$spanUsername = $('#span-id'),
		$spanPassword = $('#span-password'),
		$spanConfirmPassword = $('#span-confirm-password'),
		$spanName = $('#span-name'),
		$spanPhone = $('#span-phone'),
		$spanEmail = $('#span-email'),
		
		// 패턴
		idPattern = /^[a-zA-Z0-9]{4,12}$/,
		passwordPattern = /^[\d\D]{8,30}$/,
		namePattern = /^\D{2,50}$/,
		phonePattern = /^\d{3,4}$/,
		emailPattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
		isIdCheck = false;
		$(":input[type=file]").change(function() {
			  showImage(this, $('#img-placeholder'));
		});
		
		$('#form-signup').submit(function() {
			if(!idPattern.test($inputUsername.val())) {
				message($spanUsername, '<spring:message code="signup.idcheck" />', false);
				return false;
			} else $spanUsername.text('');
			
			if(!passwordPattern.test($inputPassword.val())) {
				message($spanPassword, '<spring:message code="signup.passwordcheck" />', false);
				return false;
			} else $spanPassword.text('');
			
			if($inputPassword.val() !== $inputConfirmPassword.val()) {
				message($spanConfirmPassword, '<spring:message code="signup.passwordnotconfirm" />', false);
				return false;
			} else $spanConfirmPassword.text('');
			
			if(!namePattern.test($inputName.val())) {
				message($spanName, '<spring:message code="signup.namecheck" />', false);
				return false;
			} else $spanName.text('');
			
			
			if(!$inputSecondPhone.val() || !phonePattern.test($inputSecondPhone.val())) {
				message($spanPhone, '<spring:message code="signup.secondphonecheck" />', false);
				return false;
			} else $spanPhone.text('');
			
			if(!$inputThirdPhone.val() || !phonePattern.test($inputSecondPhone.val())) {
				message($spanPhone, '<spring:message code="signup.thirdphonecheck" />', false);
				return false;
			} else $inputThirdPhone.text('');
			
			if($spanEmail.val() && !emailPattern.test($spanEmail.val())) {
				message($spanPassword, '<spring:message code="signup.emailcheck" />', false);
				return false;
			} else $spanEmail.text('');
			
			if(!isIdCheck) {
				message($spanUsername, '<spring:message code="signup.idduplicatecheck" />', false);
				return false;
			}
			var phone = $selectFirstPhone.val()+'-'+$inputSecondPhone.val()+'-'+$inputThirdPhone.val();
			var inputPhone = "<input type='hidden' name='phone' value='"+phone+"'/>";
			$(this).append(inputPhone);
			
			pageForwardAnim();
			
			return true;
		});
		
		
		$('#a-id-check').click(function() {
			var username = $inputUsername.val();
			if(!username){
				message($spanUsername, '<spring:message code="signup.typeId" />', false);
				return;
			}
			if(!idPattern.test($inputUsername.val())) {
				message($spanUsername, '<spring:message code="signup.idcheck" />', false);
				return;
			}
			
			
			$.ajax({
				url: '/customer/idcheck',
				method: 'post',
				data: {username: username},
				success: function(result) {
					isIdCheck = result === 'success';
					if(!isIdCheck) message($spanUsername, '<spring:message code="signup.idduplicated" />', false);
					else message($spanUsername, '<spring:message code="signup.idok" />', true);
				}
			})
		});
		
		function message($span, msg, success) {
			$span.text(msg);
			if(success) $span.css('color', '#66AA66');
			else $span.css('color', '#AA6666');
		}
	});
</script>
</html>