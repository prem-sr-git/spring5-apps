package com.back2code.spring.boot.recipe.app.services;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.back2code.spring.boot.recipe.app.commands.IngredientCommand;
import com.back2code.spring.boot.recipe.app.converters.IngredientCommandToIngredient;
import com.back2code.spring.boot.recipe.app.converters.IngredientToIngredientCommand;
import com.back2code.spring.boot.recipe.app.domain.Ingredient;
import com.back2code.spring.boot.recipe.app.repositories.IngredientRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
	
	final private IngredientRepository ingredientRepository;
	final private IngredientToIngredientCommand ingredientCommandConverter;
	final private IngredientCommandToIngredient ingredientConverter;

	public IngredientServiceImpl(IngredientRepository ingredientRepository,
			IngredientToIngredientCommand ingredientCommandConverter,
			IngredientCommandToIngredient ingredientConverter) {
		this.ingredientRepository = ingredientRepository;
		this.ingredientCommandConverter = ingredientCommandConverter;
		this.ingredientConverter = ingredientConverter;
	}

	@Override
	public IngredientCommand findCommandById(Long ingredientId) {
		
		Ingredient ingredient = ingredientRepository.findById(ingredientId).get();
		
		IngredientCommand ingredientCmd = ingredientCommandConverter.convert(ingredient);
		
		return ingredientCmd;
	}

	@Override
	@Transactional
	public void deleteById(Long ingredientId) {
	
		log.debug("Deleting ingredient @ Service Layer.... >> for ID[" + ingredientId + "]");
		
		ingredientRepository.deleteById(ingredientId);

	}

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
		log.debug(" @ Service Layer.... >> Saving new Ingredient[" + ingredientCommand + "]");
		
		Ingredient detachedIngredient = ingredientConverter.convert(ingredientCommand);
		
		Ingredient savedIngredient = ingredientRepository.save(detachedIngredient);
		
		log.debug("Saved Ingredient[" + savedIngredient.getId() + "]" + savedIngredient);
		
		IngredientCommand ingredientCommand2 = ingredientCommandConverter.convert(savedIngredient);
		
		log.debug("Saved IngredientCommand[" + ingredientCommand2 + "]");
		
		return ingredientCommand2; 
	}

	@Override
	@Transactional
	public Set<IngredientCommand> saveAllIngredientCommands(Set<IngredientCommand> ingredientCommands) {
		
		Set<Ingredient> ingredients = new HashSet<>();
		
		ingredientCommands.iterator().forEachRemaining(ingredientCommand -> ingredients.add(ingredientConverter.convert(ingredientCommand)));
		log.debug("Updating All Ingredients for recipe[" + ingredientCommands.iterator().next().getRecipeId() + "]");

		Set<IngredientCommand> savedIngredientCmds = new HashSet<>();
		
		ingredientRepository.saveAll(ingredients).iterator().forEachRemaining(ingredient -> savedIngredientCmds.add(ingredientCommandConverter.convert(ingredient)));
		
		return savedIngredientCmds;
	}

}
