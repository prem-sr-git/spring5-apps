package com.back2code.spring.boot.recipe.app.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import com.back2code.spring.boot.recipe.app.commands.IngredientCommand;
import com.back2code.spring.boot.recipe.app.converters.IngredientCommandToIngredient;
import com.back2code.spring.boot.recipe.app.converters.IngredientToIngredientCommand;
import com.back2code.spring.boot.recipe.app.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.back2code.spring.boot.recipe.app.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.back2code.spring.boot.recipe.app.domain.Ingredient;
import com.back2code.spring.boot.recipe.app.domain.Recipe;
import com.back2code.spring.boot.recipe.app.repositories.IngredientRepository;

import static org.junit.jupiter.api.Assertions.*;

class IngredientServiceImplTest {
	
	IngredientService ingredientService;
	
	IngredientCommandToIngredient ingredientConverter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	
	IngredientToIngredientCommand ingredientCommandConverter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	
	@Mock
	IngredientRepository ingredientRepository;
	
	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		ingredientService = new IngredientServiceImpl(ingredientRepository, ingredientCommandConverter, ingredientConverter);
	}

	@Test
	void testFindCommandById() {
//		fail("Not yet implemented");
	}

	@Test
	void testDeleteById() {
//		fail("Not yet implemented");
	}

	@Test
	void testSaveIngredientCommand() {
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(3l);
		command.setRecipeId(2l);
		
		
		Recipe recipe = new Recipe();
		recipe.setId(2l);
		
		Ingredient savedIngredient = new Ingredient();
		savedIngredient.setId(3l);
		savedIngredient.setRecipe(recipe);
		
//		recipe.getIngredients().add(savedIngredient);
		
		when(ingredientRepository.save(any())).thenReturn(savedIngredient);
		
		//when
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
		
		//then
		assertEquals(Long.valueOf(3l), savedCommand.getId());
		assertEquals(Long.valueOf(2l), savedCommand.getRecipeId());
		
		assertEquals(command.getId(), savedCommand.getId());
		assertEquals(command.getRecipeId(), savedCommand.getRecipeId());
		verify(ingredientRepository, times(1)).save(any());
	}

	@Test
	void testSaveAllIngredientCommands() {
//		fail("Not yet implemented");
	}

}
