package com.ecom.zestcart.controller;

import com.ecom.zestcart.model.Cart;
import com.ecom.zestcart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    // Return product details with quantity inside productDetails
    @GetMapping("/{userId}")
    public List<Map<String, Object>> getProductDetailsByUserId(@PathVariable String userId) {
        return cartService.getProductDetailsByUserId(userId);
    }

    // Existing saveCart method (unchanged)
    @PostMapping
    public Cart saveCart(@RequestBody Cart cart) {
        return cartService.saveCart(cart);
    }
}
