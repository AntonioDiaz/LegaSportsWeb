<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/towns/list";
		});
		$('#name').prop('readonly', true);
		$('#contactPerson').prop('readonly', true);
		$('#phone').prop('readonly', true);
		$('#email').prop('readonly', true);
		$('#address').prop('readonly', true);
		$('#active1').prop("disabled", true);
		$('#sports').prop("disabled", true);
		$('#sports').multiSelect('refresh');
	});
</script>
<form:form method="post" action="doUpdate" commandName="my_form" name="my_form" cssClass="form-horizontal">
	<%@ include file="/WEB-INF/jsp/towns_form.jsp"%>
	<div class="form-group">
		<label class="control-label col-sm-6">&nbsp;</label>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block">volver</button>
		</div>
		<div class="col-sm-4">&nbsp;</div>
	</div>
</form:form>