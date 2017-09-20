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
		<c:if test="${remove_undone==true}">
			showDialogAlert("No se puede realizar el borrado, compruebe que no haya referencias a esta entidad desde otras.");
		</c:if>

	});

	function fAddCourt(idCenter) {
		window.location.href = "/sportCenter/addCourt?idCenter=" + idCenter;
	}
	
	function fUpdate(idCourt) {
		window.location.href = "/sportCenter/updateCourt?idCourt=" + idCourt;
	}
	
	function fDelete(idCourt, idCenter) {
		var bodyTxt = "Se va a borrar la pista, �desea continuar?";
		showDialogConfirm(bodyTxt, 
			function(){ 
				window.location.href = "/sportCenter/doDeleteCourt?idCourt=" + idCourt + "&idCenter=" + idCenter;
			}
		);
	}
	
</script>
<h2 class="munisport-title">
	${sportCenter.name} 
</h2>
<hr>
<div align="right">
<button type="button" class="btn btn-default" onclick="fAddCourt('${sportCenter.id}')" style="width: 200px;">&nbsp;A�adir pista&nbsp;</button>
</div>
<table class="table table-hover">
	<thead>
		<tr>
			<th class="col-md-4">Nombre Pista</th>
			<th class="col-md-4">Deportes</th>
			<th class="col-md-2">&nbsp;</th>
			<th class="col-md-2">&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${courts}" var="court" >
			<tr>
				<td style="vertical-align: middle;"><strong>${court.name}</strong></td>
				<td style="vertical-align: middle;">
					<c:forEach items="${court.sportsDeref}" var="sportRef">
						${sportRef.name} &nbsp; &nbsp;
					</c:forEach>
				</td>
				<td style="vertical-align: middle;">
					<button type="button" class="btn btn-default btn-block" onclick="fUpdate('${court.id}')">Modificar</button>
				</td>
				<td style="vertical-align: middle;">
					<button type="button" class="btn btn-default btn-block" onclick="fDelete('${court.id}', '${sportCenter.id}')">Eliminar</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>