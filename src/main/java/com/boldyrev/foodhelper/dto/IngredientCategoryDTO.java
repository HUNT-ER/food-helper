package com.boldyrev.foodhelper.dto;

import com.boldyrev.foodhelper.dto.transfer.NewCategory;
import com.boldyrev.foodhelper.dto.transfer.NewIngredient;
import com.boldyrev.foodhelper.util.validators.custom_annotations.NotNullAny;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NotNullAny(groups = {})
@JsonInclude(Include.NON_NULL)
public class IngredientCategoryDTO {

    @Null(groups = {NewCategory.class})
    @NotNull(groups = {NewIngredient.class})
    @JsonProperty("id")
    private Integer id;

    @NotBlank(groups = {NewCategory.class})
    @Size(min = 1, max = 100, groups = {NewCategory.class})
    @JsonProperty("name")
    private String name;

    @Valid
    @JsonProperty("parent_category")
    private IngredientParentCategoryDTO parentCategory;

}

