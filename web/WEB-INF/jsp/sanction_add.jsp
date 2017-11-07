<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/viewClassification";
		});
	});

</script>
<div class="row" style="position: relative">
	<div class="col-sm-10">
		<div class="font_title">
			<div><u>${competition_session.name}</u></div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-2"><small>Deporte</small></div>
			<div>${competition_session.sportEntity.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-2"><small>Categoria</small></div>
			<div>${competition_session.categoryEntity.name}</div>
		</div>
		<div class="row font_subtitle">
			<div class="col-sm-2"><small>Municipio</small></div>
			<div>${competition_session.townEntity.name}</div>
		</div>
	</div>
	<div class="col-sm-2">&nbsp;</div>
</div>
<hr>
<h3 style="align-content: center">Crear sanción</h3>
<form:form method="post" action="doAddSanction" commandName="my_form" name="my_form" cssClass="form-horizontal">
	<form:hidden path="idCompetition"></form:hidden>
	<div class="form-group">
		<label class="control-label col-sm-2">Equipo</label>
		<div class="col-sm-6">
			<form:select path="idTeam" class="form-control">
				<form:option value=""></form:option>
				<form:options items="${competition_session.teamsDeref}" itemLabel="name" itemValue="id" />
			</form:select>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="idTeam" cssClass="text-danger" /></label>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2">Puntos</label>
		<div class="col-sm-6">
			<form:input path="points" class="form-control"></form:input>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="points" cssClass="text-danger" /></label>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2">Detalles</label>
		<div class="col-sm-6">
			<form:input path="description" class="form-control"></form:input>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="description" cssClass="text-danger" /></label>
	</div>
	<div class="form-group" id="div_botones">
		<label class="control-label col-sm-4">&nbsp;</label>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block">cancelar</button>
		</div>
		<div class="col-sm-2">
			<button type="submit" class="btn btn-primary btn-block">nueva sanción</button>
		</div>
		<div class="col-sm-4">&nbsp;</div>
	</div>
</form:form>