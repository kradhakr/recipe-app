package com.spring.recipe.service;

import com.spring.recipe.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    List<Recipe> findAll();
    Optional<Recipe> findById(Long recipeId);
    Recipe save(Recipe recipe);
    void delete(Recipe recipe);
    List<Recipe> findByRecipeType(String recipeType);
    List<Recipe> findAllByServingsAndIngredients(Integer servings, String ingredientName);
    List<Recipe> findAllByInstructionsContainingAndIngredients(String instructionText, String ingredientName,
                                                              String searchIngredient);
}
