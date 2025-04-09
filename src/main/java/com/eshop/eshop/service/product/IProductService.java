package com.eshop.eshop.service.product;

import com.eshop.eshop.model.Product;
import com.eshop.eshop.requests.AddProductRequest;
import com.eshop.eshop.requests.UpdateProductRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest product, Long productId);
    List<Product> getProductsByCategory(String categoryName);
    List<Product> getProductsByBrand(String brandName);
    List<Product> getProductsByCategoryAndBrand(String categoryName, String brandName);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brandName, String name);
    Long countProductsByBrandAndName(String brandName, String name);
}
