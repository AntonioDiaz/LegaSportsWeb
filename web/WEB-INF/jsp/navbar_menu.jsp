<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="taglibs.jsp"%>
<div id="navbar" class="navbar-collapse collapse">
	<ul class="nav navbar-nav">
		<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Deportes <span class="caret"></span></a>
			<ul class="dropdown-menu">
				<li><a href="/sports/list">Lista deportes</a></li>
				<li><a href="/sports/add">Nuevo deporte</a></li>
			</ul>
		</li>
		<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Categorias <span class="caret"></span></a>
			<ul class="dropdown-menu">
				<li><a href="/categories/list">Lista categorias</a></li>
				<li><a href="/categories/add">Nueva categoria</a></li>
			</ul>
		</li>
		<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Centros <span class="caret"></span></a>
			<ul class="dropdown-menu">
				<li><a href="/centers/list">Lista centros</a></li>
				<li><a href="/centers/add">Nuevo centro</a></li>
			</ul>
		</li>
		<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Competiciones <span class="caret"></span></a>
			<ul class="dropdown-menu">
				<li><a href="/competitions/list">Lista de competiciones</a></li>
				<li><a href="/competitions/add">Nueva competición</a></li>
			</ul>
		</li>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Equipos <span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="/team/list">Lista equipos</a></li>
					<li><a href="/team/add">Nuevo equipo</a></li>
					<li><a href="/club/list">Lista clubes</a></li>
					<li><a href="/club/add">Nuevo club</a></li>
				</ul>
			</li>
			<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#">Admin <span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="/users/list">Lista usuarios</a></li>
					<li><a href="/users/add">Nuevo usuario</a></li>
					<li><a href="/towns/list">Lista municipios</a></li>
					<li><a href="/towns/add">Nuevo municipio</a></li>
					<li><a href="/admin/initCompetition">Inicializa competición</a></li>
					<li><a href="/issues/list">Consulta notificaciones</a></li>
				</ul>
			</li>
		</sec:authorize>
	</ul>
	<ul class="nav navbar-nav navbar-right">
		<li>
			<a href="<c:url value="j_spring_security_logout" />">
				<b>
					<sec:authentication property="principal" var="userSession"></sec:authentication>
					<c:out value="${userSession.username}"></c:out>&nbsp;
					<c:if test="${!userSession.admin}">
						(<c:out value="${userSession.townEntity.name}"></c:out>)&nbsp;
					</c:if>
					<c:if test="${userSession.admin}">
						(admin)&nbsp;
					</c:if>
					<span class="glyphicon glyphicon-log-in"></span>
				</b>
			</a>
		</li>
	</ul>
</div>
