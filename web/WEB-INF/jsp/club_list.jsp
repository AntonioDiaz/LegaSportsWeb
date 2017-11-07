<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>
<script>

	$(document).ready(function() {
		<c:if test="${add_done==true}">
			showDialogAlert("Club creado");
		</c:if>
		<c:if test="${update_done==true}">
			showDialogAlert("Club actualizado");
		</c:if>
		<c:if test="${remove_done==true}">
			showDialogAlert("Club eliminado");
		</c:if>
		<c:if test="${remove_undone==true}">
			showDialogAlert("No se puede realizar el borrado, compruebe que no haya referencias a esta entidad desde otras.");
		</c:if>
	});

	function fUpdate(id) {
		window.location.href = "/club/update?id=" + id;
	}

	function fView(id) {
		window.location.href = "/club/view?id=" + id;
	}

	function fDelete(id) {
		var bodyTxt = "Se va a borrar el club, ¿desea continuar?";
		showDialogConfirm(bodyTxt,
			function(){
				window.location.href = "/club/doDelete?id=" + id;
			}
		);
	}

</script>
<table class="table table-hover">
	<thead>
	<tr>
		<th>Nombre club</th>
		<th>Municipio</th>
		<th>&nbsp;</th>
	</tr>
	</thead>
	<tbody>
	<c:if test="${empty clubList}">
		<tr>
			<td colspan="3">No hay clubs registrados.</td>
		</tr>
	</c:if>
	<c:forEach var="club" items="${clubList}">
		<tr>
			<td style="vertical-align: middle;">${club.name}</td>
			<td style="vertical-align: middle;">${club.townEntity.name}</td>
			<td>
				<div class="row">
					<div class="col-sm-3">&nbsp;</div>
					<div class="col-sm-3">
						<button type="button" class="btn btn-default btn-block" onclick="fView('${club.id}')">Ver info</button>
					</div>
					<div class="col-sm-3">
						<button type="button" class="btn btn-default btn-block" onclick="fUpdate('${club.id}')">Modificar</button>
					</div>
					<div class="col-sm-3">
						<button type="button" class="btn btn-default btn-block" onclick="fDelete('${club.id}')">Eliminar</button>
					</div>
				</div>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
