	
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
		<c:forEach items="${sports}" var="mySport">
			<div class="row" style="margin-bottom: 10px">
				<div class="col-sm-1" >
					<div style="width: 30px; margin: 0 auto;">
						<form:checkbox path="courtsSports" value="${mySport.id}" title="${mySport.name}"
							class="xlarge form-control" style="height:30px; margin: 0px;"></form:checkbox>
					</div>
				</div>
				<label class="col-sm-11" style="margin-top: 5px;">${mySport.name}</label>
			</div>
		</c:forEach>
	</div>
	<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="courtsSports" cssClass="text-danger" /></label>
</div>
