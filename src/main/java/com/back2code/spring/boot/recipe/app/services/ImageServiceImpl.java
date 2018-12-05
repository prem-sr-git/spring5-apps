package com.back2code.spring.boot.recipe.app.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.back2code.spring.boot.recipe.app.domain.Recipe;
import com.back2code.spring.boot.recipe.app.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	final private RecipeRepository repository;

	public ImageServiceImpl(RecipeRepository repository) {
		this.repository = repository;
	}

	@Override
	public void saveImageFile(Long recipeId, MultipartFile file) {
		log.debug("Recieved an Image File, now Saving....");
		try {
			Recipe recipe = repository.findById(recipeId).get();

			Byte[] byteObjects = new Byte[file.getBytes().length];

			int i = 0;

			for (byte b : file.getBytes()) {
				byteObjects[i++] = b;
			}
			recipe.setImage(byteObjects);

			repository.save(recipe);

		} catch (IOException e) {
			log.error("Error while saving image for recipe[" + recipeId + "]" + e);
			e.printStackTrace();
		}
	}

}
