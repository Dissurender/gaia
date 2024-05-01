package dsd.cohort.application.ingredient;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import dsd.cohort.application.recipe.RecipeEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "ingredients")
public class IngredientEntity implements Serializable {

    public IngredientEntity() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    // primary search field
    @Column(name = "food_id", unique = true)
    private String foodId;

    @Column(name = "name")
    private String name;

    // represents the name of the ingredient
    @Column(name = "text")
    private String text;

    @Column(name = "image_url", length = 2083)
    private String imageUrl;

    // represents the count of measures
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "measure", nullable = true)
    private String measure;

    @Column(name = "weight")
    private double weight;

    @Column(name = "food_category")
    private String foodCategory;
  
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

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

    @Override
    public String toString() {
        return "IngredientEntity{" +
                "id=" + id +
                ", foodId='" + foodId + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", quantity='" + quantity + '\'' +
                ", measure=" + measure +
                ", weight=" + weight +
                ", foodCategory=" + foodCategory +
                '}';
    }

}

