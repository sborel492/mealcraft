package com.aca.mealcraft.controller;

import java.util.List;

import com.aca.mealcraft.model.Recipe;
import com.aca.mealcraft.service.MealCraftService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
//import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/recipes")
@Produces(MediaType.APPLICATION_JSON)
public class MealCraftController {

	private MealCraftService service = new MealCraftService();
	
	@GET
	public List<Recipe> getRecipes() {
		return service.getRecipes();
	}

	@GET
	@Path("/category/{categoryValue}")
	public List<Recipe> getRecipesByCategory(@PathParam("categoryValue") String category) {
		return service.getRecipesByCategory(category);
	}

	@GET
	@Path("/id/{recipeIdValue}")
	public List<Recipe> getRecipeById(@PathParam("recipeIdValue") Integer recipeId) {
		return service.getRecipeById(recipeId);
	}

	@GET
	@Path("/name/{nameValue}")
	public List<Recipe> getRecipesByName(@PathParam("nameValue") String name) {
		return service.getRecipesByName(name);
	}
	
	@GET
	@Path("/ingTable")
	public List<String> showIngredientsList() {
		return service.showIngredientsTable();
	}
	
	@GET
	@Path("/rv/{nameValue}")
	public List<Recipe> getFullRecipeByName(@PathParam("nameValue") String name) {
		return service.getFullRecipeByName(name);
	}
	
	@GET
	@Path("/up/{nameValue}")
	public List<Recipe> getFullRecipeForUpdate(@PathParam("nameValue") String name) {
		return service.getFullRecipeForUpdate(name);
	}
	
	@GET
	@Path("/random")
	public List<Recipe> getRandomMenu() {
		return service.getRandomMenu();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Recipe createRecipe(Recipe newRecipe) {
		return service.createRecipe(newRecipe);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Recipe updateRecipe(Recipe updateRecipe) {
		return service.updateRecipe(updateRecipe);
	}

	@DELETE
	@Path("/{recipeIdValue}")
	public Recipe deleteRecipe(@PathParam("recipeIdValue") Integer recipeId) {
		return service.deleteRecipe(recipeId);
	}

}
