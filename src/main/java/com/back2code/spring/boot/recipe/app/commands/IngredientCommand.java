package com.back2code.spring.boot.recipe.app.commands;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class IngredientCommand {
	private Long id;
	private Long recipeId;	
	private String description;
	private BigDecimal amount;
	private String amountInFracns;
	private UnitOfMeasureCommand uom;
}
