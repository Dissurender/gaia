package dsd.cohort.application.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findByFoodId(String foodId);

    @Query(value = "SELECT * FROM ingredients ORDER BY RANDOM() LIMIT 5", nativeQuery = true)
    List<Ingredient> sample(@Param("count") int count);
}
