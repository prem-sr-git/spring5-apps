package com.back2code.spring.boot.recipe.app.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.back2code.spring.boot.recipe.app.commands.CategoryCommand;
import com.back2code.spring.boot.recipe.app.domain.Category;

import lombok.Synchronized;

@Component
public class CategoryToCategoryCommand 
implements Converter<Category, CategoryCommand> {
	
	@Synchronized
	@Nullable
	@Override
	public CategoryCommand convert(Category source) {
		if (source == null) {
			return null;
		}
		
		final CategoryCommand categoryCmd = new CategoryCommand();
		categoryCmd.setId(source.getId());
		categoryCmd.setDescription(source.getCategoryName());
		
		return categoryCmd;
	}

}