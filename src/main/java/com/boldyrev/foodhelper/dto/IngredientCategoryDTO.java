package com.boldyrev.foodhelper.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngredientCategoryDTO {

    @NotNull(message = "Ingredient category id can't be null")
    @Min(message = "Ingredient category id should be positive", value = 1)
    private Integer id;

    @NotNull(message = "Ingredient category name can't be null")
    @Size(message = "Ingredient category name length must be between 1 and 100 chars", min = 1, max = 100)
    @JsonProperty("name")
    private String name;

    @Size(message = "Ingredient category name length must be between 1 and 100 chars", min = 1, max = 100)
    @JsonProperty("parent_category")
    private String parentCategory;

}

