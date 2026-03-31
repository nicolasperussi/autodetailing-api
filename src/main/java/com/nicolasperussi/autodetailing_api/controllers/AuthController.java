package com.nicolasperussi.autodetailing_api.controllers;

import com.nicolasperussi.autodetailing_api.domain.User;
import com.nicolasperussi.autodetailing_api.domain.dtos.*;
import com.nicolasperussi.autodetailing_api.exceptions.DatabaseException;
import com.nicolasperussi.autodetailing_api.repositories.UserRepository;
import com.nicolasperussi.autodetailing_api.services.UserService;
import com.nicolasperussi.autodetailing_api.security.TokenService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/auth", produces = { "application/json" })
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserService service;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        Authentication auth = this.authenticationManager.authenticate(usernamePassword);

        User user = (User) auth.getPrincipal();
        String token = tokenService.generateToken(user);

        return ResponseEntity.ok((new LoginResponseDTO(token, user)));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserDTO data, UriComponentsBuilder uriBuilder) {
        if (this.repository.findByEmail(data.email()) != null) {
            throw new DatabaseException("This e-mail is already in use.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.name(), data.email(), encryptedPassword, data.role());

        this.service.create(user);

        URI uri = uriBuilder.path("/auth/{id}/get").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll();
        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@NonNull @PathVariable String id) {
        User user = service.findById(id);

        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> update(@NonNull @PathVariable String id, @Valid @RequestBody UpdateUserDTO data) {
        User user = (User) this.service.findById(id);

        if (data.name() != null) user.setName(data.name());
        if (data.email() != null) user.setEmail(data.email());
        if (data.role() != null) user.setRole(data.role());

        this.repository.save(user);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> toggleStatus(@NonNull @PathVariable String id, @Valid @RequestBody UserStatusDTO data) {
        service.updateStatus(id, data.active());

        return ResponseEntity.noContent().build();
    }

    // TODO: change user password
}
