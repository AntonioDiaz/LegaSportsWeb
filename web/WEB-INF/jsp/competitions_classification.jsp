<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/viewCalendar?idCompetition=${competition.id}";
		});
	});
	
</script>
<div class="row" style="position: relative">
	<div class="col-sm-10">
		<div class="font_title">
			<div>${competition.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-2"><small>Deporte</small></div>
			<div>${competition.sportEntity.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-2"><small>Categoria</small></div>
			<div>${competition.categoryEntity.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-2"><small>Municipio</small></div>
			<div>${competition.townEntity.name}</div>
		</div>
	</div>
	<div class="col-sm-2" style="position: absolute; bottom: 0; right: 0; margin-bottom: 0;">
		<button type="button" class="btn btn-default btn-block" id="btnBack">
			volver
		</button>
	</div>
</div>
<hr>
<table class="table table-hover table-condensed">
	<thead>
	<tr>
	 	<th class="col-sm-3">Posición</th>
	 	<th class="col-sm-3">Equipo</th>
	 	<th class="col-sm-3">Puntos</th>
	 	<th class="col-sm-1">PJ</th>
	 	<th class="col-sm-1">PG</th>
	 	<th class="col-sm-1">PE</th>
	 	<th class="col-sm-1">PP</th>
	</tr> 	
	</thead>
	<c:forEach var="entry" items="${classification_list}">
		<tbody>
	 	<tr>
		 	<td class="col-sm-4">${entry.position}</td>
		 	<td class="col-sm-4">${entry.team}</td>
		 	<td class="col-sm-4">${entry.points}</td>
		 	<td class="col-sm-4">${entry.matchesPlayed}</td>
		 	<td class="col-sm-4">${entry.matchesWon}</td>
		 	<td class="col-sm-4">${entry.matchesDrawn}</td>
		 	<td class="col-sm-4">${entry.matchesLost}</td>
	 	</tr> 
	 	</tbody>
	</c:forEach>
</table>