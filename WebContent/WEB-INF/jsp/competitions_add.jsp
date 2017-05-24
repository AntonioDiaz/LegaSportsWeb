<%@include file="taglibs.jsp"%>
<script>
	$(document).ready(function() {
		$('#btnBack').on('click', function(event) {
			event.preventDefault();
			window.location.href = "/competitions/list";
		});

	});
</script>
<form:form method="post" action="doAdd" commandName="my_form" cssClass="form-horizontal">
	<div class="form-group">
		<label class="control-label col-sm-2" >Deporte:</label> 
		<div class="col-sm-6">
			<form:select path="sportId" class="form-control">
				<form:option value=""></form:option>
				<form:options items="${sports}" itemLabel="name" itemValue="id" />
			</form:select>
		</div>
		<div class="col-sm-4"><form:errors path="sportId" cssClass="text-danger" /></div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" >Categoria:</label> 
		<div class="col-sm-6">
		<form:select path="categoryId" class="form-control">
			<form:option value=""></form:option>
			<form:options items="${categories}" itemLabel="name" itemValue="id" />
		</form:select>
		</div>
		<div class="col-sm-4"><form:errors path="categoryID" cssClass="text-danger" /></div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" >Nombre:</label> 
		<div class="col-sm-6">
			<form:input path="name" class="form-control" />
		</div>
		<div class="col-sm-4"><form:errors path="name" cssClass="text-danger" /></div>
	</div>
	<div class="form-group">
		<div class="col-sm-4">
			&nbsp;
		</div>
		<div class="col-sm-2">
			<button id="btnBack" type="button" class="btn btn-default btn-block" >cancelar</button>
		</div>
		<div class="col-sm-2">
			<button type="submit" class="btn btn-primary btn-block">crear</button>
		</div>
		<div class="col-sm-4">&nbsp;</div>
	</div>
</form:form>