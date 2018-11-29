package com.back2code.spring.boot.recipe.app.controllers;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.back2code.spring.boot.recipe.app.commands.IngredientCommand;
import com.back2code.spring.boot.recipe.app.commands.RecipeCommand;
import com.back2code.spring.boot.recipe.app.commands.UnitOfMeasureCommand;
import com.back2code.spring.boot.recipe.app.services.IngredientService;
import com.back2code.spring.boot.recipe.app.services.RecipeService;
import com.back2code.spring.boot.recipe.app.services.UnitOfMeasureService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	
	private final UnitOfMeasureService uomService;
	
	private final IngredientService ingredientService;

	public IngredientController(RecipeService recipeService, UnitOfMeasureService uomService, IngredientService ingredientService) {
		this.recipeService = recipeService;
		this.uomService = uomService;
		this.ingredientService = ingredientService;
	}

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String getRecipeIngredients(@PathVariable String recipeId, Model model) {
		
		RecipeCommand recipeCommand;
		
		if (!recipeId.equals("null")) {
			
			log.debug("@IngredientController >>>>>  getting Ingredients for Recipe ID[" + recipeId + "]");
			
			recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
			
		} else {
			
			recipeCommand = new RecipeCommand();
			
		}
		recipeCommand.createIngredientsCmdList();
		
		model.addAttribute("recipe", recipeCommand);
		
		log.debug("@IngredientController >>>>>  getting Unit ofMeasureCommands");
		Set<UnitOfMeasureCommand> uomCmds =  uomService.getAllUOMCommands();
		
		
		model.addAttribute("uoms", uomCmds);
		
		
		return "/recipe/ingredients/ingredients-list";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredients/addRow")
	public String addNewIngredient(@PathVariable String recipeId, Model model) {

		if (!recipeId.equals("null")) {

			RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
			// TODO make sure recipeId is valid else exception handling
		}
		IngredientCommand ingredientCmd = new IngredientCommand();
		if (!recipeId.equals("null")) {

			ingredientCmd.setRecipeId(Long.valueOf(recipeId));
		}
		log.debug("@IngredientController >>>>>  adding new Row for Ingredients for [" + recipeId + "]");

		model.addAttribute("ingredient", ingredientCmd);

		log.debug("@IngredientController >>>>>  getting Unit ofMeasureCommands for New Ingredient");

		Set<UnitOfMeasureCommand> uomCmds = uomService.getAllUOMCommands();

		model.addAttribute("uoms", uomCmds);
		model.addAttribute("operation", "Add New");

		return "/recipe/ingredients/ingredient-form";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredients/{ingredientId}/update")
	public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		
		IngredientCommand ingredientCmd = ingredientService.findCommandById(Long.valueOf(ingredientId));
		
		log.debug("@IngredientController >>>>>  adding new Row for Ingredients for ["+recipeId+"]");

		model.addAttribute("ingredient", ingredientCmd);
		
		log.debug("@IngredientController >>>>>  getting Unit ofMeasureCommands for New Ingredient");
		
		Set<UnitOfMeasureCommand> uomCmds =  uomService.getAllUOMCommands();
		
		model.addAttribute("uoms", uomCmds);
		model.addAttribute("operation", "Update");
		
		return "/recipe/ingredients/ingredient-form";
	}
	
	@PostMapping("/recipe/{recipeId}/ingredients/saveOrUpdate")
	public String saveOrUpdateIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
		
		log.debug("@IngredientController >>>>>  saving new Ingredient for ["+ingredientCommand.getRecipeId()+"]");
		
		IngredientCommand savedIngredientCmd = ingredientService.saveIngredientCommand(ingredientCommand);

		return "redirect:/recipe/"+savedIngredientCmd.getRecipeId()+"/ingredients";
	}
	
	@PostMapping("/recipe/{recipeId}/ingredients/saveAll")
	public String updateIngredients(@ModelAttribute RecipeCommand recipe) {
		
		log.debug("@IngredientController >>>>>  updating ingredients for recipe ["+recipe.getId()+"]");
		
		Set<IngredientCommand> savedIngredientCmds = ingredientService.saveAllIngredientCommands(recipe.getIngredientsCmdSet());

		return "redirect:/recipe/"+recipe.getId()+"/ingredients";
	}
	
	
	
	@GetMapping("/recipe/{recipeId}/ingredients/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId,  Model model) {
		
		Long ingredientIdVal = Long.valueOf(ingredientId);

		log.debug("@IngredientController >>>>>  Deleting Row for Ingredient["+ingredientId+"]");
		
		ingredientService.deleteById(ingredientIdVal);
		
		return "redirect:/recipe/"+recipeId+"/ingredients";
	}

}
