<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/list";
		});
	});
</script>
<form:form method="post" action="doAdd" commandName="my_form" cssClass="form-horizontal">
	<%@ include file="/WEB-INF/jsp/competitions_form.jsp"%>
	<div class="form-group">
		<div class="col-sm-4">
			&nbsp;
		</div>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block" >cancelar</button>
		</div>
		<div class="col-sm-2">
			<button type="submit" class="btn btn-primary btn-block">crear</button>
		</div>
		<div class="col-sm-4">&nbsp;</div>
	</div>
</form:form>