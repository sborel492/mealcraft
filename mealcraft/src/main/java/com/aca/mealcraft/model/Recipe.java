package com.aca.mealcraft.model;


import java.util.ArrayList;


public class Recipe {
	private String name;
	private String category;
	private Integer id;
	private ArrayList<Integer> rowId;
	private ArrayList<String> ingredientsList;
	private ArrayList<String> measurements;
	private String directions;
	private String picUrl;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public ArrayList<Integer> getRowId() {
		return rowId;
	}
	public void setRowId(ArrayList<Integer> rowId) {
		this.rowId = rowId;
	}
	public ArrayList<String> getIngredientsList() {
		return ingredientsList;
	}
	public void setIngredientsList(ArrayList<String> ingredientsList) {
		this.ingredientsList = ingredientsList;
	}
	public ArrayList<String> getMeasurements() {
		return measurements;
	}
	public void setMeasurements(ArrayList<String> measurements) {
		this.measurements = measurements;
	}
	public String getDirections() {
		return directions;
	}
	public void setDirections(String directions) {
		this.directions = directions;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}
