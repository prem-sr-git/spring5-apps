package com.back2code.spring.boot.recipe.app.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.back2code.spring.boot.recipe.app.commands.RecipeCommand;
import com.back2code.spring.boot.recipe.app.services.ImageService;
import com.back2code.spring.boot.recipe.app.services.RecipeService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {
	@Mock
	private ImageService imageService;

	@Mock
	private RecipeService recipeService;

	private ImageController imageController;

	MockMvc mockMVc;

	@BeforeEach
	protected void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageController = new ImageController(imageService, recipeService);
		mockMVc = MockMvcBuilders.standaloneSetup(imageController).build();
	}

	@Test
	public void testLoadImageForm() throws Exception {
		// given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

		// when
		mockMVc.perform(get("/recipe/1/image")).andExpect(status().isOk()).andExpect(model().attributeExists("recipe"));

		verify(recipeService, times(1)).findCommandById(anyLong());

		// then
	}

	@Test
	public void testUploadRecipeImage() throws Exception {
		MockMultipartFile multipartFile = new MockMultipartFile("file", "testing.txt", "text/plain",
				"Read this Fake Image File".getBytes());

		mockMVc.perform(multipart("/recipe/1/image").file(multipartFile))
				.andExpect(status()
				.is3xxRedirection())
				.andExpect(header().string("location", "/recipe/1/show"));
		verify(imageService, times(1)).saveImageFile(anyLong(), any());
	}
	
	@Test
	public void loadImageFromDB() throws Exception {
		// given
				RecipeCommand recipeCommand = new RecipeCommand();
				recipeCommand.setId(1L);
				
				String s = "Read this Fake Image File";
				Byte[] bytesBoxed = new Byte[s.length()];
				
				int i=0;
				for(byte b : s.getBytes()) {
					bytesBoxed[i++] = b;
				}
				
				recipeCommand.setImage(bytesBoxed);
				
				when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
				
//				when
				MockHttpServletResponse response = mockMVc.perform(get("/recipe/1/recipeimage"))
						.andExpect(status().isOk())
						.andReturn().getResponse();
				
				byte[] responseBytes = response.getContentAsByteArray();
				
				assertEquals(s.getBytes().length, responseBytes.length);
	}

}
