package com.back2code.spring.boot.recipe.app.domain;

public enum Difficulty {
	
	EASY("Easy"), MODERATE("Moderate"), KIND_OF_HARD("Kind of Hard"), HARD("Hard");
	
	private final String strValue;

	private Difficulty(String strValue) {
		this.strValue = strValue;
	}
	
	public String value() {
		return this.strValue;
	}
}
