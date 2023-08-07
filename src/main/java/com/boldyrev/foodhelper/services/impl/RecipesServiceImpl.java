package com.boldyrev.foodhelper.services.impl;

import com.boldyrev.foodhelper.exceptions.EmptyDataException;
import com.boldyrev.foodhelper.exceptions.EntityNotFoundException;
import com.boldyrev.foodhelper.models.Ingredient;
import com.boldyrev.foodhelper.models.Recipe;
import com.boldyrev.foodhelper.repositories.IngredientsRepository;
import com.boldyrev.foodhelper.repositories.RecipeIngredientsRepository;
import com.boldyrev.foodhelper.repositories.RecipesRepository;
import com.boldyrev.foodhelper.services.ImageS3Service;
import com.boldyrev.foodhelper.services.IngredientsService;
import com.boldyrev.foodhelper.services.RecipesService;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@PropertySource("classpath:s3minio.properties")
public class RecipesServiceImpl implements RecipesService {

    private final RecipesRepository recipesRepository;
    private final ImageS3Service imageService;
    private final IngredientsService ingredientsService;

    private final IngredientsRepository ingredientsRepository;

    private final RecipeIngredientsRepository recipeIngredientsRepository;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${s3.bucket.name}")
    private String bucketName;

    @Value("${s3.path.recipes}")
    private String imagePath;

    @Autowired
    public RecipesServiceImpl(RecipesRepository recipesRepository,
        ImageS3Service imageService, IngredientsService ingredientsService,
        IngredientsRepository ingredientsRepository,
        RecipeIngredientsRepository recipeIngredientsRepository) {
        this.recipesRepository = recipesRepository;
        this.imageService = imageService;
        this.ingredientsService = ingredientsService;
        this.ingredientsRepository = ingredientsRepository;
        this.recipeIngredientsRepository = recipeIngredientsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe findById(int id) {
        log.debug("Getting Recipe with id={}", id);
        return recipesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
            String.format("Recipe with id=%d not found.", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe findByTitle(String title) {
        log.debug("Getting Recipe with title={}", title);
        return recipesRepository.findByTitleIgnoreCase(title).orElseThrow(
            () -> new EntityNotFoundException(
                String.format("Recipe with title=%s not found.", title)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recipe> findAll() {
        log.debug("Getting all Recipes");

        List<Recipe> recipes = recipesRepository.findAll();

        if (recipes.isEmpty()) {
            log.debug("Recipes not found");
            throw new EmptyDataException("Recipes not found");
        }

        return recipes;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recipe> findAllByIngredients(List<Ingredient> ingredients) {
        log.debug("Finding Recipes by Ingredients");

        List<Recipe> recipes = recipesRepository.findAllByIngredients(
            ingredients.stream().map(x -> x.getId()).distinct().toList());

        if (recipes.isEmpty()) {
            log.debug("Recipes by ingredients not found");
            throw new EmptyDataException("Recipes by ingredients not found");
        }

        return recipes;

        //todo засунуть в Query ингредиент
    }

    @Override
    @Transactional
    public Recipe save(Recipe recipe) {
        //todo запретить дубликаты
        log.debug("Saving recipe with title={}", recipe.getTitle());
        enrich(recipe);
        return recipesRepository.save(recipe);
    }

    @Override
    @Transactional
    public void update(int id, Recipe recipe) {
        Recipe storedRecipe = this.findById(id);
        log.debug("Updating recipe with title={} and id={}", storedRecipe.getTitle(),
            storedRecipe.getId());

        recipe.getRecipeIngredients().forEach(r -> r.setRecipe(storedRecipe));

        storedRecipe.setTitle(recipe.getTitle());
        storedRecipe.setDescription(recipe.getDescription());
        storedRecipe.setCategory(recipe.getCategory());
        storedRecipe.setCreator(recipe.getCreator());

        //Updating recipes
        storedRecipe.getRecipeIngredients().forEach(r -> {
            r.setIngredient(null);
            r.setRecipe(null);
            r.setId(null);
        });

        recipeIngredientsRepository.deleteAllByRecipeId(storedRecipe.getId());

        //todo batch save
        storedRecipe.setRecipeIngredients(recipe.getRecipeIngredients());
    }


    @Override
    @Transactional
    public void delete(int id) {
        log.debug("Deleting Recipe with id={}", id);
        recipesRepository.deleteById(id);
    }

    public Recipe enrich(Recipe recipe) {
        //String image = imageService.save(bucketName, imagePath, recipe.getImageLink());
        recipe.getRecipeIngredients().stream().forEach(r -> r.setRecipe(recipe));
        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setImagePath("default");

        return recipe;
    }
}
