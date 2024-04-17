package dsd.cohort.application.ingredient;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public interface IngredientService {

    IngredientEntity getIngredientByFoodId(String foodId);

    IngredientEntity ingredientExists(String foodId);

    IngredientEntity createIngredient(IngredientEntity ingredient);

    Set<IngredientEntity> parseIngredients(JsonNode ingredientsJson, String recipeId);

}
