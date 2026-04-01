package com.nicolasperussi.autodetailing_api.domain.dtos.vehicle;

import java.time.Instant;

public record VehicleByCustomerResponseDTO(String id, String licensePlate, String brand, String model, int year, String color,
                                           Instant createdAt, Instant updatedAt) {
}
