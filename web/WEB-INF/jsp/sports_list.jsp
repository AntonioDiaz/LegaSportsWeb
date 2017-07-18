<%@include file="taglibs.jsp"%>

<c:forEach var="sport" items="${sports}">
	<li>${sport.name}</li>
</c:forEach>
