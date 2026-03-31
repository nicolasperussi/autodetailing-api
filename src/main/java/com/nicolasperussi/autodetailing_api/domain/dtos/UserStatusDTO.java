package com.nicolasperussi.autodetailing_api.domain.dtos;

import jakarta.validation.constraints.NotNull;

public record UserStatusDTO(@NotNull boolean active) {
}
