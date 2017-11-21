<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- css -->
<link rel="stylesheet" href="/resources/css/style.css" />

<!-- google login -->
<c:if test="${empty LOGIN_CUSTOMER }">
	<script src="https://apis.google.com/js/platform.js" async defer></script>
</c:if>
<c:if test="${not empty LOGIN_CUSTOMER }">
	<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
</c:if>
<meta name="google-signin-client_id" content="1074334930022-n1t3j867ibk3qaqt91grnt4irhb3ebhc.apps.googleusercontent.com">





<!-- helper js -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.19.1/moment.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.19.1/locale/ko.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment-range/3.0.3/moment-range.min.js"></script>


<script>
// 	window.fbAsyncInit = function() {
// 	    FB.init({
// 	      appId            : '1621993864551546',
// 	      autoLogAppEvents : true,
// 	      xfbml            : true,
// 	      version          : 'v2.11'
// 	    });
// 	    FB.AppEvents.logPageView();
//   	};

// 	(function(d, s, id){
// 		var js, fjs = d.getElementsByTagName(s)[0];
// 		if (d.getElementById(id)) {return;}
// 			js = d.createElement(s); js.id = id;
// 			js.src = "https://connect.facebook.net/en_US/sdk.js";
// 			fjs.parentNode.insertBefore(js, fjs);
// 			}(document, 'script', 'facebook-jssdk'));
  
	

	function fAlert(title, content, fn) {
		$('#alert-title').text(title);
		$('#alert-content').text(content);
		$modal = $('#div-alert');
		
		$modal.on('hidden.bs.modal', function () {
			if(fn) fn();
		});
		
		$modal.modal('show');
	};
	
	function fConfirm(title, content, fnYes, fnNo) {
		$('#confirm-title').text(title);
		$('#confirm-content').text(content);
		$modal = $('#div-confirm');
		$('#btn-confirm-yes').one('click', fnYes);
		
		$modal.on('hidden.bs.modal', function () {
			if(fnNo) fnNo();
		});
		
		$modal.modal('show');
	}
	
	function showImage($input, $img) {
		if ($input.files && $input.files[0]) {
		    var reader = new FileReader();

		    reader.onload = function(e) {
		     	$img.attr('src', e.target.result);
		     	$img.css({
		     		width: 100,
		     		height: 100
		     	});
		    };
		    reader.readAsDataURL($input.files[0]);
		}
	}
</script>