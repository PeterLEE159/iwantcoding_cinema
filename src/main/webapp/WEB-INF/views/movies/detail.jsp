<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html>
<head>
	<title><spring:message code="booking.title" /></title>
	<%@ include file="/WEB-INF/views/common/head.jsp" %>
	<style>
		.i-box {
			padding: 6px !important;
			color: white !important;
			
		}
		.i-green {
			background: #558564 !important;
		}
		.i-red {
			background: #B6244F !important;
		}
		.i-yello {
			background: #AA9933 !important; 
		}
		.i-blue {
			background: #158cba !important;
		}
		.full-img {
			height: 300px !important;
		}
		.table-movie th, .table-movie td {
			padding: 3px 0px;
		}
		.table-movie {
			margin-top: 24px;
			width: 100%;
		}
		.white-box {
			padding: 16px;
		}
		.btn {
			border-radius: 0px !important;
		}
		#btn-booking {
			float: right;
			margin: 0 24px 16px 0;
			
		}
		#btn-preview {
			padding: 8px 0;
		}
		.white-box {
			margin-bottom: 24px;
			padding-bottom: 24px;
		}
	</style>
</head>
<body>
	<c:set var="nav_active" value="movie" />
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<%@ include file="/WEB-INF/views/common/common_views.jsp" %>
	
	<div class="container top-margin">
	
		<div class="white-box" >
			<div class="row">
				<div class="col-md-3">
					<img class="full-img" src="${movie.getFullMovieURI(movie.images[0].uri) }"/>
					<a id="btn-preview" target="_blank" href="https://www.youtube.com/results?search_query=${movie.name }" class="btn btn-default btn-block"><i class="fa fa-film fa-fw"></i><spring:message code="movie.preview" /></a>
				</div>
				<div class="col-md-9 l-padding">
					<h3>
						<spring:message code="movie.allAge" var="springAllAge"/>
						<span class="i-box ${movie.common.ageLimit == 0 ? 'i-green' : movie.common.ageLimit == 12 ? 'i-blue' : movie.common.ageLimit == 15 ? 'i-yello' : 'i-red'}">${movie.common.ageLimit == 0 ? springAllAge : movie.common.ageLimit}</span>  ${movie.name }
						<a id="btn-booking" href="/movie/booking?mid=${movie.common.id }&lang=${param.lang }" class="btn btn-primary"><i class="fa fa-fw fa-ticket"></i>  <spring:message code="movie.booking" /></a>
					</h3>
					<hr />
					<div class="row">
						<div class="col-md-3">
							<h3><span class="rating" value="${movie.ratingPoint }" ></span></h3>
							<h4>${movie.ratingCount } <spring:message code="movie.reviewJoin" /></h4>
						</div>
						<div class="col-md-9">
							<h1><fmt:formatNumber value="${movie.ratingPoint }" pattern="0.0"/></h1>
						</div>
					</div>
					<table class="table-movie">
						<colgroup>
							<col width="50%" />
							<col width="50%" />
						</colgroup>
						<tr>
							<th><spring:message code="genre" /></th>
							<td><c:forEach var="genre" items="${movie.genres }" varStatus="status">${genre.type } </c:forEach></td>
						</tr>
						<tr>
							<th><spring:message code="movie.publishCountry" /></th>
							<td>${movie.publishCountry }</td>
						</tr>
						<tr>
							<th><spring:message code="movie.openDate" /></th>
							<td><fmt:formatDate value="${movie.common.openDate }" pattern="YYYY. MM. dd"/></td>
						</tr>
						<tr>
							<th><spring:message code="movie.closeDate" /></th>
							<td><fmt:formatDate value="${movie.common.closeDate }" pattern="YYYY. MM. dd" /></td>
						</tr>
						<tr>
							<th><spring:message code="movie.isDubbed" /></th>
							<spring:message code="yes" var="yes"/>
							<spring:message code="no" var="no"/>
							
							<td>${movie.common.dubbed == 1 ? yes : no }</td>
						</tr>
					</table>
				</div>
				
			</div>
			
			<hr />
			<h2><spring:message code="movie.shortStory" /></h2>
			
			<p>${movie.description }</p>
			
			
			<hr />
			<h2><spring:message code="movie.writeReview" /></h2>
			<div>
				<h3><span class="rating" onmouseover="hoverRating" onclick="clickRating" name="input-rating" value="3"></span></h3>
				<h4 id="h3-rating-desc"></h4>
				<textarea id="tx-rating" class="form-control" rows="5" style="font-size: 1.1em;"></textarea>
				<a id="btn-review" style="margin-top: 24px;" class="btn btn-default btn-forward"><i class="fa fa-fw fa-pencil"></i><spring:message code="movie.writeReview" /></a>
			</div>
			
			<hr />
			<h2><spring:message code="movie.reviewList" /></h2>
			<ul class="ul-horizontal">
				<c:if test="${not empty movie.reviews }">
					<c:forEach items="${movie.reviews }" var="review">
						<li>
							<div class="row">
								<div style="float: left">
									<img class="circular-img small-img" src="${review.customer.imageURI == null ? review.getFullCustomerURI('customer_placeholder.jpg') : review.getFullCustomerURI(review.customer.imageURI) }"/> 
								</div>
								<div style="float: left; margin-left: 24px;">
									<h4>${review.customer.name }</h4>
									<p>${review.calcPast(review.common.createdAt, param.lang ) }</p>
								</div>
							</div>
							<div class="row">
								<h4><span class="rating" value="${review.common.ratingPoint }"></span></h4>
								<p>${review.review }</p>
							</div>
						</li>
					</c:forEach>
				</c:if>
				<c:if test="${empty movie.reviews }">
					<li><h4><spring:message code="movie.noReviewYet" /></h4></li>
				</c:if>
			</ul>
		</div>
	</div>
</body>

<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
<script>
	var isClicked = false;
	var $ratingDesc = null;
	
	var isLogined = '${LOGIN_CUSTOMER.customerTypeId}' == '' ? false : true;
	$(function() {
		$ratingDesc = $('#h3-rating-desc');
		$ratingDesc.text('<spring:message code="movie.feedback.avg" />');
		
		
		$('#btn-review').click(function() {
			if(!isLogined)
				location.href = location.href+'&login=deny';
			var review = $('#tx-rating').val(),
			point = $('input[name=input-rating]').val();
			if(!review || review.trim() === '') {
				fAlert('<spring:message code="movie.review" />', '<spring:message code="movie.askWriteReview" />');
				return;
			}
			$.ajax({
				url: '/movie/insert_review',
				type: 'post',
				dataType: 'json',
				data: {content: review, ratingPoint: point, movieId: '${param.mid}', lang: '${param.lang}'},
				success: function(review) {
					location.href = location.href;
				},
			});
		});
	});
	
	function hoverRating(point) {
		if($ratingDesc && !isClicked) {
			if(point == 0)
				$ratingDesc.text('');
			else if(point == 1)
				$ratingDesc.text('<spring:message code="movie.feedback.worst" />');
			else if (point == 2)
				$ratingDesc.text('<spring:message code="movie.feedback.bad" />');
			else if (point == 3)
				$ratingDesc.text('<spring:message code="movie.feedback.avg" />');
			else if (point == 4)
				$ratingDesc.text('<spring:message code="movie.feedback.good" />');
			else if (point == 5)
				$ratingDesc.text('<spring:message code="movie.feedback.best" />');
		}
	}
	
	function clickRating(point) {
		if($ratingDesc) {
			isClicked = true;
			if(point == 0)
				$ratingDesc.text('');
			else if(point == 1)
				$ratingDesc.text('<spring:message code="movie.feedback.worst" />');
			else if (point == 2)
				$ratingDesc.text('<spring:message code="movie.feedback.bad" />');
			else if (point == 3)
				$ratingDesc.text('<spring:message code="movie.feedback.avg" />');
			else if (point == 4)
				$ratingDesc.text('<spring:message code="movie.feedback.good" />');
			else if (point == 5)
				$ratingDesc.text('<spring:message code="movie.feedback.best" />');
		}
	}
	
</script>
</html>