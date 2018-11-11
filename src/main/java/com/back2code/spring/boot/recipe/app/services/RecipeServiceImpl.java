package com.back2code.spring.boot.recipe.app.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.back2code.spring.boot.recipe.app.commands.RecipeCommand;
import com.back2code.spring.boot.recipe.app.converters.RecipeCommandToRecipe;
import com.back2code.spring.boot.recipe.app.converters.RecipeToRecipeCommand;
import com.back2code.spring.boot.recipe.app.domain.Recipe;
import com.back2code.spring.boot.recipe.app.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	final private RecipeRepository recipeRepository;
	final private RecipeCommandToRecipe recipeConverter;
	final private RecipeToRecipeCommand recipeCommandConverter;

	
	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeConverter,
			RecipeToRecipeCommand recipeCommandConverter) {
		this.recipeRepository = recipeRepository;
		this.recipeConverter = recipeConverter;
		this.recipeCommandConverter = recipeCommandConverter;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.info("Getting recipes @ Service Layer.... >> getting All Recipes");
		Set<Recipe> recipes = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}

	@Override
	public Recipe findById(Long recipeId) {

		log.info("Getting recipes @ Service Layer.... >> getting Recipe for ID[" + recipeId + "]");
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (!recipeOptional.isPresent()) {
			throw new RuntimeException("Recipe Not Found");
		}
		return recipeOptional.get();

	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {

		Recipe detachedRecipe = recipeConverter.convert(recipeCommand);

		Recipe savedRecipe = recipeRepository.save(detachedRecipe);

		log.debug("Saved Recipe[" + savedRecipe.getId() + "]");
		return recipeCommandConverter.convert(savedRecipe);
	}

}
