<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="headers.jsp"%>
</head>
<body>
	<%@include file="navbar.jsp"%>
	<div class="container">
	<h1>${sport.name} categorias: </h1>
	<c:forEach var="category" items="${categories_list}">
		<li>${category.name}</li>
	</c:forEach>
	</div>
</body>
</html>
