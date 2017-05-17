<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="headers.jsp"%>
<script>
		$(document).ready(function() { });
		
		/* */
		function fLoadClassification(idCompetition) {
			window.location.href = "/competitions/loadClassification?idCompetition=" + idCompetition;
		}		
	</script>
</head>
<body>
	<%@include file="navbar.jsp"%>
	<div class="container">	
		<h1>Classificación: ${competition.name}  (${competition.sportEntity.name} - ${competition.categoryEntity.name})</h1>
		<c:if test="${empty classification_list}">
			<button type="button" class="btn btn-primary" id="fLoadClassification" onclick="fLoadClassification('${competition.id}')">cargar clasificación</button>
		</c:if>
		<br>
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
	</div>
</body>
</html>
