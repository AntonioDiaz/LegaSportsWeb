<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>
<script>

	$(document).ready(function() {
		<c:if test="${remove_done==true}">
			showDialogAlert("La competición ha sido eliminada.");
		</c:if>
		<sec:authorize access="!hasRole('ROLE_ADMIN')">
			$('#idTown').prop('disabled', true)
		</sec:authorize>
	});

	function fViewCalendar (idCompetition) {
		window.location.href = "/competitions/view?idCompetition=" + idCompetition;
	}

	function fValidateFilterForm() {
		var idTown = $('#idTown').val();
		var idCategory = $('#idCategory').val();
		var idSport = $('#idSport').val();
		if (idTown=="" && idCategory=="" && idSport=="") {
			showDialogAlert("Es necesario indicar al menos un filtro.");
			return false;
		}
		return true;
	}


</script>
<form:form method="post" action="doFilter" commandName="form_filter" cssClass="form-inline" onsubmit="return fValidateFilterForm()">
	<div class="row">
		<div class="col-sm-3">
			<div class="form-group">
				<label class="control-label" for="idTown">Municipio &nbsp;&nbsp;</label>
				<form:select path="idTown" class="form-control" cssStyle="width: 150px">
					<form:option value=""></form:option>
					<form:options items="${towns}" itemLabel="name" itemValue="id" />
				</form:select>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="form-group">
				<label class="control-label" for="idCategory">Categoría &nbsp;&nbsp;</label>
				<form:select path="idCategory" class="form-control" cssStyle="width: 150px">
					<form:option value=""></form:option>
					<form:options items="${categories}" itemLabel="name" itemValue="id" />
				</form:select>
			</div>
		</div>
		<div class="col-sm-3">
			<div class="form-group">
				<label class="control-label" for="idSport">Deporte &nbsp;&nbsp;</label>
				<form:select path="idSport" class="form-control" cssStyle="width: 150px">
					<form:option value=""></form:option>
					<form:options items="${sports}" itemLabel="name" itemValue="id" />
				</form:select>
			</div>
		</div>
		<sec:authorize access="!hasRole('ROLE_ADMIN')">
			<div class="col-sm-3">&nbsp;</div>
		</sec:authorize>
		<div class="col-sm-1">&nbsp;</div>
		<div class="col-sm-2">
			<button type="submit" class="btn btn-default btn-block">buscar</button>
		</div>
	</div>
</form:form>
<hr>
<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-md-2">Nombre</th>
			<th class="col-md-2">Deporte</th>
			<th class="col-md-2">Categoria</th>
			<th class="col-md-2">Municipio</th>
			<th class="col-md-2">Fecha Publicación</th>
			<th class="col-md-2">&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty competitions}">
			<c:if test="${competitions==null}">
				<tr>
					<td colspan="10">Realize la búsqueda.</td>
				</tr>
			</c:if>
			<c:if test="${competitions!=null}">
				<tr>
					<td colspan="10">No hay competiciones para esta búsqueda.</td>
				</tr>
			</c:if>
		</c:if>
		<c:forEach var="competition" items="${competitions}">
			<tr>
				<td style="vertical-align: middle;"><strong>${competition.name}</strong></td>
				<td style="vertical-align: middle;">${competition.sportEntity.name}</td>
				<td style="vertical-align: middle;">${competition.categoryEntity.name}</td>
				<td style="vertical-align: middle;">${competition.townEntity.name}</td>
				<td style="vertical-align: middle;">
					<c:if test="${competition.lastPublished eq null}">
						Sin publicar
					</c:if>
					<c:if test="${competition.lastPublished ne null}">
						<fmt:formatDate type="both" pattern="dd/MM/yyyy HH:mm" value="${competition.lastPublished}"></fmt:formatDate>
					</c:if>
				</td>
				<td align="right">
					<button type="button" class="btn btn-default" onclick="fViewCalendar('${competition.id}')" title="calendario">
						Ver detalles &nbsp; &nbsp;<span class="glyphicon glyphicon-eye-open"></span>
					</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>