package com.ecom.zestcart.controller;

import com.ecom.zestcart.model.Order;
import com.ecom.zestcart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }
}
