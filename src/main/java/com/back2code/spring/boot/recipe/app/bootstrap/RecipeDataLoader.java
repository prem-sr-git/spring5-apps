package com.back2code.spring.boot.recipe.app.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.back2code.spring.boot.recipe.app.domain.Category;
import com.back2code.spring.boot.recipe.app.domain.Difficulty;
import com.back2code.spring.boot.recipe.app.domain.Ingredient;
import com.back2code.spring.boot.recipe.app.domain.Notes;
import com.back2code.spring.boot.recipe.app.domain.Recipe;
import com.back2code.spring.boot.recipe.app.domain.UnitOfMeasure;
import com.back2code.spring.boot.recipe.app.repositories.CategoryRepository;
import com.back2code.spring.boot.recipe.app.repositories.RecipeRepository;
import com.back2code.spring.boot.recipe.app.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RecipeDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private final RecipeRepository recipeRepository;
	private final CategoryRepository categoryRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	public RecipeDataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		this.recipeRepository = recipeRepository;
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Transactional
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		recipeRepository.saveAll(loadRecipes());

	}

	private List<Recipe> loadRecipes() {

		List<Recipe> recipes = new ArrayList<>();

		log.debug("Loading recipes.......");
		UnitOfMeasure uomTeaspoon = unitOfMeasureRepository.findByDescription("Teaspoon").get();
		UnitOfMeasure uomCup = unitOfMeasureRepository.findByDescription("Cup").get();
		UnitOfMeasure uomOunce = unitOfMeasureRepository.findByDescription("Ounce").get();
		UnitOfMeasure uomTablespoon = unitOfMeasureRepository.findByDescription("Tablespoon").get();
		UnitOfMeasure uomDash = unitOfMeasureRepository.findByDescription("Dash").get();
		UnitOfMeasure uomPound = unitOfMeasureRepository.findByDescription("Pound").get();

		Category mexicanCategory = new Category();

		mexicanCategory.setCategoryName("Mexican");

		Category savedMexicanCat = categoryRepository.save(mexicanCategory);

		Category americanCategory = new Category();

		americanCategory.setCategoryName("American");

		Category savedAmericanCategory = categoryRepository.save(americanCategory);

		Recipe guacRecipe = new Recipe();

		guacRecipe.getCategories().add(savedMexicanCat);
		guacRecipe.setCookTime(10);

		String description = "How to Make Perfect Guacamole";

		guacRecipe.setDescription(description);

		guacRecipe.setDifficulty(Difficulty.EASY);

		String directions = "1 Cut avocado, remove flesh: Cut the avocados in half. "
				+ "Remove seed. Score the inside of the avocado with a blunt knife and scoop out the "
				+ "flesh with a spoon. Place in a bowl. \n"
				+ "2 Mash with a fork: Using a fork, roughly mash the avocado. "
				+ "(Don't overdo it! The guacamole should be a little chunky.)\n"
				+ "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. "
				+ "The acid in the lime juice will provide some balance to the richness of the avocado and "
				+ "will help delay the avocados from turning brown.\n"
				+ "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary "
				+ "individually in their hotness. So, start with a half of one chili pepper and add to the "
				+ "guacamole to your desired degree of hotness.\n"
				+ "Remember that much of this is done to taste because of the variability in the "
				+ "fresh ingredients. Start with this recipe and adjust to your taste."
				+ "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the "
				+ "guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation "
				+ "which will turn the guacamole brown.) Refrigerate until ready to serve.\n"
				+ "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your "
				+ "guacamole, add " + "it just before serving.";

		guacRecipe.setDirections(directions);

		guacRecipe.addIngredient(new Ingredient("Ripe Avocados", BigDecimal.valueOf(2), null));

		guacRecipe.addIngredient(new Ingredient("Kosher Salt", BigDecimal.valueOf(1 / 2), uomTeaspoon));
		guacRecipe
				.addIngredient(new Ingredient("Fresh lime juice or lemon juice", BigDecimal.valueOf(1), uomTablespoon));
		guacRecipe.addIngredient(
				new Ingredient("Minced red onion or thinly sliced green onion", BigDecimal.valueOf(2), uomTablespoon));
		guacRecipe.addIngredient(
				new Ingredient("serrano chiles, stems and seeds removed, minced", BigDecimal.valueOf(2), null));
		guacRecipe.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped",
				BigDecimal.valueOf(2), uomTablespoon));
		guacRecipe.addIngredient(new Ingredient("of freshly grated black pepper", BigDecimal.valueOf(1), uomDash));
		guacRecipe.addIngredient(
				new Ingredient("ripe tomato, seeds and pulp removed, chopped", BigDecimal.valueOf(0.5), null));
		guacRecipe.addIngredient(
				new Ingredient("Garnish with red radishes or jicama. Serve with tortilla chips.", null, null));

		Notes notes = new Notes();

		String recipeNotes = "Variations\n"
				+ "For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n"
				+ "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches "
				+ "in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries "
				+ "(see our Strawberry Guacamole).\n"
				+ "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of "
				+ "availability of other ingredients stop you from making guacamole.\n"
				+ "To extend a limited supply of avocados, add either sour cream or cottage cheese to your"
				+ " guacamole dip. Purists may be horrified, but so what? It tastes great.";

		notes.setRecipeNotes(recipeNotes);
		
		guacRecipe.setNotes(notes);

		guacRecipe.setPrepTime(10);
		guacRecipe.setServings(4);
		guacRecipe.setRating(BigDecimal.valueOf(4.5));
		guacRecipe.setSource("Simply Recipes");
		guacRecipe.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

		recipes.add(guacRecipe);

//		----

		Recipe cupcakeRecipe = new Recipe();
		cupcakeRecipe.getCategories().add(savedAmericanCategory);
		cupcakeRecipe.setDescription("Apple Carrot Cupcake");
		cupcakeRecipe.setSource("Simply Recipes");
		cupcakeRecipe.setUrl("https://www.simplyrecipes.com/recipes/apple_carrot_cupcake/");
		cupcakeRecipe.setCookTime(25);
		cupcakeRecipe.setPrepTime(25);
		cupcakeRecipe.setServings(24);
		cupcakeRecipe.setDifficulty(Difficulty.EASY);
		cupcakeRecipe.setRating(BigDecimal.valueOf(3.5));


		String cupcakeDirections = "1 Preheat oven to 350°F (175°C) and place rack in the center of the oven.\n" + "\n"
				+ "2 In a large bowl, whisk together the flour, granulated sugar, baking soda, baking powder, cinnamon, nutmeg, cloves, and salt.\n"
				+ "\n" + "Stir in oil and eggs. Stir in carrots, apples, pecans, and coconut.\n" + "\n"
				+ "3 Place cupcake holders in muffin tins. Spoon the batter into the cupcake paper cups, three quarters of the way to the top. Bake 20-25 minutes (testing after 20 min), or until a tester inserted in the middle comes out clean. Let cool completely before frosting.\n"
				+ "\n"
				+ "4 Beat the cream cheese, butter, and vanilla until smooth and fluffy, about 3 minutes. Gradually add powdered sugar and mix until combined.\n"
				+ "\n" + "Once cupcakes are cool, apply frosting.";
		cupcakeRecipe.setDirections(cupcakeDirections);
//		cupcakeRecipe.setNotes(new Notes());

		cupcakeRecipe.addIngredient(new Ingredient("all purpose flour", BigDecimal.valueOf(2), uomCup));
		cupcakeRecipe.addIngredient(new Ingredient("sugar", BigDecimal.valueOf(1.25), uomCup));
		cupcakeRecipe.addIngredient(new Ingredient("baking soda", BigDecimal.valueOf(2), uomTeaspoon));
		cupcakeRecipe.addIngredient(new Ingredient("baking powder", BigDecimal.valueOf(1.5), uomTeaspoon));
		cupcakeRecipe.addIngredient(new Ingredient("cinnamon", BigDecimal.valueOf(1.5), uomTeaspoon));
		cupcakeRecipe.addIngredient(new Ingredient("ground nutmeg", BigDecimal.valueOf(0.25), uomTeaspoon));
		cupcakeRecipe.addIngredient(new Ingredient("ground cloves", BigDecimal.valueOf(0.125), uomTeaspoon));
		cupcakeRecipe.addIngredient(new Ingredient("salt", BigDecimal.valueOf(0.25), uomTeaspoon));
		cupcakeRecipe.addIngredient(
				new Ingredient("vegetable oil (light olive oil or canola oil)", BigDecimal.valueOf(1), uomCup));
		cupcakeRecipe.addIngredient(new Ingredient("large eggs, lightly beaten", BigDecimal.valueOf(4), null));
		cupcakeRecipe.addIngredient(
				new Ingredient("packed coarsely grated carrots (about 3 medium)", BigDecimal.valueOf(1.5), uomCup));
		cupcakeRecipe.addIngredient(
				new Ingredient("packed coarsely grated tart apples, such as Granny Smith (about 2 medium)",
						BigDecimal.valueOf(1.5), uomCup));
		cupcakeRecipe
				.addIngredient(new Ingredient("coarsely chopped pecans (or walnuts)", BigDecimal.valueOf(1), uomCup));
		cupcakeRecipe.addIngredient(new Ingredient("sweetened shredded coconut", BigDecimal.valueOf(0.25), uomCup));
		cupcakeRecipe
				.addIngredient(new Ingredient("cream cheese, at room temperature", BigDecimal.valueOf(8), uomOunce));
		cupcakeRecipe.addIngredient(
				new Ingredient("unsalted butter, at room temperature", BigDecimal.valueOf(4), uomTablespoon));
		cupcakeRecipe.addIngredient(new Ingredient("vanilla extract", BigDecimal.valueOf(1), uomTeaspoon));
		cupcakeRecipe.addIngredient(new Ingredient("powdered sugar, sifted", BigDecimal.valueOf(2), uomCup));

		recipes.add(cupcakeRecipe);

//		----
		Recipe brusselRecipe = new Recipe();
		brusselRecipe.getCategories().add(savedAmericanCategory);
		brusselRecipe.getCategories().add(savedMexicanCat);
		brusselRecipe.setDescription("Roasted Brussels Sprouts");
		brusselRecipe.setSource("All recipes");
		brusselRecipe.setUrl("https://www.allrecipes.com/recipe/67952/roasted-brussels-sprouts/?internalSource=hub%20recipe&referringId=1227&referringContentType=Recipe%20Hub");
		brusselRecipe.setCookTime(45);
		brusselRecipe.setPrepTime(15);
		brusselRecipe.setServings(6);
		brusselRecipe.setDifficulty(Difficulty.MODERATE);
		brusselRecipe.setRating(BigDecimal.valueOf(4));
		
		brusselRecipe.addIngredient(new Ingredient("Brussels sprouts, ends trimmed and yellow leaves removed", BigDecimal.valueOf(1.5), uomPound));
		brusselRecipe.addIngredient(new Ingredient("kosher salt", BigDecimal.valueOf(1), uomTeaspoon));
		brusselRecipe.addIngredient(new Ingredient("olive oil", BigDecimal.valueOf(3), uomTablespoon));
		brusselRecipe.addIngredient(new Ingredient("freshly ground black pepper", BigDecimal.valueOf(0.5), uomTeaspoon));
		
		String roastDirections = "Preheat oven to 400 degrees F (205 degrees C).\n" + 
				"Place trimmed Brussels sprouts, olive oil, kosher salt, and pepper in a large resealable plastic bag."
				+ " Seal tightly, and shake to coat. Pour onto a baking sheet, and place on center oven rack.\n" + 
				"Roast in the preheated oven for 30 to 45 minutes, shaking pan every 5 to 7 minutes for even browning. "
				+ "Reduce heat when necessary to prevent burning. Brussels sprouts should be darkest brown, almost black, when done. "
				+ "Adjust seasoning with kosher salt, if necessary. Serve immediately.";
		brusselRecipe.setDirections(roastDirections);

		Notes brusselNotes = new Notes();
		String brusselNotesStr = "Tip\n" + 
				"Aluminum foil can be used to keep food moist, cook it evenly, and make clean-up easier.";
		brusselNotes.setRecipeNotes(brusselNotesStr);
		
		brusselRecipe.setNotes(brusselNotes);
		
		recipes.add(brusselRecipe);
		
		log.info("Recipes Loaded........");

		return recipes;
	}

}
