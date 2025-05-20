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

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "customerId", source = "customerId")
    @Mapping(target = "tax", source = "tax")
    @Mapping(target = "shippingCost", source = "shippingCost")
    @Mapping(target = "shippingAddress", source = "shippingAddress")
    @Mapping(target = "billingAddress", source = "billingAddress")
    OrderDto toDto(Order entity);

    Set<OrderItemDto> toDtoItems(Set<OrderItem> items);

    Set<OrderItem> toEntityItems(Set<OrderItemDto> items);

    @InheritInverseConfiguration(name = "toDto")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    Order toEntity(OrderDto dto);
}
