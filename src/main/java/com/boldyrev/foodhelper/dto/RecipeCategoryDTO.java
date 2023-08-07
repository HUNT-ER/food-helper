package com.boldyrev.foodhelper.dto;

import com.boldyrev.foodhelper.dto.transfer.Exist;
import com.boldyrev.foodhelper.dto.transfer.New;
import com.boldyrev.foodhelper.dto.transfer.NewCategory;
import com.boldyrev.foodhelper.dto.transfer.NewRecipe;
import com.boldyrev.foodhelper.util.validators.custom_annotations.NotNullAny;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NotNullAny(groups = {})
@JsonInclude(Include.NON_NULL)
public class RecipeCategoryDTO {

    @Null(groups = {NewCategory.class})
    @NotNull(groups = {NewRecipe.class})
    @JsonProperty("id")
    private Integer id;

    @NotNull(groups = {NewCategory.class})
    @Size(min = 1, max = 100, groups = {NewCategory.class})
    @JsonProperty("name")
    private String name;

    @Valid
    @JsonProperty("parent_category")
    private RecipeParentCategoryDTO parentCategory;
}
