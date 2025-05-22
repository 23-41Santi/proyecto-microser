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

    // Mapeo de Order → OrderDto
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "tax", source = "tax")
    @Mapping(target = "shippingCost", source = "shippingCost")
    @Mapping(target = "shippingAddress", source = "shippingAddress")
    @Mapping(target = "billingAddress", source = "billingAddress")
    OrderDto toDto(Order entity);

    // Mapeo de OrderDto → Order
    @InheritInverseConfiguration(name = "toDto")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    Order toEntity(OrderDto dto);

    @Mapping(target = "id",        source = "id")
    @Mapping(target = "price",     source = "unitPrice")
    @Mapping(target = "discount",  source = "discount")
    @Mapping(target = "lineTotal",
            expression = "java(item.getUnitPrice() " +
                    ".multiply(java.math.BigDecimal.valueOf(item.getQuantity()))" +
                    ".subtract(item.getDiscount() == null ? java.math.BigDecimal.ZERO : item.getDiscount()))")
    OrderItemDto toOrderItemDto(OrderItem item);

    /* mapeo inverso, usualmente ignoramos lineTotal porque se recalcula */
    @InheritInverseConfiguration(name = "toOrderItemDto")
    @Mapping(target = "lineTotal", ignore = true)
    @Mapping(target = "order",     ignore = true)
    OrderItem toOrderItem(OrderItemDto dto);

    // Mapeo de colecciones
    Set<OrderItemDto> toDtoItems(Set<OrderItem> items);
    Set<OrderItem> toEntityItems(Set<OrderItemDto> items);
}