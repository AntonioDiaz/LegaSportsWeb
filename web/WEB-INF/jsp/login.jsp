<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/WEB-INF/jsp/include.jsp"%>
	<title><tiles:insertAttribute name="title" ignore="true" defaultValue="title" /></title>
	<script>
		$(document).ready(function(){
/*			$('input[name=j_username]').val('antonio.diaz');
			$('input[name=j_password]').val('admin');*/
		});
	</script>
</head>
<body>
<body>
	<div class="container">
		<form method='POST' class="form-signin" action="<c:url value='j_spring_security_check' />">
			<div class="row">
				<br>
				<div class="col-sm-3">&nbsp;</div>
				<div class="col-sm-6">
					<div style="margin: 5%; border-radius: 5px; background: #f5f5f5; padding: 30px;">
						<div style="text-align: center; width: 100%; margin-top: 20px;'">
							<img src="images/logo_vertical.png" class="img-rounded" width="300px">
						</div>
						<hr class="hr_color">
						<div class="form-group">
							<label for="j_username">Usuario</label> <input type="text" class="form-control" name='j_username'>
						</div>
						<div class="form-group">
							<label for="j_password">Contraseña</label> <input type="password" class="form-control" name='j_password'>
						</div>
						<!-- 
						<div class="form-group">
							<input type="checkbox" value="remember-me"> Recordar
						</div>
						 -->
						<hr class="hr_color">
						<button class="btn btn-lg btn-primary btn-block" type="submit">
							<i class="glyphicon glyphicon-log-in"></i> Entrar
						</button>
						<br>
						<c:if test="${not empty error}">
							<div class="alert alert-error" style="color: red;">
								${error}
							</div>
						</c:if>
					</div>
				</div>
				<div class="col-sm-3">&nbsp;</div>
			</div>
		</form>
	</div>
</body>
</html>