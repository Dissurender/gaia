package dsd.cohort.application.ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
    
    @Query(value = "SELECT i FROM IngredientEntity i WHERE i.foodId=:foodId")
    IngredientEntity findByFoodId(String foodId);
}
