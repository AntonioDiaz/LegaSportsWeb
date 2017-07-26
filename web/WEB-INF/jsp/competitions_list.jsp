<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>
<script>

	$(document).ready(function() {
		<c:if test="${remove_done==true}">
			showDialogAlert("La competición ha sido eliminada.");
		</c:if>
	});

	function fViewCalendar (idCompetition) {
		window.location.href = "/competitions/viewCalendar?idCompetition=" + idCompetition;
	}

	function fViewClassification (idCompetition) {
		window.location.href = "/competitions/viewClassification?idCompetition=" + idCompetition;
	}

</script>
<form:form method="post" action="doFilter" commandName="form_filter" cssClass="form-inline">
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
		<div class="col-sm-1">&nbsp;</div>
		<div class="col-sm-2">
			<button id="btnBack" type="submit" class="btn btn-default btn-block">buscar</button>
		</div>
	</div>
</form:form>
<hr>
<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-md-3">Nombre</th>
			<th class="col-md-2">Deporte</th>
			<th class="col-md-2">Categoria</th>
			<th class="col-md-2">Municipio</th>
			<th class="col-md-3">&nbsp;</th>
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
				<td align="right">
					<button type="button" class="btn btn-default" onclick="fViewCalendar('${competition.id}')" title="calendario">
						<span class="glyphicon glyphicon-calendar"></span>
					</button>
					<button type="button" class="btn btn-default" onclick="fViewClassification('${competition.id}')" title="clasificación">
						<span class="glyphicon glyphicon-list"></span>
					</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>