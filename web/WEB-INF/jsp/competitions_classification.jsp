<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/viewCalendar";
		});
	});
	
</script>
<div class="row" style="position: relative">
	<div class="col-sm-10">
		<div class="font_title">
			<div><u>${competition_session.name}</u></div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-2"><small>Deporte</small></div>
			<div>${competition_session.sportEntity.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-2"><small>Categoria</small></div>
			<div>${competition_session.categoryEntity.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-2"><small>Municipio</small></div>
			<div>${competition_session.townEntity.name}</div>
		</div>
	</div>
	<div class="col-sm-2" style="position: absolute; bottom: 0; right: 0; margin-bottom: 0;">
		<button type="button" class="btn btn-default btn-block" id="btnBack">
			volver
		</button>
	</div>
</div>
<hr>
<div class="row">
	<div class="col-sm-6">
		<table id="classificationTable" class="table table-hover table-condensed">
			<thead>
			<tr>
				<th>&nbsp;</th>
				<th style="text-align: left">Equipo</th>
				<th title="Partidos Jugados">PJ</th>
				<th title="Partidos Ganados">PG</th>
				<th title="Partidos Empatados">PE</th>
				<th title="Partidos Perdidos">PP</th>
				<th title="Tantos a Favor">TF</th>
				<th title="Tantos en Contra">TC</th>
				<th title="Diferencia de tantos">DT</th>
				<th>Puntos</th>
			</tr>
			</thead>
			<c:forEach var="entry" items="${classification_list}">
				<tbody>
				<tr>
					<td>${entry.position}</td>
					<td style="white-space: nowrap; text-align: left">${entry.teamEntity.name}</td>
					<td>${entry.matchesPlayed}</td>
					<td>${entry.matchesWon}</td>
					<td>${entry.matchesDrawn}</td>
					<td>${entry.matchesLost}</td>
					<td>${entry.goalsFor}</td>
					<td>${entry.goalsAgainst}</td>
					<td>${entry.goalsFor - entry.goalsAgainst}</td>
					<td>${entry.points}</td>
				</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
	<div class="col-sm-6">

	</div>

</div>
