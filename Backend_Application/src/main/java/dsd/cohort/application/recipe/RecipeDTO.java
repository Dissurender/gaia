package dsd.cohort.application.recipe;

import java.util.List;

import dsd.cohort.application.ingredient.IngredientDTO;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO {

  private String recipeId;
  private String name;
  private String description;
  private int yield;
  private int totalTime;
  private String imageUrl;
  private String url;
  private double protein;
  private double fat;
  private double carbs;
  private double calories;
  private String foodCategory;

}
