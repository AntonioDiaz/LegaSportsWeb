<%@include file="taglibs.jsp"%>

<c:forEach var="categoryRef" items="${categories}">
	<li>${categoryRef.name}</li>
</c:forEach>