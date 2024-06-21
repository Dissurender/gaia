package dsd.cohort.application.ingredient;

import dsd.cohort.application.util.DTOtoEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final DTOtoEntityMapper mapper;

    public Ingredient getIngredientByFoodId(String foodId) {
        return ingredientRepository.findByFoodId(foodId);
    }

    public Ingredient createIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = mapper.dtoToEntity(ingredientDTO);

        ingredientRepository.save(ingredient);
        return ingredient;
    }

    public List<Ingredient> getIngredients() {
        return ingredientRepository.sample(5);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }
}
