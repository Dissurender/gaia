package dsd.cohort.application.ingredient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(final IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public IngredientEntity getIngredientByFoodId(String foodId) {

        return ingredientRepository.findByFoodId(foodId);
    }

    public IngredientEntity ingredientExists(String foodId) throws ResponseStatusException {
        IngredientEntity ingredient = ingredientRepository.findByFoodId(foodId);

        if (ingredient == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found.");
        }

        return ingredient;
    }

    public IngredientEntity createIngredient(IngredientEntity ingredient) {

        ingredientRepository.save(ingredient);
        return ingredient;
    }

    public List<IngredientEntity> getAllIngredients() {
        return ingredientRepository.findAll();
    }
}
