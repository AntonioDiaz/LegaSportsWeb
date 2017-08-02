<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/viewCalendar?idCompetition=" + ${competition.id};
		});
	});
</script>
<div class="row" style="position: relative">
	<div class="col-sm-10">
		<div class="font_title">
			<div class="row">
				<div class="col-sm-6"><u>${competition.name}</u></div>
				<div class="col-sm-6">Generar calendario</div>
			</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-2"><small>Deporte</small></div>
			<div>${competition.sportEntity.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-2"><small>Categoria</small></div>
			<div>${competition.categoryEntity.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-2"><small>Municipio</small></div>
			<div>${competition.townEntity.name}</div>
		</div>
	</div>
	<div class="col-sm-2">&nbsp;</div>
</div>
<hr>
<form:form method="post" action="doLoadCalendar" commandName="my_form" cssClass="form-horizontal">
	<form:hidden path="idCompetition"></form:hidden>
	<div class="form-group">
		<label class="control-label col-sm-2">Pista Centro</label>
		<div class="col-sm-6">
			<form:select path="idCourt" class="form-control">
				<form:option value=""></form:option>
				<form:options items="${courts}" itemLabel="nameWithCenter" itemValue="id" />
			</form:select>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="idCourt" cssClass="text-danger" /></label>
	</div>
	<div class="form-group">
		<c:set var="teamNumber" value="0"></c:set>
		<c:if test="${! empty competition.teamsDeref}">
			<c:set var="teamNumber" value="${fn:length(competition.teamsDeref)}"></c:set>
		</c:if>
		<label class="control-label col-sm-2">Equipos: ${teamNumber}</label>

		<div class="col-sm-6">
			<div class="row">
				<div class="col-sm-6">
					<c:forEach items="${competition.teamsDeref}" var="team" step="2">
						${team.name}<br>
					</c:forEach>
				</div>
				<div class="col-sm-6">
					<c:forEach items="${competition.teamsDeref}" var="team" begin="1" step="2">
						${team.name}<br>
					</c:forEach>
				</div>
			</div>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"></label>
	</div>
	<div class="form-group" id="div_botones">
		<label class="control-label col-sm-4">&nbsp;</label>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block">cancelar</button>
		</div>
		<div class="col-sm-2">
			<button type="submit" class="btn btn-primary btn-block">generar calendario</button>
		</div>
		<div class="col-sm-4">&nbsp;</div>
	</div>
</form:form>
