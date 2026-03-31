package com.nicolasperussi.autodetailing_api.controllers;

import com.nicolasperussi.autodetailing_api.domain.User;
import com.nicolasperussi.autodetailing_api.domain.dtos.authentication.UpdateUserDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.authentication.UserStatusDTO;
import com.nicolasperussi.autodetailing_api.services.UserService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = {"application/json"})
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping()
    public ResponseEntity<List<User>> findAll() {
        List<User> list = this.service.findAll();
        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@NonNull @PathVariable String id) {
        User user = this.service.findById(id);

        return ResponseEntity.ok().body(user);
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> update(@NonNull @PathVariable String id, @Valid @RequestBody UpdateUserDTO data) {
        this.service.update(id, data);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> toggleStatus(@NonNull @PathVariable String id, @Valid @RequestBody UserStatusDTO data) {
        this.service.updateStatus(id, data.active());

        return ResponseEntity.noContent().build();
    }
}
