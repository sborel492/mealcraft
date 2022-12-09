(function() {
	let mealcraftapp = angular.module('mealcraftapp');
	
	mealcraftapp.controller('recipeController', function($scope, $http, $routeParams, $location) {
	
		$scope.getRecipeByName = function() {
			$http.get("/mealcraft/webapi/recipes/rv/" + $routeParams.recipeName)
			.then(function(response) {
				var recipes = response.data;
				if (recipes.length == 1) {
					$scope.recipe = recipes[0];
				} else {
					alert('get recipe failed, recipe.length: ' + recipes.length);
				}				
			}, function(response) {
				console.log('error http GET recipe by name: ' + response.status);
			});
		}
		
		$scope.getRecipeByName();
		
		$scope.addToMenu = function(recipe) {
			if (localStorage.getItem('menu') != null) {
				$scope.menu = JSON.parse(localStorage.getItem('menu'));
				$scope.menu.push(recipe);
				localStorage.setItem('menu', JSON.stringify($scope.menu));
				$scope.addStatus = 'Add to menu successful';	
				$scope.disableAdd = true;
			} else {
				// new menu
				$scope.menu = [recipe];
				localStorage.setItem('menu', JSON.stringify($scope.menu));
			}			
		}
		
		$scope.goToUpdateView = function(recipeName) {
			console.log('go to update view');
			console.log('recipeName: ' + recipeName);
			$location.path('/update/' + recipeName);
		}
		
		$scope.goToMenuView = function() {
			console.log('go to menu view');
			$location.path('/menu');
		}
	})
})() 