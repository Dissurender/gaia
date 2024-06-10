package dsd.cohort.application.recipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dsd.cohort.application.ingredient.IngredientRepository;
import dsd.cohort.application.ingredient.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;

import dsd.cohort.application.Utils.Utility;
import dsd.cohort.application.ingredient.Ingredient;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final Utility utility;
    private final IngredientService ingredientService;

    // TODO: add pagination
    // return all recipes from database
    public List<Recipe> getAllRecipes() {
        System.out.println("Fetching all recipes...");
        System.out.println("Number of recipes: " + recipeRepository.count());
        return recipeRepository.findAll();
    }

    /**
     * Retrieves a recipe from the database based on the provided recipeId
     *
     * @param recipeId
     * @return RecipeEntity object
     * @throws ResponseStatusException if unable to fetch the recipe
     */
    public Recipe getRecipeByRecipeId(String recipeId) throws ResponseStatusException {
        Recipe recipe = recipeRepository.findByRecipeId(recipeId);

        System.out.println("\nRecipe found in database: " + recipe.getRecipeId());

        return recipe;
    }

    /**
     * Retrieves a list of RecipeEntity objects based on the provided name.
     *
     * @param name the name of the recipes to retrieve
     * @return a list of RecipeEntity objects
     */
    public List<Recipe> getRecipeByName(String name) {

        List<Recipe> recipes = recipeRepository.findByName(name);

        if (recipes.isEmpty()) {
            try {
                System.out.println("\nSearch partial not found, fetching from API...");
//                recipes = queryApi(name);
                recipeRepository.saveAll(recipes);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to fetch recipes");
            }
        }

        return recipes;
    }

    /**
     * Creates a list of all recipes names from the database.
     *
     * @return a list of recipe names
     */
    public List<String> getRecipeNames() {
        List<String> recipeNames = new ArrayList<String>();
        List<Recipe> recipes = getAllRecipes();
        for (Recipe recipe : recipes) {
            recipeNames.add(recipe.getName());
        }
        return recipeNames;
    }

    public Recipe createRecipe(RecipeDTO recipeDTO) {

        Recipe recipe = Recipe.builder()
                .recipeId(recipeDTO.getRecipeId())
                .name(recipeDTO.getName())
                .description(recipeDTO.getDescription())
                .calories(recipeDTO.getCalories())
                .carbs(recipeDTO.getCarbs())
                .protein(recipeDTO.getProtein())
                .fat(recipeDTO.getFat())
                .url(recipeDTO.getUrl())
                .imageUrl(recipeDTO.getImageUrl())
                .yield(recipeDTO.getYield())
                .totalTime(recipeDTO.getTotalTime())
                .ingredients(ingredientService.getIngredients())
                .build();

        recipeRepository.save(recipe);
        return recipe;
    }

}
