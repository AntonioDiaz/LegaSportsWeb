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
						"idCategory" : $scope.filterCategory
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
	<div class="form-group" style="margin-right: 20px;">
		<label style="margin-right: 10px;">Deporte</label>
		<select class="form-control" id="sport" ng-model="filterSport" >
			<option value="">&nbsp;</option>
			<c:forEach items="${sports}" var="sport">
				<option value="${sport.id}">${sport.name}</option>
			</c:forEach>
		</select>
	</div>
	<div class="form-group" style="margin-right: 20px;">
		<label style="margin-right: 10px;">Categoria</label>
		<select class="form-control" id="category" ng-model="filterCategory" >
			<option value="">&nbsp;</option>
			<c:forEach items="${categories}" var="category">
				<option value="${category.id}">${category.name}</option>
			</c:forEach>
		</select>
	</div>			
	<div class="form-group">
		<button type="button" class="btn btn-default" ng-click="search()" style="width: 200px;">Search</button>
	</div>
	<hr>	
	<table st-table="rowCollection" class="table table-hover">
		<thead>
			<tr>
				<th class="col-md-2">Competicion</th>
				<th class="col-md-2">Deporte</th>
				<th class="col-md-2">Categoria</th>
				<th class="col-md-6">&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-if="searchDone==false">
				<td colspan="4">
					Realize la busqueda
				</td>
			</tr>
			<tr ng-if="rowCollection.length<=0 && searchDone">
				<td colspan="4">
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
				<td>
					<div class="row">
						<div class="col-sm-4">
							<button type="button" class="btn btn-default btn-block" ng-click="viewCalendar(row.id)">Calendario</button>
						</div>
						<div class="col-sm-4">
							<button type="button" class="btn btn-default btn-block" ng-click="viewClassification(row.id)">Clasificación</button>
						</div>
						<div class="col-sm-4">
							<button type="button" class="btn btn-default btn-block" ng-click="removeCompetition(row.id)">Borrar</button>
						</div>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</form>