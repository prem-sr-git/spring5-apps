package com.back2code.spring.boot.recipe.app.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.back2code.spring.boot.recipe.app.commands.IngredientCommand;
import com.back2code.spring.boot.recipe.app.domain.Ingredient;
import com.back2code.spring.boot.recipe.app.domain.Recipe;

import lombok.Synchronized;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

	private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
		this.uomConverter = uomConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		if (source == null) {
			return null;
		}
		if(source.getRecipeId() == null) {
		 throw new RuntimeException("Missing Recipe Id");	
		}
		
		final Ingredient ingredient = new Ingredient();
		final Recipe recipe = new Recipe();

		recipe.setId(source.getRecipeId());
		ingredient.setId(source.getId());
		ingredient.setAmount(source.getAmount());
		ingredient.setDescription(source.getDescription());
		ingredient.setRecipe(recipe);

		if (source.getUom() != null) {
			ingredient.setUom(uomConverter.convert(source.getUom()));
		}

		return ingredient;
	}

}
