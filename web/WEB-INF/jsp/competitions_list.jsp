<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="taglibs.jsp"%>

<script>
	$(document).ready(function() {
		<c:if test="${add_done==true}">
			showDialogAlert("La competición ha sido creada.");
		</c:if>
		<c:if test="${remove_done==true}">
			showDialogAlert("La competición ha sido eliminada.");
		</c:if>
		<c:if test="${update_done==true}">
			showDialogAlert("La competición ha sido actualizada.");
		</c:if>
	});	

	var app = angular.module('myApp', [ 'smart-table' ]);
	app.controller('myCtrl', [ '$scope', '$http', '$window',
		function($scope, $http, $window) {
			$scope.rowCollection = [];
			$scope.search = function doSearch() {
				var params = {
					params : {
						"idSport" : $scope.filterSport,
						"idCategory" : $scope.filterCategory,
						"idTown" : $scope.filterTown
					}
				};
				$http.get("/server/search_competitions", params).success(function(response) {
					$scope.searchDone = true;
					$scope.rowCollection = response;
				})
			}
			
			$scope.viewCalendar = function fViewCalendar(idCompetition) {
				window.location.href = "/competitions/viewCalendar?idCompetition=" + idCompetition;
			}
			
			$scope.viewClassification = function fViewClassification(idCompetition) {
				window.location.href = "/competitions/viewClassification?idCompetition=" + idCompetition;
			}
			
			$scope.removeCompetition = function fRemoveCompetition(idCompetition) {
				var bodyTxt = "¿Se va a borrar la competición y todos sus partidos desea continuar?";
				showDialogConfirm(bodyTxt, 
					function(){ 
						window.location.href = "/competitions/doRemove?idCompetition=" + idCompetition; 
					}
				);
			}
		}
	]);	
</script>
<form  ng-app="myApp" ng-init="searchDone=false" ng-submit="search()" ng-controller="myCtrl" class="form-inline" >
	<div class="row">
		<div class="col-md-10">
			<div class="form-group">
				<label style="margin-right: 10px;">Deporte</label>
				<select class="form-control" id="sportRef" ng-model="filterSport" style="width: 160px;">
					<option value="">&nbsp;</option>
					<c:forEach items="${sports}" var="sportRef">
						<option value="${sportRef.id}">${sportRef.name}</option>
					</c:forEach>
				</select>
			</div>
			&nbsp;&nbsp;&nbsp;
			<div class="form-group">
				<label style="margin-right: 10px;">Categoria</label>
				<select class="form-control" id="categoryRef" ng-model="filterCategory" style="width: 160px;">
					<option value="">&nbsp;</option>
					<c:forEach items="${categories}" var="categoryRef">
						<option value="${categoryRef.id}">${categoryRef.name}</option>
					</c:forEach>
				</select>
			</div>
			&nbsp;&nbsp;&nbsp;
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<div class="form-group">
					<label style="margin-right: 10px;">Municipio</label>
					<select class="form-control" id="town" ng-model="filterTown" style="width: 160px;">
						<option value="">&nbsp;</option>
						<c:forEach items="${towns}" var="town">
							<option value="${town.id}">${town.name}</option>
						</c:forEach>
					</select>
				</div>
				&nbsp;&nbsp;&nbsp;
			</sec:authorize>
		</div>
		<div class="col-md-2" align="right">
			<button type="button" class="btn btn-default btn-block" ng-click="search()">Search</button>
		</div>
	</div>
	<hr>	
	<table st-table="rowCollection" class="table table-hover">
		<thead>
			<tr>
				<th class="col-md-2">Competición</th>
				<th class="col-md-2">Deporte</th>
				<th class="col-md-2">Categoria</th>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<th class="col-md-2">Municipio</th>
				</sec:authorize>
				<sec:authorize access="!hasRole('ROLE_ADMIN')">
					<th class="col-md-2">&nbsp;</th>
				</sec:authorize>
				<th class="col-md-4">&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-if="searchDone==false">
				<td colspan="10">
					Realize la busqueda
				</td>
			</tr>
			<tr ng-if="rowCollection.length<=0 && searchDone">
				<td colspan="10">
					No existe competiciones
				</td>
			</tr>
			<tr ng-repeat="row in rowCollection">
				<td style="vertical-align: middle;">
					<strong>{{row.name}}</strong>
				</td>
				<td style="vertical-align: middle;">
					{{row.sportEntity.name}}
				</td>
				<td style="vertical-align: middle;">
					{{row.categoryEntity.name}}
				</td>
				<td style="vertical-align: middle;">
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						{{row.town.name}}
					</sec:authorize>
					<sec:authorize access="!hasRole('ROLE_ADMIN')">
						&nbsp;
					</sec:authorize>
				</td>
				<td>
					<div class="row">
						<div class="col-sm-4 div_button_list">
							<button type="button" class="btn btn-default btn-block" ng-click="viewCalendar(row.id)">Calendario</button>
						</div>
						<div class="col-sm-4 div_button_list">
							<button type="button" class="btn btn-default btn-block" ng-click="viewClassification(row.id)">Clasificación</button>
						</div>
						<div class="col-sm-4 div_button_list" style="padding-right: 15px;">
							<button type="button" class="btn btn-default btn-block" ng-click="removeCompetition(row.id)">Borrar</button>
						</div>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</form>