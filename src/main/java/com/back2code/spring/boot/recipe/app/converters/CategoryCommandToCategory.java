package com.back2code.spring.boot.recipe.app.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.back2code.spring.boot.recipe.app.commands.CategoryCommand;
import com.back2code.spring.boot.recipe.app.domain.Category;

import lombok.Synchronized;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

	@Synchronized
	@Nullable
	@Override
	public Category convert(CategoryCommand source) {
		if (source == null) {
			return null;
		}
		
		final Category category = new Category();
		category.setId(source.getId());
		category.setCategoryName(source.getDescription());
//		category.setRecipes(source.g); TODO
		
		return category;
	}

}
