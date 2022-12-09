(function() {
let mealcraftapp = angular.module('mealcraftapp');

mealcraftapp.controller('searchController', function($scope, $http, $location) {
	
	$scope.getAllRecipes = function() {
		$http.get("/mealcraft/webapi/recipes")
		.then(function(response) {
			$scope.recipes = response.data;
			console.log('number of recipes: ' + $scope.recipes.length);
		}, function(response) {
			console.log('error http GET recipes: ' + response.status);
		});
	}

		$scope.goToRecipeView = function(recipeName) {
			console.log('go to recipe view');
			console.log('recipeName: ' + recipeName);
			$location.path('/recipe/' + recipeName);
		}
		
		$scope.getAllRecipes();
	})
})()