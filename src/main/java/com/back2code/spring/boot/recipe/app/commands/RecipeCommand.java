package com.back2code.spring.boot.recipe.app.commands;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.back2code.spring.boot.recipe.app.domain.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
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
	private List<IngredientCommand> ingredientCmdLst = new ArrayList<>();
	private Difficulty difficulty;
	private Byte[] image;
	private NotesCommand notes;
	private Set<CategoryCommand> categories = new HashSet<>();
	
	public void createIngredientsCmdList() {
		
		this.setIngredientCmdLst(new ArrayList<>(this.getIngredients()));
		
	}
	
	public Set<IngredientCommand> getIngredientsCmdSet(){
		
		Set<IngredientCommand> ingredientCmdsSet = new HashSet<>(this.getIngredientCmdLst());
		
		return ingredientCmdsSet;
	}
}
