package com.ecom.zestcart.repository;

import com.ecom.zestcart.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategoryId(String categoryId);

    Product findFirstByOrderByProductFileIdDesc();
}
