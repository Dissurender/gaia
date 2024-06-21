package dsd.cohort.application.ingredient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class IngredientDTO {

  private String foodId;
  private String name;
  private String text;
  private String imageUrl;
  private int quantity;
  private String measure;
  private double weight;

}
