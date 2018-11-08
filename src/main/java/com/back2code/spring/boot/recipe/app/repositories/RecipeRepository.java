package com.back2code.spring.boot.recipe.app.repositories;

import org.springframework.data.repository.CrudRepository;

import com.back2code.spring.boot.recipe.app.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long>{

}
