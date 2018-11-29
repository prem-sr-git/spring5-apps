package com.back2code.spring.boot.recipe.app.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.back2code.spring.boot.recipe.app.commands.RecipeCommand;
import com.back2code.spring.boot.recipe.app.domain.Recipe;

import lombok.Synchronized;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

	private final CategoryCommandToCategory categoryConverter;
	private final IngredientCommandToIngredient ingredientConverter;
	private final NotesCommandToNotes notesConverter;

	public RecipeCommandToRecipe(CategoryCommandToCategory categoryConverter,
			IngredientCommandToIngredient ingredientConverter, NotesCommandToNotes notesConverter) {
		this.categoryConverter = categoryConverter;
		this.ingredientConverter = ingredientConverter;
		this.notesConverter = notesConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null) {
			return null;
		}

		final Recipe recipe = new Recipe();
		recipe.setId(source.getId());
		recipe.setDescription(source.getDescription());
		recipe.setCookTime(source.getCookTime());

		if (source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories()
				.forEach(category -> recipe.getCategories().add(categoryConverter.convert(category)));
		}

		recipe.setDifficulty(source.getDifficulty());
		recipe.setDirections(source.getDirections());
		recipe.setImage(source.getImage());

		if (source.getIngredients() != null && source.getIngredients().size() > 0) {
			source.getIngredients()
				.forEach(ingredient -> recipe.getIngredients().add(ingredientConverter.convert(ingredient)));
		}

		if (source.getNotes() != null) {
			recipe.setNotes(notesConverter.convert(source.getNotes()));
		}
		
		recipe.setPrepTime(source.getPrepTime());
		recipe.setRating(source.getRating());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());

		return recipe;
	}

}
