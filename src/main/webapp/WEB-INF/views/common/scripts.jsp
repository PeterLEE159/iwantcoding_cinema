<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<script>
$(function() {
	<c:if test="${not empty param.login}">
		$('#div-login').modal('show');
	</c:if>
	
	<c:if test="${not empty param.alert}">
		var alert = '${param.alert}';
		if(alert == 'update') {
			fAlert('<spring:message code="myupdate" />', '<spring:message code="update.success" />');
		}
			
	</c:if>
});
</script>