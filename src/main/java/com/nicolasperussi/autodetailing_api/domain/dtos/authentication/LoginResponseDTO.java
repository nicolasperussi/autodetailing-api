package com.nicolasperussi.autodetailing_api.domain.dtos.authentication;

import com.nicolasperussi.autodetailing_api.domain.User;

public record LoginResponseDTO(String token, User user) {
}
