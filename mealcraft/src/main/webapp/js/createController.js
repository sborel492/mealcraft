(function() {
	let mealcraftapp = angular.module('mealcraftapp');

	mealcraftapp.controller('createController', function($scope, $http) {		
		
		$scope.categories = ['Main', 'Soup', 'Slow Cooker', 'Breakfast','One Pot', 'Appetizer', 'Pasta', 'Dessert', 'Salad', 'Side', 'Vegan', 'Vegetarian'];
		
		$scope.getAllIngredients = function() {
			$http.get("/mealcraft/webapi/recipes/ingTable")
			.then(function(response) {				
				$scope.ingTable = response.data;
			});
		}
		
		$scope.getAllIngredients();
		
		 $scope.inputs = [{
		      ingredient: '',
		      measurement: ''
		    }
		  ];
		  $scope.log = function() {
		    console.log($scope.inputs);
		  };
		  $scope.add = function() {
		    var newLine = {
    		 ingredient: '',
		     measurement: ''
		    };
		    $scope.inputs.push(newLine);
		  }
		
		$scope.createRecipe = function() {
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
			
			$http.post("/mealcraft/webapi/recipes", $scope.recipe)
			.then(function(response) {				
				$scope.createStatus = 'create successful';
				$scope.disableCreate = true;
			}, function(response) {
				$scope.createStatus = 'error trying to create recipe';	
				console.log('error http POST recipe: ' + response.status);
			});
		}
		
		$scope.clear = function() {
			$scope.recipe.name = '';
			$scope.recipe.directions = '';
			$scope.recipe.category = '';
			$scope.inputs = [{ingredient: '', measurement: ''}];
			$scope.createForm.$setUntouched();
			$scope.createForm.$setPristine();
			$scope.disableCreate = false;
			$scope.createStatus = '';
		}
		
	});
	
})()