package com.nicolasperussi.autodetailing_api.domain.dtos.vehicle;

public record UpdateVehicleDTO(String licensePlate, String brand, String model, Integer year, String color, String customerId) {
}
