package com.boldyrev.foodhelper.dto;

import com.boldyrev.foodhelper.dto.transfer.Exist;
import com.boldyrev.foodhelper.dto.transfer.New;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class IngredientCategoryDTO {

    @JsonProperty("id")
    @Null(message = "Id should be null", groups = {New.class, Exist.class})
    private Integer id;

    @NotNull(message = "Ingredient category name can't be null",
        groups = {New.class})
    @Size(message = "Ingredient category name length must be between 1 and 100 chars",
        min = 1,
        max = 100,
        groups = {New.class, Exist.class})
    @JsonProperty("name")
    private String name;

    @Size(message = "Ingredient category name length must be between 1 and 100 chars",
        min = 1,
        max = 100,
        groups = {New.class, Exist.class})
    @JsonProperty("parent_category")
    private String parentCategory;

}

