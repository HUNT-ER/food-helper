package com.boldyrev.foodhelper.services;

import com.boldyrev.foodhelper.models.Recipe;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface RecipesService {

    Recipe findById(int id);
    Recipe getImageLinkByRecipeId(int id);
    Page<Recipe> findAll(int page, int size);
    Page<Recipe> findAllByCategoryId(int id, int page, int size);
    Page<Recipe> findAllByIngredientsId(List<Integer> ingredients, int page, int size);
    Page<Recipe> findAllByUserId(int id, int page, int size);
    Recipe save(Recipe recipe);
    void update(int id, Recipe recipe);
    void addImage(int id, MultipartFile imageFile);
    void delete(int id);
}
