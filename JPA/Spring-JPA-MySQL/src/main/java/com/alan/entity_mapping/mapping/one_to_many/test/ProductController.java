package com.alan.entity_mapping.mapping.one_to_many.test;

import com.alan.entity_mapping.mapping.one_to_many.Category;
import com.alan.entity_mapping.mapping.one_to_many.Product;
import com.alan.utils.JsonUtils;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/one-to-many")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

    private final CategoryRepository categoryRepository;
    private final Faker faker;
    private final JsonUtils jsonUtils;

    @GetMapping("/get")
    public String get() {
        List<Category> all = categoryRepository.findAllWithProducts();
        List<ProductDto> productDtoList = all.stream().map(ProductDto::new).toList();
        return jsonUtils.convertToJson(productDtoList);
    }


    @GetMapping("/create")
    public String create(@RequestParam String categoryName, @RequestParam List<String> productNameList) {
        Category category = new Category();
        category.setName(categoryName);
        List<Product> productList = productNameList.stream().map(pd -> new Product(pd, category)).toList();
        category.setProducts(productList);

        Category save = categoryRepository.save(category);
        return jsonUtils.convertToJson(new ProductDto(save));
    }
}
