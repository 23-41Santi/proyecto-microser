package com.miempresa.orderservice.service;

import com.miempresa.orderservice.model.Order;
import com.miempresa.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order save(Order order) {
        return repository.save(order);
    }
}
