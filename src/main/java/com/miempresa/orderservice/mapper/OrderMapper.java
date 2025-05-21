package com.miempresa.orderservice.mapper;

import com.miempresa.orderservice.dto.OrderDto;
import com.miempresa.orderservice.dto.OrderItemDto;
import com.miempresa.orderservice.model.Order;
import com.miempresa.orderservice.model.OrderItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // Mapeo de Order → OrderDto (ya está bien)
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "tax", source = "tax")
    @Mapping(target = "shippingCost", source = "shippingCost")
    @Mapping(target = "shippingAddress", source = "shippingAddress")
    @Mapping(target = "billingAddress", source = "billingAddress")
    OrderDto toDto(Order entity);

    // Mapeo de OrderDto → Order (ya está bien)
    @InheritInverseConfiguration(name = "toDto")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    Order toEntity(OrderDto dto);

    // === ¡AÑADE ESTOS MÉTODOS! ===
    // Mapeo INDIVIDUAL: OrderItem → OrderItemDto
    @Mapping(target = "id", source = "id")  // Puedes omitir esto si los nombres coinciden
    @Mapping(target = "sku", source = "sku")
    @Mapping(target = "price", source = "price")
    // ... otros campos de OrderItemDto
    OrderItemDto toOrderItemDto(OrderItem item);

    // Mapeo INDIVIDUAL: OrderItemDto → OrderItem
    @InheritInverseConfiguration(name = "toOrderItemDto")
    OrderItem toOrderItem(OrderItemDto dto);

    // Mapeo de colecciones (MapStruct usará automáticamente los métodos individuales)
    Set<OrderItemDto> toDtoItems(Set<OrderItem> items);

    Set<OrderItem> toEntityItems(Set<OrderItemDto> items);
}