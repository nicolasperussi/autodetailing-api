package com.nicolasperussi.autodetailing_api.domain.dtos;

import com.nicolasperussi.autodetailing_api.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserDTO(
        String name,
        @Email String email,
        UserRole role
) {}
