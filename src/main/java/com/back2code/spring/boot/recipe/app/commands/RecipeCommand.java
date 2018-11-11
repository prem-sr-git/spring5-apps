package com.back2code.spring.boot.recipe.app.commands;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.back2code.spring.boot.recipe.app.domain.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RecipeCommand {
	private Long id;
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	private BigDecimal rating; 
	private String directions;
	private Set<IngredientCommand> ingredients = new HashSet<>();
	private Difficulty difficulty;
	private Byte[] image;
	private NotesCommand notes;
	private Set<CategoryCommand> categories = new HashSet<>();
	
}
