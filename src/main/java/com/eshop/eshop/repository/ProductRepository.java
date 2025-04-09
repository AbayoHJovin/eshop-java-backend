package com.eshop.eshop.repository;

import com.eshop.eshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
   List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brandName);

    List<Product> findByCategoryNameAndBrand(String categoryName, String brandName);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brandName, String name);

    Long countByBrandAndName(String brand, String name);
}
