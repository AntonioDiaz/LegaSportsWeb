<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>
<script>

	$(document).ready(function() {
		<c:if test="${add_done==true}">
			showDialogAlert("La competición ha sido creada.");
		</c:if>
		<c:if test="${remove_done==true}">
			showDialogAlert("La competición ha sido eliminada.");
		</c:if>
		<c:if test="${update_done==true}">
			showDialogAlert("La competición ha sido actualizada.");
		</c:if>
	});

	function fViewCalendar (idCompetition) {
		window.location.href = "/competitions/viewCalendar?idCompetition=" + idCompetition;
	}

	function fViewClassification (idCompetition) {
		window.location.href = "/competitions/viewClassification?idCompetition=" + idCompetition;
	}

	function fUpdate (idCompetition) {
		window.location.href = "/competitions/update?idCompetition=" + idCompetition;
	}

	function fView (idCompetition) {
		window.location.href = "/competitions/view?idCompetition=" + idCompetition;
	}

	function fRemove (idCompetition) {
		var bodyTxt = "¿Se va a borrar la competición y todos sus partidos desea continuar?";
		showDialogConfirm(bodyTxt,
			function(){
				window.location.href = "/competitions/doRemove?idCompetition=" + idCompetition;
			}
		);
	}


</script>
<form:form method="post" action="doFilter" commandName="form_filter" cssClass="form-horizontal">
	<div class="row">
		<div class="col-md-10">
			<div class="row">
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-sm-4" for="idSport" style="text-align: left;">Deporte</label>
						<div class="col-sm-8">
							<form:select path="idSport" class="form-control">
								<form:option value=""></form:option>
								<form:options items="${sports}" itemLabel="name" itemValue="id" />
							</form:select>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-sm-4" for="idCategory">Categoria</label>
						<div class="col-sm-8">
							<form:select path="idCategory" class="form-control">
								<form:option value=""></form:option>
								<form:options items="${categories}" itemLabel="name" itemValue="id" />
							</form:select>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<label class="control-label col-sm-4" for="idTown">Municipio</label>
						<div class="col-sm-8">
							<form:select path="idTown" class="form-control">
								<form:option value=""></form:option>
								<form:options items="${towns}" itemLabel="name" itemValue="id" />
							</form:select>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-2" align="right">
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
					<button type="button" class="btn btn-default" onclick="fViewCalendar('${competition.id}')" title=calendario">
						<span class="glyphicon glyphicon-calendar"></span>
					</button>
					<button type="button" class="btn btn-default" onclick="fViewClassification('${competition.id}')" title="clasificación">
						<span class="glyphicon glyphicon-list"></span>
					</button>
					<button type="button" class="btn btn-default" onclick="fView('${competition.id}')" title="ver">
						<span class="glyphicon glyphicon-eye-open"></span>
					</button>
					<button type="button" class="btn btn-default" onclick="fUpdate('${competition.id}')" title="actualizar">
						<span class="glyphicon glyphicon-pencil"></span>
					</button>
					<button type="button" class="btn btn-default" onclick="fRemove('${competition.id}')" title="borrar">
						<span class="glyphicon glyphicon-remove"></span>
					</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>