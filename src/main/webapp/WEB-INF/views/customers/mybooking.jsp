<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglibs.jsp" %>
<html>
<head>
	<title><spring:message code="coupon" /></title>
	<%@ include file="/WEB-INF/views/common/head.jsp" %>
	<style>
		.ul-horizontal li {
			border-bottom: 0px solid !important;
			padding: 24px 12px !important;
		} 
	</style>
</head>
<body>
	<c:set var="nav_active" value="mypage" />
	<%@ include file="/WEB-INF/views/common/nav.jsp" %>
	<%@ include file="/WEB-INF/views/common/common_views.jsp" %>
	
	
	<div class="container" style="margin-top: 52px;">
		<div class="col-md-3">
			<c:set var="customer_side" value="booking" />
			<%@ include file="/WEB-INF/views/customers/customer_side.jsp" %>
		</div>
		<div class="col-md-9 white-box" style="padding: 24px 0px;">
			<h2 class="page-header" style="margin-left: 24px;"><spring:message code="receiptList" /></h2>
			<ul class="ul-horizontal zero-padding">
				<c:if test="${receipts != null && receipts.size() > 0}">
					<c:forEach items="${receipts }" var="receipt">
						<li class="li-gift hover hover-grey li-receipt-detail" rid="${receipt.id }">
							<table class="table">
								<colgroup>
									<col width="20%" />
									<col width="35%" />
									<col width="13.3%" />
									<col width="13.3%" />
									<col width="13.3%" />
								</colgroup>
								<thead>
									<tr>
										<th><spring:message code="purchaseDate" /></th>
										<th><spring:message code="receiptNo" /></th>
										<th><spring:message code="originalPrice" /></th>
										<th><spring:message code="discountPrice" /></th>
										<th><spring:message code="purchasePrice" /></th>
										
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><fmt:formatDate value="${receipt.purchaseDate }" pattern="yyyy.MM.dd HH:mm"/></td>
										<td>${receipt.rid }</td>
										<td><fmt:formatNumber value="${receipt.price }" pattern="#,###"/>  <spring:message code="won" /></td>
										<td><fmt:formatNumber value="${receipt.discountPrice }" pattern="#,###"/> <spring:message code="won" /></td>
										<td><fmt:formatNumber value="${receipt.price - receipt.discountPrice}" pattern="#,###"/> <spring:message code="won" /></td>
									</tr>
								</tbody>
							</table>
						</li>
					</c:forEach>
				</c:if>
				<c:if test="${receipts == null || receipts.size() == 0}">
					<li><h4><spring:message code="noPurchaseRecord" /></h4></li>
				</c:if>
			</ul>
		</div>
		
	</div>

	<div class="modal fade" id="div-receipt" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
		    <div class="modal-content">
		    	<div class="modal-header">
		        	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        	<h4 class="modal-title"><spring:message code="receiptInfo" /></h4>
		      	</div>
		      	
		      	<div class="modal-body">
					<table class="table">
						<colgroup>
							<col width="20%" />
							<col width="20%" />
							<col width="20%" />
							<col width="40%" />
						</colgroup>
						<thead>
							<tr>
								<th><spring:message code="movieName" /></th>
								<th><spring:message code="screenName" /></th>
								<th><spring:message code="seat" /></th>
								<th><spring:message code="startedTime" /></th>
							</tr>
						</thead>
						<tbody id="tbody-receipt-detail">
						
						</tbody>
					</table>
		      	</div>
		      	<div class="modal-footer">
		        	<button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-check fa-fw"></i><spring:message code="ok" /></button>
		      	</div>
		      	
		    </div>
		</div>
	</div>
</body>
	
<%@ include file="/WEB-INF/views/common/scripts.jsp" %>
<script src="/resources/js/main.js"></script>
<script>
	$(function() {
		var $tbodyReceipt = $('#tbody-receipt-detail'),
		lang = '${param.lang}',
		$modalSeat = $('#div-receipt');
		
		
		var result = '${param.result}';
		if(result === 'success')
			fAlert('<spring:message code="alert" />', '<spring:message code="receiptSuccess" />')
		
		if(lang == null || lang == '') lang = 'ko';
		
		$('.li-receipt-detail').click(function() {
			
			var receiptId = $(this).attr('rid');
			
			loadingAnim();
			$.ajax({
				data: {receiptId: receiptId, lang: lang},
				type: 'get',
				url: '/customer/receipt_detail',
				dataType: 'json',
				success: function(timetables) {
					finishLoadingAnim();
					$tbodyReceipt.empty();
					var html = '';
					$.each(timetables, function(index, timetable) {
						
						html += "<tr>";
						html += "<td>"+timetable.movie.name+"</td>";
						html += "<td>"+timetable.screen.name+"</td>";
						html += "<td>"+timetable.seat.name+"</td>";
						html += "<td>"+moment(timetable.startedAt, 'x').format('YYYY.MM.DD HH:mm')+"</td>";
						html += "</tr>";
					});
					$tbodyReceipt.append(html);
					$modalSeat.modal();
				}
			});
		});
	});
</script>
</html>