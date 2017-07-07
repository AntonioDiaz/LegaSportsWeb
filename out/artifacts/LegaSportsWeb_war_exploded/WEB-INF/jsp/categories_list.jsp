<%@include file="taglibs.jsp"%>

<c:forEach var="category" items="${categories}">
	<li>${category.name}</li>
</c:forEach>