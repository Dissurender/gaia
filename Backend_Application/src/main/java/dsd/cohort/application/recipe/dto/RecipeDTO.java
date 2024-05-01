package dsd.cohort.application.recipe.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsd.cohort.application.ingredient.IngredientDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeDTO implements Serializable {

  private String recipeId;

  @JsonProperty("label")
  private String name;
  private int yield;
  private int totalTime;

  @JsonProperty("image")
  private String imageUrl;
  private String url;

  private String calories;

  @JsonProperty("totalNutrients")
  private Nutrients nutrients;
  private List<IngredientDTO> ingredients;

  public Nutrients getNutrients() {
    return nutrients;
  }

  public void setNutrients(Nutrients nutrients) {
    this.nutrients = nutrients;
  }

  public String getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(String recipeId) {
    String[] urlArray = url.split("#");
    this.recipeId = urlArray[urlArray.length - 1];
    ;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getYield() {
    return yield;
  }

  public void setYield(int yield) {
    this.yield = yield;
  }

  public int getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(int totalTime) {
    this.totalTime = totalTime;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCalories() {
    return calories;
  }

  public void setCalories(String calories) {
    this.calories = calories;
  }

  public List<IngredientDTO> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<IngredientDTO> ingredients) {
    this.ingredients = ingredients;
  }
}
