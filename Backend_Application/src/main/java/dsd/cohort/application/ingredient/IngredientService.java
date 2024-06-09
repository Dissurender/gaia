package dsd.cohort.application.ingredient;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface IngredientService {

    Ingredient getIngredientByFoodId(String foodId);

    Ingredient ingredientExists(String foodId);

    Ingredient createIngredient(Ingredient ingredient);

    List<Ingredient> getAllIngredients();
}
