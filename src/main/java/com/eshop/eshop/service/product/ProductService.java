package com.eshop.eshop.service.product;

import com.eshop.eshop.exceptions.ResourceNotFoundException;
import com.eshop.eshop.model.Category;
import com.eshop.eshop.model.Product;
import com.eshop.eshop.repository.CategoryRepository;
import com.eshop.eshop.repository.ProductRepository;
import com.eshop.eshop.requests.AddProductRequest;
import com.eshop.eshop.requests.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(()-> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Product Not Found!"));
    }

    @Override
    public void deleteProductById(Long id) {
     productRepository.findById(id).ifPresentOrElse(productRepository::delete,
             ()-> {throw new ResourceNotFoundException("Product Not Found!");});
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long productId) {
    return productRepository.findById(productId)
            .map(existingProduct-> updateExistingProduct(existingProduct, request))
            .map(productRepository :: save)
            .orElseThrow(()-> new ResourceNotFoundException("Product Not Found!"));
    }
    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request){
      existingProduct.setName(request.getName());
      existingProduct.setBrand(request.getBrand());
      existingProduct.setPrice(request.getPrice());
      existingProduct.setInventory(request.getInventory());
      existingProduct.setDescription(request.getDescription());

      Category category = categoryRepository.findByName(request.getCategory().getName());
      existingProduct.setCategory(category);
      return existingProduct;
    }
    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        return productRepository.findByCategoryName((categoryName));
    }

    @Override
    public List<Product> getProductsByBrand(String brandName) {
        return productRepository.findByBrand(brandName);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String categoryName, String brandName) {
        return productRepository.findByCategoryNameAndBrand(categoryName,brandName);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brandName, String name) {
        return productRepository.findByBrandAndName(brandName,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brandName, String name) {
        return productRepository.countByBrandAndName(brandName,name);
    }
}
