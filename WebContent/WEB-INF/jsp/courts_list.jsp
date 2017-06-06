<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() { 
		<c:if test="${remove_done==true}">
			showDialogAlert("La pista ha sido borrada.");
		</c:if>
		<c:if test="${update_done==true}">
			showDialogAlert("La pista ha sido actualizada.");
		</c:if>
		<c:if test="${add_done==true}">
			showDialogAlert("Pista creada.");
		</c:if>
	});

	function fAddCourt(idCenter) {
		window.location.href = "/center/addCourt?idCenter=" + idCenter;
	}
	
	function fUpdate(idCourt) {
		window.location.href = "/center/updateCourt?idCourt=" + idCourt;
	}
	
	function fDelete(idCourt, idCenter) {
		var bodyTxt = "Se va a borrar la pista, ¿desea continuar?";
		showDialogConfirm(bodyTxt, 
			function(){ 
				window.location.href = "/center/doDeleteCourt?idCourt=" + idCourt + "&idCenter=" + idCenter; 
			}
		);
	}
	
</script>
<h2 style="color: #0061a8">
	${sportCenter.name} 
</h2>
<hr>
<div align="right">
<button type="button" class="btn btn-default" onclick="fAddCourt('${sportCenter.id}')" style="width: 200px;">&nbsp;Añadir pista&nbsp;</button>
</div>
<table class="table table-hover">
	<thead>
		<tr>
			<th>Nombre Pista</th>
			<th>Deportes</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${courts}" var="court" >
			<tr>
				<td class="col-md-4" style="vertical-align: middle;"><strong>${court.name}</strong></td>
				<td class="col-md-4" style="vertical-align: middle;">
					<c:forEach items="${court.sportsDeref}" var="sport">
						${sport.name} &nbsp; &nbsp;
					</c:forEach>
				</td>
				<td class="col-md-2" style="vertical-align: middle;">
					<button type="button" class="btn btn-default btn-block" onclick="fUpdate('${court.id}')">Modificar</button>
				</td>
				<td class="col-md-2" style="vertical-align: middle;">
					<button type="button" class="btn btn-default btn-block" onclick="fDelete('${court.id}', '${sportCenter.id}')">Eliminar</button>
					
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>