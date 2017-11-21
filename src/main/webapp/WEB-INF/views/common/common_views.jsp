<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<div id="div-loading">
	<img src="/resources/images/page_loading.gif"/>
</div>


<c:if test="${empty LOGIN_CUSTOMER }">
	<!-- 로그인폼 -->
	<div class="modal fade" id="div-login" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
		    <div class="modal-content">
		    	<div class="modal-header">
		        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        	<h4 class="modal-title"><spring:message code="login" /></h4>
		      	</div>
		      	<form action="/customer/login?lang=${param.lang }" method="post" class="form-horizontal">
		      		<input type="hidden" name="redirectURI" value="${param.redirectURI == null ? redirectURI : param.redirectURI}"/>
			      	<div class="modal-body">
			      		<div class="form-group">
			      			<label class="col-md-2 control-label"><spring:message code="id" /></label>
			      			<div class="col-md-10">
			      				<input type="text" class="form-control" name="username"/>
			      			</div>
			        	</div>
			        	<div class="form-group">
			      			<label class="col-md-2 control-label"><spring:message code="password" /></label>
			      			<div class="col-md-10">
			      				<input type="password" class="form-control" name="password"/>
			      			</div>
			      		</div>
			      		<div class="text-right">
			      			<a href="/customer/find?target=id" class="origin-atag"><spring:message code="findID" /></a>
			      			<a href="/customer/find?target=pwd" class="origin-atag" style="margin-left: 12px;"><spring:message code="findPassword" /></a>
			      		</div>
			      		<h4 class="font-alert">
							<c:if test="${param.login eq 'fail' }">
								<spring:message code="login.fail" />
							</c:if>
							<c:if test="${param.login eq 'retry' }">
								<spring:message code="login.retry" />
							</c:if>
							<c:if test="${param.login eq 'deny' }">
								<spring:message code="login.deny" />
							</c:if>
							<c:if test="${param.login eq 'signup' }">
								<spring:message code="login.signup" />
							</c:if>
						</h4>
			      		<button type="submit" class="btn btn-primary btn-block btn-forward" style="margin-top: 24px;"><i class="fa fa-sign-in fa-fw"></i><spring:message code="login" /></button>
				        <a href="/customer/signupform" type="button" class="btn btn-default btn-block btn-forward" style="margin-top: 8px;"><i class="fa fa-user-plus fa-fw"></i><spring:message code="signup" /></a>
			      		<p class="margin-top line-with-text"><span> <spring:message code="or" /> </span></p>
			      		
			      		<a id="btn-google" class="btn btn-default btn-block hover"><i class="fa fa-google fa-fw"></i><spring:message code="google.login" /> <div class="g-signin2" data-onsuccess="onSignIn" style="display: none;"></div></a>
<!-- 			      		<a id="btn-fb" class="btn btn-default btn-block hover"><i class="fa fa-facebook-f fa-fw"></i><spring:message code="facebook.login" /> <div class="g-signin2" data-onsuccess="onSignIn" style="display: none;"></div></a> -->
			      	
			      	</div>
		      	</form>
		    </div>
		</div>
	</div>
		
		
	<script>
		
		
		$(function() {
			// 구글 로그인 버튼 클릭
			$('#btn-google').click(function() {
				$('.abcRioButtonContentWrapper').click();
			});
// 			// 페이스북 로그인
// 			$('#btn-fb').click(function() {
// 				FB.login(function(response){
// 					console.log(response);
// 					var user = {
// 						username: response.authResponse.accessToken,
// 						thirdParty: 'F',
// 					};
// 					$.ajax({
// 						url: '/customer/third_login',
// 						method: 'post',
// 						data: user,
// 						dataType: 'json',
// 						success: function(result) {
// 							if(result)
// 								location.href = location.href;
// 						}
// 					});
// 				}, {scope: 'public_profile,email'});
// 			});
		});
		
		
		//구글 로그인 성공 시
		function onSignIn(googleUser) {
			var profile = googleUser.getBasicProfile();
			var user = {
				username: profile.getEmail(),
				thirdPartyValidationId: profile.getId(),
				name: profile.getName(),
				imageURI: profile.getImageUrl(),
				email: profile.getEmail(),
				thirdParty: 'G'
			};
			
			// ajax 구글 로그인
			$.ajax({
				url: '/customer/third_login',
				method: 'post',
				data: user,
				dataType: 'json',
				success: function(result) {
					if(result)
						location.href = location.href;
				}
			});
		}
		
		
		
		
		
	</script>
</c:if>
<!-- 세션이 있을 경우에는 로그아웃 기능 활성화 -->
<c:if test="${not empty LOGIN_CUSTOMER  }">
	<script>
		$(function() {
			// 로그아웃
			$('.btn-logout').click(function() {
				var customerType = '${LOGIN_CUSTOMER.thirdParty}';

				if(customerType != null && customerType != '') { // 3자 로그아웃
					if(customerType === 'G') {					 // 구글 로그아웃
						var auth2 = gapi.auth2.getAuthInstance();
					    auth2.signOut().then(function() {
					    	location.href = "/customer/logout?lang=";
					    });
					} else if(customerType === 'N') {			 // 네이버 로그아웃
						
					} else if(customerType === 'F') {			 // 페이스북 로그아웃
						FB.logout(function(response) {
							location.href = "/customer/logout?lang=";
						});
					}
				} else { 										 // 일반 로그아웃
					location.href = "/customer/logout?lang=";
				}
			});
		});
		// 구글 라이브러리 호출
		
		(function onLoad() {
		  	gapi.load('auth2', function() {
		    	gapi.auth2.init();
		  	});
		})();
		
	</script>
</c:if>
<!-- alert창 -->
<div class="modal fade" id="div-alert" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
	    <div class="modal-content">
	    	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        	<h4 class="modal-title" id="alert-title"></h4>
	      	</div>
	      	
	      	<div class="modal-body">
				<p id="alert-content"></p>
	      	</div>
	      	<div class="modal-footer">
	        	<button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-check fa-fw"></i><spring:message code="yes" /></button>
	      	</div>
	      	
	    </div>
	</div>
</div>


<!-- confirm창 -->
<div class="modal fade" id="div-confirm" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
	    <div class="modal-content">
	    	<div class="modal-header">
	        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        	<h4 class="modal-title" id="confirm-title"></h4>
	      	</div>
	      	
	      	<div class="modal-body">
				<p id="confirm-content"></p>
	      	</div>
	      	<div class="modal-footer">
	        	<button id="btn-confirm-yes" type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-check fa-fw"></i><spring:message code="yes" /></button>
	        	<button type="button" class="btn btn-default" data-dismiss="modal"><i class="fa fa-fire fa-fw"></i><spring:message code="no" /></button>
	      	</div>
	    </div>
	</div>
</div>
