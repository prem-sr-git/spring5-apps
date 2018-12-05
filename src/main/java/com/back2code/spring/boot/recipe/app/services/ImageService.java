package com.back2code.spring.boot.recipe.app.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	void saveImageFile(Long recipeId, MultipartFile file);

}
