package com.alan.entity_mapping.mapping.one_to_many.test;

import com.alan.entity_mapping.mapping.one_to_many.Category;
import com.alan.entity_mapping.mapping.one_to_many.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    private String category;

    @JsonProperty("product_name_list")
    private List<String> productNameList;

    public ProductDto(Category category) {
        this.category = category.getName();

        if(Objects.nonNull(category.getProducts())) {
            this.productNameList = category.getProducts().stream().map(Product::getName).toList();
        }

    }
}
