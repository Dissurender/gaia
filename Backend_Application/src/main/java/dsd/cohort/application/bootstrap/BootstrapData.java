package dsd.cohort.application.bootstrap;

import dsd.cohort.application.ingredient.IngredientRepository;
import dsd.cohort.application.recipe.RecipeRepository;
import dsd.cohort.application.user.User;
import dsd.cohort.application.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static dsd.cohort.application.util.Encryption.encryptString;

@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Number of users: " + userRepository.count());
//
//        if (userRepository.count() == 0) {
//            System.out.println("Loading bootstrap data...");
//
//            User user = new User();
//            user.setFirstName("Johnny");
//            user.setLastName("Test");
//            user.setEmail("test@test.com");
//            user.setPassword(passwordEncoder.encode("testPassword@1"));
//            userRepository.save(user);
//
//            user = new User();
//            user.setFirstName("Jane");
//            user.setLastName("Test");
//            user.setEmail("test2@test.com");
//            user.setPassword(encryptString("testPassword@1"));
//            userRepository.save(user);
//
//            user = new User();
//            user.setFirstName("Mittens");
//            user.setLastName("Gato");
//            user.setEmail("definitelynotacat@feline.online");
//            user.setPassword(encryptString("FancyFe@st9"));
//            userRepository.save(user);
//
//            user = new User();
//            user.setFirstName("Kitsune");
//            user.setLastName("Gato");
//            user.setEmail("finef3line@cat.naps");
//            user.setPassword(encryptString("Afet3rnoonN@pper"));
//            userRepository.save(user);
//
//            user = new User();
//            user.setFirstName("Harrison");
//            user.setLastName("Couturiaux");
//            user.setEmail("bestboi@pawsome.com");
//            user.setPassword(encryptString("theM@ilmanKn0ws"));
//            userRepository.save(user);
//
//            System.out.println("Bootstrap Data Loaded: Users - " + userRepository.count());
//        }
//
//        if (recipeRepository.findAll().isEmpty()) {
//
//
////
////      Recipe recipe = new Recipe();
////
////      recipe.setName("Test Recipe");
////      recipe.setDescription("Test Instructions");
////      recipe.setRecipeId("Test_Chicken");
////      recipe.setName("Chicken fried test");
////      recipe.setImageUrl("https://images.unsplash.com/photo-1606728035253-49e8a23146de?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
////      recipe.setUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
////      recipe.setCalories(420);
////      recipe.setCarbs(100);
////      recipe.setProtein(100);
////      recipe.setFat(100);
////      recipe.setYield(3);
////      recipe.setTotalTime(20);
////      recipeRepository.save(recipe);
////
////      recipe = new Recipe();
////      recipe.setName("Test Recipe 2");
////      recipe.setDescription("You've been borgered");
////      recipe.setRecipeId("Test_Borger");
////      recipe.setName("Borger test");
////      recipe.setImageUrl("https://images.unsplash.com/photo-1606728035253-49e8a23146de?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
////      recipe.setUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
////      recipe.setCalories(300);
////      recipe.setCarbs(100);
////      recipe.setProtein(100);
////      recipe.setFat(100);
////      recipe.setYield(1);
////      recipe.setTotalTime(2);
////      recipeRepository.save(recipe);
////
//            System.out.println("Bootstrap Data Loaded: Recipes - " + recipeRepository.count());
//        }
//
//        if (ingredientRepository.findAll().isEmpty()) {
//      Ingredient ingredient = new Ingredient();
//
//      ingredient.setName("Chicken");
//      ingredient.setText("Chicken");
//      ingredient.setFoodId("chicken");
//      ingredient.setImageUrl(null);
//      ingredient.setQuantity(0);
//      ingredient.setWeight(0);
//      ingredient.setMeasure(null);
//      ingredient.setFoodCategory("Meat");
//      ingredientRepository.save(ingredient);
        }
    }

