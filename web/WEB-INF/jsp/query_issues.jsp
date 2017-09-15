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

</script>
<form:form method="post" action="doFilter" commandName="form_filter" cssClass="form-inline">
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
<table class="table table-hover">
	<thead>
	<tr>
		<th class="col-md-2">Municipio</th>
		<th class="col-md-3">Competición</th>
		<th class="col-md-2">Fecha</th>
		<th class="col-md-2">Usuario</th>
		<th class="col-md-3">Partido</th>
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
				<td colspan="10" style="padding: 0px;">
					<table id="issuesTable" width="100%" style="">
						<tr>
							<td class="col-sm-2">${issue.town.name}</td>
							<td class="col-sm-3" title="${issue.competition.fullName}">
								<div style="width: 250px;" class="hideextra">${issue.competition.fullName}</div>
							</td>
							<td class="col-sm-2">
								<fmt:formatDate type="both" pattern="dd/MM/yyyy HH:mm" value="${issue.dateSent}"></fmt:formatDate>
							</td>
							<td class="col-sm-2">${issue.clientId}</td>
							<td class="col-sm-3">
								<c:if test="${issue.match == null}">
									-
								</c:if>
								<c:if test="${issue.match != null}">
									<div style="width: 250px;" class="hideextra">${issue.match.fullName}</div>
								</c:if>

							</td>
						</tr>
						<tr>
							<td class="col-sm-10" colspan="9"><em>${issue.description}</em></td>
						</tr>
					</table>
				</td>
			</tr>

		</c:forEach>
	</tbody>
</table>
