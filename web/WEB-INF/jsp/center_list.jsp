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
		window.location.href = "/sportCenter/update?id=" + centerId;
	}

	function fView(centerId) {
		window.location.href = "/sportCenter/view?id=" + centerId;
	}
	
	function fViewCourts(centerId) {
		window.location.href = "/sportCenter/listCourts?idSportCenter=" + centerId;
	}
	
	function fDelete(centerId) {
		var bodyTxt = "Se va a borrar el centro, ¿desea continuar?";
		showDialogConfirm(bodyTxt, 
			function(){ 
				window.location.href = "/sportCenter/doDelete?id=" + centerId;
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
		<c:forEach var="sportCenter" items="${centers}">
			<tr>
				<td style="vertical-align: middle;">${sportCenter.name}</td>
				<td style="vertical-align: middle;">${sportCenter.townEntity.name}</td>
				<td>
					<div class="row">
						<div class="col-sm-3">
							<button type="button" class="btn btn-default btn-block" onclick="fViewCourts('${sportCenter.id}')">Pistas</button>
						</div>
						<div class="col-sm-3">
							<button type="button" class="btn btn-default btn-block" onclick="fView('${sportCenter.id}')">Ver info</button>
						</div>
						<div class="col-sm-3">
							<button type="button" class="btn btn-default btn-block" onclick="fUpdate('${sportCenter.id}')">Modificar</button>
						</div>
						<div class="col-sm-3">
							<button type="button" class="btn btn-default btn-block" onclick="fDelete('${sportCenter.id}')">Eliminar</button>
						</div>
					</div>
				</td>
			</tr>			
		</c:forEach>
	</tbody>
</table>
