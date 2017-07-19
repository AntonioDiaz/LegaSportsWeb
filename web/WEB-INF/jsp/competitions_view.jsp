<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/doFilter";
		});
		$('#name').prop('readonly', true);
		$('#idCategory').prop('disabled', true);
		$('#idSport').prop('disabled', true);
		$('#idTown').prop('disabled', true)
	});
</script>
<form:form method="post" action="doAdd" commandName="my_form" cssClass="form-horizontal">
	<%@ include file="/WEB-INF/jsp/competitions_form.jsp"%>
	<div class="form-group">
		<div class="col-sm-6">
			&nbsp;
		</div>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block" >volver</button>
		</div>
		<div class="col-sm-4">&nbsp;</div>
	</div>
</form:form>