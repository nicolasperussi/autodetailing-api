package com.nicolasperussi.autodetailing_api.controllers;

import com.nicolasperussi.autodetailing_api.domain.dtos.authentication.*;
import com.nicolasperussi.autodetailing_api.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/auth", produces = {"application/json"})
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO data) {
        LoginResponseDTO loginReponse = this.service.login(data);

        return ResponseEntity.ok(loginReponse);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserDTO data, UriComponentsBuilder uriBuilder) {
        String userId = this.service.register(data);

        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(userId).toUri();

        return ResponseEntity.created(uri).build();
    }


    // TODO: change user password
}
