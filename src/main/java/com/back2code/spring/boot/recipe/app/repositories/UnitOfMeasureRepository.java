package com.back2code.spring.boot.recipe.app.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.back2code.spring.boot.recipe.app.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long>{

	Optional<UnitOfMeasure> findByDescription(String description);
}
