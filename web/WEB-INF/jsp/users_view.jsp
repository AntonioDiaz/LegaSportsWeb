<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/users/list";
		});
		$('#username').prop('readonly', true);
		$('#password01').prop('readonly', true);
		$('#password02').prop('readonly', true);

		$('input[name=updatePassword]').prop("disabled", true);
		$('input[name=admin]').prop("disabled", true);
		$('input[name=bannedUser]').prop("disabled", true);
		$('input[name=accountNonExpired]').prop("disabled", true);
		$('input[name=enabled]').prop("disabled", true);
		setTimeout(function(){ $('#townEntity\\.id').attr('disabled', true); }, 500);

	});
</script>
<form:form method="post" action="doUpdate" commandName="my_form" name="my_form" cssClass="form-horizontal">
	<%@ include file="/WEB-INF/jsp/users_form.jsp"%>
	<div class="form-group">
		<label class="control-label col-sm-6">&nbsp;</label>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block">volver</button>
		</div>
		<div class="col-sm-4">&nbsp;</div>
	</div>
</form:form>