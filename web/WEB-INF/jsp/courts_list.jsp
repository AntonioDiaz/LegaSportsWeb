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
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/centers/list";
		});
	});

	function fAddCourt(idCenter) {
		window.location.href = "/courts/add?idCenter=" + idCenter;
	}
	
	function fUpdate(idCourt) {
		window.location.href = "/courts/update?idCourt=" + idCourt;
	}
	
	function fDelete(idCourt, idCenter) {
		var bodyTxt = "Se va a borrar la pista, ¿desea continuar?";
		showDialogConfirm(bodyTxt, 
			function(){ 
				window.location.href = "/courts/doDelete?idCourt=" + idCourt + "&idCenter=" + idCenter;
			}
		);
	}
	
</script>
<div class="row" style="position: relative">
	<div class="col-sm-8">
		<div class="font_title">
			Centro deportivo: ${centerSession.name} (${centerSession.townEntity.name})
		</div>
	</div>
	<div class="col-sm-4">
		<div class="row">
			<div class="col-sm-6">&nbsp;</div>
			<div class="col-sm-6">
				<button type="button" class="btn btn-default btn-block" id="btnBack">
					volver
				</button>
				<button type="button" class="btn btn-default btn-block" onclick="fAddCourt('${centerSession.id}')" >
					nueva pista
				</button>
			</div>
		</div>
	</div>
</div>
<hr>
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
					<button type="button" class="btn btn-default btn-block" onclick="fDelete('${court.id}', '${center.id}')">Eliminar</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>