package com.miempresa.orderservice.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long id) {
        super("Orden no encontrada: " + id);
    }
}
