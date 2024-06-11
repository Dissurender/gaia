package dsd.cohort.application.recipe;

import dsd.cohort.application.ingredient.Ingredient;
import dsd.cohort.application.ingredient.IngredientRepository;
import dsd.cohort.application.ingredient.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;
    private final IngredientRepository ingredientRepository;

    /**
     * Retrieves all recipes from the database.
     *
     * @return a list of RecipeEntity objects representing all recipes in the database
     */
    // TODO: add pagination
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public boolean recipeExists(String id) {
        return recipeRepository.findByRecipeId(id) != null;
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

    @Transactional
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
                .build();

        // make sure that ingredients merge
        List<Ingredient> ingredientList = ingredientService.getIngredients();
        for (int i = 0; i < ingredientList.size(); i++) {
            ingredientList.set(i, ingredientRepository.saveAndFlush(ingredientList.get(i)));
        }
        recipe.setIngredients(ingredientList);

        recipeRepository.save(recipe);
        return recipe;
    }

}
