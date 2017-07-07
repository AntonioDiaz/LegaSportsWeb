<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() { });
	
	/* */
	function fLoadCalendar(idCompetition) {
		window.location.href = "/competitions/loadCalendar?idCompetition=" + idCompetition;
	}

	/* show the div with the week selected, if none is selected show every week. */
	function fUpdateWeekCalendar(){
	    if ($("#week_selection").val()) {
            $("div[name^='week_id_']").each(function () {
                $(this).hide();
            });
            var weekSelected = 'week_id_' + $("#week_selection").val();
            $("div[name=" + weekSelected + "]").show();
        } else {
            $("div[name^='week_id_']").each(function () {
                $(this).show();
            });
        }
    }

</script>
<h2 style="color: #0061a8">
	Calendario: ${competition.name}  (${competition.sportEntity.name} - ${competition.categoryEntity.name})
</h2>
<select id="week_selection" class="form-control" style="width: 200px;" onchange="fUpdateWeekCalendar()">
    <option></option>
    <c:forEach begin="1" end="${weeks_count}" varStatus="loop">
        <option value="${loop.index}">Jornada ${loop.index}</option>
    </c:forEach>
</select>
<hr>
<c:if test="${empty matches_list}">
	<div align="right">
		<button type="button" class="btn btn-default" id="fLoadCalendar" onclick="fLoadCalendar('${competition.id}')" style="width: 200px;">
			cargar calendario
		</button>
	</div>		
	<br>
</c:if>
<c:set var="previous_week" value="-1"></c:set>
<c:forEach var="match" items="${matches_list}">
	<c:if test="${match.week!=previous_week}">
		<c:if test="${previous_week!=-1}">
			</table>
            </div>
		</c:if>
        <div name="week_id_${match.week}">
		<h3>Jornada ${match.week}</h3>
	 	<table class="table table-striped table-condensed">
	</c:if>
 	<tr>
	 	<td class="col-sm-2"><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${match.date}" /></td>
	 	<td class="col-sm-3">${match.teamLocal}</td>
	 	<td class="col-sm-3">${match.teamVisitor}</td>
	 	<td class="col-sm-2">${match.scoreLocal} - ${match.scoreVisitor}</td>
	 	<td class="col-sm-2" class="tdlink">${match.place}&nbsp;</td>
 	</tr> 
	<c:set var="previous_week" value="${match.week}"></c:set>
</c:forEach>