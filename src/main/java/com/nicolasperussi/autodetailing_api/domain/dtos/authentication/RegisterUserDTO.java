package com.nicolasperussi.autodetailing_api.domain.dtos.authentication;

import com.nicolasperussi.autodetailing_api.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterUserDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank String password,
        @NotNull UserRole role
) {}
