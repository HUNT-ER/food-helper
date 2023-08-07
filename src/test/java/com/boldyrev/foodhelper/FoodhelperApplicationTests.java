//package com.boldyrev.foodhelper;
//
//import com.boldyrev.foodhelper.dto.IngredientCategoryDTO;
//import com.boldyrev.foodhelper.models.Ingredient;
//import com.boldyrev.foodhelper.models.IngredientCategory;
//import com.boldyrev.foodhelper.models.Recipe;
//import com.boldyrev.foodhelper.repositories.IngredientCategoriesRepository;
//import com.boldyrev.foodhelper.repositories.IngredientsRepository;
//import com.boldyrev.foodhelper.repositories.RecipeCategoriesRepository;
//import com.boldyrev.foodhelper.repositories.RecipesRepository;
//import com.boldyrev.foodhelper.repositories.UsersRepository;
//import com.boldyrev.foodhelper.services.IngredientCategoriesService;
//import com.boldyrev.foodhelper.services.RecipesService;
//import com.boldyrev.foodhelper.util.mappers.IngredientCategoryMapper;
//import com.boldyrev.foodhelper.util.mappers.RecipeIngredientMapper;
//import jakarta.persistence.EntityManager;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
//
//@SpringBootTest
//class FoodhelperApplicationTests {
//
//    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
//    @Autowired
//    private EntityManager entityManager;
//    @Autowired
//    private IngredientCategoriesRepository ingredientCategoriesRepository;
//    @Autowired
//    private IngredientsRepository ingredientsRepository;
//    @Autowired
//    private RecipeCategoriesRepository recipeCategoriesRepository;
//    @Autowired
//    private RecipesRepository recipesRepository;
//    @Autowired
//    private UsersRepository usersRepository;
//    @Autowired
//    private RecipesService recipesService;
//    @Autowired
//    private IngredientCategoryMapper ingredientCategoryMapper;
//    @Autowired
//    private IngredientCategoriesService ingredientCategoriesService;
//
//    @Autowired
//    private RecipeIngredientMapper recipeIngredientMapper;
//
////    @Autowired
////    private IngredientRepository ir;
////
////    @Autowired
////    private RecipeRepository rr;
//
//    @Tests
//    @Rollback(false)
//    @Transactional
//    void contextLoads() throws Exception {
//
//        Ingredient beef = ingredientsRepository.findByNameIgnoreCase("Beef fillet").get();
//        Ingredient chicken = ingredientsRepository.findByNameIgnoreCase("Chicken breast").get();
//
//        List<Ingredient> ingredients = List.of(beef, chicken);
//
//        Recipe recipe = new Recipe();
//        recipe.setCreator(usersRepository.findByUsernameIgnoreCase("hunter").get());
//        recipe.setCategory(recipeCategoriesRepository.findByNameIgnoreCase("Cream-soups").get());
//        recipe.setCreatedAt(LocalDateTime.now());
//        recipe.setTitle("title");
//        recipe.setDescription("description");
//        recipe.setImagePath("image/path");
//        recipe.setImageLink("Link");
//
//        recipe.getRecipeIngredients().stream().map(r -> recipeIngredientMapper.recipeIngredientToDTO(r)).collect(
//            Collectors.toSet());
//
////        recipe.setIngredients(new ArrayList<>(ingredients));
//        //ingredients.stream().forEach(i -> i.getRecipes().add(recipe));
//
//        recipesRepository.save(recipe);
//
//    }
//
//    @Tests
//    @Transactional
//    void context() {
//        Ingredient beef = ingredientsRepository.findByNameIgnoreCase("Beef fillet").get();
//        Ingredient chicken = ingredientsRepository.findByNameIgnoreCase("Chicken breast").get();
//
//        List<Ingredient> ingredients = List.of(beef, chicken);
//
//        List<Recipe> recipes = recipesService.findAllByIngredients(ingredients);
//
//        recipes.forEach(x -> System.out.println(x.getTitle()));
//
//    }
//
//    @Tests
//    void context2() {
//
//        IngredientCategory category = ingredientCategoriesRepository.findById(5).get();
//
//        IngredientCategoryDTO categoryDTO = ingredientCategoryMapper.categoryToCategoryDTO(
//            category);
//
//        System.out.println("Name: " + categoryDTO.getName());
//        System.out.println("Parent: " + categoryDTO.getParentCategory());
//
//        IngredientCategory category1 = ingredientCategoryMapper.categoryDTOtoCategory(categoryDTO);
//
//        System.out.println("Name: " + category.getName());
//        System.out.println("Parent: " + category.getParentCategory().getName());
//
//        //todo определиться что нужно в DTO у категорий
//    }
//
//    @Tests
//    void context3() throws JsonProcessingException {
//        List<IngredientCategory> categories = ingredientCategoriesService.findAll();
//
//        System.out.println(categories.get(4).getParentCategory().getName());
//
//        List<IngredientCategoryDTO> categoriesDTO = categories.stream().map(ingredientCategoryMapper::categoryToCategoryDTO).toList();
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        System.out.println(mapper.writeValueAsString(categoriesDTO));
//
//        //todo get all custom recursive
//    }
//
//
//
//
////    @Tests
////    void context4() {
////        IngredientT ingredientT = ir.findById(1).get();
////        IngredientT ingredientT1 = ir.findById(2).get();
////        System.out.println(ingredientT.getName());
////        RecipeT recipeT = new RecipeT();
////        recipeT.setTitle("test recipe 4");
////        recipeT.setDescription("my fourth m2m recipeT");
////
////        RecipeIngredient recipeIngredient = new RecipeIngredient();
////        recipeIngredient.setWeight(1500);
////        recipeIngredient.setIngredient(ingredientT);
////        recipeIngredient.setRecipe(recipeT);
////
////        RecipeIngredient recipeIngredient2 = new RecipeIngredient();
////        recipeIngredient2.setWeight(100);
////        recipeIngredient2.setIngredient(ingredientT1);
////        recipeIngredient2.setRecipe(recipeT);
////
////        recipeT.getList().add(recipeIngredient);
////        recipeT.getList().add(recipeIngredient2);
////
////        rr.save(recipeT);
////    }
//
////    @Tests
////    void context5() {
////        RecipeT recipeT = rr.findById(4).get();
////
////        Set<Integer> s = new HashSet<>();
////
////        recipeT.getList().stream().filter(x -> x.getIngredient().getName() == "Beef").findFirst().get();
////    }
//
//}
