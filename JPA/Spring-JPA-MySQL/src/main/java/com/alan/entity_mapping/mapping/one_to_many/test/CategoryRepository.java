package com.alan.entity_mapping.mapping.one_to_many.test;

import com.alan.entity_mapping.mapping.one_to_many.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.products")
    List<Category> findAllWithProducts();
}
