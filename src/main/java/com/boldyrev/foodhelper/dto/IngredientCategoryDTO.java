package com.boldyrev.foodhelper.dto;

import com.boldyrev.foodhelper.dto.transfer.Exist;
import com.boldyrev.foodhelper.dto.transfer.New;
import com.boldyrev.foodhelper.util.validators.custom_annotations.NotNullAny;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@NotNullAny(groups = {Exist.class})
public class IngredientCategoryDTO {

    @Null(groups = {New.class, Exist.class})
    @JsonProperty("id")
    @JsonInclude(Include.NON_NULL)
    private Integer id;

    @NotNull(groups = {New.class})
    @Size(min = 1, max = 100, groups = {New.class, Exist.class})
    @JsonProperty("name")
    @JsonInclude(Include.NON_NULL)
    private String name;

    @Size(min = 1, max = 100, groups = {New.class, Exist.class})
    @JsonProperty("parent_category")
    private String parentCategory;

}

