<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form:hidden path="id"></form:hidden>
<%--Name--%>
<div class="form-group">
	<label class="control-label col-sm-2" >Nombre del municipio</label>
	<div class="col-sm-6">
		<form:input path="name" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;">
		<form:errors path="name" cssClass="text-danger" />
	</label>
</div>
<%--Contact Person--%>
<div class="form-group">
	<label class="control-label col-sm-2" >Persona de contacto</label>
	<div class="col-sm-6">
		<form:input path="contactPerson" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;">
		<form:errors path="contactPerson" cssClass="text-danger" />
	</label>
</div>
<%--Phone--%>
<div class="form-group">
	<label class="control-label col-sm-2" >Teléfono de contacto</label>
	<div class="col-sm-6">
		<form:input path="phone" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;">
		<form:errors path="phone" cssClass="text-danger" />
	</label>
</div>
<%--Email--%>
<div class="form-group">
	<label class="control-label col-sm-2" >Email de contacto</label>
	<div class="col-sm-6">
		<form:input path="email" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;">
		<form:errors path="email" cssClass="text-danger" />
	</label>
</div>
<%--Address--%>
<div class="form-group">
	<label class="control-label col-sm-2" >Dirección</label>
	<div class="col-sm-6">
		<form:input path="address" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;">
		<form:errors path="address" cssClass="text-danger" />
	</label>
</div>
<%--townEntity active--%>
<div class="form-group">
	<label class="control-label col-sm-2" >Activar municipio</label>
	<div class="col-sm-6">
		<div style="width: 30px;">
			<form:checkbox path="active" class="xlarge form-control" style="height:30px; margin: 0px;" />
		</div>
	</div>
</div>
<div class="form-group">
	<label class="control-label col-sm-2">Deportes</label>
	<div class="col-sm-6">
		<form:select path="sports" class="form-control">
			<form:options items="${sports}" itemLabel="name" itemValue="id"/>
		</form:select>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="sports" cssClass="text-danger" /></label>
</div>
<link rel="stylesheet" href="/css/multi-select.css">
<script src="/js/jquery.multi-select.js"></script>
<script type="text/javascript">
	// run pre selected options
	//$('#teams').multiSelect();
	$('#sports').multiSelect({
		selectableHeader: "<div class='custom-header'>Disponibles</div>",
		selectionHeader: "<div class='custom-header'>Seleccionados</div>"
	});
</script>

