package dsd.cohort.application.Utils;

import dsd.cohort.application.ingredient.Ingredient;
import dsd.cohort.application.ingredient.IngredientDTO;
import dsd.cohort.application.user.User;
import dsd.cohort.application.user.dto.UserRegisterDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class DTOtoEntityMapperTest {

    private DTOtoEntityMapper MakeDTOtoEntityMapperWithDefaultStubPasswordEncoder() {
        PasswordEncoder fakePasswordEncoder = mock(PasswordEncoder.class);
        return new DTOtoEntityMapper(fakePasswordEncoder);
    }

    private DTOtoEntityMapper MakeDTOtoEntityMapperWithMockPasswordEncoder(PasswordEncoder mockOrStubPasswordEncoder) {
        return new DTOtoEntityMapper(mockOrStubPasswordEncoder);
    }

    @Test
    public void dtoToEntity_IngredientDTO_MapsToIngredient() {
        DTOtoEntityMapper mapper = MakeDTOtoEntityMapperWithDefaultStubPasswordEncoder();
        IngredientDTO ingredientDTO = IngredientDTO.builder()
                .foodId("123")
                .name("Tomato")
                .text("Fresh Tomato")
                .measure("cup")
                .weight(150.456)
                .imageUrl("http://example.com/tomato.jpg")
                .quantity(2)
                .foodCategory("Vegetable")
                .build();

        // Act
        Ingredient ingredient = mapper.dtoToEntity(ingredientDTO);

        // Assert
        assertEquals("123", ingredient.getFoodId());
        assertEquals("Tomato", ingredient.getName());
        assertEquals("Fresh Tomato", ingredient.getText());
        assertEquals("cup", ingredient.getMeasure());
        assertEquals(150.46, ingredient.getWeight());
        assertEquals("http://example.com/tomato.jpg", ingredient.getImageUrl());
        assertEquals(2, ingredient.getQuantity());
        assertEquals("Vegetable", ingredient.getFoodCategory());
    }

    @Test
    public void dtoToEntity_UserRegisterDTO_MapsToUser() {
        PasswordEncoder mockPasswordEncoder = mock(PasswordEncoder.class);
        Mockito.when(mockPasswordEncoder.encode("password123")).thenReturn("encodedPassword123");
        DTOtoEntityMapper mapper = MakeDTOtoEntityMapperWithMockPasswordEncoder(mockPasswordEncoder);
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        // Act
        User user = mapper.dtoToEntity(userRegisterDTO);

        // Assert
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("encodedPassword123", user.getPassword());
    }
}