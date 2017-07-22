<%@include file="taglibs.jsp" %>
<!-- Modal -->
<div id="updatePopup" class="modal fade" role="dialog">
	<div class="modal-dialog modal-sm" style="width: 40%">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="modalTitle">MuniSports 2017 - Jornada <spam id="weekNumber"></spam> </h4>
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
							<input class="form-control" id="inputScoreLocal" type="number" min="0">
						</div>
						<div class="col-sm-6">
							<input class="form-control" id="inputScoreVisitor" type="number" min="0">
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-6">
							Fecha y hora
						</div>
						<div class="col-sm-6">
							<input class="form-control" id="inputMatchDate" type="text" maxlength="16"
								   onchange="javascript:fValidateDate()">
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-6">
							Polideportivo / Pista
						</div>
						<div class="col-sm-6">
							<select class="form-control" id="selectMatchCourt">
								<option value=""></option>
								<c:forEach var="court" items="${courts}" varStatus="loop">
									<option value="${court.id}">${court.sportCenter.name} - ${court.name}</option>
								</c:forEach>
							</select>
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
	$(document).ready(function () { });

	/* */
	function fLoadCalendar(idCompetition) {
		window.location.href = "/competitions/loadCalendar?idCompetition=" + idCompetition;
	}

	/* if the date has wrong format mark the input as error. */
	function fValidateDate(){
		const pattern = /^(\d{1,2})\/(\d{1,2})\/(\d{4})\s(\d{1,2}):(\d{1,2})$/g;
		let newDate = $('#inputMatchDate').val();
		let isValidDate = pattern.test(newDate);
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
		$('#inputScoreVisitor').val(scoreVisitor);
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
				$("#date_" + matchSelected.id).html(result.dateStr);
				$("#place_" + matchSelected.id).html("");
				if (result.sportCenterCourt!=null && result.sportCenterCourt.sportCenter.name!=null) {
					var courtNameStr = result.sportCenterCourt.sportCenter.name;
					courtNameStr += " - ";
					courtNameStr += result.sportCenterCourt.name;
					$("#place_" + matchSelected.id).html(courtNameStr);
				}
			}
		});
	}

	$('#updateScorePopup').on('shown.bs.modal', function () {
		$(this).find("input:visible:first").focus();
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
		<select id="week_selection" class="form-control" onchange="fUpdateWeekCalendar()">
			<option></option>
			<c:forEach begin="1" end="${weeks_count}" varStatus="loop">
				<option value="${loop.index}">Jornada ${loop.index}</option>
			</c:forEach>
		</select>
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
					<tr class="divlink" onclick="fUpdateShowPopup(${match.id})">
						<td class="col-sm-2">
							<div id="date_${match.id}">
									${match.dateStr}
							</div>
						</td>
						<td class="col-sm-3" style="text-align: left">${match.teamLocal}</td>
						<td class="col-sm-3" style="text-align: left">${match.teamVisitor}</td>
						<td class="col-sm-1">
							<div id="score_${match.id}">
									${match.scoreLocal} - ${match.scoreVisitor}
							</div>
						</td>
						<td id="place_${match.id}" class="col-sm-3">
							<c:if test="${match.sportCenterCourt!=null}">
								${match.sportCenterCourt.sportCenter.name} - ${match.sportCenterCourt.name}
							</c:if>
						</td>
					</tr>
					</c:if>
			</c:forEach>
		</table>
	</div>
</c:forEach>