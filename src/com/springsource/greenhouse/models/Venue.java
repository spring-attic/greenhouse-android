package com.springsource.greenhouse.models;

public class Venue {
	private String id;

	public Venue(String id) {
		this.id = id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
