package com.boldyrev.foodhelper.services.impl;

import com.boldyrev.foodhelper.exceptions.RecipeNotFoundException;
import com.boldyrev.foodhelper.models.Ingredient;
import com.boldyrev.foodhelper.models.Recipe;
import com.boldyrev.foodhelper.repositories.ImageS3Repository;
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

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${s3.bucket.name}")
    private String bucketName;

    @Value("${s3.path.recipes}")
    private String imagePath;

    @Autowired
    public RecipesServiceImpl(RecipesRepository recipesRepository,
        ImageS3Service imageService, IngredientsService ingredientsService) {
        this.recipesRepository = recipesRepository;
        this.imageService = imageService;
        this.ingredientsService = ingredientsService;
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe findById(int id) {
        log.debug("Getting Recipe with id={}", id);
        return recipesRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException(
            String.format("Recipe with id=%d not found.", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe findByTitle(String title) {
        log.debug("Getting Recipe with title={}", title);
        return recipesRepository.findByTitleIgnoreCase(title).orElseThrow(
            () -> new RecipeNotFoundException(
                String.format("Recipe with title=%s not found.", title)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recipe> findAll() {
        log.debug("Getting all Recipes");
        return recipesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Recipe> findAllByIngredients(List<Ingredient> ingredients) {
        log.debug("Finding Recipes by Ingredients");
        return recipesRepository.findAllByIngredients(
            ingredients.stream().map(x -> x.getId()).distinct().toList());
        //todo засунуть в Query ингредиент
    }

    @Override
    @Transactional
    public Recipe save(Recipe recipe) {
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

        storedRecipe.setIngredients(recipe.getIngredients());
        storedRecipe.setImageLink(recipe.getImageLink());
        storedRecipe.setDescription(recipe.getDescription());
        storedRecipe.setTitle(recipe.getTitle());
        storedRecipe.setCategory(recipe.getCategory());
        storedRecipe.setCreator(recipe.getCreator());
        storedRecipe.setImagePath(recipe.getImagePath());
    }

    @Override
    @Transactional
    public void delete(int id) {
        log.debug("Deleting Recipe with id={}", id);
        recipesRepository.deleteById(id);
    }

    public Recipe enrich(Recipe recipe) {
        String image = imageService.save(bucketName, imagePath, recipe.getImageLink());

        recipe.setCreatedAt(LocalDateTime.now());
        recipe.setImagePath(image);

        return recipe;
    }
}