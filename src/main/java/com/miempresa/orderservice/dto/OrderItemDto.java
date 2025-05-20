package com.miempresa.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    @NotNull
    private Long productId;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal discount;
}
