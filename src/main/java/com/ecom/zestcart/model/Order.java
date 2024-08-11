package com.ecom.zestcart.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String userId;
    private List<OrderItem> items;
    private double total;
    private String address;
    private String status;

    // Getters and Setters

    public static class OrderItem {
        private String productId;
        private int quantity;

        // Getters and Setters
    }
}
