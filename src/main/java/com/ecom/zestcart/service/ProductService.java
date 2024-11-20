package com.ecom.zestcart.service;

import com.ecom.zestcart.model.FileMaster;
import com.ecom.zestcart.model.Product;
import com.ecom.zestcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FileService fileService;

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    // Get products by category
    public List<Product> getProductsByCategory(String categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Save or update product
    public Product saveProduct(Product product, MultipartFile image) {
        // Auto-generate the ProductFileId logic
        Product lastProduct = productRepository.findFirstByOrderByProductFileIdDesc();
        String newProductId;

        if (lastProduct != null && lastProduct.getProductFileId() != null) {
            int lastNum = Integer.parseInt(lastProduct.getProductFileId().replace("PROD", ""));
            newProductId = "PROD" + String.format("%03d", lastNum + 1);
        } else {
            newProductId = "PROD001";
        }

        product.setProductFileId(newProductId);

        // Handle image upload and set image URL
        if (image != null && !image.isEmpty()) {
            product.setImageUrl(fileService.fileUpload(List.of(image), newProductId).get(0).getDocumentPath());
        }

        // Save the product
        return productRepository.save(product);
    }

    // Delete a product
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
