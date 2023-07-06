package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.RecipeCategory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoriesRepository extends JpaRepository<RecipeCategory, Integer> {

    Optional<RecipeCategory> findByNameIgnoreCase(String name);
}
