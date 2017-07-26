<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>
<script>

	$(document).ready(function() {
		$(document).ready(function() {
			<c:if test="${add_done==true}">
				showDialogAlert("Equipo creado");
			</c:if>
			<c:if test="${update_done==true}">
				showDialogAlert("Equipo actualizado");
			</c:if>
			<c:if test="${delete_done==true}">
				showDialogAlert("Equipo eliminado");
			</c:if>
		});
	});

	function fUpdate(id) {
		window.location.href = "/team/update?id=" + id;
	}

	function fView(id) {
		window.location.href = "/team/view?id=" + id;
	}

	function fDelete(id) {
		var bodyTxt = "Se va a borrar el equipo, ¿desea continuar?";
		showDialogConfirm(bodyTxt,
			function(){
				window.location.href = "/team/doDelete?id=" + id;
			}
		);
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
		<th class="col-sm-2">Nombre equipo</th>
		<th class="col-sm-2">Municipio</th>
		<th class="col-sm-2">Categoria</th>
		<th class="col-sm-2">Deporte</th>
		<th class="col-sm-4">&nbsp;</th>
	</tr>
	</thead>
	<tbody>
	<c:if test="${empty teamList}">
		<c:if test="${teamList==null}">
			<tr>
				<td colspan="10">Realize la búsqueda.</td>
			</tr>
		</c:if>
		<c:if test="${teamList!=null}">
			<tr>
				<td colspan="10">No hay equipos para esta búsqueda.</td>
			</tr>
		</c:if>
	</c:if>
	<c:forEach var="team" items="${teamList}">
		<tr>
			<td style="vertical-align: middle;">${team.name}</td>
			<td style="vertical-align: middle;">${team.townEntity.name}</td>
			<td style="vertical-align: middle;">${team.categoryEntity.name}</td>
			<td style="vertical-align: middle;">${team.sportEntity.name}</td>
			<td>
				<div class="row">
					<div class="col-sm-4">
						<button type="button" class="btn btn-default btn-block" onclick="fView('${team.id}')">Ver info</button>
					</div>
					<div class="col-sm-4">
						<button type="button" class="btn btn-default btn-block" onclick="fUpdate('${team.id}')">Modificar</button>
					</div>
					<div class="col-sm-4">
						<button type="button" class="btn btn-default btn-block" onclick="fDelete('${team.id}')">Eliminar</button>
					</div>
				</div>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
