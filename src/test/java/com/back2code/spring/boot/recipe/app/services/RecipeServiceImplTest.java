package com.back2code.spring.boot.recipe.app.services;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.back2code.spring.boot.recipe.app.converters.RecipeCommandToRecipe;
import com.back2code.spring.boot.recipe.app.converters.RecipeToRecipeCommand;
import com.back2code.spring.boot.recipe.app.domain.Recipe;
import com.back2code.spring.boot.recipe.app.repositories.RecipeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RecipeServiceImplTest {

	@InjectMocks
	RecipeServiceImpl recipeService;

	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;

	@Mock
	RecipeRepository recipeRepository;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Transactional
	@Test
	public void getRecipesTest() throws Exception {

		Recipe recipe = new Recipe();
		HashSet<Recipe> recipesData = new HashSet<>();
		recipesData.add(recipe);

		when(recipeService.getRecipes()).thenReturn(recipesData);

		Set<Recipe> recipes = recipeService.getRecipes();

		assertEquals(recipes.size(), 1);
		verify(recipeRepository, times(1)).findAll();
		verify(recipeRepository, never()).findById(anyLong());
	}

	@Test
	public void findById() {
		Recipe recipe = new Recipe();
		recipe.setId(1L);

		Optional<Recipe> recipeOptional = Optional.ofNullable(recipe);

		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

		Recipe recipeReturned = recipeService.findById(1L);

		assertNotNull(recipeReturned, "Null Recipe returned");
		verify(recipeRepository, times(1)).findById(anyLong());
		verify(recipeRepository, never()).findAll();

	}

	@Test
	public void testDeleteById() {
		// given
		Long idToDelete = 2L;

		// when
		recipeService.deleteById(idToDelete);

		// then
		verify(recipeRepository, times(1)).deleteById(anyLong());
	}

}