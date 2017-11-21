<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="list-group">
	<a href="/customer/mybooking?lang=${param.lang }" class="list-group-item btn-forward ${customer_side == 'booking' ? 'active' : ''}"><i class="fa fa-fw fa-ticket"></i><spring:message code="mypurchase" /></a>
	<a href="/customer/mypost?page=1&lang=${param.lang }" class="list-group-item btn-forward ${customer_side == 'post' ? 'active' : ''}"><i class="fa fa-fw fa-paper-plane"></i><spring:message code="myreview" /></a>
	<c:if test="${ LOGIN_CUSTOMER.thirdParty == null}" >
		<a href="/customer/myupdate?lang=${param.lang }" class="list-group-item btn-forward ${customer_side == 'update' ? 'active' : ''}"><i class="fa fa-fw fa-info"></i><spring:message code="myinfo" /></a>
	</c:if>
</div>