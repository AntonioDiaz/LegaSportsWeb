<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file="headers.jsp"%>
	<script>
		$(document).ready(function() {						
			$("#fAddCompetition").click(function(){ 
				window.location.href = "/competitions/add"; 
			});
		});	
		
		function fViewCalendar(idCompetition) {
			window.location.href = "/competitions/viewCalendar?idCompetition=" + idCompetition;
		}
		
		function fViewClassificacion(idCompetition) {
			window.location.href = "/competitions/viewClassification?idCompetition=" + idCompetition;
		}

		function fRemoveCalendar(idCompetition) {
			var retVal = confirm("¿Se va a borrar la competición y todos sus partidos desea continuar?");
			if (retVal) {
				window.location.href = "/competitions/doRemove?idCompetition=" + idCompetition;
			} 			
		}
		
		
	</script>
</head>
<body>
	<%@include file="navbar.jsp"%>
	<div class="container">
		<table style="width: 100%">
			<tr>
				<td><h1>Lista de competiciones:</h1></td>
				<td align="right"><button type="button" class="btn btn-primary" id="fAddCompetition">nueva competición</button></td>
			</tr>
		</table>
		<table class="table table-hover">
	    <thead>
	      <tr>
	        <th>Competición</th>
	        <th>Deporte</th>
	        <th>Categoria</th>
	        <th>&nbsp;</th>
	      </tr>
	    </thead>
	    <tbody>
			<c:forEach var="competition" items="${competitions_list}">
				<tr>
					<td>
						${competition.name}
					</td>
					<td>
						${competition.sportEntity.name}
					</td>
					<td>
						${competition.categoryEntity.name}
					</td>
					<td>
						<button type="button" class="btn btn-primary" onclick="fViewCalendar('${competition.id}')">Calendario</button>
						<button type="button" class="btn btn-primary" onclick="fViewClassificacion('${competition.id}')">Clasificación</button>
						<button type="button" class="btn btn-warning" onclick="fRemoveCalendar('${competition.id}')">Eliminar</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</div>
</body>
</html>
