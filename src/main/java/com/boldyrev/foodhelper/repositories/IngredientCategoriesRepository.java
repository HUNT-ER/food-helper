package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.IngredientCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IngredientCategoriesRepository extends JpaRepository<IngredientCategory, Integer> {

    Optional<IngredientCategory> findByNameIgnoreCase(String name);

}
