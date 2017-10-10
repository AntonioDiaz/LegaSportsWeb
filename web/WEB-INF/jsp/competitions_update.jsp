<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/viewCalendar";
		});
		var isDisabled = true;
		<c:if test="${is_updatable}">
			isDisabled = false;
		</c:if>
		$('#idCategory').prop('disabled', isDisabled);
		$('#idSport').prop('disabled', isDisabled);
		$('#idTown').prop('disabled', isDisabled);
		fLoadTeams(isDisabled);

		$('#my_form').submit(function() {
			$('#idCategory').prop('disabled', false);
			$('#idSport').prop('disabled', false);
			$('#idTown').prop('disabled', false);
			$('#teams').prop("disabled", false);
			$('#teams').multiSelect('refresh');
		});
	});

	function fLoadTeams(disableTeams) {
		var updateTeamSelected = function() {
			<c:forEach items="${my_form.teams}" var="team">
			$('#teams').multiSelect('select', '${team}');
			</c:forEach>
			fUpdateTeamsSelectedCount();
			if(disableTeams) {
				$('#teams').prop("disabled", true);
				$('#teams').multiSelect('refresh');
			}
		};
		fUpdateTeams(updateTeamSelected);
		fUpdateTeamsSelectedCount();
	}

</script>
<form:form method="post" action="doUpdate" commandName="my_form" cssClass="form-horizontal">
	<%@ include file="/WEB-INF/jsp/competitions_form.jsp"%>
	<div class="form-group">
		<div class="col-sm-4">
			&nbsp;
		</div>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block" >cancelar</button>
		</div>
		<div class="col-sm-2">
			<button type="submit" class="btn btn-primary btn-block">actualizar</button>
		</div>
		<div class="col-sm-4">&nbsp;</div>
	</div>
</form:form>