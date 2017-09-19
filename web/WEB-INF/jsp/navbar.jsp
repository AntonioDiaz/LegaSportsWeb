<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="taglibs.jsp"%>
<nav class="navbar navbar-default" style="margin-bottom: 10px; margin-top: 10px;">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
					data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/">
				<img src="/images/logo.png" class="img-rounded" width="180px">
			</a>
		</div>
		<tiles:insertAttribute name="menu_elements" />
	</div>
</nav>
