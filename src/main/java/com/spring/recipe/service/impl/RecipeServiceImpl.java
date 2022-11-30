package com.spring.recipe.service.impl;

import com.spring.recipe.model.Ingredient;
import com.spring.recipe.model.Recipe;
import com.spring.recipe.repository.RecipeRepository;
import com.spring.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.spring.recipe.specification.RecipeSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll(Sort.by("title").ascending());
    }

    @Override
    public Optional<Recipe> findById(Long recipeId) {
        return recipeRepository.findById(recipeId);
    }

    @Override
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    @Override
    public List<Recipe> findByRecipeType(String recipeType) {
        return recipeRepository.findAll(where(recipeHasType(recipeType)));
    }

   @Override
    public List<Recipe> findAllByServingsAndIngredients(Integer servings, String ingredientName) {
       List<Recipe> recipeList = recipeRepository.findAll(where(recipeHasServings(servings)) //
                        .and(recipeHasIngredients(ingredientName)));
       return recipeList.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Recipe> findAllByInstructionsContainingAndIngredients(String instructionText, String ingredientName,
                                                                      String searchIngredient){
        List<Recipe> recipeList = new ArrayList<>();
        if(searchIngredient.equalsIgnoreCase("exclude")){
            recipeList =  recipeRepository.findAll(where(recipeHasInstructionText(instructionText)));
            for(int i=0; i<recipeList.size(); i++){
                Recipe recipe = recipeList.get(i);
                Set<Ingredient> ingredientSet = recipe.getIngredients();
                for(Ingredient ingredient : ingredientSet){
                   if(ingredient.getDescription().contains(ingredientName)){
                       recipeList.remove(recipe);
                    }
                }
            }
        }
        else if(searchIngredient.equalsIgnoreCase("include")){
            recipeList =  recipeRepository.findAll(where(recipeHasInstructionText(instructionText))
                    .and(recipeHasIngredients(ingredientName)));
        }
        return recipeList.stream().distinct().collect(Collectors.toList());
    }
}
