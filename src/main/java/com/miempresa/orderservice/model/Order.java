package com.miempresa.orderservice.model;

import com.miempresa.orderservice.data.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders",
        indexes = {@Index(name = "idx_orders_customer", columnList = "customer_id")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @NotNull
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private OrderStatus status;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    @Column(nullable = false, precision = 14, scale = 2)
    @Builder.Default
    private BigDecimal subtotal = BigDecimal.ZERO;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    @Column(nullable = false, precision = 14, scale = 2)
    @Builder.Default
    private BigDecimal tax = BigDecimal.ZERO;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    @Column(nullable = false, precision = 14, scale = 2)
    @Builder.Default
    private BigDecimal shippingCost = BigDecimal.ZERO;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    @Column(nullable = false, precision = 14, scale = 2)
    @Builder.Default
    private BigDecimal total = BigDecimal.ZERO;

    @Size(max = 1000)
    @Column(length = 1000)
    private String shippingAddress;

    @Size(max = 1000)
    @Column(length = 1000)
    private String billingAddress;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<OrderItem> items;

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.orderDate = now;
        this.lastUpdated = now;
        calculateTotal();
    }

    @PreUpdate
    public void onUpdate() {
        this.lastUpdated = LocalDateTime.now();
        calculateTotal();
    }

    public void calculateTotal() {
        this.total = this.subtotal.add(this.tax).add(this.shippingCost);
    }
}