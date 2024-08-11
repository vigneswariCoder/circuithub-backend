package com.ecom.zestcart.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "carts")
public class Cart {
    @Id
    private String id;
    private String userId;
    private List<CartItem> items;

    // Getters and Setters

    public static class CartItem {
        private String productId;
        private int quantity;

        // Getters and Setters
    }
}
