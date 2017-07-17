<%@include file="taglibs.jsp"%>
<script>
	function fUpdate(userName) {
		window.location.href = "/users/update?userName=" + userName;
	}

	function fView(userName) {
		window.location.href = "/users/view?userName=" + userName;
	}

	function fDelete(userName) {
		var bodyTxt = "Se va a borrar el usuario " + userName + ", ¿desea continuar?";
		showDialogConfirm(bodyTxt, 
			function(){ 
				window.location.href = "/users/doDelete?userName=" + userName; 
			}
		);
	}

	$(document).ready(function() { 
		<c:if test="${remove_done==true}">
			showDialogAlert("El usuario ha sido borrado.");
		</c:if>
		<c:if test="${update_done==true}">
			showDialogAlert("El usuario ha sido actualizado.");
		</c:if>
		<c:if test="${add_done==true}">
			showDialogAlert("Usuario creado.");
		</c:if>
	});
</script>
<table class="table table-hover">
	<thead>
		<tr>
			<th>Nombre usuario</th>
			<th>Municipio</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty users}">
			<tr>
				<td colspan="3">No hay usuarios.</td>
			</tr>
		</c:if>
		<c:forEach var="user" items="${users}">
			<tr>
				<td style="vertical-align: middle;">${user.username}</td>
				<td style="vertical-align: middle;">
					<c:if test="${user.admin}">
						Administrador
					</c:if>
					<c:if test="${!user.admin}">
						${user.town.name}
					</c:if>
				</td>
				<td>
					<div class="row">
						<div class="col-sm-4">
							<button type="button" class="btn btn-default btn-block" onclick="fView('${user.username}')">Ver info</button>
						</div>
						<div class="col-sm-4">
							<button type="button" class="btn btn-default btn-block" onclick="fUpdate('${user.username}')">Modificar</button>
						</div>
						<div class="col-sm-4">
							<button type="button" class="btn btn-default btn-block" onclick="fDelete('${user.username}')">Eliminar</button>
						</div>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
