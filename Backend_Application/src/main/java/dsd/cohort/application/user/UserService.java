package dsd.cohort.application.user;

import dsd.cohort.application.ingredient.Ingredient;
import dsd.cohort.application.ingredient.IngredientRepository;
import dsd.cohort.application.recipe.Recipe;
import dsd.cohort.application.recipe.RecipeRepository;
import dsd.cohort.application.user.dto.UserDataRequestDTO;
import dsd.cohort.application.user.dto.UserRegisterDTO;
import dsd.cohort.application.user.dto.UserRequestDTO;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository usersRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final PasswordEncoder passwordEncoder;

    // TODO: decay func
    @Deprecated
    public boolean userExists(String email) {
        return usersRepository.findByEmail(email).isPresent();
    }

    public boolean countUsers() {
        return usersRepository.count() > 0;
    }

    public User createUser(UserRegisterDTO user) {

        if (usersRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EntityExistsException();
        }

        User newUser = new User.Builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        return usersRepository.save(newUser);
    }


    public void addRecipe(UserDataRequestDTO userDataRequestDTO) {

        User user = usersRepository.findByEmail(userDataRequestDTO.getEmail()).orElseThrow();
        Recipe recipe = recipeRepository.findByRecipeId(userDataRequestDTO.getId());

        if (userExists(userDataRequestDTO.getEmail()) && recipe != null) {
            user.getFavoriteRecipes().add(recipe);
            usersRepository.save(user);
            //Temporary add all recipe ingredients to Grocery List
            this.addGroceryItems(user, recipe);
        }

    }


    public void deleteRecipe(String email, String recipeId) {

        User user = usersRepository.findByEmail(email).orElseThrow();
        Recipe recipe = recipeRepository.findByRecipeId(recipeId);

        if (userExists(email) && recipe != null) {

            if (!user.getFavoriteRecipes().contains(recipe)) {
                return;
            }

            user.getFavoriteRecipes().remove(recipe);
            usersRepository.save(user);
        }

    }


    public List<User> getAll() {
        return usersRepository.findAll();
    }


    public Set<Recipe> getUserFavorites(String email) {

        User user = usersRepository.findByEmail(email).orElseThrow();
        if (userExists(email)) {
            return user.getFavoriteRecipes();
        }
        return null;
    }


    public boolean addGroceryItem(UserDataRequestDTO userDataRequestDTO) {

        User user = usersRepository.findByEmail(userDataRequestDTO.getEmail()).orElseThrow();
        Ingredient ingredient = ingredientRepository.findByFoodId(userDataRequestDTO.getId());
        user.getGroceryList().add(ingredient);
        usersRepository.save(user);

        return user.getGroceryList().contains(ingredient);

    }

    public void addGroceryItems(User user, Recipe recipe) {

        for (Ingredient ingredient : recipe.getIngredients()) {
            user.getGroceryList().add(ingredient);
        }
        usersRepository.save(user);
    }


    public Set<Ingredient> getGroceryList(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow()
                .getGroceryList();
    }


    public boolean removeFromGroceryList(UserDataRequestDTO userDataRequestDTO) {
        User user = usersRepository.findByEmail(userDataRequestDTO.getEmail()).orElseThrow();
        Ingredient ingredient = ingredientRepository.findByFoodId(userDataRequestDTO.getId());

        if (userExists(userDataRequestDTO.getEmail()) && ingredient != null) {

            if (!user.getGroceryList().contains(ingredient)) {
                return false;
            }

            user.getGroceryList().remove(ingredient);
            usersRepository.save(user);
            return true;
        }

        return false;
    }

    /**
     * Authenticates a user based on the provided UserRequestDTO by encrypting
     * the password and comparing it to the stored encrypted password.
     * <p>
     * WARNING: THIS METHOD IS A DEMO METHOD AND SHOULD NOT BE USED IN PRODUCTION
     *
     * @param userRequestDTO the UserRequestDTO containing user credentials
     * @return true if the user authentication is successful
     * @throws NoSuchElementException if the user is not found
     */

    public User userauth(UserRequestDTO userRequestDTO) throws NoSuchElementException {

        if (!userExists(userRequestDTO.getEmail())) {
            throw new NoSuchElementException("User not found");
        }

        User user = usersRepository.findByEmail(userRequestDTO.getEmail()).orElseThrow();
        if (user.getPassword().equals(passwordEncoder.encode(userRequestDTO.getPassword()))) {
            return user;
        }
        return null;
    }
}
