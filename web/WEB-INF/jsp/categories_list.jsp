<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		<c:if test="${add_done==true}">
			showDialogAlert("La categoria ha sido creada.");
		</c:if>
		<c:if test="${remove_done==true}">
			showDialogAlert("La categoria ha sido eliminada.");
		</c:if>
		<c:if test="${update_done==true}">
			showDialogAlert("La categoria ha sido actualizada.");
		</c:if>
	});

	function fUpdate(id) {
		window.location.href = "/categories/update?idCategory=" + id;
	}

	function fView(id) {
		window.location.href = "/categories/view?idCategory=" + id;
	}

	function fDelete(id) {
		var bodyTxt = "Se va a borrar la categoría, ¿desea continuar?";
		showDialogConfirm(bodyTxt,
			function(){
				window.location.href = "/categories/doDelete?id=" + id;
			}
		);
	}
</script>

<div class="row">
	<div class="col-sm-8">
		<table class="table table-hover">
		<thead>
		<tr>
			<th class="col-md-1">Orden</th>
			<th class="col-md-4">Nombre</th>
			<th class="col-md-6">&nbsp;</th>
		</tr>
		</thead>
		<tbody>
		<c:if test="${empty categories}">
			<c:if test="${categories==null}">
				<tr>
					<td colspan="10">Realize la búsqueda.</td>
				</tr>
			</c:if>
			<c:if test="${categories!=null}">
				<tr>
					<td colspan="10">No hay categorias registradas.</td>
				</tr>
			</c:if>
		</c:if>
		<c:forEach var="category" items="${categories}">
			<tr>
				<td style="vertical-align: middle;">${category.order}</td>
				<td style="vertical-align: middle;"><strong>${category.name}</strong></td>
				<td align="right">

					<button type="button" class="btn btn-default" onclick="fView('${category.id}')" title="ver detalle">
						Ver detalle &nbsp; &nbsp;<span class="glyphicon glyphicon-eye-open"></span>
					</button>
					<button type="button" class="btn btn-default" onclick="fUpdate('${category.id}')" title="modificar">
						Modificar &nbsp; &nbsp;<span class="glyphicon glyphicon-edit"></span>
					</button>
					<button type="button" class="btn btn-default" onclick="fDelete('${category.id}')" title="eliminar">
						Eliminar &nbsp; &nbsp;<span class="glyphicon glyphicon-remove"></span>
					</button>
				</td>
			</tr>
		</c:forEach>
		</tbody>
		</table>
	</div>
</div>