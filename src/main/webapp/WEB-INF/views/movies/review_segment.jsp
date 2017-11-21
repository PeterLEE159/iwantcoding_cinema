<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<input type="hidden" name="page" value="${param.page == null ? '1' : param.page }" />
<ul id="ul-reviews" class="ul-horizontal zero-padding">
	<c:if test="${reviews != null && reviews.size() > 0}">
		
		<c:forEach items="${reviews }" var="review">
			<c:if test="${review.movie != null }">
				<li>
					<div class="row">
						<div style="float: left">
							<img class="circular-img small-img" src="${review.customer.imageURI == null ? review.getFullCustomerURI('customer_placeholder.jpg') : review.getFullCustomerURI(review.customer.imageURI) }"/> 
						</div>
						<div style="float: left; margin-left: 24px;">
							<h4 
							<c:if test="${furiLocation == 'movie' }">
								class="hover btn-forward" onclick="location.href = '/movie/reviews?page=1&lang=${param.lang }&cid=${review.customer.id }'"
							</c:if>
							>${review.customer.name }</h4>
							<p>${review.calcPast(review.common.createdAt, param.lang ) }</p>
						</div>
					</div>
					<div class="row">
						<h4><span class="rating" value="${review.common.ratingPoint }"></span></h4>
						<p>${review.review }</p>
					</div>
					<div class="row small-top-margin">
						<span onclick="location.href = '/movie/detail?mid=${review.movie.movieId }&lang=${param.lang }';" class="style-box hover style-green-box btn-forward"># ${review.movie.name }</span>
					</div>
				</li>
			</c:if>
		</c:forEach>
	</c:if>
	<c:if test="${reviews == null || reviews.size() == 0}">
		<li><h4><spring:message code="noReviewRecord" /></h4></li>
	</c:if>
</ul>


<script>
	$(function() {
		$inputPage = $('input[name=page]'),
		$ulReviews = $('#ul-reviews'),
		$reviewWindow = $(window),
		$reviewDocument = $(document),
		lang = '${param.lang}',
		isOnProcess = false,
		furiLocation = '${furiLocation}',
		cid = '${param.cid}';
		
		if(lang == null || lang == '') lang = 'ko';
		
		
		$reviewWindow.scroll(function() {
			if(!isOnProcess) {
				reviewDocumentHeight = $reviewDocument.height();
			 	if($reviewWindow.scrollTop() > reviewDocumentHeight - 2000) {
			 		isOnProcess = true;
			 		var nextPage = parseInt($inputPage.val()) + 1;
			 		$inputPage.val(nextPage);
			 		var html = '';
			 		$.ajax({
			 			data: {page: nextPage, lang: lang, cid: cid},
			 			type: 'get',
			 			url: '/'+furiLocation+'/more_review',
			 			dataType: 'json',
			 			success: function(reviews) {
			 				if(reviews.length == 0)  {
			 					isOnProcess = true;
			 					return;
			 				}
			 				$.each(reviews, function(index, review) {
			 					if(review.movie == null || review.movie.movieId == 0) return;
			 					
			 					var pastTime = calcPastTime(review.common.createdAt, lang);
			 					
			 					var imgURI = review.customer.imageURI == null ? customerURI('customer_placeholder.jpg') : customerURI(review.customer.imageURI);
			 					var linkURI = "location.href = '/movie/detail?mid="+review.movie.movieId+"&lang="+lang+";'";
			 					
			 					html += "<li>";
			 					html += "<div class='row'>";
			 					html += "<div style='float: left'>";
			 					html += "<img class='circular-img small-img' src='"+imgURI+"'/>";
			 					html += "</div>";
			 					html += "<div style='float: left; margin-left: 24px;'>";
			 					html += "<h4>"+review.customer.name+"</h4>";
			 					html += "<p>"+pastTime+"</p>";
			 					html += "</div>";
								html += "</div>";
								html += "<div class='row'>";	
								html += "<h4><span class='rating' value="+review.common.ratingPoint+"></span></h4>";
								html += "<p>"+review.review+"</p>";
								html += "</div>";
								html += "<div class='row small-top-margin'>";
								html += "<span onclick="+linkURI+" class='style-box hover style-green-box'># "+review.movie.name+"</span>";
								html += "</div>";
								html += "</li>";
								
			 				});
			 				$reviews = $(html);
			 				$ratings = $reviews.find('span.rating');
			 				insertRating($ratings);
			 				$ulReviews.append($reviews);
			 				isOnProcess = false;
			 			}
			 		});
					
				}
			}
		});
	});
</script>