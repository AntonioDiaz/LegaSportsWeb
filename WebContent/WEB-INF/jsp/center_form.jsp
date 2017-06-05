	<form:hidden path="id"></form:hidden>
	<div class="form-group">
		<label class="control-label col-sm-2">Nombre del centro</label> 
		<div class="col-sm-6">
			<form:input path="name" class="form-control"></form:input>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="name" cssClass="text-danger" /></label>
	</div>
	
	<div class="form-group">
		<label class="control-label col-sm-2">Dirección del centro</label> 
		<div class="col-sm-6">
			<form:input path="address" class="form-control"></form:input>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="address" cssClass="text-danger" /></label>
	</div>
