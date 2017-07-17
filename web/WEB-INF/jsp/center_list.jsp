<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>
<script>
	
	$(document).ready(function() { 
		<c:if test="${remove_done==true}">
			showDialogAlert("El centro ha sido borrado.");
		</c:if>
		<c:if test="${update_done==true}">
			showDialogAlert("El centro ha sido actualizado.");
		</c:if>
		<c:if test="${add_done==true}">
			showDialogAlert("Centro creado.");
		</c:if>
	});
	
	function fUpdate(centerId) {
		window.location.href = "/center/update?id=" + centerId;
	}

	function fView(centerId) {
		window.location.href = "/center/view?id=" + centerId;
	}
	
	function fViewCourts(centerId) {
		window.location.href = "/center/listCourts?idSportCenter=" + centerId;
	}
	
	function fDelete(centerId) {
		var bodyTxt = "Se va a borrar el centro, ¿desea continuar?";
		showDialogConfirm(bodyTxt, 
			function(){ 
				window.location.href = "/center/doDelete?id=" + centerId;
			}
		);
	}
	
</script>
<table class="table table-hover">
	<thead>
		<tr>
			<th>Nombre centro</th>
			<th>Municipio</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${empty centers}">
			<tr>
				<td colspan="3">No hay centros registrados.</td>
			</tr>
		</c:if>
		<c:forEach var="center" items="${centers}">
			<tr>
				<td style="vertical-align: middle;">${center.name}</td>
				<td style="vertical-align: middle;">${center.town.name}</td>
				<td>
					<div class="row">
						<div class="col-sm-3">
							<button type="button" class="btn btn-default btn-block" onclick="fViewCourts('${center.id}')">Pistas</button>
						</div>
						<div class="col-sm-3">
							<button type="button" class="btn btn-default btn-block" onclick="fView('${center.id}')">Ver info</button>
						</div>
						<div class="col-sm-3">
							<button type="button" class="btn btn-default btn-block" onclick="fUpdate('${center.id}')">Modificar</button>
						</div>
						<div class="col-sm-3">
							<button type="button" class="btn btn-default btn-block" onclick="fDelete('${center.id}')">Eliminar</button>
						</div>
					</div>
				</td>
			</tr>			
		</c:forEach>
	</tbody>
</table>
