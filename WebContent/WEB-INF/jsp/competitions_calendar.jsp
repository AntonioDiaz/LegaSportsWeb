<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="headers.jsp"%>
<script>
		$(document).ready(function() { });
		
		/* */
		function fLoadCalendar(idCompetition) {
			window.location.href = "/competitions/loadCalendar?idCompetition=" + idCompetition;
		}		
	</script>
</head>
<body>
	<%@include file="navbar.jsp"%>
	<div class="container">
	
		<h1>Calendario: ${competition.name}  (${competition.sportEntity.name} - ${competition.categoryEntity.name})</h1>
		<c:if test="${empty matches_list}">
			<button type="button" class="btn btn-primary" id="fLoadCalendar" onclick="fLoadCalendar('${competition.id}')">cargar calendario</button>
		</c:if>
		<br>
		<c:set var="previous_week" value="-1"></c:set>
		<c:forEach var="match" items="${matches_list}">
			<c:if test="${match.week!=previous_week}">
				<c:if test="${match.week!=-1}">
					</table>
				</c:if>	
				<h3>Jornada ${match.week}</h3>
			 	<table class="table table-hover table-condensed">
			</c:if>
			 	<tr>
				 	<td class="col-sm-2"><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${match.date}" /></td>
				 	<td class="col-sm-3">${match.teamLocal}</td>
				 	<td class="col-sm-3">${match.teamVisitor}</td>
				 	<td class="col-sm-2">${match.scoreLocal} - ${match.scoreVisitor}</td>
				 	<td class="col-sm-2">${match.place}&nbsp;</td>
			 	</tr> 
			<c:set var="previous_week" value="${match.week}"></c:set>
		</c:forEach>
	</div>
</body>
</html>
