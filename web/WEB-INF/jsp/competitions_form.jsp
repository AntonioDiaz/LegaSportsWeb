<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<link rel="stylesheet" href="/css/multi-select.css">

<script>

	$(document).ready(function() {
		$('#idTown').on('change', function (event) {
			fUpdateCourts();
			fUpdateTeams();
		});
		$('#idSport').on('change', function (event) {
			fUpdateCourts();
			fUpdateTeams();
		});
		$('#idCategory').on('change', function (event) {
			fUpdateTeams();
		});
		$('#teams').on('change', function (event) {
			fUpdateTeamsSelectedCount();
		});
		var updateCourtSelected = function() {
			$('#idCourt option[value="'+ ${my_form.idCourt} +'"]').prop("selected", "selected");
		};
		var updateTeamSelected = function() {
			<c:forEach items="${my_form.teams}" var="team">
				$('#teams').multiSelect('select', '${team}');
			</c:forEach>
			fUpdateTeamsSelectedCount();
		};
		fUpdateCourts(updateCourtSelected);
		fUpdateTeams(updateTeamSelected);
	});

	function  fUpdateTeamsSelectedCount() {
		$("#spamTeamsSelected").html(" " );
		if ($("#teams").val()!=null && $("#teams").val().length>0) {
			teamsSelected = $("#teams").val().length;
			$("#spamTeamsSelected").html(": " + teamsSelected);
		}
	}

	function fUpdateTeams(functionOnDone){
		$('#teams').empty();
		$('#teams').multiSelect('refresh');
		if ($('#idTown').val() && $('#idSport').val() && $('#idCategory').val()) {
			var filter = {
				idTown: $('#idTown').val(),
				idSport: $('#idSport').val(),
				idCategory: $('#idCategory').val()
			};
			console.log("filter GET ->" + JSON.stringify(filter));
			$.ajax({
				url: '/server/teams/',
				type: 'GET',
				data: filter,
				contentType: "application/json",
				success: function (result) {
					for (let i=0; i<result.length; i++) {
						$('#teams').multiSelect('addOption', { value: result[i].id, text: result[i].name});
					}
					if (functionOnDone!=null) {
						functionOnDone();
					}
				}
			});
		}
	}
	function fUpdateCourts(functionOnDone){
		$('#idCourt').empty();
		if ($('#idTown').val() && $('#idSport').val()) {
			let courtFilter = {
				idTown: $('#idTown').val(),
				idSport: $('#idSport').val()
			};
			$.ajax({
				url: '/server/courts/',
				type: 'GET',
				data: courtFilter,
				contentType: "application/json",
				success: function (result) {
					$("#idCourt").append('<option value=""></option>')
					for (let i=0; i<result.length; i++) {
						$("#idCourt").append('<option value="' + result[i].id +'">'+ result[i].nameWithCenter +'</option>');
					}
					if (functionOnDone!=null) {
						functionOnDone();
					}
				}
			});
		}
	}
</script>
<form:hidden path="id"></form:hidden>
<sec:authorize access="!hasRole('ROLE_ADMIN')">
	<form:hidden path="idTown"></form:hidden>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<div class="form-group">
		<label class="control-label col-sm-2">Municipio</label>
		<div class="col-sm-6">
			<form:select path="idTown" class="form-control">
				<form:option value=""></form:option>
				<form:options items="${towns}" itemLabel="name" itemValue="id" />
			</form:select>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="idTown" cssClass="text-danger" /></label>
	</div>
</sec:authorize>
<div class="form-group">
	<label class="control-label col-sm-2" >Nombre</label>
	<div class="col-sm-6">
		<form:input path="name" class="form-control" />
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="name" cssClass="text-danger" /></label>
</div>
<div class="form-group">
	<label class="control-label col-sm-2" >Deporte</label>
	<div class="col-sm-6">
		<form:select path="idSport" class="form-control">
			<form:option value=""></form:option>
			<form:options items="${sports}" itemLabel="name" itemValue="id"/>
		</form:select>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="idSport" cssClass="text-danger"/></label>
</div>
<div class="form-group">
	<label class="control-label col-sm-2">Categoria</label>
	<div class="col-sm-6">
		<form:select path="idCategory" class="form-control">
			<form:option value=""></form:option>
			<form:options items="${categories}" itemLabel="name" itemValue="id" />
		</form:select>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="idCategory" cssClass="text-danger" /></label>
</div>
<div class="form-group">
	<label class="control-label col-sm-2">Pabellón / Pista</label>
	<div class="col-sm-6">
		<form:select path="idCourt" class="form-control">
			<form:option value=""></form:option>
			<form:options items="${courts}" itemLabel="nameWithCenter" itemValue="id" />
		</form:select>
	</div>
	<label class="control-label col-sm-4">&nbsp;</label>
</div>
<div class="form-group">
	<label class="control-label col-sm-2">Equipos</label>
	<div class="col-sm-6">
		<select id='teams' name='teams' multiple='multiple'>
		</select>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="teams" cssClass="text-danger" /></label>
</div>


<script src="/js/jquery.multi-select.js"></script>
<script type="text/javascript">
	// run pre selected options
	//$('#teams').multiSelect();
	$('#teams').multiSelect({
		selectableHeader: "<div class='custom-header'>Disponibles</div>",
		selectionHeader: "<div class='custom-header'>Seleccionados</div>"
	});
</script>