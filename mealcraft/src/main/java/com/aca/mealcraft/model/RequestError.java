package com.aca.mealcraft.model;

public class RequestError {

	private Integer id;
	private String message;

	public RequestError(Integer id, String message) {
		this.id = id;
		this.message = message;
	}

	public RequestError() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
