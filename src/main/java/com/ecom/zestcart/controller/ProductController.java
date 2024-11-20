package com.ecom.zestcart.controller;

import com.ecom.zestcart.model.Product;
import com.ecom.zestcart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Get all products
    @GetMapping("/open/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Get product by ID
    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    // Get products by category
    @GetMapping("/products/category")
    public List<Product> getProductsByCategory(@RequestParam String categoryId) {
        return productService.getProductsByCategory(categoryId);
    }

    // Add a new product
    @PostMapping("/product/add")
    public ResponseEntity<Product> createProduct(
            @ModelAttribute Product product,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        Product savedProduct = productService.saveProduct(product, image);
        return ResponseEntity.ok(savedProduct);
    }

    // Update an existing product
    @PutMapping("/product/update/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable String id,
            @ModelAttribute Product product,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        product.setId(id);
        Product updatedProduct = productService.saveProduct(product, image);
        return ResponseEntity.ok(updatedProduct);
    }

    // Delete a product by ID
    @DeleteMapping("/product/delete/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }
}
