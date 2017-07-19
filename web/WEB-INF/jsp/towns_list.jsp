<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>

<script>
	$(document).ready(function() {
		<c:if test="${add_done==true}">
		showDialogAlert("El municipio ha sido creado.");
		</c:if>
		<c:if test="${remove_done==true}">
		showDialogAlert("El municipio ha sido eliminado.");
		</c:if>
		<c:if test="${update_done==true}">
		showDialogAlert("El municipio ha sido actualizado.");
		</c:if>
	});

	function fDelete(id, name) {
		var bodyTxt = "¿Se va a borrar el municipio: " + name + ", desea continuar?";
		showDialogConfirm(bodyTxt, function () {
			window.location.href = "/towns/delete?idTown=" + id;
		});
	}

	function fUpdate(id) {
		window.location.href = "/towns/update?idTown=" + id;
	}

	function fView(id) {
		window.location.href = "/towns/view?idTown=" + id;
	}

</script>
<table class="table table-hover">
	<thead>
	<tr>
		<th>Municipio</th>
		<th>Responsable</th>
		<th>&nbsp;</th>
	</tr>
	</thead>
	<tbody>
	<c:if test="${empty towns}">
		<tr>
			<td colspan="3">No hay municipios.</td>
		</tr>
	</c:if>
	<c:forEach var="townEntity" items="${towns}">
		<tr>
			<td style="vertical-align: middle;">${townEntity.name}</td>
			<td style="vertical-align: middle;">${townEntity.contactPerson}</td>
			<td>
				<div class="row">
					<div class="col-sm-4">
						<button type="button" class="btn btn-default btn-block" onclick="fView('${townEntity.id}')">Ver info</button>
					</div>
					<div class="col-sm-4">
						<button type="button" class="btn btn-default btn-block" onclick="fUpdate('${townEntity.id}')">Modificar</button>
					</div>
					<div class="col-sm-4">
						<button type="button" class="btn btn-default btn-block" onclick="fDelete('${townEntity.id}', '${townEntity.name}')">Eliminar</button>
					</div>
				</div>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
