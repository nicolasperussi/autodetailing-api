package com.nicolasperussi.autodetailing_api.domain.dtos.customer;

import com.nicolasperussi.autodetailing_api.domain.dtos.vehicle.CreateVehicleDTO;
import jakarta.validation.constraints.Email;

public record CreateCustomerDTO(
        String name,
        String phone,
        @Email String email,
        String document,
        CreateVehicleDTO vehicle
    ) {
}
