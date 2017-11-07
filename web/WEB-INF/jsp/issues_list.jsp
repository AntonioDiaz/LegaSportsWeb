<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>
<script>

	$(document).ready(function() {
		fUpdateCompetitions(${form_filter.competitionId});

	});
	
	function fUpdateCompetitions(competitionSelected) {
		var townId = $('#townId').val();
		$('#competitionId').empty();
		$('#competitionId').append($('<option>', { text: ''	}));
		if (townId!=null && townId!="") {
			$.ajax({
				url: '/server/competitionsInTown/' + townId,
				type: 'GET',
				contentType: "application/json",
				success: function (result) {
					console.log("get result " + JSON.stringify(result));
					for (var i=0; i<result.length; i++) {
						var competitionId = result[i].id;
						var competitionName = result[i].name;
						competitionName += " (" + result[i].sportEntity.name + " - " + result[i].categoryEntity.name +")";
						$('#competitionId').append($('<option>', { value: competitionId, text: competitionName}));
					}
					$('#competitionId option[value="' + competitionSelected + '"]').prop("selected", "selected");
				}
			});
		}
	}

	function fViewDetails(idIssue) {
		window.location.href = "/issues/view?idIssue=" + idIssue;
	}
	
	function fValidateForm() {
		var townId = $('#townId').val();
		if (townId=="") {
			showDialogAlert("Indique el municipio para filtrar los resultados.");
			return false;
		}
	}

</script>
<form:form method="post" action="doFilter" commandName="form_filter" cssClass="form-inline" onsubmit="return fValidateForm()">
	<div class="row">
		<div class="col-sm-3">
			<div class="form-group">
				<label class="control-label" for="townId">Municipio &nbsp;&nbsp;</label>
				<form:select path="townId" class="form-control" cssStyle="width: 150px" onchange="fUpdateCompetitions()">
					<form:option value=""></form:option>
					<form:options items="${towns}" itemLabel="name" itemValue="id" />
				</form:select>
			</div>
		</div>
		<div class="col-sm-7">
			<div class="form-group">
				<label class="control-label" for="competitionId">Competición &nbsp;&nbsp;</label>
				<form:select path="competitionId" class="form-control" cssStyle="width: 450px">
					<form:option value=""></form:option>
				</form:select>
			</div>
		</div>
		<div class="col-sm-2">
			<button type="submit" class="btn btn-default btn-block">buscar</button>
		</div>
	</div>
</form:form>
<hr>
<table class="table table-hover" id="issuesTable">
	<thead>
	<tr>
		<th class="col-md-2">Municipio</th>
		<th class="col-md-3">Competición</th>
		<th class="col-md-3">Partido</th>
		<th class="col-md-1">Fecha</th>
		<th class="col-md-1">&nbsp;</th>
	</tr>
	</thead>
	<tbody>
		<c:if test="${empty issues}">
			<c:if test="${issues==null}">
				<tr>
					<td colspan="10">Realize la búsqueda.</td>
				</tr>
			</c:if>
			<c:if test="${issues!=null}">
				<tr>
					<td colspan="10">No hay notificaciones para esta búsqueda.</td>
				</tr>
			</c:if>
		</c:if>
		<c:forEach var="issue" items="${issues}">
			<tr>
				<td class="col-sm-2">${issue.town.name}</td>
				<td class="col-sm-3" title="${issue.competition.fullName}">
					<div style="width: 300px;" class="hideextra">${issue.competition.fullName}</div>
				</td>
				<td class="col-sm-3" title="${issue.matchDescription}">
					<c:if test="${issue.match == null}">
						-
					</c:if>
					<c:if test="${issue.match != null}">
						<div style="width: 300px;" class="hideextra">${issue.matchDescription}</div>
					</c:if>
				</td>
				<td class="col-sm-1">
					<fmt:formatDate type="both" pattern="dd/MM/yyyy HH:mm" value="${issue.dateSent}" timeZone="Europe/Madrid"></fmt:formatDate>
				</td>
				<td class="col-sm-1" align="right">
					<button type="button" class="btn btn-default" onclick="fViewDetails('${issue.id}')" title="ver detalles">
						<span class="glyphicon glyphicon-eye-open"></span>
					</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
