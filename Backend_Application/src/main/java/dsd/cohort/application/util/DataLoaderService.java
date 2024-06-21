package dsd.cohort.application.util;

import dsd.cohort.application.auth.AuthenticationService;
import dsd.cohort.application.auth.RegisterRequestDTO;
import dsd.cohort.application.ingredient.Ingredient;
import dsd.cohort.application.ingredient.IngredientDTO;
import dsd.cohort.application.ingredient.IngredientService;
import dsd.cohort.application.recipe.Recipe;
import dsd.cohort.application.recipe.RecipeDTO;
import dsd.cohort.application.recipe.RecipeService;
import dsd.cohort.application.user.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DataLoaderService {

    private final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);

    private final AuthenticationService authenticationService;
    private final RecipeService recipeService;
    private final UserService userService;
    private final IngredientService ingredientService;

    private final PasswordEncoder passwordEncoder;
    private final DecimalFormat df = new DecimalFormat("#.00");

    /**
     * Initializes csv parsing by scanning a specified directory for CSV files,
     * ensuring that ingredient data is processed before other data types. Logs the progress
     * and handles any IO exceptions that may occur.
     *
     * @throws IOException if an IO error occurs during file processing
     */
    @PostConstruct
    public void loadData() throws IOException {
        logger.info("Starting data loader service...");
        logger.info("DataLoader service started.");

        String directory = "Backend_Application/src/main/resources/csv";
        Path path = Paths.get(directory);

        if (!Files.exists(path)) {
            logger.info("DataLoader service directory does not exist.");
            logger.info("Absolute path: {}", path.toAbsolutePath());
            return;
        }

        try (Stream<Path> stream = Files.walk(path)) {
            List<Path> filePaths = stream
                    .filter(Files::isRegularFile)
                    .toList();
            logger.info("Found {} files in {}", filePaths.size(), directory);

            filePaths.stream()
                    .filter(filePath -> filePath.getFileName().toString().equalsIgnoreCase("MOCK_DATA_INGREDIENT.csv"))
                    .forEach(this::fileProcessor);
            logger.info("Finished loading ingredient data.");

            filePaths.stream()
                    .filter(filePath -> !filePath.getFileName().toString().equalsIgnoreCase("MOCK_DATA_INGREDIENT.csv"))
                    .forEach(this::fileProcessor);
            logger.info("Finished loading other data.");

            logger.info("{} files parsed.", filePaths.size());
        } catch (IOException e) {
            logger.error("Error while reading csv files.", e);
        }
    }

    private void fileProcessor(Path filePath) {

        try (FileReader reader = new FileReader(filePath.toFile());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())
        ) {
            String fileName = filePath.getFileName().toString();

            switch (fileName) {
                case "MOCK_DATA_USER.csv":

                    if (userService.countUsers()) {
                        logger.info("Found {} users.", userService.countUsers());
                        logger.info("Skipping user registrations.");
                        return;
                    }

                    logger.info("Loading MOCK_DATA_USER.csv...");
                    for (CSVRecord csvRecord : csvParser) {
                        authenticationService.register(
                                RegisterRequestDTO.builder()
                                        .firstName(csvRecord.get("first_name"))
                                        .lastName(csvRecord.get("last_name"))
                                        .email(csvRecord.get("email"))
                                        .password(passwordEncoder.encode(csvRecord.get("password")))
                                        .build()
                        );
                    }
                    break;

                case "MOCK_DATA_RECIPES.csv":

                    if (!recipeService.getAllRecipes().isEmpty()) {
                        logger.info("Found {} recipes.", recipeService.getAllRecipes().size());
                        logger.info("Skipping recipe creations.");
                        return;
                    }

                    logger.info("Loading MOCK_DATA_RECIPES.csv...");
                    for (CSVRecord csvRecord : csvParser) {

                        if (recipeService.recipeExists(csvRecord.get("recipe_id"))) {
                            logger.info("Found recipe with id {}, skipping.", csvRecord.get("recipe_id"));
                            continue;
                        }

                        Recipe recipe = recipeService.createRecipe(
                                RecipeDTO.builder()
                                        .recipeId(csvRecord.get("recipe_id"))
                                        .name(csvRecord.get("name"))
                                        .description(csvRecord.get("description"))
                                        .yield(Integer.parseInt(csvRecord.get("yield")))
                                        .totalTime(Integer.parseInt(csvRecord.get("total_time")))
                                        .imageUrl(csvRecord.get("image_url"))
                                        .url(csvRecord.get("url"))
                                        .protein(Double.parseDouble(csvRecord.get("protein")))
                                        .fat(Double.parseDouble(csvRecord.get("fat")))
                                        .carbs(Double.parseDouble(csvRecord.get("carbs")))
                                        .calories(Double.parseDouble(csvRecord.get("calories")))
                                        .foodCategory(csvRecord.get("food_category"))
                                        .build()
                        );

                        if (recipe == null) {
                            logger.error("Error creating recipe: {}", csvRecord.get("recipe_id"));
                        }
                    }
                    break;

                case "MOCK_DATA_INGREDIENTS.csv":

                    if (!ingredientService.getAllIngredients().isEmpty()) {
                        logger.info("Found {} ingredients.", ingredientService.getAllIngredients().size());
                        logger.info("Skipping ingredients creations.");
                        return;
                    }

                    logger.info("Loading MOCK_DATA_INGREDIENT.csv...");
                    for (CSVRecord csvRecord : csvParser) {

                        if (ingredientService.getIngredientByFoodId(csvRecord.get("food_id")) != null) {
                            logger.info("Found ingredient with id {}, skipping.", csvRecord.get("food_id"));
                            continue;
                        }

                        Ingredient ingredient = ingredientService.createIngredient(
                                IngredientDTO.builder()
                                        .foodId(csvRecord.get("food_id"))
                                        .name(csvRecord.get("name"))
                                        .text(csvRecord.get("text"))
                                        .imageUrl(csvRecord.get("image_url"))
                                        .quantity(Integer.parseInt(csvRecord.get("quantity")))
                                        .measure(csvRecord.get("measure"))
                                        .weight(Double.parseDouble(csvRecord.get("weight")))
                                        .build()
                        );

                        if (ingredient == null) {
                            logger.error("Error creating ingredient: {}", csvRecord.get("food_id"));
                        }
                    }
                    break;

                default:
                    logger.info("Ignoring unknown file: {}", fileName);
                    throw new IOException("Unsupported file format or unrecognized file");
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
