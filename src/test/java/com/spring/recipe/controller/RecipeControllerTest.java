package com.spring.recipe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.recipe.model.Ingredient;
import com.spring.recipe.model.Recipe;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final String API = "/api/recipe";

    @Test
    public void test01GetAllRecipe() throws Exception {
        this.mockMvc.perform( get(this.API) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath("$", hasSize(5)) )
                .andExpect( jsonPath("$[0].title", is("Creamy Scrambled Eggs Recipe")) )
                .andExpect( jsonPath("$[1].title", is("Garlic Butter for Steaks and Mash Potatoes")) )
                .andReturn();
    }

    @Test
    public void test02CreateRecipe() throws Exception {
        this.mockMvc.perform( post("/api/recipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(this.newRecipe() )) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.id", is(6)) )
                .andExpect( jsonPath("$.title", is("Garlic Vinegar")) )
                .andReturn();

    }

    @Test
    public void test03GetRecipeById() throws Exception {
        int id = 6;
        this.mockMvc.perform( get(this.API + "/" + id) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath("$.title", is("Garlic Vinegar")) )
                .andExpect( jsonPath("$.instructions", is("Mix Garlic and Vinegar.")) )
                .andReturn();
    }

    @Test
    public void test04GetRecipeNotFound() throws Exception {
        int id = 10;
        this.mockMvc.perform( get(this.API + "/" + id) )
                .andExpect( status().isNotFound() )
                .andExpect( jsonPath("$.message", is("Recipe not found :: " + id)) )
                .andReturn();
    }

    @Test
    public void test05UpdateRecipe() throws Exception {
        int id = 6;
        Recipe recipe = getRecipe( apiGet("/" + id).getResponse() );
        recipe.setServings(7);
        recipe.setInstructions("Mix garlic and vinegar.Whisk the ingredients properly.");

        this.mockMvc.perform( put(this.API + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString( recipe )) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.title", is("Garlic Vinegar")) )
                .andExpect( jsonPath("$.servings", is(7)) )
                .andExpect( jsonPath("$.instructions", is("Mix garlic and vinegar.Whisk the ingredients properly.")) )
                .andExpect( jsonPath("$.ingredients[0].description", is("vinegar")) )
                .andExpect( jsonPath("$.ingredients[0].amount", is(100.0)) )
                .andReturn();
    }

    @Test
    public void test06UpdateRecipeNotFound() throws Exception {
        int id = 11;
        this.mockMvc.perform( put(this.API + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString( this.newRecipe() )) )
                .andExpect( status().isNotFound())
                .andExpect( jsonPath("$.message", is("Recipe not found :: " + id)) )
                .andReturn();
    }

    @Test
    public void test07DeleteRecipe() throws Exception {
        int id = 6;
        this.mockMvc.perform( delete(this.API + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString( this.newRecipe() )) )
                .andExpect( status().isOk() )
                .andReturn();
    }

    @Test
    public void test08DeleteRecipeNotFound() throws Exception {
        int id = 6;
        this.mockMvc.perform( delete(this.API + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString( this.newRecipe() )) )
                .andExpect( status().isNotFound())
                .andExpect( jsonPath("$.message", is("Recipe not found :: " + id)) )
                .andReturn();
    }

    @Test
    public void test09GetAllRecipeTypeFound() throws Exception {
        String recipeType = "Vegetarian";
        this.mockMvc.perform( get(this.API + "/type/" + recipeType) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath("$", hasSize(2)) )
                .andExpect( jsonPath("$[0].title", is("Zucchini Rice Casserole")) )
                .andExpect( jsonPath("$[0].servings", is(3)) )
                .andExpect( jsonPath("$[1].title", is("Jeera Rice")) )
                .andExpect( jsonPath("$[1].servings", is(4)) )
                .andReturn();
    }

    @Test
    public void test10GetAllRecipeTypeNotFound() throws Exception {
        String recipeType = "Veg";
        this.mockMvc.perform( get(this.API + "/type/" + recipeType) )
                .andExpect( status().isNotFound() )
                .andExpect( jsonPath("$.message", is("Recipe not found for type ::" +recipeType)) )
                .andReturn();
    }

    @Test
    public void test11GetRecipeByServingsAndIngredientsFound() throws Exception {
        int serving = 2;
        String ingredientName = "Egg";
        this.mockMvc.perform( get(this.API + "/serving/?servings=" + serving + "&ingredientName=" + ingredientName) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath("$", hasSize(1)) )
                .andExpect( jsonPath("$[0].title", is("Creamy Scrambled Eggs Recipe")) )
                .andExpect( jsonPath("$[0].instructions", startsWith("Whisk the eggs, milk, salt and pepper")) )
                .andReturn();
    }

    @Test
    public void test12GetRecipeByServingsAndIngredientsNotFound() throws Exception {
        int serving = 1;
        String ingredientName = "Chicken";
        this.mockMvc.perform( get(this.API + "/serving/?servings=" + serving + "&ingredientName=" + ingredientName) )
                .andExpect( status().isNotFound() )
                .andExpect( jsonPath("$.message", is("Recipe not found for servings ::"
                        +serving+ " and ingredientName ::"+ ingredientName)) )
                .andReturn();
    }

    @Test
    public void test13GetRecipeByInstructionTextsAndIngredientsInclude() throws Exception {
        String ingredientName = "Chicken";
        String instructionText = "chicken";
        String searchIngredient = "include";
        this.mockMvc.perform( get(this.API + "/instruction/?instructionText=" + instructionText + "&ingredientName=" + ingredientName
                        + "&searchIngredient=" + searchIngredient) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath("$", hasSize(1)) )
                .andExpect( jsonPath("$[0].title", is("Pulled Chicken Sandwiches (Crock Pot)")) )
                .andExpect( jsonPath("$[0].instructions", startsWith("Stir together all sauce ingredients")) )
                .andReturn();
    }

    @Test
    public void test14GetRecipeByInstructionTextsAndIngredientsExcludeFound() throws Exception {
        String ingredientName = "chicken";
        String searchIngredient = "exclude";
        String instructionText = "egg";
        this.mockMvc.perform( get(this.API + "/instruction/?instructionText=" + instructionText + "&ingredientName=" + ingredientName
                        + "&searchIngredient=" + searchIngredient) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath("$", hasSize(1)) )
                .andExpect( jsonPath("$[0].title", is("Creamy Scrambled Eggs Recipe")) )
                .andExpect( jsonPath("$[0].instructions", startsWith("Whisk the eggs, milk, salt and pepper")) )
                .andReturn();
    }

    @Test
    public void test15GetRecipeByInstructionTextsAndIngredientsExcludeNotFound() throws Exception {
        String ingredientName = "egg";
        String searchIngredient = "exclude";
        String instructionText = "egg";
        this.mockMvc.perform( get(this.API + "/instruction/?instructionText=" + instructionText +
                        "&ingredientName=" + ingredientName + "&searchIngredient=" + searchIngredient) )
                .andExpect( status().isNotFound() )
                .andExpect( jsonPath("$.message", is("Recipe not found for instructionText containing ::"
                        + instructionText + " and ingredientName ::" +ingredientName)) )
                .andReturn();
    }

    // Helper function
    public Recipe newRecipe() {
        Ingredient garlic  = new Ingredient("garlic", 100.0);
        Ingredient vinegar  = new Ingredient("vinegar", 100.0);

        Set<Ingredient> ingredientGarlicVinegar= new HashSet<>();
        ingredientGarlicVinegar.add(garlic);
        ingredientGarlicVinegar.add(vinegar);
        return new Recipe("Garlic Vinegar", "Vegetarian",
                5, "Mix Garlic and Vinegar.", ingredientGarlicVinegar);
    }

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Recipe getRecipe(MockHttpServletResponse res) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(res.getContentAsString(), Recipe.class);
    }

    public MvcResult apiGet(String uri) throws Exception {
        return this.mockMvc.perform(get(this.API + uri))
                .andExpect(status().isOk())
                .andReturn();
    }
}
