package com.boldyrev.foodhelper.repositories;

import com.boldyrev.foodhelper.models.Recipe;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipesRepository extends JpaRepository<Recipe, Integer> {

    Optional<Recipe> findByTitleIgnoreCase(String title);

    @Query(value = """
        SELECT DISTINCT r 
        FROM Recipe r 
        JOIN FETCH r.category c
        JOIN FETCH c.parentCategory 
        JOIN FETCH r.creator 
        JOIN FETCH r.recipeIngredients i
        JOIN FETCH i.id.ingredient
        WHERE i.id IN :ingredients """)
    List<Recipe> findAllByIngredients(@Param("ingredients") List<Integer> ingredients);

    @Query(value = """      
        SELECT DISTINCT r 
        FROM Recipe r 
        JOIN FETCH r.category c
        JOIN FETCH c.parentCategory 
        JOIN FETCH r.creator 
        LEFT JOIN FETCH r.recipeIngredients i
        LEFT JOIN FETCH i.id.ingredient""")
    Page<Recipe> findAll(Pageable pageable);

    @Query("""
        SELECT r 
        FROM Recipe r 
        JOIN FETCH r.category c
        JOIN FETCH c.parentCategory 
        JOIN FETCH r.creator 
        LEFT JOIN FETCH r.recipeIngredients i
        LEFT JOIN FETCH i.id.ingredient
        WHERE r.id = :id
        """)
    Optional<Recipe> findById(@Param("id") int id);

    @Query("""
        SELECT DISTINCT r 
        FROM Recipe r 
        JOIN FETCH r.category c
        JOIN FETCH c.parentCategory 
        JOIN FETCH r.creator 
        LEFT JOIN FETCH r.recipeIngredients i
        LEFT JOIN FETCH i.id.ingredient
        WHERE c.id = :id
        """)
    Page<Recipe> findAllByCategoryId(@Param("id") int id, Pageable pageable);

    @Query("""
        SELECT DISTINCT r 
        FROM Recipe r 
        JOIN FETCH r.category c
        JOIN FETCH c.parentCategory 
        JOIN FETCH r.creator 
        LEFT JOIN FETCH r.recipeIngredients i
        LEFT JOIN FETCH i.id.ingredient 
        WHERE r.id IN(
            SELECT r.id.recipe.id 
            FROM RecipeIngredient r 
            WHERE r.id.ingredient.id in :id
            GROUP BY 1
            HAVING COUNT(r.id.recipe.id) >= :count)
        """)
    Page<Recipe> findAllByIngredientsId(@Param("id") List<Integer> ingredients, @Param("count") int ingredientsCount, Pageable pageable);
}
