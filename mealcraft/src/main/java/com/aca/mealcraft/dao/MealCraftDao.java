package com.aca.mealcraft.dao;

import java.util.ArrayList;
import java.util.List;

import com.aca.mealcraft.model.Recipe;

public interface MealCraftDao {
	public List<Recipe> getRecipes();
	public List<Recipe> getRecipesByCategory(String category);
//	public List<Recipe> getRecipesByIngredient(String ingredient);
	public List<Recipe> getRecipeById(Integer recipeId);
	public List<Recipe> getRecipesByName(String name);
	public Recipe createRecipe(Recipe newRecipe);
	public Recipe updateRecipe(Recipe updateRecipe);
	public Recipe deleteRecipe(Integer recipeId);
	List<Recipe> getFullRecipeByName(String name);
	public List<String> showIngredientsList(String name);
	public List<Recipe> getRandomMenu();
	public List<String> showIngredientsTable();
	public List<Recipe> getFullRecipeForUpdate(String name);
}
