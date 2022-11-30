package com.spring.recipe;

import com.spring.recipe.model.Ingredient;
import com.spring.recipe.model.Recipe;
import com.spring.recipe.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.HashSet;
import java.util.Set;
@SpringBootApplication
@EnableJpaAuditing
public class RecipeApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(RecipeApplication.class);
	@Autowired
	RecipeRepository recipeRepository;

	public static void main(String[] args) {
		SpringApplication.run(RecipeApplication.class, args);
	}
	@Override
	public void run(String... args) {
		Ingredient egg = new Ingredient("egg", 50.0);
		Ingredient onions  = new Ingredient("onion", 50.0);
		Ingredient chicken  = new Ingredient("chicken", 500.0);
		Ingredient onion  = new Ingredient("onion", 50.0);
		Ingredient rice  = new Ingredient("rice", 500.0);
		Ingredient zucchini  = new Ingredient("zucchini", 50.0);
		Ingredient steaks  = new Ingredient("steaks", 500.0);
		Ingredient garlic  = new Ingredient("steaks", 20.0);
		Ingredient potatoes  = new Ingredient("potatoes", 200.0);
		Ingredient basmatiRice  = new Ingredient("basmati rice", 500.0);
		Ingredient jeera  = new Ingredient("jeera", 20.0);
		Ingredient salt  = new Ingredient("salt", 20.0);

		Set<Ingredient> ingredientScrambledEggs = new HashSet<>();
		ingredientScrambledEggs.add(egg);
		ingredientScrambledEggs.add(onions);

		Set<Ingredient> ingredientPulledChicken = new HashSet<>();
		ingredientPulledChicken.add(chicken);
		ingredientPulledChicken.add(onion);

		Set<Ingredient> ingredientZucchiniRice = new HashSet<>();
		ingredientZucchiniRice.add(rice);
		ingredientZucchiniRice.add(zucchini);

		Set<Ingredient> ingredientSteakPotatoes = new HashSet<>();
		ingredientSteakPotatoes.add(garlic);
		ingredientSteakPotatoes.add(steaks);
		ingredientSteakPotatoes.add(potatoes);

		Set<Ingredient> ingredientJeeraRice = new HashSet<>();
		ingredientJeeraRice.add(basmatiRice);
		ingredientJeeraRice.add(jeera);
		ingredientJeeraRice.add(salt);

		Recipe scrambledEggRecipe = new Recipe("Creamy Scrambled Eggs Recipe", "Non Vegetarian",
				2, "Whisk the eggs, milk, salt and pepper;\n" +
				"Melt butter in a non stick pan or well seasoned skillet over medium heat (or medium low if your stove is strong);\n" +
				"Add egg mixture, wait 5 seconds (for the base to just start setting) then start leisurely (not frantically!)" +
				" running a rubber spatula or flat edge wooden spoon in long strokes back and forth across the pan, and around " +
				"the edges. This technique pushes the cooked egg off the base and piles them up (which creates those beautiful" +
				" soft curds you see) and lets the raw egg spread out into the pan to cook;\n" +
				"After 30 seconds, also start gently folding the eggs over (ie scoop and gently flip)." , ingredientScrambledEggs);
		Recipe pulledChickenRecipe = new Recipe("Pulled Chicken Sandwiches (Crock Pot)", "Non Vegetarian",
				2, "Stir together all sauce ingredients in a 5-6 qt slow cooker.\n" +
				" Add chicken and turn to coat. Cover and cook on high 3 to 4 hours or on low for 6 – 7 hours." +
				" Chicken is done when cooked through and easy to shred. Remove chicken to a cutting board and " +
				"shred each breast using two forks. Place shredded chicken back in the crock pot and stir to coat with" +
				" the yummy sauce.", ingredientPulledChicken);
		Recipe zucchiniRecipe = new Recipe("Zucchini Rice Casserole", "Vegetarian",
				3, "Pour rice into a 9-by-13-inch baking dish. Bring broth to a simmer in a small saucepan." +
				" Stir hot broth into the rice along with zucchini (and/or squash), bell peppers, onion and salt." +
				" Cover with foil. Bake for 45 minutes. Remove foil and continue baking until the rice is tender " +
				"and most of the liquid is absorbed, 35 to 45 minutes more.Meanwhile, whisk milk and flour in a small saucepan. " +
				"Cook over medium heat until bubbling and thickened, 3 to 4 minutes. Reduce heat to low. Add 1 1/2 cups " +
				"Jack cheese and corn and cook, stirring, until the cheese is melted. Set aside.Heat oil in a large skillet " +
				"over medium heat and add sausage. Cook, stirring and breaking the sausage into small pieces with a spoon," +
				" until lightly browned and no longer pink, about 4 minutes.When the rice is done, stir in the sausage and " +
				"cheese sauce. ", ingredientZucchiniRice);
		Recipe steaksRecipe = new Recipe("Garlic Butter for Steaks and Mash Potatoes", "Non Vegetarian",
				3, "Peel your potatoes, then thoroughly rinse.Bring a large pot of salted water to a boil." +
				" Carefully add the potatoes to the hot boiling water and boil until potatoes break apart easily when pierced with fork, " +
				"about 20 minutes.Add in yogurt to the mashed potatoes, stir well. Slowly pour in the warm butter-milk mixture while using " +
				"the potato masher to reach the desired consistency and smoothness. Season with sea salt and pepper, to your taste.\n" +
				"To make the steak bites:Heat your oil in a large heavy skillet over medium-high heat." +
				"Add in crushed garlic cloves and cook for 1 minute, until fragrant.", ingredientSteakPotatoes);
		Recipe jeeraRecipe = new Recipe("Jeera Rice", "Vegetarian",
				4, "This jeera rice recipe gives a restaurant style taste and is mildly spiced," +
				" fragrant and tastes too good with full-on flavors from the cumin.In this recipe method, the rice grains " +
				"are cooked first with whole spices. Then a tempering with cumin and green chillies is made. This tempering" +
				" is then added to the cooked rice and mixed. This method of making jeera rice is mostly used in restaurants. \n" +
				"Optionally the rice can be cooked with some saffron strands to add some Color and Flavor in the cumin rice." +
				"This method is also useful if you have some leftover cooked rice. If using leftover cooked rice then " +
				"alter the ingredients accordingly depending upon the quantity of rice.", ingredientJeeraRice);
		recipeRepository.save(scrambledEggRecipe);
		recipeRepository.save(scrambledEggRecipe);
		recipeRepository.save(pulledChickenRecipe);
		recipeRepository.save(zucchiniRecipe);
		recipeRepository.save(steaksRecipe);
		recipeRepository.save(jeeraRecipe);

		logger.info("Recipe data loaded..");
	}
}
