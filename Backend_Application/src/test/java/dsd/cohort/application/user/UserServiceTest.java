package dsd.cohort.application.user;

import dsd.cohort.application.ingredient.Ingredient;
import dsd.cohort.application.recipe.Recipe;
import dsd.cohort.application.recipe.RecipeRepository;
import dsd.cohort.application.user.dto.UserDataRequestDTO;
import dsd.cohort.application.user.dto.UserRegisterDTO;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserRegisterDTO userRegisterDTO;

    @BeforeEach
    public void setUp() {
        userRegisterDTO = new UserRegisterDTO("John", "Doe", "john.doe@example.com", "password123");
        User testUser = new User.Builder()
                .firstName(userRegisterDTO.getFirstName())
                .lastName(userRegisterDTO.getLastName())
                .email(userRegisterDTO.getEmail())
                .password(userRegisterDTO.getPassword())
                .build();
    }


    @Test
    public void createUser_WithValidDetails_CreatesAndSavesUser() {
        // Arrange
        when(userRepository.findByEmail(userRegisterDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userRegisterDTO.getPassword())).thenReturn("encodedPassword");
        User expectedUser = new User.Builder()
                .firstName(userRegisterDTO.getFirstName())
                .lastName(userRegisterDTO.getLastName())
                .email(userRegisterDTO.getEmail())
                .password("encodedPassword")
                .build();
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Act
        User createdUser = userService.createUser(userRegisterDTO);

        // Assert
        assertEquals(expectedUser, createdUser);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void createUser_WithExistingEmail_ThrowsException() {
        // Arrange
        when(userRepository.findByEmail(userRegisterDTO.getEmail())).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(EntityExistsException.class, () -> userService.createUser(userRegisterDTO));
    }

    @Test
    public void createUser_WithValidDetails_EncodesPassword() {
        // Arrange
        when(userRepository.findByEmail(userRegisterDTO.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userRegisterDTO.getPassword())).thenReturn("encodedPassword");
        User expectedUser = new User.Builder()
                .firstName(userRegisterDTO.getFirstName())
                .lastName(userRegisterDTO.getLastName())
                .email(userRegisterDTO.getEmail())
                .password("encodedPassword")
                .build();
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Act
        User createdUser = userService.createUser(userRegisterDTO);

        // Assert
        assertEquals("encodedPassword", createdUser.getPassword());
        verify(passwordEncoder).encode(userRegisterDTO.getPassword());
    }

    //
    //
    @Test
    public void AddRecipe_AddsRecipeToFavorites() {
        // Arrange
        UserDataRequestDTO userDataRequestDTO = new UserDataRequestDTO("test@example.com", "recipeId");
        User user = new User.Builder().email("test@example.com").build();
        Recipe recipe = Recipe.builder().recipeId("recipeId").build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(recipeRepository.findByRecipeId("recipeId")).thenReturn(recipe);

        // Act
        userService.addRecipe(userDataRequestDTO);

        // Assert
        assertTrue(user.getFavoriteRecipes().contains(recipe));
    }

    @Test
    public void AddRecipe_AddsIngredientsToGroceryList() {
        // Arrange
        UserDataRequestDTO userDataRequestDTO = new UserDataRequestDTO("test@example.com", "recipeId");
        User user = new User.Builder().email("test@example.com").build();
        Ingredient ingredient = Ingredient.builder().id(1L).build();
        Recipe recipe = Recipe.builder().recipeId("recipeId").ingredients(List.of(ingredient)).build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(recipeRepository.findByRecipeId("recipeId")).thenReturn(recipe);

        // Act
        userService.addRecipe(userDataRequestDTO);

        // Assert
        assertTrue(user.getGroceryList().contains(ingredient));
    }

    @Test
    public void AddRecipe_UserDoesNotExist() {
        // Arrange
        UserDataRequestDTO userDataRequestDTO = new UserDataRequestDTO("nonexistent@example.com", "recipeId");

        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> userService.addRecipe(userDataRequestDTO));
    }
}