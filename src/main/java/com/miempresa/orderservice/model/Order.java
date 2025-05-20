package com.miempresa.orderservice.model;

import com.miempresa.orderservice.data.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders",
        indexes = {@Index(name = "idx_orders_customer", columnList = "customer_id")})
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private OrderStatus status;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal tax;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal shippingCost;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal total;

    @Column(columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(columnDefinition = "TEXT")
    private String billingAddress;

    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    /* Relación con items de la orden */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<OrderItem> items;
}
