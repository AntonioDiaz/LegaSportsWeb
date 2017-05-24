<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() { });
	
	/* */
	function fLoadClassification(idCompetition) {
		window.location.href = "/competitions/loadClassification?idCompetition=" + idCompetition;
	}		
</script>
<h2 style="color: #0061a8">
	Calendario: ${competition.name}  (${competition.sportEntity.name} - ${competition.categoryEntity.name})
</h2>
<hr>
<c:if test="${empty classification_list}">
	<button type="button" class="btn btn-primary" id="fLoadClassification" onclick="fLoadClassification('${competition.id}')">cargar clasificación</button>
	<br>
</c:if>
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