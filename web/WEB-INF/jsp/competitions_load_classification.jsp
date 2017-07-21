<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/viewCalendar?idCompetition=" + ${competition.id};
		});						
	});	
</script>
<h2>
	Calendario: ${competition.name}  (${competition.sportEntity.name} - ${competition.categoryEntity.name})
</h2>
<hr>
<form:form method="post" action="doLoadClassification" commandName="my_form" cssClass="form-horizontal">
	<form:hidden path="idCompetition"/>
	<div class="form-group">
		<label class="control-label" >Clasificación:</label>
		<form:textarea path="matchesTxt" rows="15" class="form-control"/>
	</div>
	<div class="form-group">
		<div class="col-sm-8">
			&nbsp;
		</div>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block" >cancelar</button>
		</div>
		<div class="col-sm-2">
			<button type="submit" class="btn btn-primary btn-block">crear</button>
		</div>				
	</div>
</form:form>