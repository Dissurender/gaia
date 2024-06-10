package dsd.cohort.application.Utils;

import dsd.cohort.application.auth.AuthenticationService;
import dsd.cohort.application.auth.RegisterRequestDTO;
import dsd.cohort.application.ingredient.Ingredient;
import dsd.cohort.application.ingredient.IngredientDTO;
import dsd.cohort.application.ingredient.IngredientService;
import dsd.cohort.application.recipe.Recipe;
import dsd.cohort.application.recipe.RecipeDTO;
import dsd.cohort.application.recipe.RecipeService;
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
    private final PasswordEncoder passwordEncoder;
    private final IngredientService ingredientService;
    private final DecimalFormat df = new DecimalFormat("#.00");

    @PostConstruct
    public void loadData() {
        String directory = "src/main/resources/csv";

        try (Stream<Path> files = Files.walk(Paths.get(directory))
                .filter(Files::isRegularFile)
        ) {
            List<Path> filePaths = files.toList();

            // Ensure that ingredients are parsed before recipes
            filePaths.stream()
                    .filter(path -> path.getFileName().toString().equalsIgnoreCase("MOCK_DATA_INGREDIENT.csv"))
                    .forEach(this::fileProcessor);

            filePaths.stream()
                    .filter(path -> !path.getFileName().toString().equalsIgnoreCase("MOCK_DATA_INGREDIENT.csv"))
                    .forEach(this::fileProcessor);

            System.out.println(filePaths.size() + " files parsed.");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void fileProcessor(Path filePath) {

        try (FileReader reader = new FileReader(filePath.toFile());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)
        ) {
            String fileName = filePath.getFileName().toString();

            switch (fileName) {
                case "MOCK_DATA_USER.csv":
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

                case "MOCK_DATA_RECIPE.csv":
                    for (CSVRecord csvRecord : csvParser) {
                        Recipe recipe = recipeService.createRecipe(
                                // recipe_id,name,description,yield,total_time,image_url,url,protein,fat,carbs,calories
                                RecipeDTO.builder()
                                        .recipeId(csvRecord.get("recipe_id"))
                                        .name(csvRecord.get("name"))
                                        .description(csvRecord.get("description"))
                                        .yield(Integer.parseInt(csvRecord.get("yield")))
                                        .totalTime(Integer.parseInt(csvRecord.get("total_time")))
                                        .imageUrl(csvRecord.get("image_url"))
                                        .url(csvRecord.get("url"))
                                        .protein(Double.parseDouble(df.format(csvRecord.get("protein"))))
                                        .fat(Double.parseDouble(df.format(csvRecord.get("fat"))))
                                        .carbs(Double.parseDouble(df.format(csvRecord.get("carbs"))))
                                        .calories(Double.parseDouble(df.format(csvRecord.get("calories"))))
                                        .build()
                        );

                        if (recipe == null) {
                            logger.error("Error creating recipe: {}", csvRecord.get("recipe_id"));
                        }
                    }
                    break;

                case "MOCK_DATA_INGREDIENT.csv":
                    for (CSVRecord csvRecord : csvParser) {
                        // food_id	name	text	image_url	quantity	measure	weight	food_category
                        Ingredient ingredient = ingredientService.createIngredient(
                                IngredientDTO.builder()
                                        .foodId(csvRecord.get("food_id"))
                                        .name(csvRecord.get("name"))
                                        .text(csvRecord.get("text"))
                                        .imageUrl(csvRecord.get("image_url"))
                                        .quantity(Integer.parseInt(csvRecord.get("quantity")))
                                        .measure(csvRecord.get("measure"))
                                        .weight(Double.parseDouble(df.format(csvRecord.get("wieght"))))
                                        .foodCategory(csvRecord.get("food_category"))
                                        .build()
                        );

                        if (ingredient == null) {
                            logger.error("Error creating ingredient: {}", csvRecord.get("food_id"));
                        }
                    }
                    break;

                default:
                    throw new IOException("Unsupported file format or unrecognized file");
            }


        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
