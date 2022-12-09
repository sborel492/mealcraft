package com.aca.mealcraft.dao;

import java.util.ArrayList;
import java.util.List;

import com.aca.mealcraft.model.Recipe;

public abstract class MealCraftDaoMock implements MealCraftDao {

	private static Integer lastRecipeId = 0;
	private static List<Recipe> recipes = new ArrayList<Recipe>();

	private static Integer getNextRecipeId() {
		return ++lastRecipeId;
	}

	static {
		Recipe mtlf = new Recipe();
		mtlf.setName("Meatloaf");
		mtlf.setId(getNextRecipeId());
		mtlf.setCategory("Main");
		mtlf.setDirections("- Preheat oven to 375 degrees F."
					   + "\n- Crush crackers"
					   + "\n- Place all ingredients in a large mixing bowl and mix together by hand."
					   + "\n- In a 9x13 pan, form two loaves."
					   + "\n- Bake for 90 minutes, remove and top with a bit more ketchup, bake an additional 15 minutes.");
		ArrayList<String> ingredientsListMtlf = new ArrayList<String>();
		ingredientsListMtlf.add("2 lbs ground chuck");
		ingredientsListMtlf.add("1 lb ground hot sausage");
		ingredientsListMtlf.add("2 eggs");
		ingredientsListMtlf.add("15 buttery crackers");
		ingredientsListMtlf.add("1 tsp salt");
		ingredientsListMtlf.add("1 tsp pepper");
		ingredientsListMtlf.add("1 tsp garlic salt");
		ingredientsListMtlf.add("1 tsp dried onion");
		ingredientsListMtlf.add("1 Tbsp worcestershire sauce");
		ingredientsListMtlf.add("1 Tbsp mustard");
		ingredientsListMtlf.add("1 Tbsp ketchup");
		mtlf.setIngredientsList(ingredientsListMtlf);

		recipes.add(mtlf);
	}

	@Override
	public List<Recipe> getRecipes() {
		List<Recipe> myRecipes = new ArrayList<Recipe>();
		myRecipes.addAll(recipes);
		return myRecipes;
	}

	@Override
	public List<Recipe> getRecipesByCategory(String category) {
		List<Recipe> myRecipes = new ArrayList<Recipe>();
		for (Recipe recipe : MealCraftDaoMock.recipes) {
			if (recipe.getCategory().equals(category)) {
				myRecipes.add(recipe);
			}
		}
		return myRecipes;
	}

//	@Override
//	public List<Recipe> getRecipesByIngredient(String ingredient) {
//		List<Recipe> myRecipes = new ArrayList<Recipe>();
//		for (Recipe recipe : MealCraftDaoMock.recipes) {
//			if (recipe.getIngredientsList().contains(ingredient)) {
//				myRecipes.add(recipe);
//			}
//		}
//		return myRecipes;
//	}

	@Override
	public List<Recipe> getRecipeById(Integer recipeId) {
		List<Recipe> myRecipes = new ArrayList<Recipe>();
		for (Recipe recipe : MealCraftDaoMock.recipes) {
			if (recipe.getId().intValue() == recipeId.intValue()) {
				myRecipes.add(recipe);
			}
		}
		return myRecipes;
	}

	@Override
	public List<Recipe> getRecipesByName(String name) {
		List<Recipe> myRecipes = new ArrayList<Recipe>();
		for (Recipe recipe : MealCraftDaoMock.recipes) {
			if (recipe.getName().toLowerCase().contains(name.toLowerCase())) {
				myRecipes.add(recipe);
			}
		}
		return myRecipes;
	}

	@Override
	public Recipe createRecipe(Recipe newRecipe) {
		newRecipe.setId(getNextRecipeId());
		recipes.add(newRecipe);
		return newRecipe;
	}

	@Override
	public Recipe updateRecipe(Recipe updateRecipe) {
		List<Recipe> recipes = getRecipeById(updateRecipe.getId());
		ArrayList<String> ingredientsList = new ArrayList<String>();

		if (recipes.size() > 0) {
			Recipe recipe = recipes.get(0);
			recipe.setCategory(updateRecipe.getCategory());
			recipe.setDirections(updateRecipe.getDirections());
			recipe.setName(updateRecipe.getName());			
			ingredientsList.add("30 lbs lard");
			recipe.setIngredientsList(ingredientsList);
		}

		return updateRecipe;
	}

	@Override
	public Recipe deleteRecipe(Integer recipeId) {
		List<Recipe> recipes = getRecipeById(recipeId);
		Recipe recipe = null;

		if (recipes.size() > 0) {
			recipe = recipes.get(0);
			MealCraftDaoMock.recipes.remove(recipe);
		}

		return recipe;
	}

	@Override
	public List<Recipe> getFullRecipeByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> showIngredientsList(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Recipe> getRandomMenu() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<Movie> getReport(Integer startReleaseYear, Integer endReleaseYear) {
//		List<Movie> myRecipes = new ArrayList<Movie>();
//		for (Movie movie : movies) {
//			if (movie.getReleaseYear() >= startReleaseYear) {
//				if (movie.getReleaseYear() <= endReleaseYear) {
//					myRecipes.add(movie);
//				}
//			}
//		}
//		return myRecipes;
//	}

}
