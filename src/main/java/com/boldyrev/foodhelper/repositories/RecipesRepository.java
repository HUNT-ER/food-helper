package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.Recipe;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipesRepository extends JpaRepository<Recipe, Integer> {

    Optional<Recipe> findByTitleIgnoreCase(String title);
}
