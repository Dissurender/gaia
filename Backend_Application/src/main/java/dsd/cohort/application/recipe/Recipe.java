package dsd.cohort.application.recipe;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import dsd.cohort.application.ingredient.Ingredient;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    @Column(name = "recipe_id", unique = true)
    private String recipeId;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
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
    private List<Ingredient> ingredients = new ArrayList<>();

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
