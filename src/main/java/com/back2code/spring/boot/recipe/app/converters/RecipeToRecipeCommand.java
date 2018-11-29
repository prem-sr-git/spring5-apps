package com.back2code.spring.boot.recipe.app.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.back2code.spring.boot.recipe.app.commands.RecipeCommand;
import com.back2code.spring.boot.recipe.app.domain.Recipe;

import lombok.Synchronized;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

	private final CategoryToCategoryCommand categoryCommandConverter;
	private final IngredientToIngredientCommand ingredientCommandConverter;
	private final NotesToNotesCommand notesCommandConverter;

	public RecipeToRecipeCommand(CategoryToCategoryCommand categoryCommandConverter,
			IngredientToIngredientCommand ingredientCommandConverter, NotesToNotesCommand notesCommandConverter) {
		this.categoryCommandConverter = categoryCommandConverter;
		this.ingredientCommandConverter = ingredientCommandConverter;
		this.notesCommandConverter = notesCommandConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public RecipeCommand convert(Recipe source) {
		if (source == null) {
			return null;
		}

		final RecipeCommand recipeCmd = new RecipeCommand();
		recipeCmd.setId(source.getId());
		recipeCmd.setId(source.getId());
		recipeCmd.setDescription(source.getDescription());
		recipeCmd.setCookTime(source.getCookTime());

		if (source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories().forEach(Category -> recipeCmd.getCategories().add(categoryCommandConverter.convert(Category)));
		}

		recipeCmd.setDifficulty(source.getDifficulty());
		recipeCmd.setDirections(source.getDirections());
		recipeCmd.setImage(source.getImage());
		
		if (source.getIngredients()!=null && source.getIngredients().size()>0){
			source.getIngredients()
				.forEach(ingredient -> recipeCmd.getIngredients().add(ingredientCommandConverter.convert(ingredient)));
		}
		
		if(source.getNotes()!=null) {
			recipeCmd.setNotes(notesCommandConverter.convert(source.getNotes()));
		}
		
		recipeCmd.setPrepTime(source.getPrepTime());
		recipeCmd.setRating(source.getRating());
		recipeCmd.setServings(source.getServings());
		recipeCmd.setSource(source.getSource());
		recipeCmd.setUrl(source.getUrl());
		return recipeCmd;
	}

}
