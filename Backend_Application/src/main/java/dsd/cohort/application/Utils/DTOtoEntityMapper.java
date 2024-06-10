package dsd.cohort.application.Utils;


import dsd.cohort.application.ingredient.Ingredient;
import dsd.cohort.application.ingredient.IngredientDTO;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class DTOtoEntityMapper {

    private final DecimalFormat df = new DecimalFormat("#.00");

    public Ingredient dtoToEntity(IngredientDTO ingredientDTO) {
        return Ingredient.builder()
                .foodId(ingredientDTO.getFoodId())
                .name(ingredientDTO.getName())
                .text(ingredientDTO.getText())
                .measure(ingredientDTO.getMeasure())
                .weight(Double.parseDouble(df.format(ingredientDTO.getWeight())))
                .imageUrl(ingredientDTO.getImageUrl())
                .quantity(ingredientDTO.getQuantity())
                .foodCategory(ingredientDTO.getFoodCategory())
                .build();
    }
}
