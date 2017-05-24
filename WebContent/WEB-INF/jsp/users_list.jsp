<%@include file="taglibs.jsp"%>
<script>
	function fUpdate(userName) {
		window.location.href = "/users/update?userName=" + userName;
	}
	
	function fDelete(userName) {
		if (confirm("Se va a borrar el usuario " + userName + ", ¿desea continuar?")) {
			window.location.href = "/users/doDelete?userName=" + userName;
		}
	}	
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
				<td style="vertical-align: middle;">municipio</td>
				<td>
					<div class="row">
						<div class="col-sm-6">
							<button type="button" class="btn btn-primary btn-block" onclick="fUpdate('${user.username}')">Modificar</button>
						</div>
						<div class="col-sm-6">
							<button type="button" class="btn btn-warning btn-block" onclick="fDelete('${user.username}')">Eliminar</button>
						</div>
					</div>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
