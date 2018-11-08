package com.back2code.spring.boot.recipe.app.repositories;



import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.back2code.spring.boot.recipe.app.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	Optional<Category> findByCategoryName(String categoryName); 
}
