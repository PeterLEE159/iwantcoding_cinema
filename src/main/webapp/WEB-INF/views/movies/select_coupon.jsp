<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html>
<head>
	<title><spring:message code="coupon" /></title>
	<%@ include file="/WEB-INF/views/common/head.jsp" %>
	<style>
		.ul-horizontal li {
			padding-left: 24px;
		}
		.white-box {
			padding: 24px;
		}
		.div-main {
			margin-bottom: 32px;
		}
		.gtop-margin {
			margin-top: 64px;
		}
	</style>
</head>
<body>
	<c:set var="nav_active" value="booking" />
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<%@ include file="/WEB-INF/views/common/common_views.jsp" %>
	
	<div class="container div-main">
		<div class="row">
			<div class="col-md-7 white-box">
			
				<h3 class="page-header top-margin"><spring:message code="coupon.plantobuy.ticket" /></h3>
				<ul class="ul-horizontal zero-padding">
					<c:forEach items="${tickets }" var="ticket">
						<li>
							<h4>${ticket.movieName } <small><fmt:formatDate value="${ticket.startedAt }" pattern="yyyy.MM.dd, HH:mm"/></small></h4>
							<p>${ticket.screenName },  ${ticket.seatName } <spring:message code="seat" /></p>
						</li>
					</c:forEach>
				</ul>
				
				
				<hr />
				<h3 class="page-header gtop-margin"><spring:message code="coupon.availableToUse" /></h3>
				<ul class="ul-horizontal zero-padding">
					<c:if test="${coupones != null }">
						<c:forEach items="${coupones }" var="coupon">
							<li class="li-coupon hover hover-grey" cid="${coupon.id }" cname="${coupon.coupon.name }">
								<h4>${coupon.coupon.name }<small style="margin-left: 36px;"><fmt:formatDate value="${coupon.expiredAt }" pattern="yyyy.MM.dd"/> <spring:message code="until" /></small></h4>
								<p>1회에 한해서 무료로 관람가능 <spring:message code="coupon.freeOnce" /></p>
							</li>
						</c:forEach>
					</c:if>
					<c:if test="${coupones == null }">
						<li><h4><spring:message code="coupon.noAvailable" /></h4></li>
					</c:if>
				</ul>
				
				<h3 class="page-header gtop-margin"><spring:message code="coupon.discountEvent" /></h3>
				<ul class="ul-horizontal zero-padding">
					<c:if test="${discounts != null }">
						<c:forEach items="${discounts }" var="discount">
							<li class="li-discount hover hover-grey" did="${discount.id }" dname="${discount.name }" dpercent="${discount.discountPercent }">
								<h4>${discount.name }<small style="margin-left: 36px;"><fmt:formatDate value="${discount.endedDate }" pattern="yyyy.MM.dd"/> <spring:message code="until" /></small></h4>
								<p>${discount.detail }</p>
								<h3><small><fmt:formatNumber value="${discount.discountPercent }" pattern="00.0" />% <spring:message code="discount" /></small></h3>
							</li>
						</c:forEach>
					</c:if>
					<c:if test="${coupones == null }">
						<li><h4><spring:message code="discount.noAvailable" /></h4></li>
					</c:if>
				</ul>
				
				<hr />
				
			</div>
			<div class="col-md-4 col-md-offset-1">
				<div class="white-box">
					<h3><spring:message code="coupon.paymentPrice"/></h3>
					<form id="form-pay" action="/movie/payment" method="post">
						<table class="table top-margin">
							<colgroup>
								<col width="40%"/>
								<col width="*"/>
							</colgroup>
							<tr>
								<th><spring:message code="coupon.origin" /></th>
								<td id="td-price" class="text-right"></td>
							</tr>
							<tr>
								<th><spring:message code="coupon.discountprice" /></th>
								<td id="td-discount" class="text-right"></td>
							</tr>
							<tr style="padding: 32px 0px;">
								<th class="font-bigger"><spring:message code="coupon.paymentprice" /></th>
								<td class="font-alert text-right bold" style="font-size: 1.5em;" id="td-payment" class="text-right"></td>
							</tr>
						</table>
						<button class="btn btn-primary btn-block" type="submit"><i class="fa fa-credit-card fa-fw"></i> <spring:message code="coupon.pay" /></button>
					</form>
				</div>
				
				<div class="white-box top-margin">
					<h3><spring:message code="coupon.discountList" /></h3>
					<table class="table top-margin">
						<colgroup>
							<col width="70%"/>
							<col width="*"/>
						</colgroup>
						<tbody id="tbody-discount">
							<tr id="tr-no-discount">
								<td colspan="2"><spring:message code="coupon.noDiscountList" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		
	</div>
	
</body>

<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
<script>
	$(function() {
		var eachPrice = parseInt('${LOGIN_CUSTOMER.type.price}'),
		ticketLength = parseInt('${tickets.size()}'),
		discountPrice = 0,
		cid = 0,
		did = 0,
		isCouponUsed = false,
		isDiscountUsed = false,
		$formPay = $('#form-pay'),
		$tdDiscount = $('#td-discount'),
		$tdPayment = $('#td-payment');
		
		originalPrice = eachPrice * ticketLength;
		var woncurrency = '<spring:message code="won" />';
		
		$('#td-price').text(numberWithCommas(originalPrice) + " "+woncurrency);
		$tdDiscount.text("0 "+woncurrency);
		$tdPayment.text(numberWithCommas(originalPrice) + " "+woncurrency);
		
		// 쿠폰 클릭 시
		$('.li-coupon').click(function() {
			if(isCouponUsed){
				fAlert('<spring:message code="alert" />', '<spring:message code="coupon.alreadyApplied" />');
				return;
			}
			
			$this = $(this);
			
			var discountName = $this.attr('cname');
			fConfirm(discountName, '<spring:message code="coupon.askToUse" />', function() {
				isCouponUsed = true;
				
				
				cid = $this.attr('cid');
				discountPrice += eachPrice;
				$tdDiscount.text(numberWithCommas(discountPrice) +" "+woncurrency);
				$tdPayment.text(numberWithCommas((originalPrice - discountPrice)) + " "+woncurrency);
				
				addDiscount(discountName, eachPrice);
			});
		});
		// 할인 클릭 시
		$('.li-discount').click(function() {
			if(isDiscountUsed) {
				fAlert('<spring:message code="alert" />', '<spring:message code="discount.alreadyApplied" />');
				return;
			}

			$this = $(this);
			var discountName = $this.attr('dname');
			fConfirm(discountName, '<spring:message code="discount.askToUse" />', function() {
				var isDiscountUsed = true,
				did = $this.attr('did'),
				discounted = originalPrice / parseInt($this.attr('dpercent'));
				
				discountPrice += discounted;
				
				$tdDiscount.text(numberWithCommas(discountPrice +" "+woncurrency));
				$tdPayment.text(numberWithCommas((originalPrice - discountPrice) + " "+woncurrency));
				
				addDiscount(discountName, discounted);
			});
			
		});
		// 할인내역 테이블에 추가
		function addDiscount(name, price) {
			$tr = $('#tr-no-discount');
			if($tr) $tr.remove();
			
			$tbDiscount = $('#tbody-discount');
			var html = "<tr><th>"+name+"</th><td class='text-right'>"+numberWithCommas(price)+" "+woncurrency+"</td></tr>";
			$tbDiscount.append(html);
		}
		// 결제 쿠폰 클릭 시
		$formPay.submit(function() {
			
			
			var html = "<input type='hidden' name='price' value='"+originalPrice+"' />";
			html += "<input type='hidden' name='discountPrice' value='"+discountPrice+"'/>";
			html += "<input type='hidden' name='couponCustomerId' value='"+cid+"'/>";
			html += "<input type='hidden' name='discountId' value='"+did+"'/>";
			$(this).append(html);
			
			return true;
			
		});
		
		
	});
	
	
	
	
</script>
</html>