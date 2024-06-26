package dsd.cohort.application.ingredient;

import com.fasterxml.jackson.annotation.JsonProperty;
import dsd.cohort.application.recipe.Recipe;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ingredient_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID id;

    // primary search field
    @Column(name = "food_id", unique = true)
    private String foodId;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "text", columnDefinition = "TEXT")
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

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

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
                '}';
    }

}

