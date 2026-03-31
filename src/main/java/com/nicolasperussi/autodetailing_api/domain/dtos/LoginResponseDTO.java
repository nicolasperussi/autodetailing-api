package com.nicolasperussi.autodetailing_api.domain.dtos;

import com.nicolasperussi.autodetailing_api.domain.User;

public record LoginResponseDTO(String token, User user) {
}
