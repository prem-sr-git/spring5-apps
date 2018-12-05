package com.back2code.spring.boot.recipe.app.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.back2code.spring.boot.recipe.app.commands.RecipeCommand;
import com.back2code.spring.boot.recipe.app.services.ImageService;
import com.back2code.spring.boot.recipe.app.services.RecipeService;

@Controller
public class ImageController {
	private final ImageService imageService;

	private final RecipeService recipeService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.recipeService = recipeService;
		this.imageService = imageService;
	}

	@GetMapping("/recipe/{recipeId}/image")
	public String loadImageForm(@PathVariable String recipeId, Model model) {
		RecipeCommand recipeCommand;
		if (recipeId == null) {
			recipeCommand = new RecipeCommand();
		} else {
			recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		}
		model.addAttribute("recipe", recipeCommand);
		return "recipe/image-upload-form";
	}

	@PostMapping("/recipe/{recipeId}/image")
	public String uploadRecipeImage(@PathVariable String recipeId, @RequestParam("file") MultipartFile file) {

		imageService.saveImageFile(Long.valueOf(recipeId), file);

		return "redirect:/recipe/" + recipeId + "/show";
	}

	@GetMapping("/recipe/{recipeId}/recipeimage")
	public void loadImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
		
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		
		if(recipeCommand.getImage() !=null) {
			byte[] byteArray = new byte[recipeCommand.getImage().length];
		
			int i=0;
			for(Byte wrappedByte: recipeCommand.getImage()) {
				byteArray[i++] = wrappedByte; //auto-unboxing
			}
			response.setContentType("image/jpeg");
			InputStream is = new ByteArrayInputStream(byteArray);
			IOUtils.copy(is, response.getOutputStream());
		}
	}
}
