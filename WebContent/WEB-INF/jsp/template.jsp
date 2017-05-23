<!DOCTYPE html>
<html lang="en">
<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
	<title><tiles:insertAttribute name="title" ignore="true" defaultValue="title" /></title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script> -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/angular-smart-table/2.1.6/smart-table.js"></script>
</head>
<body>
	<nav class="navbar navbar-inverse" style="margin-bottom: 0px;">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="/">
				<tiles:insertAttribute name="title" ignore="true" defaultValue="LegaSports" />
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
			<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Usuarios <span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="/users/list">Lista usuarios</a></li>
					<li><a href="/users/add">Nuevo usuario</a></li>
				</ul>
			</li>
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
