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
	<label class="control-label col-sm-2">Etiqueta</label>
	<div class="col-sm-6">
		<form:input path="tag" class="form-control"></form:input>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="tag" cssClass="text-danger" /></label>
</div>
