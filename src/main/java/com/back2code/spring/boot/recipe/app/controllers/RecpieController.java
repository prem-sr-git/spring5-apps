package com.back2code.spring.boot.recipe.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.back2code.spring.boot.recipe.app.services.RecipeService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class RecpieController {
	public RecipeService recipeService;

	public RecpieController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@RequestMapping("/recipe")
	public String getRecipe(@RequestParam("id") Long recipeId, Model model) {
		log.info("@RecipeController >>>>>  getting recipe for ID["+recipeId+"]");
		model.addAttribute("recipe", recipeService.findById(recipeId));
		
		return "pretty-recipe";
	}
}