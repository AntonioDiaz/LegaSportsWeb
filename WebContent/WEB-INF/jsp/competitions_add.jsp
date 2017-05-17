<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="headers.jsp"%>
<script>
		$(document).ready(function() {						
			$('#btnBack').on('click', function(event) {
				event.preventDefault();
				window.location.href = "/competitions/list";
			});

		});
	</script>
</head>
<body>
	<%@include file="navbar.jsp"%>
	<div class="container">
		<h1>Nueva competición</h1>
		<br>
		<form:form method="post" action="doAdd" commandName="my_form" cssClass="form-horizontal">
			<div class="form-group">
				<label class="control-label col-sm-2" >Deporte:</label> 
				<div class="col-sm-10">
					<form:select path="sportId" class="form-control">
						<form:option value=""></form:option>
						<form:options items="${sports}" itemLabel="name" itemValue="id" />
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" >Categoria:</label> 
				<div class="col-sm-10">
				<form:select path="categoryId" class="form-control">
					<form:option value=""></form:option>
					<form:options items="${categories}" itemLabel="name" itemValue="id" />
				</form:select>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" >Nombre:</label> 
				<div class="col-sm-10">
					<form:input path="name" class="form-control" />
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-2">
					&nbsp;
				</div>
				<div class="col-sm-2">
					<button id="btnBack" type="button" class="btn btn-default btn-block" >cancelar</button>
				</div>
				<div class="col-sm-2">
					<button type="submit" class="btn btn-primary btn-block">crear</button>
				</div>
				<div class="col-sm-6">&nbsp;</div>
			</div>
		</form:form>
		<br>
	</div>
</body>
</html>
