package com.nicolasperussi.autodetailing_api.domain.dtos.vehicle;

public record CreateVehicleDTO(String licensePlate, String brand, String model, int year, String color, String customerId) {
}
