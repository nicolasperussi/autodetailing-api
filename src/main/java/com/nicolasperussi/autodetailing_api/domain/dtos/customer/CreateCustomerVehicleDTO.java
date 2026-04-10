package com.nicolasperussi.autodetailing_api.domain.dtos.customer;

public record CreateCustomerVehicleDTO(String licensePlate, String brand, String model, int year, String color) {
}
