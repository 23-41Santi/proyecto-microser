package com.miempresa.orderservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.miempresa.orderservice.model.Order;
import com.miempresa.orderservice.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public List<Order> getOrders() {
        return service.findAll();
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return service.save(order);
    }
}
