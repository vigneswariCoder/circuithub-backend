package com.ecom.zestcart.repository;

import com.ecom.zestcart.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
}
