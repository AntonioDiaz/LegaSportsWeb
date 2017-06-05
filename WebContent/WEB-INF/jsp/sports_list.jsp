<%@include file="taglibs.jsp"%>

<c:forEach var="sportCenter" items="${sportsCenters}">
	<li>${sportCenter.name}</li>
</c:forEach>
