package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.Recipe;
import com.boldyrev.foodhelper.models.RecipeIngredient;
import com.boldyrev.foodhelper.models.RecipeIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RecipeIngredientsRepository extends
    JpaRepository<RecipeIngredient, RecipeIngredientId> {

    @Modifying(flushAutomatically = true)
    @Query("DELETE FROM RecipeIngredient WHERE id.recipe.id = :id")
    void deleteAllByRecipeId(@Param("id") int id);
}
