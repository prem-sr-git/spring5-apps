package com.back2code.spring.boot.recipe.app.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.back2code.spring.boot.recipe.app.commands.IngredientCommand;
import com.back2code.spring.boot.recipe.app.domain.Ingredient;
import com.back2code.spring.boot.recipe.app.utils.DecimalToFractionConverter;

import lombok.Synchronized;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

	private final UnitOfMeasureToUnitOfMeasureCommand uomCommandConverter;

	public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand uomCommandConverter) {
		this.uomCommandConverter = uomCommandConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient source) {
		if (source == null) {
			return null;
		}

		final IngredientCommand ingredientCmd = new IngredientCommand();
		ingredientCmd.setId(source.getId());
		if (source.getRecipe() != null) {
			ingredientCmd.setRecipeId(source.getRecipe().getId());
		}
		ingredientCmd.setAmount(source.getAmount());
		
		if(source.getAmount() !=null) {
			ingredientCmd.setAmountInFracns(DecimalToFractionConverter.convert(source.getAmount()));
		}
		
		ingredientCmd.setDescription(source.getDescription());

		if (source.getUom() != null) {
			ingredientCmd.setUom(uomCommandConverter.convert(source.getUom()));
		}

		return ingredientCmd;
	}

}
