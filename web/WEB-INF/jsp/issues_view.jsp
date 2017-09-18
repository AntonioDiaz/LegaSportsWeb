<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>
<script>

	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/issues/doFilter";
		});
	});
</script>
<div class="row form-horizontal">
	<div class="form-group">
		<label class="control-label col-sm-2">Municipio</label>
		<div class="control-label col-sm-6" style="text-align: left;">${issue.town.name}</div>
		<div class="col-sm-4"></div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2">Competición</label>
		<div class="control-label col-sm-6" style="text-align: left;">${issue.competition.fullName}</div>
		<div class="col-sm-4"></div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2">Partido</label>
		<div class="control-label col-sm-6" style="text-align: left;">${issue.matchDescription}</div>
		<div class="col-sm-4"></div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2">Descripión error</label>
		<div class="control-label col-sm-6" style="text-align: left;">${issue.description}</div>
		<div class="col-sm-4"></div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2">Usuario</label>
		<div class="control-label col-sm-6" style="text-align: left;">${issue.clientId}</div>
		<div class="col-sm-4"></div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2">Fecha</label>
		<div class="control-label col-sm-6" style="text-align: left;">${issue.dateSent}</div>
		<div class="col-sm-4"></div>
	</div>
	<div class="form-group">
		<label class="col-sm-6">&nbsp;</label>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block">volver</button>
		</div>
		<div class="col-sm-4">&nbsp;</div>
	</div>
</div>