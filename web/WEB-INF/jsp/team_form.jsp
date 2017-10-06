<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<sec:authentication property="principal" var="userSession"></sec:authentication>
<script>

	$(document).ready(function() {
		<sec:authorize access="!hasRole('ROLE_ADMIN')">
			$('#idTown option[value="'+ ${userSession.townEntity.id} +'"]').prop("selected", "selected");
			$('#idTown').prop('disabled', true);
		</sec:authorize>
		fUpdateClubs();
		$('#idClub option[value="'+ ${my_form.idClub} +'"]').prop("selected", "selected");

	});

	/** update clubs select depending on the town selected. */
	function fUpdateClubs(){
		var idTownSelected = $('#idTown').val();
		$('#idClub').empty();
		$("#idClub").append('<option value=""></option>')
		for (var i=0; i<clubsArray.length; i++) {
			if (idTownSelected == clubsArray[i].townId) {
				var clubId = clubsArray[i].id;
				var clubName = clubsArray[i].name;
				$("#idClub").append('<option value="' + clubId +'">' + clubName + '</option>');
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
<div class="form-group">
	<label class="control-label col-sm-2">Club</label>
	<div class="col-sm-6">
		<form:select path="idClub" class="form-control"></form:select>
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
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="idSport" cssClass="text-danger" /></label>
</div>

<div class="form-group">
	<label class="control-label col-sm-2">Nombre</label>
	<div class="col-sm-6">
		<form:input path="name" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="name" cssClass="text-danger" /></label>
</div>
