package com.miempresa.orderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Order {
    @Id
    private Long id;
    private String customerName;
    private String status;
}
