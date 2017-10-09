<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/viewCalendar";
		});
		$('#btnAddSanction').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/addSanction";
		});
		$('#btnUpdate').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/updateClassification";
		});
		$('#btnPrint').on('click', function(event) {
			event.preventDefault();
			window.print();
		});

	});
	
</script>
<div id="id_classification" class="row" style="position: relative">
	<div class="col-sm-8">
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
	<div class="col-sm-2 no-print">
		<button type="button" class="btn btn-default btn-block" id="btnPrint">
			imprimir
		</button>
	</div>
	<div class="col-sm-2 no-print">
		<button type="button" class="btn btn-default btn-block" id="btnBack">
			volver
		</button>
		<button type="button" class="btn btn-default btn-block" id="btnAddSanction">
			sanción
		</button>
		<button type="button" class="btn btn-default btn-block" id="btnUpdate">
			actualizar
		</button>
	</div>
</div>
<hr>
<h3>Clasificación</h3>
<div class="row">
	<div class="col-sm-7">
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
				<th title="Puntos antes sanción">PT</th>
				<th title="Sanciones">SA</th>
				<th title="Puntos Finales">PF</th>
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
					<td>${entry.sanctions}</td>
					<td>${entry.points - entry.sanctions}</td>
				</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
	<div class="col-sm-5" style="padding-left: 20px;">
		<small>
		<div class="paragraph_normal">Sanciones</div>
		<ul class="list-group">
			<c:if test="${empty sanctions_list}">
				<li class="list-group-item">No hay sanciones en esta competición.</li>
			</c:if>
			<c:forEach items="${sanctions_list}" var="sanction">
				<li class="list-group-item">${sanction.team.name}: ${sanction.description} <span class="badge">${sanction.points}</span></li>
			</c:forEach>
		</ul>
		</small>
	</div>
</div>