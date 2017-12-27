<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		<c:if test="${add_done==true}">
			showDialogAlert("El deporte ha sido creado.");
		</c:if>
		<c:if test="${remove_done==true}">
			showDialogAlert("El deporte ha sido eliminado.");
		</c:if>
		<c:if test="${update_done==true}">
			showDialogAlert("El deporte ha sido actualizado.");
		</c:if>
		<c:if test="${remove_undone==true}">
			showDialogAlert("No se puede realizar el borrado, compruebe que no haya referencias a esta entidad desde otras.");
		</c:if>
	});

	function fUpdate(id) {
		window.location.href = "/sports/update?id=" + id;
	}

	function fView(id) {
		window.location.href = "/sports/view?id=" + id;
	}

	function fDelete(id) {
		var bodyTxt = "Se va a borrar el deporte, ¿desea continuar?";
		showDialogConfirm(bodyTxt,
			function(){
				window.location.href = "/sports/doDelete?id=" + id;
			}
		);
	}
</script>

<div class="row">
	<div class="col-sm-10">
		<table class="table table-hover">
			<thead>
			<tr>
				<th class="col-md-1">Orden</th>
				<th class="col-md-3">Nombre</th>
				<th class="col-md-3">Etiqueta</th>
				<th class="col-md-5">&nbsp;</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach var="sport" items="${sports}">
				<tr>
					<td style="vertical-align: middle;">${sport.order}</td>
					<td style="vertical-align: middle;"><strong>${sport.name}</strong></td>
					<td style="vertical-align: middle;">${sport.tag}</td>
					<td align="right">
						<button type="button" class="btn btn-default" onclick="fView('${sport.id}')" title="ver detalle">
							Ver detalle &nbsp;<span class="glyphicon glyphicon-eye-open"></span>
						</button>
						<button type="button" class="btn btn-default" onclick="fUpdate('${sport.id}')" title="modificar">
							Modificar &nbsp;<span class="glyphicon glyphicon-edit"></span>
						</button>
						<button type="button" class="btn btn-default" onclick="fDelete('${sport.id}')" title="eliminar">
							Eliminar &nbsp;<span class="glyphicon glyphicon-remove"></span>
						</button>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>