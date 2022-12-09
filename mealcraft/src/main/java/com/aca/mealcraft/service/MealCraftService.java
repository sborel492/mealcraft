package com.aca.mealcraft.service;

import java.util.ArrayList;
import java.util.List;

import com.aca.mealcraft.dao.MealCraftDao;
import com.aca.mealcraft.dao.MealCraftDaoMock;
import com.aca.mealcraft.dao.MealCraftImpl;
import com.aca.mealcraft.model.Recipe;
import com.aca.mealcraft.model.RequestError;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
public class MealCraftService {
	
//	private MealCraftDao mealcraftDao = new MealCraftDaoMock();
	private MealCraftDao mealcraftDao = new MealCraftImpl();

	public List<Recipe> getRecipes() {
		return mealcraftDao.getRecipes();
	}

	public List<Recipe> getRecipesByCategory(String category) {
		return mealcraftDao.getRecipesByCategory(category);
	}

//	public List<Recipe> getRecipesByIngredient(String ingredient) {
//		return mealcraftDao.getRecipesByIngredient(ingredient);
//	}

	public List<Recipe> getRecipeById(Integer recipeId) {
		validateId(recipeId);
		return mealcraftDao.getRecipeById(recipeId);
	}

	public List<Recipe> getRecipesByName(String name) {
		validateName(name);
		return mealcraftDao.getRecipesByName(name);
	}
	
	public List<Recipe> getFullRecipeByName(String name) {
		validateName(name);
		return mealcraftDao.getFullRecipeByName(name);
	}

	public Recipe createRecipe(Recipe newRecipe) {
		return mealcraftDao.createRecipe(newRecipe);
	}

	public Recipe updateRecipe(Recipe updateRecipe) {
		return mealcraftDao.updateRecipe(updateRecipe);
	}

	public Recipe deleteRecipe(Integer recipeId) {
		validateId(recipeId);
		return mealcraftDao.deleteRecipe(recipeId);
	}
	
	public List<Recipe> getRandomMenu() {
		return mealcraftDao.getRandomMenu();
	}

//	public List<Recipe> getReport(Integer startCreateDateTime, Integer endCreateDateTime) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	private void validateId(Integer recipeId) {
		List<Recipe> recipes = mealcraftDao.getRecipeById(recipeId);

		if (recipes.size() == 0) {
			RequestError error = new RequestError();
			error.setId(3);
			error.setMessage("Invalid value '" + recipeId + "' for recipe ID. Recipe ID does not exist.");
			Response response = Response.status(400).entity(error).build();
			throw new WebApplicationException(response);
		}

	}

	private void validateReleaseYear(Integer releaseYear) {
		if (null == releaseYear || releaseYear < 1935 || releaseYear > 2025) {
			RequestError error = new RequestError();
			error.setId(2);
			error.setMessage("Invalid value '" + releaseYear
					+ "' for release year. Value must be greater than 1935 and less than 2025.");
			Response response = Response.status(400).entity(error).build();
			throw new WebApplicationException(response);
		}

	}

	private void validateName(String name) {
		if (name == null || name.length() < 1) {
			RequestError error = new RequestError();
			error.setId(1);
			error.setMessage("Invalid value '" + name + "' for name. Value must contain one or more characters.");
			Response response = Response.status(400).entity(error).build();
			throw new WebApplicationException(response);
		}
	}

	public List<String> showIngredientsList(String name) {
		validateName(name);
		return mealcraftDao.showIngredientsList(name);
	}

	public List<String> showIngredientsTable() {
		return mealcraftDao.showIngredientsTable();
	}

	public List<Recipe> getFullRecipeForUpdate(String name) {
		validateName(name);
		return mealcraftDao.getFullRecipeForUpdate(name);
	}

}
