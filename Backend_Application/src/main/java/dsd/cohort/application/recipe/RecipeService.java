package dsd.cohort.application.recipe;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface RecipeService {

    // Will hold a list of recipes that meet the search criteria
    List<Recipe> getRecipeByName(String name);

    Recipe getRecipeByRecipeId(String recipeId);

    Recipe createRecipe(String recipeId);

    List<Recipe> getAllRecipes(); // TODO: add pagination

    List<String> getRecipeNames();

}
