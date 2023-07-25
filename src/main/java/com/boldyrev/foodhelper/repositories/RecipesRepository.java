package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.Recipe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RecipesRepository extends JpaRepository<Recipe, Integer> {

    Optional<Recipe> findByTitleIgnoreCase(String title);

    @Query(value = "SELECT r FROM Recipe r INNER JOIN r.ingredients i WHERE i.id IN :ingredients GROUP BY r.id")
    List<Recipe> findAllByIngredients(List<Integer> ingredients);

}
