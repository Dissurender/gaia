package dsd.cohort.application.recipe;

import dsd.cohort.application.ingredient.Ingredient;
import dsd.cohort.application.ingredient.IngredientRepository;
import dsd.cohort.application.ingredient.IngredientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @InjectMocks
    private RecipeService recipeService;
    @Mock
    private IngredientService ingredientService;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private RecipeRepository recipeRepository;

    private Recipe testRecipe() {
        return Recipe.builder()
                .id(1L)
                .recipeId("test-recipe-id")
                .name("Test Recipe")
                .description("Test Description")
                .calories(100)
                .carbs(10)
                .protein(5)
                .fat(2)
                .url("http://test.url")
                .imageUrl("http://test.image.url")
                .yield(4)
                .totalTime(30)
                .build();
    }

    private RecipeDTO testRecipeDTO() {
        return RecipeDTO.builder()
                .recipeId("1")
                .name("Sample Recipe")
                .description("Sample Description")
                .calories(100)
                .carbs(20)
                .protein(10)
                .fat(5)
                .url("http://sample.url")
                .imageUrl("http://sample.image.url")
                .yield(4)
                .totalTime(30)
                .build();
    }

    private List<Ingredient> testIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(Ingredient.builder().foodId("1").name("Ingredient 1").build());
        ingredients.add(Ingredient.builder().foodId("2").name("Ingredient 2").build());
        return ingredients;
    }

    @Test
    public void GetAllRecipes_ReturnsAllRecipes() {
        // Arrange
        Recipe recipe1 = Recipe.builder().recipeId("1").name("Recipe 1").build();
        Recipe recipe2 = Recipe.builder().recipeId("2").name("Recipe 2").build();
        List<Recipe> expectedRecipes = List.of(recipe1, recipe2);
        when(recipeRepository.findAll()).thenReturn(expectedRecipes);

        // Act
        List<Recipe> actualRecipes = recipeService.getAllRecipes();

        // Assert
        assertEquals(expectedRecipes, actualRecipes);
    }

    @Test
    public void GetAllRecipes_NoRecipes_ReturnsEmptyList() {
        // Arrange
        when(recipeRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Recipe> actualRecipes = recipeService.getAllRecipes();

        // Assert
        assertEquals(Collections.emptyList(), actualRecipes);
    }

    @Test
    public void GetAllRecipes_RepositoryThrowsException_PropagatesException() {
        // Arrange
        RuntimeException exception = new RuntimeException("Database error");
        doThrow(exception).when(recipeRepository).findAll();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> recipeService.getAllRecipes(), "Database error");
    }

    @Test
    public void RecipeExists_ValidId_ReturnsTrue() {
        // Arrange
        String validId = "validRecipeId";
        Recipe recipe = new Recipe();
        recipe.setRecipeId(validId);
        when(recipeRepository.findByRecipeId(validId)).thenReturn(recipe);

        // Act
        boolean result = recipeService.recipeExists(validId);

        // Assert
        assertTrue(result);
    }

    @Test
    public void RecipeExists_InvalidId_ReturnsFalse() {
        // Arrange
        String invalidId = "invalidRecipeId";
        when(recipeRepository.findByRecipeId(invalidId)).thenReturn(null);

        // Act
        boolean result = recipeService.recipeExists(invalidId);

        // Assert
        assertFalse(result);
    }

    @Test
    public void RecipeExists_NullId_ReturnsFalse() {
        // Arrange
        String nullId = null;
        when(recipeRepository.findByRecipeId(nullId)).thenReturn(null);

        // Act
        boolean result = recipeService.recipeExists(nullId);

        // Assert
        assertFalse(result);
    }

    @Test
    public void GetRecipeById_ValidId_ReturnsRecipe() {
        // Arrange
        Recipe recipe = testRecipe();
        when(recipeRepository.findByRecipeId(recipe.getRecipeId())).thenReturn(recipe);

        // Act
        Recipe result = recipeService.getRecipeByRecipeId(recipe.getRecipeId());

        // Assert
        assertEquals(recipe.getRecipeId(), result.getRecipeId());
        assertEquals(recipe.getName(), result.getName());
        verify(recipeRepository, times(1)).findByRecipeId(recipe.getRecipeId());
    }

    @Test
    public void GetRecipeById_InvalidId_ThrowsException() {
        // Arrange
        when(recipeRepository.findByRecipeId("invalidRecipeId")).thenReturn(null);

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            recipeService.getRecipeByRecipeId("invalidRecipeId");
        });
        verify(recipeRepository, times(1)).findByRecipeId("invalidRecipeId");
    }

    @Test
    public void GetRecipeByName_ExistingName_ReturnsRecipes() {
        // Arrange
        Recipe recipe = testRecipe();
        List<Recipe> expectedRecipes = new ArrayList<>();
        expectedRecipes.add(recipe);
        when(recipeRepository.findByName(recipe.getName())).thenReturn(expectedRecipes);

        // Act
        List<Recipe> actualRecipes = recipeService.getRecipeByName(recipe.getName());

        // Assert
        assertEquals(expectedRecipes, actualRecipes);
    }

    @Test
    public void GetRecipeByName_NoExistingName_SavesRecipes() {
        // Arrange
        String name = "Nonexistent Recipe";
        List<Recipe> emptyRecipes = new ArrayList<>();
        when(recipeRepository.findByName(name)).thenReturn(emptyRecipes);

        // Act
        List<Recipe> actualRecipes = recipeService.getRecipeByName(name);

        // Assert
        verify(recipeRepository).saveAll(emptyRecipes);
    }

    @Test
    public void GetRecipeByName_ExceptionOccurs_ThrowsResponseStatusException() {
        // Arrange
        String name = "Test Recipe";
        List<Recipe> emptyRecipes = new ArrayList<>();
        when(recipeRepository.findByName(name)).thenReturn(emptyRecipes);
        doThrow(new RuntimeException()).when(recipeRepository).saveAll(emptyRecipes);

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            recipeService.getRecipeByName(name);
        });
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals("Unable to fetch recipes", exception.getReason());
    }

    @Test
    public void GetRecipeNames_ReturnsAllRecipeNames() {
        // Arrange
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(Recipe.builder().name("Recipe1").build());
        recipes.add(Recipe.builder().name("Recipe2").build());
        when(recipeRepository.findAll()).thenReturn(recipes);

        // Act
        List<String> recipeNames = recipeService.getRecipeNames();

        // Assert
        List<String> expectedNames = List.of("Recipe1", "Recipe2");
        assertEquals(expectedNames, recipeNames);
    }

    @Test
    public void GetRecipeNames_NoRecipes_ReturnsEmptyList() {
        // Arrange
        when(recipeRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<String> recipeNames = recipeService.getRecipeNames();

        // Assert
        assertEquals(new ArrayList<>(), recipeNames);
    }

    @Test
    public void GetRecipeNames_MultipleRecipes_ReturnsCorrectNames() {
        // Arrange
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(Recipe.builder().name("Recipe1").build());
        recipes.add(Recipe.builder().name("Recipe2").build());
        recipes.add(Recipe.builder().name("Recipe3").build());
        when(recipeRepository.findAll()).thenReturn(recipes);

        // Act
        List<String> recipeNames = recipeService.getRecipeNames();

        // Assert
        List<String> expectedNames = List.of("Recipe1", "Recipe2", "Recipe3");
        assertEquals(expectedNames, recipeNames);
    }

    @Test
    public void createRecipe_WithValidDTO_CreatesAndSavesRecipe() {
        RecipeDTO recipeDTO = testRecipeDTO();
        List<Ingredient> ingredients = testIngredients();
        when(ingredientService.getIngredients()).thenReturn(ingredients);
        when(ingredientRepository.saveAndFlush(any(Ingredient.class))).thenAnswer(i -> i.getArguments()[0]);

        Recipe result = recipeService.createRecipe(recipeDTO);

        assertEquals(recipeDTO.getRecipeId(), result.getRecipeId());
        assertEquals(recipeDTO.getName(), result.getName());
        assertEquals(recipeDTO.getDescription(), result.getDescription());
        assertEquals(recipeDTO.getCalories(), result.getCalories());
        assertEquals(recipeDTO.getCarbs(), result.getCarbs());
        assertEquals(recipeDTO.getProtein(), result.getProtein());
        assertEquals(recipeDTO.getFat(), result.getFat());
        assertEquals(recipeDTO.getUrl(), result.getUrl());
        assertEquals(recipeDTO.getImageUrl(), result.getImageUrl());
        assertEquals(recipeDTO.getYield(), result.getYield());
        assertEquals(recipeDTO.getTotalTime(), result.getTotalTime());
        verify(recipeRepository).save(result);
    }

    @Test
    public void createRecipe_WithValidDTO_MergesAndSavesIngredients() {
        RecipeDTO recipeDTO = testRecipeDTO();
        List<Ingredient> ingredients = testIngredients();
        when(ingredientService.getIngredients()).thenReturn(ingredients);
        when(ingredientRepository.saveAndFlush(any(Ingredient.class))).thenAnswer(i -> i.getArguments()[0]);

        Recipe result = recipeService.createRecipe(recipeDTO);

        for (Ingredient ingredient : ingredients) {
            verify(ingredientRepository).saveAndFlush(ingredient);
        }
        assertEquals(ingredients, result.getIngredients());
    }

    @Test
    public void createRecipe_TransactionManagement_EnsuresConsistency() {
        RecipeDTO recipeDTO = testRecipeDTO();
        List<Ingredient> ingredients = testIngredients();
        when(ingredientService.getIngredients()).thenReturn(ingredients);
        when(ingredientRepository.saveAndFlush(any(Ingredient.class))).thenAnswer(i -> i.getArguments()[0]);

        Recipe result = recipeService.createRecipe(recipeDTO);

        verify(recipeRepository).save(result);
        for (Ingredient ingredient : ingredients) {
            verify(ingredientRepository).saveAndFlush(ingredient);
        }
    }
}