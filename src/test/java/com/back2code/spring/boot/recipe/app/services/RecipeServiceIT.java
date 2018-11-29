package com.back2code.spring.boot.recipe.app.services;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.back2code.spring.boot.recipe.app.commands.RecipeCommand;
import com.back2code.spring.boot.recipe.app.converters.RecipeCommandToRecipe;
import com.back2code.spring.boot.recipe.app.converters.RecipeToRecipeCommand;
import com.back2code.spring.boot.recipe.app.domain.Recipe;
import com.back2code.spring.boot.recipe.app.repositories.RecipeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RecipeServiceIT {

	private static final String DESCRIPTION = "description";

	@Autowired
	RecipeService recipeService;

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	RecipeToRecipeCommand recipeCommandConverter;
	
	@Autowired
	RecipeCommandToRecipe recipeConverter;

	@Test
	@Transactional
	void testSaveOfDescription() {
		
		// given
		Iterable<Recipe> recipes = recipeRepository.findAll();
		Recipe testRecipe = recipes.iterator().next();
		RecipeCommand testRecipeCommand = recipeCommandConverter.convert(testRecipe);

		// when
		testRecipeCommand.setDescription(DESCRIPTION);
		RecipeCommand savedTestRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

		// then
		assertNotNull(savedTestRecipeCommand);
		assertEquals(DESCRIPTION, savedTestRecipeCommand.getDescription());
		assertEquals(testRecipe.getId(), savedTestRecipeCommand.getId());
		assertEquals(testRecipe.getCategories().size(), savedTestRecipeCommand.getCategories().size());
		assertEquals(testRecipe.getIngredients().size(), savedTestRecipeCommand.getIngredients().size());
		
	}

}