<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html>
<head>
	<title><spring:message code="coupon" /></title>
	<%@ include file="/WEB-INF/views/common/head.jsp" %>
</head>
<style>
	.white-box {
		padding: 36px;
	}
	li.hover {
		padding: 12px !important;
	}
</style>
<body>
	<c:set var="nav_active" value="coupon" />
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<%@ include file="/WEB-INF/views/common/common_views.jsp" %>
	
	<div class="container white-box">
		<h3 class="page-header"><spring:message code="coupon.availableToIssue" /></h3>
		<ul class="ul-horizontal zero-padding">
			<c:forEach items="${coupones }" var="coupon">
				<li class="li-coupon hover hover-grey" couponId="${coupon.id }" name="${coupon.name }" distributeType="${coupon.distributeType }">
					<h4>${coupon.name }<small style="margin-left: 36px;"><fmt:formatDate value="${coupon.distributeUntil }"/> <spring:message code="until" /></small></h4>
					<p>${coupon.reason }</p>
					<spring:message code="coupon.internetonly" var="internetOnly" />
					<spring:message code="coupon.cinemaonly" var="cinemaOnly" />
					<p>${coupon.distributeType == 'I' ? internetOnly : cinemaOnly }</p>
				</li>
			</c:forEach>
		</ul>
		<hr />
		
		<h3 class="page-header"><spring:message code="coupon.eventGift" /></h3>
		<ul class="ul-horizontal zero-padding">
			<c:forEach items="${gifts }" var="gift">
				<li class="li-gift hover hover-grey" name="${gift.name }" giftId="${gift.id }" distributeType="${gift.distributeType }">
					<h4>${gift.name }<small style="margin-left: 36px;"><fmt:formatDate value="${gift.distributeUntil }"/> <spring:message code="until" /></small></h4>
					<p>${gift.reason }</p>
					<spring:message code="coupon.delieveryOnlyToAddress" var="deAddress"/>					
					<p>${gift.distributeType == 'D' ? deAddress : cinemaOnly }</p>
				</li>
			</c:forEach>
		</ul>
	</div>
</body>

<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
<script>
	$('.li-coupon').click(function() {
		$this = $(this);
		if($this.attr('distributeType') !== 'I') {
			fAlert('<spring:message code="coupon.distType" />', '<spring:message code="coupon.feedback.cinemaonly" />');
			return;
		}
		var couponId = $this.attr('couponId');
		fConfirm($this.attr('name'), '<spring:message code="coupon.feedback.couponOnceonly" />', function() {
			$.ajax({
				type: 'post',
				url: '/movie/issue_coupon',
				data: {couponId: couponId},
				success: function(result) {
					if(result == 'success')
						fAlert('<spring:message code="coupon.issue" />', '<spring:message code="coupon.feedback.success" />');
					else
						fAlert('<spring:message code="coupon.issue" />', '<spring:message code="coupon.feedback.failure" />');
				}
			})
		});
	});
	
	$('.li-gift').click(function() {
		$this = $(this);
		if($this.attr('distributeType') !== 'D') {
			fAlert('<spring:message code="coupon.distType" />', '<spring:message code="coupon.feedback.cinemaonly" />');
			return;
		}
		var giftId = $this.attr('giftId');
		fConfirm($this.attr('name'), '<spring:message code="gift.feedback.delieveryToAddress" />', function() {
			$.ajax({
				type: 'post',
				url: '/movie/issue_gift',
				data: {giftId: giftId},
				success: function(result) {
					if(result == 'success')
						fAlert('<spring:message code="gift.issue" />', '<spring:message code="gift.feedback.success" />');
					else
						fAlert('<spring:message code="gift.issue" />', '<spring:message code="gift.feedback.failure" />');
				}
			})
		});
	});
</script>
</html>