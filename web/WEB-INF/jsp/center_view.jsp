<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function(){
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/center/list";
		});
		$('#name').prop('readonly', true);
		$('#address').prop('readonly', true);
		$('#idTown').prop('disabled', true);

	});

</script>

<form:form method="post" action="doUpdate" commandName="my_form" name="my_form" cssClass="form-horizontal">

	<%@ include file="/WEB-INF/jsp/center_form.jsp"%>

	<div class="form-group" id="div_botones">
		<label class="control-label col-sm-6">&nbsp;</label>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block">volver</button>
		</div>
		<div class="col-sm-4">&nbsp;</div>
	</div>
</form:form>