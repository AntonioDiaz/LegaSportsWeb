<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="headers.jsp" %>
</head>
<body>
	<%@include file="navbar.jsp" %>
	<div class="container">
		<c:if test="${not empty message}">
			<h1>${message}</h1>
		</c:if>
		<table>
			<tr>
				<td><a href="cleanDB">clean DB</a></td>
			</tr>
			<tr>
				<td><a href="loadSports">loadSports</a></td>
			</tr>
			<tr>
				<td><a href="loadCategories">loadCategories</a></td>
			</tr>
			<tr>
				<td><a href="loadCompetitions">loadCompetitions</a></td>
			</tr>
			<tr>
				<td><a href="loadMatches">loadMatches</a></td>
			</tr>
			<tr>
				<td><a href="sports/">json sports</a></td>
			</tr>
			<tr>
				<td><a href="categories/">json categories</a></td>
			</tr>
			<tr>
				<td><a href="competitions/">json competitions</a></td>
			</tr>
			<tr>
				<td><a href="matches/">json matches</a></td>
			</tr>
		</table>
	</div>
</body>
</html>
