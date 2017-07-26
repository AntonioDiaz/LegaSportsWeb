<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<script>

	$(document).ready(function() {
		fUpdateClubs();
		$('#idClub option[value="'+ ${my_form.idClub} +'"]').prop("selected", "selected");
	});

	function fUpdateClubs(){
		var idTownSelected = $('#idTown').val();
		$('#idClub').empty();
		if (idTownSelected==null) {
			$('#idClub').prop('disabled', true);
		} else {
			//in case the form is on view screen.
			let isTownDisabled = $('#idTown').prop('disabled');
			$('#idClub').prop('disabled', isTownDisabled);
			$("#idClub").append('<option value=""></option>')
			for (var i=0; i<clubsArray.length; i++) {
				if (idTownSelected == clubsArray[i].townId) {
					var clubId = clubsArray[i].id;
					var clubName = clubsArray[i].name;
					$("#idClub").append('<option value="' + clubId +'">'+ clubName +'</option>');
				}
			}
		}
	}

	var clubsArray = [];
	<c:forEach var="club" items="${club_list}" varStatus="loop">
		clubsArray[${loop.index}] = {
			id: ${club.id},
			name: "${club.name}",
			townId: "${club.townEntity.id}"
		}
	</c:forEach>

</script>
<form:hidden path="id"></form:hidden>
<sec:authorize access="!hasRole('ROLE_ADMIN')">
	<form:hidden path="idTown"></form:hidden>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<div class="form-group">
		<label class="control-label col-sm-2" >Municipio</label>
		<div class="col-sm-6">
			<form:select path="idTown" class="form-control" onchange="fUpdateClubs()">
				<form:option value=""></form:option>
				<form:options items="${towns}" itemLabel="name" itemValue="id" />
			</form:select>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="idTown" cssClass="text-danger" /></label>
	</div>
</sec:authorize>
<div class="form-group">
	<label class="control-label col-sm-2">Club</label>
	<div class="col-sm-6">
		<form:select path="idClub" class="form-control" disabled="true"></form:select>
	</div>
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
	<label class="control-label col-sm-2">Deporte</label>
	<div class="col-sm-6">
		<form:select path="idSport" class="form-control">
			<form:option value=""></form:option>
			<form:options items="${sports}" itemLabel="name" itemValue="id" />
		</form:select>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="idCategory" cssClass="text-danger" /></label>
</div>

<div class="form-group">
	<label class="control-label col-sm-2">Nombre</label>
	<div class="col-sm-6">
		<form:input path="name" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="name" cssClass="text-danger" /></label>
</div>
