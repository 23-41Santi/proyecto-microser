package com.miempresa.orderservice.service;

import com.miempresa.orderservice.dto.OrderDto;
import com.miempresa.orderservice.exception.OrderNotFoundException;
import com.miempresa.orderservice.mapper.OrderMapper;
import com.miempresa.orderservice.model.Order;
import com.miempresa.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return mapper.toDto(order);
    }

    @Transactional
    public OrderDto create(OrderDto dto) {
        Order order = mapper.toEntity(dto);
        // subtotal, total y callbacks se calculan en la entidad
        Order saved = repository.save(order);
        return mapper.toDto(saved);
    }

    @Transactional
    public OrderDto update(Long id, OrderDto dto) {
        Order existing = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        // MapStruct actualiza entidad existente
        Order updated = mapper.toEntity(dto);
        Order saved = repository.save(updated);
        return mapper.toDto(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new OrderNotFoundException(id);
        repository.deleteById(id);
    }
}
