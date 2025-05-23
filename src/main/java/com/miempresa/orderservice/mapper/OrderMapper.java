package com.miempresa.orderservice.mapper;

import com.miempresa.orderservice.dto.OrderDto;
import com.miempresa.orderservice.dto.OrderItemDto;
import com.miempresa.orderservice.model.Order;
import com.miempresa.orderservice.model.OrderItem;
import com.miempresa.orderservice.repository.OrderRepository;
import org.mapstruct.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    /* ---------- Order ↔ OrderDto ---------- */

    // Entidad → DTO
    @Mapping(target = "items", source = "items")
    OrderDto toDto(Order entity);

    // DTO → Entidad (crear)
    @InheritInverseConfiguration(name = "toDto")
    @Mapping(target = "orderDate",  ignore = true)  // se setea en @PrePersist
    @Mapping(target = "lastUpdated", ignore = true)
    Order toEntity(OrderDto dto);

    /* ---------- OrderItem ↔ OrderItemDto ---------- */

    // Entidad → DTO
    @Mapping(target = "lineTotal",
            expression = "java(item.getUnitPrice()"
                    + ".multiply(java.math.BigDecimal.valueOf(item.getQuantity()))"
                    + ".subtract(item.getDiscount() == null ? java.math.BigDecimal.ZERO : item.getDiscount()))")
    OrderItemDto toOrderItemDto(OrderItem item);

    // DTO → Entidad
    @InheritInverseConfiguration(name = "toOrderItemDto")
    @Mapping(target = "id",        ignore = true)   // JPA lo genera
    @Mapping(target = "lineTotal", ignore = true)   // se recalcula
    @Mapping(target = "order",     ignore = true)   // se enlaza abajo
    OrderItem toOrderItem(OrderItemDto dto);

    /* ---------- Colecciones ---------- */

    Set<OrderItemDto> toDtoItems(Set<OrderItem> items);
    Set<OrderItem>   toEntityItems(Set<OrderItemDto> items);

    /* ---------- Enlace bidireccional ---------- */

    @AfterMapping
    default void linkItems(@MappingTarget Order order) {
        if (order.getItems() != null) {
            order.getItems().forEach(i -> i.setOrder(order));
        }
    }
}

// Servicio de ejemplo (fuera de la interfaz Mapper)
@Service
class OrderService {
    private final OrderMapper mapper;
    private final OrderRepository repository;

    public OrderService(OrderMapper mapper, OrderRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Transactional
    public OrderDto create(OrderDto dto) {
        Order order = mapper.toEntity(dto);      // el hook ya vincula los ítems
        Order saved = repository.save(order);    // cascada inserta los OrderItem
        return mapper.toDto(saved);
    }
}
