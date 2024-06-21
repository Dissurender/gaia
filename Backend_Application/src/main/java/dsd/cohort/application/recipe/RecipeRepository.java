package dsd.cohort.application.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {

    @Query(value = "SELECT r FROM Recipe r WHERE r.name LIKE '%:name%'")
    List<Recipe> findByName(String name);

    @Query(value = "SELECT r FROM Recipe r WHERE r.recipeId=:recipeId")
    Recipe findByRecipeId(@Param("recipeId") String recipeId);
}
