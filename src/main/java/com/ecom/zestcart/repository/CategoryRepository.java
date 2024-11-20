package com.ecom.zestcart.repository;
import com.ecom.zestcart.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {

    Category findFirstByOrderByCategoryFileIdDesc();
}

