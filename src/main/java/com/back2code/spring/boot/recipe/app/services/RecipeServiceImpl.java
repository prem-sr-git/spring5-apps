package com.back2code.spring.boot.recipe.app.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.back2code.spring.boot.recipe.app.domain.Recipe;
import com.back2code.spring.boot.recipe.app.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	final private RecipeRepository recipeRepository;

	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
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
		
		log.info("Getting recipes @ Service Layer.... >> getting Recipe for ID["+recipeId+"]");
		return recipeRepository.findById(recipeId).get();
		
	}

}
