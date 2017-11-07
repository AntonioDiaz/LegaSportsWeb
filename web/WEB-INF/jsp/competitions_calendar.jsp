<%@include file="taglibs.jsp" %>
<spring:eval var="CONSTANT_PENDING" expression="T(com.adiaz.utils.LocalSportsConstants).MATCH_STATE_PENDING"></spring:eval>
<spring:eval var="CONSTANT_PLAYED" expression="T(com.adiaz.utils.LocalSportsConstants).MATCH_STATE_PLAYED"></spring:eval>
<spring:eval var="CONSTANT_CANCELED" expression="T(com.adiaz.utils.LocalSportsConstants).MATCH_STATE_CANCELED"></spring:eval>
<!-- Modal -->

<div id="updatePopup" class="modal fade" role="dialog">
	<div class="modal-dialog modal-sm" style="width: 45%">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header modal-header-localsports">
				<button type="button" class="close-localsports close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title" id="modalTitle">LocalSports Web</h4>
			</div>
			<div class="modal-body modal-body-localsports">
				<div class="modal-row-localsports-title">
					<u>
						<spam>Jornada
							<spam id="weekNumber"></spam>
							:
						</spam>
						${competition_session.name}
						<small>(${competition_session.sportEntity.name} - ${competition_session.categoryEntity.name})</small>
					</u>
				</div>
				<div class="row modal-row-localsports">
					<div class="col-sm-4">Equipo local</div>
					<div class="col-sm-8">
						<select class="form-control" id="selectTeamLocal">
							<option value=""></option>
							<c:forEach var="team" items="${competition_session.teamsDeref}">
								<option value="${team.id}">${team.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row modal-row-localsports">
					<div class="col-sm-4">Equipo visitante</div>
					<div class="col-sm-8">
						<select class="form-control" id="selectTeamVisitor">
							<option value=""></option>
							<c:forEach var="team" items="${competition_session.teamsDeref}">
								<option value="${team.id}">${team.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row modal-row-localsports">
					<div class="col-sm-4">
						Fecha y hora
					</div>
					<div class="col-sm-8">
						<div class='input-group date' id='inputMatchDate'>
							<input type='text' class="form-control" />
							<span class="input-group-addon">
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
						<script type="text/javascript">
							$(function () {
								$('#inputMatchDate').datetimepicker({
									format: DATE_FORMAT,
									locale: 'es'
								});
							});
						</script>
					</div>
				</div>
				<div class="row modal-row-localsports">
					<div class="col-sm-4">
						Polideportivo / Pista
					</div>
					<div class="col-sm-8">
						<select class="form-control" id="selectMatchCourt">
							<option value=""></option>
							<c:forEach var="court" items="${courts}">
								<option value="${court.id}">${court.nameWithCenter}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row modal-row-localsports">
					<div class="col-sm-4">
						Estado
					</div>
					<div class="col-sm-8">
						<select class="form-control" id="selectState" onchange="javascript:fUpdatedState()">
							<c:forEach var="state" items="${states}">
								<option value="${state.value}">${state.stateDesc}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="row modal-row-localsports">
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
				<div class="row">
					<div class="col-sm-6">&nbsp;</div>
					<div class="col-sm-3">
						<button type="button" class="btn btn-default btn-block" data-dismiss="modal">Cancelar</button>
					</div>
					<div class="col-sm-3">
						<button id="score_button_accept" type="button" class="btn btn-primary btn-block" data-dismiss="modal">Aceptar
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	$(document).ready(function () {
		<c:if test="${publish_done==true}">
			showDialogAlert("Se ha publicado el calendario con las ultimas modificaciones.");
		</c:if>
		<c:if test="${publish_none==true}">
			showDialogAlert("No hay ningún cambio para publicar en el calendario.");
		</c:if>
		<c:if test="${add_done==true}">
			showDialogAlert("La competición ha sido creada.");
		</c:if>
		<c:if test="${update_done==true}">
			showDialogAlert("La competición ha sido actualizada.");
		</c:if>


		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/doFilter";
		});
		$('#btnPublish').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/publishCalendar";
		});
		$('#btnGenerate').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/loadCalendar";
		});
		$('#btnUpdate').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/update";
		});
		$('#btnViewClassification').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/viewClassification";
		});
		$('#btnExport').on('click', function(event) {
			export_table_to_csv("table_to_export", "calendar.csv");
		});
		$('#btnPrint').on('click', function(event) {
			window.print();
		});
		$('#btnDelete').on('click', function(event) {
			event.preventDefault();
			var bodyTxt = "¿Se va a borrar la competición y todos sus partidos desea continuar?";
			showDialogConfirm(bodyTxt,
				function(){
					window.location.href = "/competitions/doRemove";
				}
			);
		});
		<c:if test="${empty matches_list}">
			$('#btnPublish').prop("disabled", true);
			$('#week_selection').prop("disabled", true);
		</c:if>

	});

	function fUpdatedState(){
		let newState = $('#selectState').val();
		var setScoreReadonly = newState==MATCH_STATE_PENDING || newState==MATCH_STATE_CANCELED;
		$('#inputScoreVisitor').prop("readonly", setScoreReadonly)
		$('#inputScoreLocal').prop("readonly", setScoreReadonly)
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
		var teamLocalId = matchSelected.teamLocalId;
		var teamVisitorId = matchSelected.teamVisitorId;
		var scoreLocal = matchSelected.scoreLocal;
		var scoreVisitor = matchSelected.scoreVisitor;
		var matchDate = matchSelected.dateStr;
		var matchCourtId = matchSelected.courtId;
		var matchState = matchSelected.state;
		/* init popup values */
		$('#weekNumber').html(week);
		//$('#scoreLocalTeam').html(local);
		//$('#scoreVisitorTeam').html(visitor);
		$('#inputScoreLocal').val(scoreLocal);
		$('#inputScoreLocal').focus(function () {
			$(this).select();
		});
		$('#inputScoreVisitor').val(scoreVisitor);
		$('#inputScoreVisitor').focus(function () {
			$(this).select();
		});
		$('#inputMatchDate').data("DateTimePicker").date(moment(matchDate, DATE_FORMAT));
		if (matchCourtId != null) {
			$('#selectMatchCourt option[value="' + matchCourtId + '"]').prop("selected", "selected");
		} else {
			$('#selectMatchCourt option[value=""]').prop("selected", "selected");
		}
		if (teamLocalId!= null) {
			$('#selectTeamLocal option[value="' + teamLocalId + '"]').prop("selected", "selected");
		} else {
			$('#selectTeamLocal option[value=""]').prop("selected", "selected");
		}
		if (teamVisitorId!= null) {
			$('#selectTeamVisitor option[value="' + teamVisitorId + '"]').prop("selected", "selected");
		} else {
			$('#selectTeamVisitor option[value=""]').prop("selected", "selected");
		}
		$('#selectState option[value="' + matchState + '"]').prop("selected", "selected");

		$('#score_button_accept').prop("disabled", false);
		$('#inputMatchDate').parent().removeClass("has-error");
		$('#updatePopup').modal('show');
		$('#score_button_accept').off('click');
		$("#score_button_accept").click(function () {
			var newDateStr = "";
			if ($('#inputMatchDate').data("DateTimePicker").date()!=null) {
				newDateStr = $('#inputMatchDate').data("DateTimePicker").date().format(DATE_FORMAT);
			}
			let newMatch = {
				id: matchSelected.id,
				courtId: $('#selectMatchCourt').val(),
				dateStr: newDateStr,
				teamLocalId: $('#selectTeamLocal').val(),
				teamVisitorId: $('#selectTeamVisitor').val(),
				scoreLocal: $('#inputScoreLocal').val(),
				scoreVisitor: $('#inputScoreVisitor').val(),
				state: $('#selectState').val()
			};
			$.ajax({
				url: '/server/match/' + idMatch,
				type: 'PUT',
				data: JSON.stringify(newMatch),
				contentType: "application/json",
				success: function (result) {
					updateMatchProperties(indexArray)
				}
			});
		});
		fUpdatedState();
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
				console.log("get result " + JSON.stringify(result));
				matchesArray[indexArray].scoreLocal = result.scoreLocal;
				matchesArray[indexArray].scoreVisitor = result.scoreVisitor;
				matchesArray[indexArray].dateStr = result.dateStr;
				matchesArray[indexArray].courtId = result.courtId;
				matchesArray[indexArray].teamLocalId = result.teamLocalId;
				matchesArray[indexArray].teamLocalName = result.teamLocalName;
				matchesArray[indexArray].teamVisitorId = result.teamVisitorId;
				matchesArray[indexArray].teamVisitorName = result.teamVisitorName;
				matchesArray[indexArray].state = result.state;
				$("#teamlocal_" + matchSelected.id).html(result.teamLocalName ==  null ? " - " : result.teamLocalName);
				$("#teamvisitor_" + matchSelected.id).html(result.teamVisitorName ==  null ? " - " : result.teamVisitorName);
				var strScore;
				if (matchesArray[indexArray].state==MATCH_STATE_PENDING) {
					strScore = "(Pendiente)";
				}
				if (matchesArray[indexArray].state==MATCH_STATE_PLAYED) {
					strScore = result.scoreLocal + " - " + result.scoreVisitor;
				}
				if (matchesArray[indexArray].state==MATCH_STATE_CANCELED) {
					strScore = "(Cancelado)";
				}
				$("#score_" + matchSelected.id).html(strScore);
				$("#date_" + matchSelected.id).html(result.dateStr == null ? " - " : result.dateStr);
				$("#place_" + matchSelected.id).html(result.courtName==null? " - " : result.courtName);
				/* mark fields add updated. */
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
				$("#teamlocal_" + matchSelected.id).removeClass("updated_field");
				if (result.updatedTeamLocal) {
					$("#teamlocal_" + matchSelected.id).addClass("updated_field");
				}
				$("#teamvisitor_" + matchSelected.id).removeClass("updated_field");
				if (result.updatedTeamVisitor) {
					$("#teamvisitor_" + matchSelected.id).addClass("updated_field");
				}
			}
		});
	}

	$('#updatePopup').on('shown.bs.modal', function () {
		$(this).find("#selectState").focus();
	});

	/*Init matchesArray*/
	var matchesArray = [];
	<c:forEach var="matchForm" items="${matches_list}" varStatus="loop">
		matchesArray[${matchForm.id}] = {
			id: ${matchForm.id},
			week: ${matchForm.week},
			teamLocalId: "${matchForm.teamLocalId}",
			teamLocalName: "${matchForm.teamLocalName}",
			teamVisitorId: "${matchForm.teamVisitorId}",
			teamVisitorName: "${matchForm.teamVisitorName}",
			scoreLocal: ${matchForm.scoreLocal},
			scoreVisitor: ${matchForm.scoreVisitor},
			dateStr: "${matchForm.dateStr}",
			courtId: "${matchForm.courtId}",
			courtName: "${matchForm.courtName}",
			state: "${matchForm.state}"
		}
	</c:forEach>
</script>
<div class="row" style="position: relative">
	<div class="col-sm-8">
		<div class="row font_subtitle">
			<div class="col-sm-4 col-print-3"><small>Competición</small></div>
			<div>${competition_session.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-4 col-print-3"><small>Deporte</small></div>
			<div>${competition_session.sportEntity.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-4 col-print-3"><small>Categoria</small></div>
			<div>${competition_session.categoryEntity.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-4 col-print-3"><small>Municipio</small></div>
			<div>${competition_session.townEntity.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-4 col-print-3"><small>Fecha publicación</small></div>
			<div>
				<c:if test="${competition_session.lastPublished eq null}">
					Sin publicar
				</c:if>
				<c:if test="${competition_session.lastPublished ne null}">
					<fmt:formatDate type="both" pattern="dd/MM/yyyy HH:mm" value="${competition_session.lastPublished}" timeZone="Europe/Madrid"></fmt:formatDate>
				</c:if>
			</div>
		</div>
	</div>
	<div class="col-sm-4 no-print" <%--style="position: absolute; bottom: 0; right: 0; margin-bottom: 0;"--%>>
		<div class="row">
			<div class="col-sm-6">
				<c:if test="${empty matches_list}">
					<button type="button" class="btn btn-default btn-block" id="btnGenerate">
						generar calendario
					</button>
				</c:if>
				<c:if test="${!empty matches_list}">
					<button type="button" class="btn btn-default btn-block" id="btnViewClassification">
						clasificación
					</button>
				</c:if>
				<button type="button" class="btn btn-default btn-block" id="btnUpdate">
					modificar
				</button>
				<button type="button" class="btn btn-default btn-block" id="btnPrint">imprimir</button>
				<button type="button" class="btn btn-default btn-block" id="btnExport">
					exportar
				</button>
			</div>
			<div class="col-sm-6">
				<button type="button" class="btn btn-default btn-block" id="btnBack">
					volver
				</button>
				<button type="button" class="btn btn-default btn-block" id="btnPublish">publicar</button>
				<button type="button" class="btn btn-default btn-block" id="btnDelete">
					eliminar
				</button>
				<select id="week_selection" class="form-control" onchange="fUpdateWeekCalendar()" style="margin-top: 5px">
					<option></option>
					<c:forEach begin="1" end="${weeks_count}" varStatus="loop">
						<option value="${loop.index}">Jornada ${loop.index}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>
</div>
<hr>
<c:forEach begin="1" end="${weeks_count}" varStatus="loopForWeek">
	<div name="week_id_${loopForWeek.index}">
		<h4>Jornada ${loopForWeek.index}</h4>
		<table class="table table-hover	table-condensed" width="100%">
			<c:forEach var="matchForm" items="${matches_list}" varStatus="loopForMatches">
				<c:if test="${loopForWeek.index==matchForm.week}">
					<c:set var="updated_date" value=""></c:set>
					<c:set var="updated_score" value=""></c:set>
					<c:set var="updated_court" value=""></c:set>
					<c:set var="updated_team_local" value=""></c:set>
					<c:set var="updated_team_visitor" value=""></c:set>
					<c:if test="${matchForm.updatedDate}">
						<c:set var="updated_date" value="updated_field"></c:set>
					</c:if>
					<c:if test="${matchForm.updatedScore}">
						<c:set var="updated_score" value="updated_field"></c:set>
					</c:if>
					<c:if test="${matchForm.updatedCourt}">
						<c:set var="updated_court" value="updated_field"></c:set>
					</c:if>
					<c:if test="${matchForm.updatedTeamLocal}">
						<c:set var="updated_team_local" value="updated_field"></c:set>
					</c:if>
					<c:if test="${matchForm.updatedTeamVisitor}">
						<c:set var="updated_team_visitor" value="updated_field"></c:set>
					</c:if>
					<tr class="divlink" onclick="fUpdateShowPopup(${matchForm.id})">
						<td class="col-sm-2 ">
							<div id="date_${matchForm.id}" class="${updated_date}" style="width: 100%">
								<c:if test="${matchForm.dateStr!=null}">
									${matchForm.dateStr}
								</c:if>
								<c:if test="${matchForm.dateStr==null}">
									-
								</c:if>
							</div>
						</td>
						<td class="col-sm-3" style="text-align: left">
							<div id="teamlocal_${matchForm.id}" class="${updated_team_local}" style="width: 100%">
								<c:if test="${matchForm.teamLocalName!=null}">
									${matchForm.teamLocalName}
								</c:if>
								<c:if test="${matchForm.teamLocalName==null}">
									-
								</c:if>
							</div>
						</td>
						<td class="col-sm-3" style="text-align: left">
							<div id="teamvisitor_${matchForm.id}" class="${updated_team_visitor}" style="width: 100%">
								<c:if test="${matchForm.teamVisitorName!=null}">
									${matchForm.teamVisitorName}
								</c:if>
								<c:if test="${matchForm.teamVisitorName==null}">
									-
								</c:if>
							</div>
						</td>
						<td class="col-sm-1">
							<div id="score_${matchForm.id}" class="${updated_score}">
									<c:if test="${matchForm.state==CONSTANT_PENDING}">
										(Pendiente)
									</c:if>
									<c:if test="${matchForm.state==CONSTANT_PLAYED}">
										${matchForm.scoreLocal} - ${matchForm.scoreVisitor}
									</c:if>
									<c:if test="${matchForm.state==CONSTANT_CANCELED}">
										(Cancelado)
									</c:if>
							</div>
						</td>
						<td class="col-sm-3">
							<div id="place_${matchForm.id}" class="${updated_court}" style="width: 100%">
								<c:if test="${matchForm.courtName!=null}">
									${matchForm.courtName}
								</c:if>
								<c:if test="${matchForm.courtName==null}">
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
<!-- table to export -->
<div style="display: none">
	<table id="table_to_export">
		<tr>
			<td>Jornada</td>
			<td>Estado</td>
			<td>Fecha</td>
			<td>Equipo Local</td>
			<td>Equipo Visitante</td>
			<td>Marcador Local</td>
			<td>Marcador Visitante</td>
			<td>Localicacion</td>
		</tr>
		<c:forEach var="matchForm" items="${matches_list}" varStatus="loopForMatches">
			<tr>
				<td>${matchForm.week}</td>
				<td>${matchForm.state}</td>
				<td>${matchForm.dateStr}</td>
				<td>${matchForm.teamLocalName}</td>
				<td>${matchForm.teamVisitorName}</td>
				<td>${matchForm.scoreLocal}</td>
				<td>${matchForm.scoreVisitor}</td>
				<td>${matchForm.courtName}</td>
			</tr>
		</c:forEach>
	</table>
</div>