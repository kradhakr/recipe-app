package com.spring.recipe.controller;

import com.spring.recipe.RecipeApplication;
import com.spring.recipe.exception.ResourceNotFoundException;
import com.spring.recipe.model.Recipe;
import com.spring.recipe.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RecipeController {
	private static final Logger logger = LoggerFactory.getLogger(RecipeApplication.class);
	@Autowired
	private RecipeService recipeService;

	@GetMapping("/recipe")
	public List<Recipe> getAllRecipe() {
		return recipeService.findAll();
	}

	@GetMapping("/recipe/{id}")
	public Recipe getRecipeById(@PathVariable(value = "id") Long recipeId)
			throws ResourceNotFoundException {
		return this.recipeService.findById(recipeId)
				.orElseThrow(() -> new ResourceNotFoundException("Recipe not found :: " + recipeId));
	}

	@PostMapping("/recipe")
	public Recipe createRecipe(@Valid @RequestBody Recipe recipe) {
		return recipeService.save(recipe);
	}

	@PutMapping("/recipe/{id}")
	public Recipe updateRecipe(@PathVariable(value = "id") Long recipeId,
							 @Valid @RequestBody Recipe recipeDetails) throws ResourceNotFoundException {

		return this.recipeService.findById(recipeId)
				.map(recipe -> {
					recipe.setTitle(recipeDetails.getTitle());
					recipe.setType(recipeDetails.getType());
					recipe.setServings(recipeDetails.getServings());
					recipe.setIngredients(recipeDetails.getIngredients());
					recipe.setInstructions(recipeDetails.getInstructions());
					return this.recipeService.save(recipe);
				})
				.orElseThrow(() -> new ResourceNotFoundException("Recipe not found :: " + recipeId));
	}

	@DeleteMapping("/recipe/{id}")
	public String deleteRecipe(@PathVariable(value = "id") Long recipeId) throws ResourceNotFoundException {
		Recipe recipe = recipeService.findById(recipeId)
				.orElseThrow(() -> new ResourceNotFoundException("Recipe not found :: " + recipeId));
		recipeService.delete(recipe);
		logger.info("Deleted recipe with id  :: "+recipeId);
		return "Deleted recipe with id  :: "+recipeId;
	}

	@GetMapping("/recipe/type/{recipeType}")
	public List<Recipe> getRecipeByType(@PathVariable(value = "recipeType") String recipeType) throws ResourceNotFoundException {
		List<Recipe> recipeList = recipeService.findByRecipeType(recipeType);
		if(recipeList.isEmpty()){
			throw new ResourceNotFoundException("Recipe not found for type ::" +recipeType);
		}
		return recipeList;
	}


	@GetMapping("/recipe/serving")
	public List<Recipe> getRecipeByServingAndIngredient(@RequestParam("servings") String servings,
														@RequestParam("ingredientName") String ingredientName) throws ResourceNotFoundException {
		List<Recipe> recipeList =  recipeService.findAllByServingsAndIngredients(Integer.valueOf(servings), ingredientName);
		if(recipeList.isEmpty()){
			throw new ResourceNotFoundException("Recipe not found for servings ::"
					+servings+ " and ingredientName ::"+ ingredientName);
		}
		return recipeList;
	}

	@GetMapping("/recipe/instruction")
	public List<Recipe> getRecipeByInstructionAndIngredient(@RequestParam("instructionText") String instructionText,
															@RequestParam("ingredientName") String ingredientName,
															@RequestParam("searchIngredient") String searchIngredient
															) throws ResourceNotFoundException {
		List<Recipe> recipeList =  recipeService.findAllByInstructionsContainingAndIngredients(instructionText,ingredientName, searchIngredient);
		if(recipeList.isEmpty()){
			throw new ResourceNotFoundException("Recipe not found for instructionText containing ::"+ instructionText +
					" and ingredientName ::" +ingredientName);
		}
		return recipeList;
	}
}