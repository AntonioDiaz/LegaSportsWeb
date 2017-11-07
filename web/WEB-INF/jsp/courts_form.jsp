	
<form:hidden path="idCenter"></form:hidden>
<form:hidden path="idCourt"></form:hidden>
<form:hidden path="nameCenter"></form:hidden>

<div class="form-group">
	<label class="control-label col-sm-2">Nombre de la pista</label> 
	<div class="col-sm-6">
		<form:input path="name" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="name" cssClass="text-danger" /></label>
</div>
<div class="form-group">
	<label class="control-label col-sm-2">Deportes</label>
	<div class="col-sm-6">
		<form:select path="courtsSports" class="form-control">
			<form:options items="${sports}" itemLabel="name" itemValue="id"/>
		</form:select>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="courtsSports" cssClass="text-danger" /></label>
</div>
<link rel="stylesheet" href="/css/multi-select.css">
<script src="/js/jquery.multi-select.js"></script>
<script type="text/javascript">
	$('#courtsSports').multiSelect({
		selectableHeader: "<div class='custom-header'>Disponibles</div>",
		selectionHeader: "<div class='custom-header'>Seleccionados</div>"
	});
</script>


