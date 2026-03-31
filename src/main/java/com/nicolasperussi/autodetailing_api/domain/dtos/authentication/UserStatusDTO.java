package com.nicolasperussi.autodetailing_api.domain.dtos.authentication;

import jakarta.validation.constraints.NotNull;

public record UserStatusDTO(@NotNull boolean active) {
}
