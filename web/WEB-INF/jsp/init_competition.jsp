<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/";
		});
	});
</script>
<form:form method="post" action="doCompetition" commandName="my_form" name="my_form" cssClass="form-horizontal">
	<div class="form-group">
		<label class="control-label col-sm-2">Municipio</label>
		<div class="col-sm-6">
			<form:select path="idTown" class="form-control">
				<form:option value=""></form:option>
				<form:options items="${towns}" itemLabel="name" itemValue="id" />
			</form:select>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="idTown" cssClass="text-danger" /></label>
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
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="idCategory" cssClass="text-danger" /></label>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" >Nombre</label>
		<div class="col-sm-6">
			<form:input path="name" class="form-control" />
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="name" cssClass="text-danger" /></label>
	</div>
	<div class="form-group" id="div_botones">
		<label class="control-label col-sm-4">&nbsp;</label>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block">cancelar</button>
		</div>
		<div class="col-sm-2">
			<button type="submit" class="btn btn-primary btn-block">crear competicion</button>
		</div>
		<div class="col-sm-4">&nbsp;</div>
	</div>
</form:form>