(function() {
	let mealcraftapp = angular.module('mealcraftapp', ['ngRoute']);
	
	mealcraftapp.config(function($routeProvider) {
		  $routeProvider
		  .when("/menu", {
		    templateUrl : "menu.html",
		    controller : "menuController"
		  })
		      .when("/recipe/:recipeName", {
		    templateUrl : "recipe.html",
		    controller : "recipeController"
		  })
		    .when("/update/:recipeName", {
		    templateUrl : "update.html",
		    controller : "updateController"
		  })
		  .when("/create", {
		    templateUrl : "create.html",
		    controller : "createController"
		  })
		  .when("/stack", {
		    templateUrl : "stack.html"
		  })
		  .when("/resume", {
		    templateUrl : "resume.html"
		  })
		  .when("/main", {
			templateUrl : "main.html"
		  })
		  .otherwise({
			templateUrl: "main.html"
		  });
		});
	
})()