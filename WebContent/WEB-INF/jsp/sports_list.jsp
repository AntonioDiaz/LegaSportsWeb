<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="headers.jsp"%>
</head>
<body>
	<%@include file="navbar.jsp"%>
	<div class="container">
	<h1>Lista de deportes</h1>
	<c:forEach var="sportVO" items="${sports_list}">
		<li>${sportVO.name}</li>
	</c:forEach>
	</div>
</body>
</html>
