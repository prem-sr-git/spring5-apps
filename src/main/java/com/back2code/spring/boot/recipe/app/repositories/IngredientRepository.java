package com.back2code.spring.boot.recipe.app.repositories;

import org.springframework.data.repository.CrudRepository;

import com.back2code.spring.boot.recipe.app.domain.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

}
