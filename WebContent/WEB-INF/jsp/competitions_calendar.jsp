<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() { });
	
	/* */
	function fLoadCalendar(idCompetition) {
		window.location.href = "/competitions/loadCalendar?idCompetition=" + idCompetition;
	}		
</script>
<h2 style="color: #0061a8">
	Calendario: ${competition.name}  (${competition.sportEntity.name} - ${competition.categoryEntity.name})
</h2>
<hr>
<c:if test="${empty matches_list}">
	<button type="button" class="btn btn-primary" id="fLoadCalendar" onclick="fLoadCalendar('${competition.id}')">cargar calendario</button>
	<br>
</c:if>
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