package dsd.cohort.application.recipe;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public interface RecipeService {

    // Will hold a list of recipes that meet the search partial
    List<RecipeEntity> getRecipeByName(String name);

    RecipeEntity getRecipeByRecipeId(String recipeId);

    RecipeEntity createRecipe(String recipeId);

    List<RecipeEntity> getAllRecipes();

    RecipeEntity fetchRecipe(String recipeId)
            throws ResponseStatusException, JsonMappingException, JsonProcessingException;

}
