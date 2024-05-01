package dsd.cohort.application.ingredient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IngredientDTO implements Serializable {
  
  private String foodId;
  private String name;
  private String text;
  private String imageUrl;
  private int quantity;
  private String measure;
  private double weight;
  private String foodCategory;

  public String getFoodId() {
    return foodId;
  }

  public void setFoodId(String foodId) {
    this.foodId = foodId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public String getMeasure() {
    return measure;
  }

  public void setMeasure(String measure) {
    this.measure = measure;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public String getFoodCategory() {
    return foodCategory;
  }

  public void setFoodCategory(String foodCategory) {
    this.foodCategory = foodCategory;
  }
}
