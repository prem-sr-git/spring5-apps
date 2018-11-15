package com.back2code.spring.boot.recipe.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.back2code.spring.boot.recipe.app.commands.RecipeCommand;
import com.back2code.spring.boot.recipe.app.services.RecipeService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class RecpieController {
	public RecipeService recipeService;

	public RecpieController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping("/recipe/{recipeId}/show")
	public String getRecipe(@PathVariable String recipeId, Model model) {
		log.debug("@RecipeController >>>>>  getting recipe for ID["+recipeId+"]");
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(recipeId)));
		
		return "recipe/pretty-recipe";
	}
	
	@GetMapping("/recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		log.debug("@RecipeController >>>>>  Creating new Recipe");
		
		return "recipe/recipe-form";
	}

	@GetMapping("/recipe/{recipeId}/update")
	public String updateRecipe(@PathVariable String recipeId, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));
		log.debug("@RecipeController >>>>>  Updating Recipe");
		
		return "recipe/recipe-form";
	}
	
	@PostMapping("/recipe")
	public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand command) {
		log.debug("@RecipeController >>>>>  saveOrUpdate recipe");

		RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(command);
		log.debug("@RecipeController >>>>>  getting saved recipe for ID["+savedRecipeCommand.getId()+"]");
		
		return "redirect:/recipe/"+savedRecipeCommand.getId()+"/show";
	}
	
	
	@GetMapping("/recipe/{recipeId}/delete")
	public String deleteRecipe(@PathVariable String recipeId) {
		log.debug("@RecipeController >>>>>  Deleting recipe for ID["+recipeId+"]");
		recipeService.deleteById(Long.valueOf(recipeId));
		return "redirect:/";
	}
}