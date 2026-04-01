package com.nicolasperussi.autodetailing_api.domain.dtos.customer;

import com.nicolasperussi.autodetailing_api.domain.dtos.vehicle.CreateVehicleDTO;
import jakarta.validation.constraints.Email;

public record UpdateCustomerDTO(
        String name,
        String phone,
        @Email String email,
        String document
    ) {
}
