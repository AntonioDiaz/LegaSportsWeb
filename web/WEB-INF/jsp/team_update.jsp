<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/team/doFilter";
		});
		<c:if test="${elegibleForDelete==false}">
			$('#idTown').prop('disabled', true);
			$('#idClub').prop('disabled', true);
			$('#idSport').prop('disabled', true);
			$('#idCategory').prop('disabled', true);
		</c:if>
		$('#my_form').submit(function() {
			$('#idTown').prop('disabled', false);
			$('#idClub').prop('disabled', false);
			$('#idSport').prop('disabled', false);
			$('#idCategory').prop('disabled', false);
		});
	});
</script>
<form:form method="post" action="doUpdate" commandName="my_form" name="my_form" cssClass="form-horizontal">

	<%@ include file="/WEB-INF/jsp/team_form.jsp"%>

	<div class="form-group" id="div_botones">
		<div class="col-sm-4">&nbsp;</div>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block">cancelar</button>
		</div>
		<div class="col-sm-2">
			<button type="submit" class="btn btn-primary btn-block">actualizar equipo</button>
		</div>
		<div class="col-sm-4">&nbsp;</div>
	</div>
</form:form>
<c:if test="${elegibleForDelete==false}">
	<br>
	<div class="row">
		<div class="col-sm-2">&nbsp;</div>
		<div class="col-sm-10">
			<small>
				*Al estar en una competición solo se puede modificar el nombre.
			</small>
		</div>
	</div>
</c:if>