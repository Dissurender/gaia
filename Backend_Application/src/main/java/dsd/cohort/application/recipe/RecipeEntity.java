package dsd.cohort.application.recipe;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import dsd.cohort.application.ingredient.IngredientEntity;
import dsd.cohort.application.recipe.dto.RecipeDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "recipes")
public class RecipeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private transient Long id;

    @Column(name = "recipe_id", unique = true)
    private String recipeId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "yield")
    private Integer yield;

    @Column(name = "total_time")
    private Integer totalTime; // in minutes

    @Column(name = "image_url", length = 2083)
    private String imageUrl;

    @Column(name = "url", length = 2083)
    private String url;

    @Column(name = "protein")
    private double protein;

    @Column(name = "fat")
    private double fat;

    @Column(name = "carbs")
    private double carbs;

    @Column(name = "calories")
    private double calories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<IngredientEntity> ingredients = new HashSet<>();

    public RecipeEntity() {}

    public RecipeEntity(RecipeDTO recipeDTO) {
        this.recipeId = recipeDTO.getRecipeId();
        this.name = recipeDTO.getName();
        this.yield = recipeDTO.getYield();
        this.totalTime = recipeDTO.getTotalTime();
        this.imageUrl = recipeDTO.getImageUrl();
        this.url = recipeDTO.getUrl();
        this.protein = recipeDTO.getNutrients().getProtein();
        this.fat = recipeDTO.getNutrients().getFat();
        this.carbs = recipeDTO.getNutrients().getCarbs();
        this.calories = recipeDTO.getNutrients().getCalories();
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYield() {
        return yield;
    }

    public void setYield(Integer yield) {
        this.yield = yield;
    }

    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
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

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public Set<IngredientEntity> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<IngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "RecipeEntity{" +
                "id=" + id +
                ", recipeId='" + recipeId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", yield=" + yield +
                ", totalTime=" + totalTime +
                ", imageUrl='" + imageUrl + '\'' +
                ", url='" + url + '\'' +
                ", protein=" + protein +
                ", fat=" + fat +
                ", carbs=" + carbs +
                ", calories=" + calories +
                ", ingredients=" + ingredients +
                '}';
    }
}
