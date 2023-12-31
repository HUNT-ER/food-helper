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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        return recipesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
            String.format("Recipe with id=%d not found.", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe getImageLinkByRecipeId(int id) {
        Recipe recipe = findById(id);

        if (recipe.getImagePath() != null) {
            recipe.setImageLink(imageService.getDownloadLink(bucketName, recipe.getImagePath()));
        }

        return recipe;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recipe> findAll(int page, int size) {
        Page<Recipe> recipes = recipesRepository.findAll(
            PageRequest.of(page, size, Sort.by("title")));

        if (recipes.isEmpty()) {
            throw new EmptyDataException("Recipes not found");
        }

        return recipes;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recipe> findAllByCategoryId(int id, int page, int size) {
        Page<Recipe> recipes = recipesRepository.findAllByCategoryId(id,
            PageRequest.of(page, size, Sort.by("title")));

        if (recipes.isEmpty()) {
            throw new EmptyDataException("Recipes not found");
        }

        return recipes;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recipe> findAllByIngredientsId(List<Integer> ingredients, int page, int size) {
        Page<Recipe> recipes = recipesRepository.findAllByIngredientsId(ingredients,
            ingredients.size(), PageRequest.of(page, size, Sort.by("title")));

        if (recipes.isEmpty()) {
            log.debug("Recipes not found");
            throw new EmptyDataException("Recipes not found");
        }

        return recipes;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recipe> findAllByUserId(int id, int page, int size) {
        Page<Recipe> recipes = recipesRepository.findAllByUserId(id,
            PageRequest.of(page, size, Sort.by("title")));

        if (recipes.isEmpty()) {
            log.debug("Recipes not found");
            throw new EmptyDataException("Recipes not found");
        }

        return recipes;
    }

    @Override
    @Transactional
    public Recipe save(Recipe recipe) {
        enrich(recipe);
        return recipesRepository.save(recipe);
    }

    @Override
    @Transactional
    public void update(int id, Recipe recipe) {
        Recipe storedRecipe = this.findById(id);

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
        recipesRepository.deleteById(id);
        imageService.delete(bucketName, imagePath + id);
    }

    private Recipe enrich(Recipe recipe) {
        recipe.getRecipeIngredients().stream().forEach(r -> r.setRecipe(recipe));
        recipe.setCreatedAt(LocalDateTime.now());

        return recipe;
    }

    private void deleteOldIngredients(Recipe recipe) {
        recipe.getRecipeIngredients().forEach(r -> {
            r.setIngredient(null);
            r.setRecipe(null);
            r.setId(null);
        });

        recipeIngredientsRepository.deleteAllByRecipeId(recipe.getId());
    }
}
