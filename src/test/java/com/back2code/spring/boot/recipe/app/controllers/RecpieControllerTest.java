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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.back2code.spring.boot.recipe.app.commands.RecipeCommand;
import com.back2code.spring.boot.recipe.app.domain.Recipe;
import com.back2code.spring.boot.recipe.app.services.RecipeService;

class RecpieControllerTest{
	
    @Mock
    RecipeService recipeService;
    
	MockMvc mockMvc;

    @InjectMocks
    RecpieController controller;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testGetRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		when(recipeService.findById(anyLong())).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/1/show"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/pretty-recipe"))
		.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testGetNewRecipeFormView() throws Exception {
		
		mockMvc.perform(get("/recipe/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/recipe-form"))
		.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testUpdateRecipeView() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(2L);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/2/update"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/recipe-form"))
				.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void testSaveOrUpdateRecipe() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(2L);
		
		when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);
		
		
		mockMvc.perform(post("/recipe")
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.param("id", "")
					.param("description", "some description"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/show"));
			
		
	}
	
	@Test
	public void testDeleteRecipe() throws Exception {
		
		mockMvc.perform(get("/recipe/1/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
			
		verify(recipeService, times(1)).deleteById(anyLong());
	}

}
