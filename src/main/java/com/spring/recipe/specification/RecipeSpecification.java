package com.spring.recipe.specification;

import com.spring.recipe.model.Recipe;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class RecipeSpecification  {
    public static Specification<Recipe> recipeHasType(String recipeType) {
        return(root,query,criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.upper(root.get("type")), recipeType.toUpperCase());
    }
    public static Specification<Recipe> recipeHasInstructionText(String instructionText) {
        return(root,query,criteriaBuilder) ->
             criteriaBuilder.like(criteriaBuilder.upper(root.get("instructions")),
                     "%" + instructionText.toUpperCase() + "%");
    }
    public static Specification<Recipe> recipeHasServings(int servings) {
        return(root,query,criteriaBuilder) ->
             criteriaBuilder.equal(root.get("servings"), servings);
    }
    public static Specification<Recipe> recipeHasIngredients(String ingredientName) {
        return((root,query,criteriaBuilder) -> {
            Join<Object, Object> recipeIngredientListJoin = root.join("ingredients");
            return criteriaBuilder.equal(criteriaBuilder.upper(recipeIngredientListJoin.get("description")),
                    ingredientName.toUpperCase());
        } );
    }
}
