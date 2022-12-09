package com.aca.mealcraft.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.aca.mealcraft.model.Recipe;
import com.aca.mealcraft.dao.MariaDbUtil;

public class MealCraftImpl implements MealCraftDao {
	
	private static String selectAllRecipes =
			"SELECT * FROM recipes";
	
	private static String selectRecipesByCategory =
			"SELECT * FROM recipes "
			+ "WHERE categoryName = ?";
	
//	private static String selectRecipesByIngredient =
//			"";
	
	private static String selectRecipeById =
			"SELECT * FROM recipes "
			+ "WHERE recipeId = ?";
	
	private static String selectRecipesByName =
			"SELECT * FROM recipes "
			+ "WHERE UPPER(recipeName) LIKE ?";
	
	private static String selectRecipeByName =
			"SELECT * FROM recipes " + 
			"WHERE recipeName = ?";
	
	private static String selectIngListByName =
			"SELECT CONCAT(measurement,' ', ingName) AS ingList " + 
			"FROM recipe_ingredient " + 
			"WHERE recipeName = ?";
	
	private static String selectIngredientsByName =
			"SELECT ingName " + 
			"FROM recipe_ingredient " + 
			"WHERE recipeName = ?";
	
	private static String selectMsrListByName =
			"SELECT measurement " + 
			"FROM recipe_ingredient " + 
			"WHERE recipeName = ?";

	private static String selectRowIdsByName =
			"SELECT rowId " + 
			"FROM recipe_ingredient " + 
			"WHERE recipeName = ?";
	
	private static String insertIntoRecipe =
			"INSERT INTO recipes (recipeName, directions, categoryName) " + 
			"VALUES " + 
			"(?, ?, ?)";
	
	private static String insertIntoRI=
			"INSERT INTO recipe_ingredient (recipeName, measurement, ingName) " + 
			"VALUES " + 
			"(?, ?, ?)";
	
	private static String updateRecipeById =
			"UPDATE recipes " + 
			"SET recipeName = ?, " + 
			"	 directions = ?, " + 
			"	 categoryName = ?, " + 
			"	 picUrl = ? " + 
			"WHERE recipeId = ?";
	
	private static String updateRiByIng =
			"UPDATE recipe_ingredient " + 
			"SET measurement = ?, " + 
			"	 ingName= ? " +
			"WHERE rowId = ?";
	
	private static String deleteRecipeById =
			"DELETE FROM recipes "
			+ "WHERE recipeId = ?";
	
	private static String selectNewRecipeId =
			"SELECT LAST_INSERT_ID() AS 'recipeId'";
	
	private static String selectRandomMenu =
			"SELECT * FROM recipes " + 
			"WHERE categoryName NOT IN('Appetizer', 'Side', 'Breakfast', 'Dessert', 'Salad') " + 
			"ORDER BY RAND() LIMIT 7";

	private static String selectAllIng =
			"SELECT ingName AS ingredientsList FROM ingredients";

	
	

	private List<Recipe> makeRecipes(ResultSet result) throws SQLException {
		List<Recipe> myRecipes = new ArrayList<Recipe>();
		
		
		while(result.next()) {
			Recipe recipe = new Recipe();
			ArrayList<String> ingredientsList = new ArrayList<String>();
			ArrayList<Integer> rowIds = new ArrayList<Integer>();
			recipe.setId(result.getInt("recipeId"));
			recipe.setName(result.getString("recipeName"));	
			recipe.setDirections(result.getString("directions"));
			recipe.setPicUrl(result.getString("picUrl"));
			recipe.setCategory(result.getString("categoryName"));
			ingredientsList.addAll(showIngredientsList(result.getString("recipeName")));
			recipe.setIngredientsList(ingredientsList);
			rowIds.addAll(getRowId(result.getString("recipeName")));
			recipe.setRowId(rowIds);
			myRecipes.add(recipe);
		}
		
		return myRecipes;
	}
	
	private ArrayList<Integer> getRowId(String name) {
		ArrayList<Integer> rowIds = new ArrayList<Integer>();
		PreparedStatement ps = null;
		ResultSet result2 = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(selectRowIdsByName);
			ps.setString(1, name);
			result2 = ps.executeQuery();
			rowIds = makeRowIdsList(result2);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result2.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rowIds;
	}

	private ArrayList<Integer> makeRowIdsList(ResultSet result) throws SQLException {
		ArrayList<Integer> rowIds = new ArrayList<Integer>();
		
		while(result.next()) {
			rowIds.add(result.getInt("rowId"));
		}
		
		return rowIds;
	}

	public ArrayList<String> showIngredientsList(String name) {
		ArrayList<String> ingList = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet result3 = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(selectIngListByName);
			ps.setString(1, name);
			result3 = ps.executeQuery();
			ingList = makeIngList(result3);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result3.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ingList;
	}

	private ArrayList<String> makeIngList(ResultSet result) throws SQLException {
		ArrayList<String> ingList = new ArrayList<String>();
		
		while(result.next()) {
			ingList.add(result.getString("ingList"));
		}
		
		return ingList;
	}
	
	private int getNewRecipeId(Connection conn) {
		ResultSet rs = null;
		Statement statement = null;
		int newRecipeId = 0;
		
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(selectNewRecipeId);
			while(rs.next()) {
				newRecipeId = rs.getInt("recipeId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return newRecipeId;
	}
	
	@Override
	public List<Recipe> getRecipes() {
		List<Recipe> myRecipes = new ArrayList<Recipe>();
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(selectAllRecipes);
			myRecipes = makeRecipes(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return myRecipes;
	}

	@Override
	public List<Recipe> getRecipesByCategory(String category) {
		List<Recipe> myRecipes = new ArrayList<Recipe>();
		ResultSet result = null;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(selectRecipesByCategory);
			ps.setString(1, category.toString());
			result = ps.executeQuery();
			myRecipes = makeRecipes(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return myRecipes;
	}

//	@Override
//	public List<Recipe> getRecipesByIngredient(String ingredient) {
//		
//		return null;
//	}

	@Override
	public List<Recipe> getRecipeById(Integer recipeId) {
		List<Recipe> myRecipes = new ArrayList<Recipe>();
		ResultSet result = null;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(selectRecipeById);
			ps.setInt(1, recipeId);
			result = ps.executeQuery();
			myRecipes = makeRecipes(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return myRecipes;
	}

	@Override
	public List<Recipe> getRecipesByName(String name) {
		List<Recipe> myRecipes = new ArrayList<Recipe>();
		ResultSet result = null;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(selectRecipesByName);
			name = "%" + name.toUpperCase() + "%";
			ps.setString(1, name);
			result = ps.executeQuery();
			myRecipes = makeRecipes(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return myRecipes;
	}
	
	@Override
	public List<Recipe> getFullRecipeByName(String name) {
		List<Recipe> myRecipe = null;
		ResultSet result = null;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(selectRecipeByName);

			ps.setString(1, name);
			result = ps.executeQuery();
			myRecipe = makeRecipes(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return myRecipe;
	}
	
	@Override
	public List<Recipe> getFullRecipeForUpdate(String name) {
		List<Recipe> myRecipe = null;
		ResultSet result = null;
		PreparedStatement ps = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(selectRecipeByName);

			ps.setString(1, name);
			result = ps.executeQuery();
			myRecipe = makeRecipeForUpdate(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return myRecipe;
	}
	
	private List<Recipe> makeRecipeForUpdate(ResultSet result) throws SQLException {
		List<Recipe> myRecipes = new ArrayList<Recipe>();
		
		
		while(result.next()) {
			Recipe recipe = new Recipe();
			ArrayList<String> ingredientsList = new ArrayList<String>();
			ArrayList<String> measurements = new ArrayList<String>();
			ArrayList<Integer> rowIds = new ArrayList<Integer>();
			recipe.setId(result.getInt("recipeId"));
			recipe.setName(result.getString("recipeName"));	
			recipe.setDirections(result.getString("directions"));
			recipe.setPicUrl(result.getString("picUrl"));
			recipe.setCategory(result.getString("categoryName"));
			ingredientsList.addAll(justIngredients(result.getString("recipeName")));
			recipe.setIngredientsList(ingredientsList);
			measurements.addAll(getMeasurements(result.getString("recipeName")));
			recipe.setMeasurements(measurements);
			rowIds.addAll(getRowId(result.getString("recipeName")));
			recipe.setRowId(rowIds);
			myRecipes.add(recipe);
		}
		
		return myRecipes;
	}

	private ArrayList<String> getMeasurements(String name) {
		ArrayList<String> measurements = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet result = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(selectMsrListByName);
			ps.setString(1, name);
			result = ps.executeQuery();
			measurements = makeMeasurementsList(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return measurements;
	}

	private ArrayList<String> makeMeasurementsList(ResultSet result) throws SQLException {
		ArrayList<String> measurements = new ArrayList<String>();
		
		while(result.next()) {
			measurements.add(result.getString("measurement"));
		}
		
		return measurements;
	}

	private ArrayList<String> justIngredients(String name) {
		ArrayList<String> ingredients = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet result = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			ps = conn.prepareStatement(selectIngredientsByName);
			ps.setString(1, name);
			result = ps.executeQuery();
			ingredients = makeIngredients(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ingredients;
	}

	private ArrayList<String> makeIngredients(ResultSet result) throws SQLException {
		ArrayList<String> ingredients = new ArrayList<String>();
		
		while(result.next()) {
			ingredients.add(result.getString("ingName"));
		}
		
		return ingredients;
	}

	@Override
	public Recipe createRecipe(Recipe newRecipe) {
		int updateRowCount = 0;
		PreparedStatement ps = null;
		Connection conn = MariaDbUtil.getConnection();
			
		try {
			ps = conn.prepareStatement(insertIntoRecipe);			
			ps.setString(1, newRecipe.getName());
			ps.setString(2, newRecipe.getDirections());
			ps.setString(3, newRecipe.getCategory().toString());			
			updateRowCount = ps.executeUpdate();
			System.out.println("insert row count: " + updateRowCount);
			insertIntoRI(newRecipe);
			int newRecipeId = getNewRecipeId(conn);
			newRecipe.setId(newRecipeId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return newRecipe;
	}

	private void insertIntoRI(Recipe newRecipe) {
		PreparedStatement ps2 = null;
		int[] updateRowCount2;
		Connection conn = MariaDbUtil.getConnection();
		int i = 0; 
		
		try {
			ps2 = conn.prepareStatement(insertIntoRI);
		for (String ing : newRecipe.getIngredientsList()) {			
			ps2.setString(1, newRecipe.getName());
			ps2.setString(2, newRecipe.getMeasurements().get(i++));
			ps2.setString(3, ing);
			ps2.addBatch();	
		}
		updateRowCount2 = ps2.executeBatch();
		System.out.println("insert row count2: " + updateRowCount2);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps2.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Recipe updateRecipe(Recipe updateRecipe) {
		List<Recipe> recipes = this.getRecipeById(updateRecipe.getId());
		
		if (recipes.size() > 0) {
			int updateRowCount = 0;
			PreparedStatement ps = null;
			
			Connection conn = MariaDbUtil.getConnection();
			
			try {
				ps = conn.prepareStatement(updateRecipeById);
				ps.setString(1, updateRecipe.getName());
				ps.setString(2, updateRecipe.getDirections());
				ps.setString(3, updateRecipe.getCategory().toString());
				ps.setString(4, updateRecipe.getPicUrl());
				ps.setInt(5, updateRecipe.getId());
				
				System.out.println("update row count: " + updateRowCount);
				updateRI(updateRecipe);
				
				updateRowCount = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					ps.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return updateRecipe;
	}
	
	private void updateRI(Recipe updateRecipe) {
		PreparedStatement ps2 = null;
		int[] updateRowCount2;
		Connection conn = MariaDbUtil.getConnection();
		int i = 0; 
		
		try {
			ps2 = conn.prepareStatement(updateRiByIng);
		for (String ing : updateRecipe.getIngredientsList()) {				
			ps2.setString(1, updateRecipe.getMeasurements().get(i));
			ps2.setString(2, ing);
			ps2.setInt(3, updateRecipe.getRowId().get(i++));
			ps2.addBatch();	
		}
		
		updateRowCount2 = ps2.executeBatch();
		System.out.println("insert row count2: " + updateRowCount2);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps2.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public Recipe deleteRecipe(Integer recipeId) {
		List<Recipe> recipes = this.getRecipeById(recipeId);
		Recipe recipeToDelete = null;
		
		if (recipes.size() > 0) {
			recipeToDelete = recipes.get(0);
			int updateRowCount = 0;
			PreparedStatement ps = null;
			
			Connection conn = MariaDbUtil.getConnection();
			
			try {
				ps = conn.prepareStatement(deleteRecipeById);
				ps.setInt(1, recipeId);
				updateRowCount = ps.executeUpdate();
//				System.out.println("update row count: " + updateRowCount);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					ps.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return recipeToDelete;
	}

	@Override
	public List<Recipe> getRandomMenu() {
		
		List<Recipe> myMenu = new ArrayList<Recipe>();
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(selectRandomMenu);
			myMenu = makeRecipes(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return myMenu;
	}

	@Override
	public List<String> showIngredientsTable() {
		List<String> ingTable = new ArrayList<String>();
		ResultSet result = null;
		Statement statement = null;
		
		Connection conn = MariaDbUtil.getConnection();
		
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(selectAllIng);
			ingTable = makeIngTable(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				result.close();
				statement.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ingTable;
	}

	private List<String> makeIngTable(ResultSet result) throws SQLException {
		List<String> ingTable = new ArrayList<String>();
		while(result.next()) {
			ingTable.add(result.getString("ingredientsList"));
		}
		return ingTable;
	}
	
//	@Override
//	public List<Recipe> getReport(Integer startCreateDateTime, Integer endCreateDateTime) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
