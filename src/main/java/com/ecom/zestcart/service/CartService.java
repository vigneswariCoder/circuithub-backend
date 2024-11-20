package com.ecom.zestcart.service;

import com.ecom.zestcart.model.Cart;
import com.ecom.zestcart.model.CartItem;
import com.ecom.zestcart.model.Product;
import com.ecom.zestcart.repository.CartRepository;
import com.ecom.zestcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    // Fetch product details with quantity embedded in productDetails
    public List<Map<String, Object>> getProductDetailsByUserId(String userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);

        // If no carts found, return an empty list
        if (carts.isEmpty()) {
            return new ArrayList<>();
        }

        // Fetch items from the first cart (assuming one cart per user)
        List<CartItem> items = carts.get(0).getItems();

        if (items == null || items.isEmpty()) {
            return new ArrayList<>(); // No items in cart
        }

        // Prepare the result list
        List<Map<String, Object>> response = new ArrayList<>();

        // Retrieve product details and add quantity to productDetails
        for (CartItem item : items) {
            Optional<Product> productOptional = productRepository.findById(item.getProductId());

            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                // Create a Map to hold product details and quantity
                Map<String, Object> productWithQuantity = new HashMap<>();

                // Copy product details into the map
                productWithQuantity.put("id", product.getId());
                productWithQuantity.put("name", product.getName());
                productWithQuantity.put("description", product.getDescription());
                productWithQuantity.put("price", product.getPrice());
                productWithQuantity.put("stock", product.getStock());
                productWithQuantity.put("imageUrl", product.getImageUrl());
                productWithQuantity.put("category", product.getCategoryId());

                // Add quantity to the product details
                productWithQuantity.put("quantity", item.getQuantity());

                // Add the map to the response list
                response.add(productWithQuantity);
            }
        }

        return response;
    }

    public Cart saveCart(Cart cart) {
        try {
            // Fetch existing cart for the user
            List<Cart> existingCarts = cartRepository.findByUserId(cart.getUserId());

            if (existingCarts.isEmpty()) {
                // No existing cart, save new cart
                return cartRepository.save(cart);
            } else {
                // Update the first existing cart (or handle multiple carts if needed)
                Cart existingCart = existingCarts.get(0);
                List<CartItem> existingItems = existingCart.getItems();

                if (existingItems == null) {
                    existingItems = new ArrayList<>(); // Ensure existingItems is initialized
                }

                CartItem newItem = cart.getItems().get(0);
                boolean itemExists = false;

                for (CartItem existingItem : existingItems) {
                    if (existingItem.getProductId().equals(newItem.getProductId())) {
                        // If the product already exists, update the quantity
                        int newQuantity = Integer.parseInt(existingItem.getQuantity()) +
                                Integer.parseInt(newItem.getQuantity());
                        existingItem.setQuantity(String.valueOf(newQuantity));
                        itemExists = true;
                        break;
                    }
                }

                if (!itemExists) {
                    // If the product does not exist, add it to the cart
                    existingItems.add(newItem);
                }

                existingCart.setItems(existingItems);
                return cartRepository.save(existingCart);
            }
        } catch (Exception e) {
            // Log and handle exception
            e.printStackTrace();
            throw new RuntimeException("Error saving cart", e);
        }
    }

}
