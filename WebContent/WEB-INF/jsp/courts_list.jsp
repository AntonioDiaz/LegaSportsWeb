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
	
</script>
<h2 style="color: #0061a8">
	${sportCenter.name} 
</h2>
<hr>
<button type="button" class="btn btn-default" onclick="fAddCourt('${sportCenter.id}')" style="width: 200px;">&nbsp;Añadir pista&nbsp;</button>
<table class="table table-hover">
	<thead>
		<tr>
			<th>Nombre Pista</th>
			<th>Deportes</th>
			<th>&nbsp;</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${sportCenter.courtsDeref}" var="court" >
			<tr>
				<td style="vertical-align: middle;">${court.name}</td>
				<td style="vertical-align: middle;">
					<c:forEach items="${court.sportsDeref}" var="sport">
						${sport.name} &nbsp; &nbsp;
					</c:forEach>
				</td>
				<td style="vertical-align: middle;">
					<div class="row">
						<div class="col-sm-6">
							<button type="button" class="btn btn-default btn-block" onclick="fUpdate('${center.id}')">Modificar</button>
						</div>
						<div class="col-sm-6">
							<button type="button" class="btn btn-default btn-block" onclick="fDelete('${center.id}')">Eliminar</button>
						</div>
					</div>
					
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>