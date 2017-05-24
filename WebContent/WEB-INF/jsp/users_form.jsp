	<div class="form-group">
		<label class="control-label col-sm-2" >Nombre de usuario</label> 
		<div class="col-sm-6">
			<form:input path="username" class="form-control"></form:input>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="username" cssClass="text-danger" /></label>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" >Contraseña</label>
		<div class="col-sm-1">
			<div style="width: 30px; margin: 0 auto;">
				<form:checkbox id="updatePassword" path="updatePassword" class="xlarge form-control" style="height:30px; margin: 0px;" />
			</div>
		</div>
		<div class="col-sm-5">
			<div class="row">
				<div class="col-sm-6">
					<form:password path="password01" cssClass="form-control" placeholder="****"></form:password>
				</div>
				<div class="col-sm-6">
					<form:password path="password02" cssClass="form-control" placeholder="****"></form:password>
				</div>
			</div>
		</div>
		<label class="control-label col-sm-4" style="text-align: left;"><form:errors path="password" cssClass="text-danger" /></label>
	</div>	
	<div class="form-group">
		<label class="control-label col-sm-2" >Administrador</label>
		<div class="col-sm-1">
			<div style="width: 30px; margin: 0 auto;">
				<form:checkbox path="admin" class="xlarge form-control" style="height:30px; margin: 0px;" />
			</div>
		</div>
		<div class="col-sm-9">&nbsp;</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" >Bloquear acceso</label>
		<div class="col-sm-1">
			<div style="width: 30px; margin: 0 auto;">
				<form:checkbox path="bannedUser" class="xlarge form-control" style="height:30px; margin: 0px;" />
			</div>
		</div>
		<div class="col-sm-9">&nbsp;</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" >Cuenta no caducada</label>
		<div class="col-sm-1">
			<div style="width: 30px; margin: 0 auto;">
				<form:checkbox path="accountNonExpired" class="xlarge form-control" style="height:30px; margin: 0px;" />
			</div>
		</div>
		<div class="col-sm-9">&nbsp;</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" >Cuenta activa</label>
		<div class="col-sm-1">
			<div style="width: 30px; margin: 0 auto;">
				<form:checkbox path="enabled" class="xlarge form-control" style="height:30px; margin: 0px;" />
			</div>
		</div>
		<div class="col-sm-9">&nbsp;</div>
	</div>	