# Food Helper API
This API allows you to create and manage recipes. Supports search recipes by its ingredients and saving photos in S3 storage.
Also supports CRUD operations for all entites (recipes, ingredients, categories)

# Build with
- Spring Boot
- Spring Data JPA
- Spring Web MVC
- Spring Security
- JWT
- Hibernate
- PostgreSQL
- AOP
- Lombok

## Set up 
- [*clone*](https://github.com/HUNT-ER/food-helper.git) the project
- change [application.properties](src/main/resources/application.properties) file based on your database configurations
- change [s3minio.properties](src/main/resources/s3minio.properties.origin) file for S3 based storage properties
- run the project using [FoodhelperApplication.java](src/main/java/com/boldyrev/foodhelper/FoodhelperApplication.java) 

# API Reference 

## Ingredient categories operations

**GET** `/api/ingredient-categories`
  returns list of categories

**GET** `/api/ingredient-categories/{id}`
  returns category by ID

**GET** `/api/ingredient-categories/{id}/ingredients`
  returns list ingredients belongs to category

**POST** `/api/ingredient-categories`
  create new category by request body:
```agsl
  request body:
{
    "name": "Ingredient Category",
    "parent_category": {
        "id": 4    
    }
}
```

**PUT** `/api/ingredient-categories/{id} `
updates category by id

**DELETE** `/api/ingredient-categories/{id} `
deletes category by id


## Recipe category operations

**GET** `/api/recipe-categories`
  returns list of categories

**GET** `/api/ recipe-categories/{id}`
  returns category by ID

**GET** `/api/recipe-categories/{id}/recipes`
  returns list recipes belongs to category

**POST** `/api/recipe-categories`
  create new category by request body:
```agsl
  request body:
    {
        "name": "Recipe Category",
        "parent_category": {
            "id": 5    
        }
    }
```

**PUT** `/api/recipe-categories/{id}`
updates category by id

**DELETE** `/api/recipe-categories/{id}`
deletes category by id


## Ingredients operations

**GET** `/api/ingredients`
  returns list of ingredients

**GET** `/api/ingredients/{id}`
  returns ingredient by Id

**GET** `/api/ingredients/search?name&page&size`
Search ingredient by name, with pagination by page and size

**POST** `/api/ingredients `
  create new ingredient by request body:
```agsl
  request body:
    {
        "name": "Ingredient",
        "ingredient_category": {
            "id": 3
        }
    }
```

**PUT** `/api/ingredients/{id} `
updates ingredient by id

**DELETE** `/api/ingredients/{id} `
deletes ingredient by id


## Users operations
**GET** `/api/users`
  returns list of users

**GET** `/api/users/{id}/recipes?page&size`
  returns user recipes by Id

### Create user
Creating user now only available through the database

## Recipe operations
**GET** `/api/recipes`
  returns list of recipes

**GET** `/api/recipes/{id}`
  returns recipe by Id

**GET** `/api/recipes/search?ingredients&page&size`
Search recipe by ingredients, with pagination by page and size

**GET** `/api/recipes/{id}/image`
  returns recipe image by Id

**PUT** `/api/recipes/{id}/image`
  saves recipe image

**POST** `/api/recipes `
  create new recipe by request body:
```agsl
  request body:
    {
    "title": "Recipe",
    "description": "Description",
    "category": {
      "id": 6
    },
    "creator": {
      "id": 1
    },
    "ingredients": [
      {
        "ingredient": {
          "id": 2
        }
      },
      {
        "ingredient": {
          "id": 1
        }
      }
      ]
    }
```

**PUT** `/api/recipes/{id} `
updates recipe by id

**DELETE** `/api/recipes/{id} `
deletes recipe by Id


# Entity diagram
![food-helper](https://github.com/HUNT-ER/food-helper/assets/38404914/4d26bebb-71fc-4e49-b3a3-9533d66142a4)

# What I learned
- Improved skills in Spring Boot, Entity relationships, API Architecture
