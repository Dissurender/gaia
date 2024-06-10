package dsd.cohort.application.Utils;


import dsd.cohort.application.ingredient.Ingredient;
import dsd.cohort.application.ingredient.IngredientDTO;
import dsd.cohort.application.user.User;
import dsd.cohort.application.user.dto.UserRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@RequiredArgsConstructor
@Component
public class DTOtoEntityMapper {

    private final PasswordEncoder passwordEncoder;
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

    public User dtoToEntity(UserRegisterDTO userRegisterDTO) {
        return User.builder()
                .firstName(userRegisterDTO.getFirstName())
                .lastName(userRegisterDTO.getLastName())
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .build();
    }
}
