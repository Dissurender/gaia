package dsd.cohort.application.ingredient;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(final IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientEntity getIngredientByFoodId(String foodId) {
        return ingredientExists(foodId);
    }

    @Override
    public IngredientEntity ingredientExists(String foodId) throws ResponseStatusException {
        IngredientEntity ingredient = ingredientRepository.findByFoodId(foodId);

        if (ingredient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found.");
        }

        return ingredient;
    }

    @Override
    public IngredientEntity createIngredient(IngredientEntity ingredient) {

        IngredientEntity ingredientEntity = new IngredientEntity();

        ingredientRepository.save(ingredientEntity);
        return ingredientEntity;
    }
 
    /**
     * Parses the given JSON node representing ingredients and creates a Set of
     * IngredientEntity objects.
     *
     * @param ingredientsJson the JSON node representing ingredients
     * @return a Set of IngredientEntity objects parsed from the JSON node
     */
    public Set<IngredientEntity> parseIngredients(JsonNode ingredientsJson, String recipeId) {
        
        Set<IngredientEntity> ingredients = new HashSet<>();
        DecimalFormat df = new DecimalFormat("#.00");

        if (ingredientsJson.isArray()) {
            for (JsonNode ingredient : ingredientsJson) {

                String foodId = ingredient.findValue("foodId").textValue();
                IngredientEntity existingIngredient = ingredientRepository.findByFoodId(foodId);

                if (existingIngredient != null) {
                    ingredients.add(existingIngredient);
                    continue;
                }

                IngredientEntity newIngredient = new IngredientEntity();

                newIngredient.setFoodId(ingredient.findValue("foodId").textValue());
                newIngredient.setText(ingredient.findValue("text").textValue());
                newIngredient.setQuantity(ingredient.findValue("quantity").intValue());
                newIngredient.setMeasure(ingredient.findValue("measure").textValue());
                newIngredient.setName(ingredient.findValue("food").textValue());
                newIngredient.setFoodCategory(ingredient.findValue("foodCategory").textValue());
                newIngredient.setImageUrl(ingredient.findValue("image").textValue());

                Double weight = ingredient.findValue("weight").doubleValue();
                newIngredient.setWeight(Double.parseDouble(df.format(weight)));

                ingredients.add(newIngredient);

                ingredientRepository.save(newIngredient);

            }
        }

        return ingredients;

    }
}
