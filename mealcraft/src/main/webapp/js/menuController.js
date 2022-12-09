(function() {
	let mealcraftapp = angular.module('mealcraftapp');
	
	mealcraftapp.controller('menuController', function($scope, $http, $location) {	
		
		$scope.categories = ['Main', 'Soup', 'Slow Cooker', 'Breakfast','One Pot', 'Appetizer', 'Pasta', 'Dessert', 'Salad', 'Side', 'Vegan', 'Vegetarian'];
		
		/*populate every recipe*/
		$scope.getAllRecipes = function() {
			$http.get("/mealcraft/webapi/recipes")
			.then(function(response) {
				$scope.recipes = response.data;
				console.log('number of recipes: ' + $scope.recipes.length);
			}, function(response) {
				console.log('error http GET recipes: ' + response.status);
			});
		}
		
		/*search by name*/
		$scope.getRecipesByName = function() {
			$http.get("/mealcraft/webapi/recipes/name/" + $scope.recipe.name)
			.then(function(response) {
				$scope.recipes = response.data;
				console.log('number of recipes: ' + $scope.recipes.length);
			}, function(response) {
				console.log('error http GET recipes: ' + response.status);
			});
		}
		
		/*filter by category*/
		$scope.getRecipesByCategory = function() {
			$http.get("/mealcraft/webapi/recipes/category/" + $scope.recipe.category)
			.then(function(response) {
				$scope.recipes = response.data;
				console.log('number of recipes: ' + $scope.recipes.length);
			}, function(response) {
				console.log('error http GET recipes: ' + response.status);
			});
		}
		
		$scope.clearSearch = function() {
			$scope.recipe.name = '';
			$scope.recipe.category = '';
			$scope.getAllRecipes();
		}
		
		/*click through to recipe*/
		$scope.goToRecipeView = function(recipeName) {
			console.log('go to recipe view');
			console.log('recipeName: ' + recipeName);
			$location.path('/recipe/' + recipeName);
		}
		
		$scope.getAllRecipes();
			
		/*generate random menu*/
		$scope.getRandomMenu = function() {
			$http.get("/mealcraft/webapi/recipes/random")
			.then(function(response) {
				$scope.randomMenu = response.data;
				for (let i = 0; i < $scope.randomMenu.length; i++) {
					  $scope.addToMenu($scope.randomMenu[i]);
					}
			}, function(response) {
				console.log('error http GET random: ' + response.status);
			});
		}
		
		/*add single recipe to menu*/
		$scope.addToMenu = function(recipe) {
			if (localStorage.getItem('menu') != null) {
				$scope.menu = JSON.parse(localStorage.getItem('menu'));
				$scope.menu.push(recipe);
				localStorage.setItem('menu', JSON.stringify($scope.menu));
			} else {
				// new menu
				$scope.menu = [recipe];
				localStorage.setItem('menu', JSON.stringify($scope.menu));
			}			
		}
		
		$scope.getMenu = function() {
			if (localStorage.getItem('menu') != null) {
				$scope.menu = JSON.parse(localStorage.getItem('menu'));				
			} else {				
				$scope.menu = [];				
			}			
		}
		
		$scope.getMenu();
		$scope.viewMenu = false;
		
		$scope.viewMyMenu = function() {			
			$scope.viewMenu = true;
		}
		
		$scope.hideMyMenu = function() {			
			$scope.viewMenu = false;
		}
		
		$scope.emptyMyMenu = function() {
			if (localStorage.getItem('menu') != null) {
				$scope.menu = [];
				localStorage.setItem('menu', JSON.stringify($scope.menu));			
			}			
		}
			
	});

})()