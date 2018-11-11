package com.back2code.spring.boot.recipe.app.services;

import java.util.Set;

import com.back2code.spring.boot.recipe.app.commands.RecipeCommand;
import com.back2code.spring.boot.recipe.app.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();

	Recipe findById(Long recipeId);
	
	RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
}
