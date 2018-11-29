package com.back2code.spring.boot.recipe.app.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.back2code.spring.boot.recipe.app.commands.IngredientCommand;
import com.back2code.spring.boot.recipe.app.commands.RecipeCommand;
import com.back2code.spring.boot.recipe.app.services.IngredientService;
import com.back2code.spring.boot.recipe.app.services.RecipeService;
import com.back2code.spring.boot.recipe.app.services.UnitOfMeasureService;

class IngredientControllerTest {

	@Mock
	RecipeService recipeService;

	@Mock
	UnitOfMeasureService uomService;

	@Mock
	IngredientService ingredientService;

	IngredientController controller;

	MockMvc mockMvc;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		controller = new IngredientController(recipeService, uomService, ingredientService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testListIngredients() throws Exception {
		// given
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

		// when
		mockMvc.perform(get("/recipe/1/ingredients")).andExpect(status().isOk())
				.andExpect(view().name("/recipe/ingredients/ingredients-list"))
				.andExpect(model().attributeExists("recipe"));

		// then
		verify(recipeService, times(1)).findCommandById(anyLong());
	}

	@Test
	public void testSaveOrUpdateIngredient() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(1l);
		ingredientCommand.setRecipeId(2l);

		// when
		when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);
		
		// then
		mockMvc.perform(post("/recipe/2/ingredients/saveOrUpdate")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("id", "")
					.param("description", "xomething"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/ingredients"));
	}

	@Test
	public void testAddNewIngredient() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1l);
		IngredientCommand ingredientCmd =  new IngredientCommand();
		ingredientCmd.setRecipeId(1l);
		
		//when
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		when(uomService.getAllUOMCommands()).thenReturn(new HashSet<>());
		
		//then
		mockMvc.perform(get("/recipe/1/ingredients/addRow"))
				.andExpect(status().isOk())
				.andExpect(view().name("/recipe/ingredients/ingredient-form"))
				.andExpect(model().attributeExists("uoms"))
				.andExpect(model().attributeExists("operation"))
				.andExpect(model().attributeExists("ingredient"));
		
		verify(recipeService, times(1)).findCommandById(anyLong());
		verify(uomService, times(1)).getAllUOMCommands();
	}

	@Test
		public void testDeleteIngredient() throws Exception {
			//then
					mockMvc.perform(get("/recipe/1/ingredients/1/delete"))
							.andExpect(status().is3xxRedirection())
							.andExpect(view().name("redirect:/recipe/1/ingredients"));
					verify(ingredientService, times(1)).deleteById(anyLong());
		}

}
