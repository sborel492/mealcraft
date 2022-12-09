(function() {
	let mealcraftapp = angular.module('mealcraftapp');
	
	mealcraftapp.controller('updateController', function($scope, $http, $routeParams, $location) {
		
		$scope.categories = ['Main', 'Soup', 'Slow Cooker', 'Breakfast','One Pot', 'Appetizer', 'Pasta', 'Dessert', 'Salad', 'Side', 'Vegan', 'Vegetarian'];
		
		$scope.getRecipeByName = function() {
			$http.get("/mealcraft/webapi/recipes/up/" + $routeParams.recipeName)
			.then(function(response) {
				let recipes = response.data;
				if (recipes.length == 1) {
					$scope.recipe = recipes[0];
				} else {
					//TODO error message
				}				
			}, function(response) {
				console.log('error http GET recipe by name: ' + response.status);
			});
		}
		
		$scope.getAllIngredients = function() {
			$http.get("/mealcraft/webapi/recipes/ingTable")
			.then(function(response) {				
				$scope.ingTable = response.data;
			});
		}

		$scope.getRecipeByName();
		$scope.getAllIngredients();
		
		$scope.updateRecipe = function() {
			var newIngredients = document.getElementsByClassName('ingredient-select')
			var ingredientsToUpdate = []
			Array.from(newIngredients).forEach((ingredient)=>{
				ingredientsToUpdate.push(ingredient.value.split(":")[1])
			})
			$scope.recipe.ingredientsList = ingredientsToUpdate
			
			var newMeasurements = document.getElementsByClassName('measurement-select')
			var measurementsToUpdate = []
			Array.from(newMeasurements).forEach((measurement)=>{
				measurementsToUpdate.push(measurement.value)
			})
			$scope.recipe.measurements = measurementsToUpdate
			
			$http.put("/mealcraft/webapi/recipes", $scope.recipe)
			.then(function(response) {				
				$scope.updateStatus = 'update successful';
			}, function(response) {
				$scope.updateStatus = 'error trying to update recipe';	
				console.log('error http PUT recipes: ' + response.status);
			});
		}
		
		$scope.deleteRecipe = function() {
			$http.delete("/mealcraft/webapi/recipes/" + $scope.recipe.id)
			.then(function(response) {				
				$scope.updateStatus = 'delete successful';	
				$scope.disableUpdate = true;
			}, function(response) {
				$scope.updateStatus = 'error trying to delete recipe';	
				console.log('error http DELETE recipe: ' + response.status);
			});
		}
		
		$scope.goToRecipeView = function(recipeName) {
			console.log('go to recipe view');
			console.log('recipeName: ' + recipeName);
			$location.path('/recipe/' + recipeName);
		}
		
	})
})() 