<%@include file="taglibs.jsp" %>
<!-- Modal -->
<div id="updatePopup" class="modal fade" role="dialog">
	<div class="modal-dialog modal-sm" style="width: 45%">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header modal-header-munisports">
				<button type="button" class="close-munisports close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="modalTitle">MuniSports 2017</h4>
			</div>
			<div class="modal-body modal-body-munisports">
				<div class="modal-row-munisports-title">
					<u>
						<spam>Jornada <spam id="weekNumber"></spam>:</spam>
						${competition.name} <small>(${competition.sportEntity.name} - ${competition.categoryEntity.name})</small>
					</u>
				</div>
				<div class="row modal-row-munisports">
					<div class="col-sm-4">Equipo local</div>
					<div class="col-sm-8">
						<spam id="scoreLocalTeam">Local</spam>
					</div>
				</div>
				<div class="row modal-row-munisports">
					<div class="col-sm-4">Equipo visitante</div>
					<div class="col-sm-8">
						<spam id="scoreVisitorTeam">Visitante</spam>
					</div>
				</div>
				<div class="row modal-row-munisports">
					<div class="col-sm-4">
						Fecha y hora
					</div>
					<div class="col-sm-8">
						<input class="form-control" id="inputMatchDate" type="text" maxlength="16" placeholder="dd/MM/yyyy hh:mm"
							   onchange="javascript:fValidateDate()">
					</div>
				</div>
				<div class="row modal-row-munisports">
					<div class="col-sm-4">
						Polideportivo / Pista
					</div>
					<div class="col-sm-8">
						<select class="form-control" id="selectMatchCourt">
							<option value=""></option>
							<c:forEach var="court" items="${courts}" varStatus="loop">
								<option value="${court.id}">${court.sportCenter.name} - ${court.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row modal-row-munisports">
					<div class="col-sm-4">
						Marcador <br> (local / visitante)
					</div>
					<div class="col-sm-4">
						<input class="form-control" id="inputScoreLocal" type="number" min="0">
					</div>
					<div class="col-sm-4">
						<input class="form-control" id="inputScoreVisitor" type="number" min="0">
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
	$(document).ready(function () {	});

	/* */
	function fPublishCalendar(idCompetition) {
		window.location.href = "/competitions/publishCalendar?idCompetition=" + idCompetition;
	}

	/* */
	function fLoadCalendar(idCompetition) {
		window.location.href = "/competitions/loadCalendar?idCompetition=" + idCompetition;
	}

	/* if the date has wrong format mark the input as error. */
	function fValidateDate(){
		const pattern = /^(\d{1,2})\/(\d{1,2})\/(\d{4})\s(\d{1,2}):(\d{1,2})$/g;
		let newDate = $('#inputMatchDate').val();
		let isValidDate = newDate=="" || pattern.test(newDate);
		$('#score_button_accept').prop("disabled", !isValidDate);
		$('#inputMatchDate').parent().removeClass("has-error");
		if (!isValidDate){
			$('#inputMatchDate').parent().addClass("has-error");
		}

	}

	/* show the div with the week selected, if none is selected show every week. */
	function fUpdateWeekCalendar() {
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


	function fUpdateShowPopup(indexArray) {
		var matchSelected = matchesArray[indexArray];
		var idMatch = matchSelected.id;
		var week = matchSelected.week;
		var local = matchSelected.teamLocal;
		var visitor = matchSelected.teamVisitor;
		var scoreLocal = matchSelected.scoreLocal;
		var scoreVisitor = matchSelected.scoreVisitor;
		var matchDate = matchSelected.dateStr;
		var matchCourtId = matchSelected.courtId;
		/* init popup values */
		$('#weekNumber').html(week);
		$('#scoreLocalTeam').html(local);
		$('#scoreVisitorTeam').html(visitor);
		$('#inputScoreLocal').val(scoreLocal);
		$('#inputScoreLocal').focus(function() { $(this).select(); } );
		$('#inputScoreVisitor').val(scoreVisitor);
		$('#inputScoreVisitor').focus(function() { $(this).select(); } );
		$('#inputMatchDate').val(matchDate);
		if (matchCourtId!=null) {
			$('#selectMatchCourt option[value="'+ matchCourtId +'"]').prop("selected", "selected");
		} else {
			$('#selectMatchCourt option[value=""]').prop("selected", "selected");
		}
		$('#score_button_accept').prop("disabled", false);
		$('#inputMatchDate').parent().removeClass("has-error");
		$('#updatePopup').modal('show');
		$('#score_button_accept').off('click');
		$("#score_button_accept").click(function () {
			matchSelected.scoreLocal = $('#inputScoreLocal').val();
			matchSelected.scoreVisitor = $('#inputScoreVisitor').val();
			matchSelected.dateStr = $('#inputMatchDate').val();
			matchSelected.courtId = $('#selectMatchCourt').val();
			console.log("matchSelected ->" + JSON.stringify(matchSelected));
			$.ajax({
				url: '/server/match/' + idMatch,
				type: 'PUT',
				data: JSON.stringify(matchSelected),
				contentType: "application/json",
				success: function (result) {
					console.log("result ->" + JSON.stringify(result));
					updateMatchProperties(indexArray)
				}
			});
		});
	}

	/*Update view and array.*/
	function updateMatchProperties(indexArray) {
		var matchSelected = matchesArray[indexArray];
		$("#date_" + matchSelected.id).html(matchSelected.dateStr);
		$.ajax({
			url: '/server/match/' + matchSelected.id,
			type: 'GET',
			data: JSON.stringify(matchSelected),
			contentType: "application/json",
			success: function (result) {
				console.log("result GET ->" + JSON.stringify(result));
				matchesArray[indexArray].scoreLocal = result.scoreLocal;
				matchesArray[indexArray].scoreVisitor = result.scoreVisitor;
				matchesArray[indexArray].dateStr = result.dateStr;
				matchesArray[indexArray].courtId = result.courtId;
				$("#score_" + matchSelected.id).html(result.scoreLocal + " - " + result.scoreVisitor);
				$("#date_" + matchSelected.id).html(result.dateStr==null?" - ":result.dateStr);
				$("#place_" + matchSelected.id).html(" - ");
				if (result.sportCenterCourt!=null && result.sportCenterCourt.sportCenter.name!=null) {
					var courtNameStr = result.sportCenterCourt.sportCenter.name;
					courtNameStr += " - ";
					courtNameStr += result.sportCenterCourt.name;
					$("#place_" + matchSelected.id).html(courtNameStr);
				}
				/*mark fields add updated.*/
				$("#date_" + matchSelected.id).removeClass("updated_field");
				if (result.updatedDate) {
					$("#date_" + matchSelected.id).addClass("updated_field");
				}
				$("#score_" + matchSelected.id).removeClass("updated_field");

				if (result.updatedScore) {
					$("#score_" + matchSelected.id).addClass("updated_field");
				}
				$("#place_" + matchSelected.id).removeClass("updated_field");
				if (result.updatedCourt) {
					$("#place_" + matchSelected.id).addClass("updated_field");
				}
			}
		});
	}

	$('#updatePopup').on('shown.bs.modal', function () {
		$(this).find("#inputScoreLocal").focus();
	});

	/*Init matchesArray*/
	var matchesArray = [];
	<c:forEach var="match" items="${matches_list}" varStatus="loop">
		matchesArray[${match.id}] = {
			id: ${match.id},
			week: ${match.week},
			teamLocal: "${match.teamLocal}",
			teamVisitor: "${match.teamVisitor}",
			scoreLocal: ${match.scoreLocal},
			scoreVisitor: ${match.scoreVisitor},
			dateStr: "${match.dateStr}"
		}
		<c:if test="${match.sportCenterCourt!=null}">
			matchesArray[${match.id}].courtId = "${match.sportCenterCourt.id}";
			matchesArray[${match.id}].courtName = "${match.sportCenterCourt.name}";
		</c:if>
	</c:forEach>
</script>
<div class="row" style="position: relative">
	<div class="col-sm-10">
		<h2><small>Calendario: </small>${competition.name} - ${competition.sportEntity.name} - ${competition.categoryEntity.name}</h2>
	</div>
	<div class="col-sm-2" style="position: absolute; bottom: 0px; right: 0px; margin-bottom: 10px;">
		<div class="row">
			<div class="col-sm-8" style="padding: 0px;">
				<select id="week_selection" class="form-control" onchange="fUpdateWeekCalendar()">
					<option></option>
					<c:forEach begin="1" end="${weeks_count}" varStatus="loop">
						<option value="${loop.index}">Jornada ${loop.index}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-sm-2" style="padding-left: 5px;">
				<button type="button" class="btn btn-default" onclick="fPublishCalendar('${competition.id}')" title="publicar">
					<span class="glyphicon glyphicon-ok"></span>
				</button>
			</div>
		</div>
	</div>
</div>
<hr>
<c:if test="${empty matches_list}">
	<div align="right">
		<button type="button" class="btn btn-default" id="fLoadCalendar" onclick="fLoadCalendar('${competition.id}')" style="width: 200px;">
			cargar calendario
		</button>
	</div>
	<br>
</c:if>
<c:forEach begin="1" end="${weeks_count}" varStatus="loopForWeek">
	<div name="week_id_${loopForWeek.index}">
		<h4>Jornada ${loopForWeek.index}</h4>
		<table class="table table-hover	table-condensed">
			<c:forEach var="match" items="${matches_list}" varStatus="loopForMatches">
				<c:if test="${loopForWeek.index==match.week}">
					<c:set var="updated_date" value=""></c:set>
					<c:set var="updated_score" value=""></c:set>
					<c:set var="updated_court" value=""></c:set>
					<c:if test="${match.updatedDate}">
						<c:set var="updated_date" value="updated_field"></c:set>
					</c:if>
					<c:if test="${match.updatedScore}">
						<c:set var="updated_score" value="updated_field"></c:set>
					</c:if>
					<c:if test="${match.updatedCourt}">
						<c:set var="updated_court" value="updated_field"></c:set>
					</c:if>
					<tr class="divlink" onclick="fUpdateShowPopup(${match.id})">
						<td class="col-sm-2 ">
							<div id="date_${match.id}" class="${updated_date}" style="width: 100%">
								<c:if test="${match.dateStr!=null}">
									${match.dateStr}
								</c:if>
								<c:if test="${match.dateStr==null}">
									-
								</c:if>
							</div>
						</td>
						<td class="col-sm-3" style="text-align: left">${match.teamLocal}</td>
						<td class="col-sm-3" style="text-align: left">${match.teamVisitor}</td>
						<td class="col-sm-1">
							<div id="score_${match.id}" class="${updated_score}">
									${match.scoreLocal} - ${match.scoreVisitor}
							</div>
						</td>
						<td class="col-sm-3">
							<div id="place_${match.id}" class="${updated_court}" style="width: 100%">
								<c:if test="${match.sportCenterCourt!=null}">
									${match.sportCenterCourt.sportCenter.name} - ${match.sportCenterCourt.name}
								</c:if>
								<c:if test="${match.sportCenterCourt==null}">
									-
								</c:if>
							</div>
						</td>
					</tr>
					</c:if>
			</c:forEach>
		</table>
	</div>
</c:forEach>