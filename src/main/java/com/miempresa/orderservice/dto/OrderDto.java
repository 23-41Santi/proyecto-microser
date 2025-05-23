package com.miempresa.orderservice.dto;

import com.miempresa.orderservice.data.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long orderId;

    @NotNull
    private OrderStatus status;

    @NotNull
    private Long customerId;

    @NotNull
    private Set<@Valid OrderItemDto> items;

    @PositiveOrZero
    private BigDecimal tax;

    @PositiveOrZero
    private BigDecimal shippingCost;
    private BigDecimal discount;

    private String shippingAddress;
    private String billingAddress;

}
