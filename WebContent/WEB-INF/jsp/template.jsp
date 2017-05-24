<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/WEB-INF/jsp/include.jsp"%>
	<title><tiles:insertAttribute name="title" ignore="true" defaultValue="title" /></title>
</head>
<body>
	<div class="container">
		<%@ include file="/WEB-INF/jsp/navbar.jsp" %>
		<div class="jumbotron" style="padding: 10px 30px;height:100%; min-height: 550px; background:transparent !important; border: 1px solid #e7e7e7;">
			<h2 style="color: #0061a8">
				<tiles:insertAttribute name="page_title" ignore="true" defaultValue="title" />
			</h2>
			<hr>
			<tiles:insertAttribute name="body" />
		</div>
	</div>
</body>
</html>
