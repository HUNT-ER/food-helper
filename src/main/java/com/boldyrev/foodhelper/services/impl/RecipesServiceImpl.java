package com.boldyrev.foodhelper.services.impl;

import com.boldyrev.foodhelper.exceptions.EmptyDataException;
import com.boldyrev.foodhelper.exceptions.EntityNotFoundException;
import com.boldyrev.foodhelper.models.Recipe;
import com.boldyrev.foodhelper.repositories.RecipeIngredientsRepository;
import com.boldyrev.foodhelper.repositories.RecipesRepository;
import com.boldyrev.foodhelper.services.ImageS3Service;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@PropertySource("classpath:s3minio.properties")
public class RecipesServiceImpl implements RecipesService {

    private final RecipesRepository recipesRepository;
    private final ImageS3Service imageService;
    private final RecipeIngredientsRepository recipeIngredientsRepository;
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Value("${s3.bucket.name}")
    private String bucketName;
    @Value("${s3.path.recipes}")
    private String imagePath;

    @Autowired
    public RecipesServiceImpl(RecipesRepository recipesRepository,
        ImageS3Service imageService, RecipeIngredientsRepository recipeIngredientsRepository) {
        this.recipesRepository = recipesRepository;
        this.imageService = imageService;
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
    public Recipe getImageLinkByRecipeId(int id) {
        log.debug("Getting image for recipe with id={}", id);
        Recipe recipe = findById(id);

        if (recipe.getImagePath() != null) {
            recipe.setImageLink(imageService.getDownloadLink(bucketName, recipe.getImagePath()));
        }

        log.debug("Image download link for recipe with id={} : {}", id, recipe.getImageLink());
        return recipe;
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
        deleteOldIngredients(storedRecipe);

        //todo batch save
        storedRecipe.setRecipeIngredients(recipe.getRecipeIngredients());
    }

    @Override
    @Transactional
    public void addImage(int id, MultipartFile imageFile) {
        Recipe recipe = findById(id);

        String newImagePath = imageService.save(bucketName, imagePath + id + "/", imageFile);
        if (recipe.getImagePath() != null) {
            imageService.delete(bucketName, recipe.getImagePath());
        }
        recipe.setImagePath(newImagePath);
    }

    @Override
    @Transactional
    public void delete(int id) {
        log.debug("Deleting Recipe with id={}", id);
        recipesRepository.deleteById(id);
        imageService.delete(bucketName, imagePath + id);
    }

    public Recipe enrich(Recipe recipe) {
        recipe.getRecipeIngredients().stream().forEach(r -> r.setRecipe(recipe));
        recipe.setCreatedAt(LocalDateTime.now());

        return recipe;
    }

    public void deleteOldIngredients(Recipe recipe) {
        recipe.getRecipeIngredients().forEach(r -> {
            r.setIngredient(null);
            r.setRecipe(null);
            r.setId(null);
        });

        recipeIngredientsRepository.deleteAllByRecipeId(recipe.getId());
    }
}
