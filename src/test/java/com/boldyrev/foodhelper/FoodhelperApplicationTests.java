package com.boldyrev.foodhelper;

import com.boldyrev.foodhelper.models.Ingredient;
import com.boldyrev.foodhelper.models.IngredientCategory;
import com.boldyrev.foodhelper.models.Recipe;
import com.boldyrev.foodhelper.models.RecipeCategory;
import com.boldyrev.foodhelper.repositories.IngredientCategoriesRepository;
import com.boldyrev.foodhelper.repositories.IngredientsRepository;
import com.boldyrev.foodhelper.repositories.RecipeCategoriesRepository;
import com.boldyrev.foodhelper.repositories.RecipesRepository;
import com.boldyrev.foodhelper.repositories.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class FoodhelperApplicationTests {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private IngredientCategoriesRepository ingredientCategoriesRepository;
    @Autowired
    private IngredientsRepository ingredientsRepository;
    @Autowired
    private RecipeCategoriesRepository recipeCategoriesRepository;
    @Autowired
    private RecipesRepository recipesRepository;
    @Autowired
    private UsersRepository usersRepository;



    @Test
    @Rollback(false)
    @Transactional
    void contextLoads() throws Exception {
        Ingredient ingredient = ingredientsRepository.findByNameIgnoreCase("Beef fillet").get();
        Ingredient ingredient2 = ingredientsRepository.findByNameIgnoreCase("Chicken breast").get();

        Recipe recipe = new Recipe();
        recipe.setCreator(usersRepository.findByUsernameIgnoreCase("hunter").get());
        recipe.setCategory(recipeCategoriesRepository.findByNameIgnoreCase("Cream-soups").get());
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setTitle("title");
        recipe.setDescription("description");
        recipe.setIngredients(List.of(ingredient, ingredient2));

        recipesRepository.save(recipe);

        System.out.println(recipe.getIngredients().get(0).getRecipes().get(0).getTitle());
    }

}
