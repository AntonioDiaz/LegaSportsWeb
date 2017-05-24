<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/WEB-INF/jsp/include.jsp"%>
	<title><tiles:insertAttribute name="title" ignore="true" defaultValue="title" /></title>
</head>
<body>
	<nav class="navbar navbar-default" style="margin-bottom: 0px;">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="/">
				<img src="/images/logo.png" class="img-rounded" width="180px">
			</a>
		</div>
		
		<ul class="nav navbar-nav">
			<li><a href="/sports/list">Deportes</a></li>
			<li><a href="/categories/list">Categorias</a></li>
			<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Competiciones <span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="/competitions/list">Lista</a></li>
					<li><a href="/competitions/add">Nueva</a></li>
				</ul>
			</li>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Usuarios <span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="/users/list">Lista usuarios</a></li>
						<li><a href="/users/add">Nuevo usuario</a></li>
					</ul>
				</li>
			</sec:authorize>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href="<c:url value="j_spring_security_logout" />"><span class="glyphicon glyphicon-log-in"></span>&nbsp;Salir&nbsp;</a></li>
		</ul>
	</div>
	</nav>
	<div class="container">
		<h1><tiles:insertAttribute name="page_title" ignore="true" defaultValue="title" /></h1>
		<hr>
		<tiles:insertAttribute name="body" />
	</div>
</body>
</html>
