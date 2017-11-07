<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<form:hidden path="id"></form:hidden>
<div class="form-group">
	<label class="control-label col-sm-2">Nombre</label>
	<div class="col-sm-6">
		<form:input path="name" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="name" cssClass="text-danger" /></label>
</div>
<div class="form-group">
	<label class="control-label col-sm-2">Persona de contacto</label>
	<div class="col-sm-6">
		<form:input path="contactPerson" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="contactPerson" cssClass="text-danger" /></label>
</div>
<div class="form-group">
	<label class="control-label col-sm-2">Email de contacto</label>
	<div class="col-sm-6">
		<form:input path="contactEmail" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="contactEmail" cssClass="text-danger" /></label>
</div>
<div class="form-group">
	<label class="control-label col-sm-2">Dirección</label>
	<div class="col-sm-6">
		<form:input path="contactAddress" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="contactAddress" cssClass="text-danger" /></label>
</div>
<div class="form-group">
	<label class="control-label col-sm-2">Teléfono</label>
	<div class="col-sm-6">
		<form:input path="contactPhone" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="contactPhone" cssClass="text-danger" /></label>
</div>
<sec:authorize access="!hasRole('ROLE_ADMIN')">
	<form:hidden path="idTown"></form:hidden>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<div class="form-group">
		<label class="control-label col-sm-2" >Municipio</label>
		<div class="col-sm-6">
			<form:select path="idTown" class="form-control">
				<form:option value=""></form:option>
				<form:options items="${towns}" itemLabel="name" itemValue="id" />
			</form:select>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="idTown" cssClass="text-danger" /></label>
	</div>
</sec:authorize>