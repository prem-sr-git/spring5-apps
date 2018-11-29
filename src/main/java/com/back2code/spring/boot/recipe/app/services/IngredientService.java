package com.back2code.spring.boot.recipe.app.services;

import java.util.Set;

import com.back2code.spring.boot.recipe.app.commands.IngredientCommand;

public interface IngredientService {
	
	IngredientCommand findCommandById(Long ingredientId);
	
	void deleteById(Long ingredientId);
	
	IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
	
	Set<IngredientCommand> saveAllIngredientCommands(Set<IngredientCommand> ingredientCommands);

}
