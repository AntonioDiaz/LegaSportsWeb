<%@include file="taglibs.jsp"%>
<!-- Modal -->
<div id="updateScorePopup" class="modal fade" role="dialog">
    <div class="modal-dialog modal-sm" style="width: 40%">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title" id="modalTitle">MuniSports 2017</h4>
            </div>
            <div class="modal-body">
                <div>
                    <div class="row">
                        <div class="col-sm-6">
                            <p id="scoreLocalTeam" style="font-size: 16px;">Local</p>
                        </div>
                        <div class="col-sm-6">
                            <p id="scoreVisitorTeam" style="font-size: 16px;">Visitante</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6">
                            <input class="form-control" id="scoreLocal" type="number">
                        </div>
                        <div class="col-sm-6">
                            <input class="form-control" id="scoreVisitor" type="number">
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div id="buttons_confirm" class="row">
                    <div class="col-sm-6">&nbsp;</div>
                    <div class="col-sm-3">
                        <button type="button" class="btn btn-default btn-block" data-dismiss="modal">Cancelar</button>
                    </div>
                    <div class="col-sm-3">
                        <button id="score_button_accept" type="button" class="btn btn-default btn-block" data-dismiss="modal">Aceptar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

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

    function fUpdateScore(indexArray) {
        var matchSelected = matchesArray[indexArray];
	    var idMatch = matchSelected.id;
        var local = matchSelected.teamLocal;
        var visitor = matchSelected.teamVisitor;
        var scoreLocal = matchSelected.scoreLocal;
        var scoreVisitor = matchSelected.scoreVisitor;
        /* init popup values */
        $('#scoreLocalTeam').html(local);
        $('#scoreVisitorTeam').html(visitor);
        $('#scoreLocal').val(scoreLocal);
        $('#scoreVisitor').val(scoreVisitor);
        $('#updateScorePopup').modal('show');
        $('#score_button_accept').off('click');
        $("#score_button_accept").click(function(){
            matchSelected.scoreLocal = $('#scoreLocal').val();
            matchSelected.scoreVisitor = $('#scoreVisitor').val();
            console.log(matchSelected);
            console.log(JSON.stringify(matchSelected));

            $.ajax({
                url: '/server/match/' + idMatch,
                type: 'PUT',
                data: JSON.stringify(matchSelected),
                contentType: "application/json",
                success: function(result) {
                    matchesArray[indexArray] = result;
                    updateMatchProperties(indexArray)
                }
            });
        });
    }

    function updateMatchProperties(indexArray) {
        var matchSelected = matchesArray[indexArray];
        $("#score_" + matchSelected.id).html(matchSelected.scoreLocal + " - " + matchSelected.scoreVisitor);
    }

    $('#updateScorePopup').on('shown.bs.modal', function () {
        $(this).find("input:visible:first").focus();
    });

    var matchesArray = [];
    <c:forEach var="match" items="${matches_list}" varStatus="loop">
        matchesArray[${loop.index}] = {
            id:${match.id},
            teamLocal: "${match.teamLocal}",
            teamVisitor:"${match.teamVisitor}",
            scoreLocal:${match.scoreLocal},
            scoreVisitor:${match.scoreVisitor},
            scorePlace:"${match.place}"
        }
    </c:forEach>
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
<c:forEach var="match" items="${matches_list}" varStatus="loop">
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
	 	<td class="col-sm-2" id="date_${match.id}"><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${match.date}" /></td>
	 	<td class="col-sm-3">${match.teamLocal}</td>
	 	<td class="col-sm-3">${match.teamVisitor}</td>
		<td class="col-sm-2">
			<div id="score_${match.id}" class="divlink" onclick="fUpdateScore(${loop.index})">
                    ${match.scoreLocal} - ${match.scoreVisitor}
            </div>
		</td>
	 	<td id="place_${match.id}" class="col-sm-2" class="tdlink">${match.place}&nbsp;</td>
 	</tr> 
	<c:set var="previous_week" value="${match.week}"></c:set>
</c:forEach>
