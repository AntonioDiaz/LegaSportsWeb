<%@include file="taglibs.jsp"%>

<c:forEach var="sportVO" items="${sports}">
	<li>${sportVO.name}</li>
</c:forEach>
