package com.nicolasperussi.autodetailing_api.services;

import com.nicolasperussi.autodetailing_api.domain.User;
import com.nicolasperussi.autodetailing_api.domain.dtos.authentication.RegisterUserDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.authentication.UpdateUserDTO;
import com.nicolasperussi.autodetailing_api.exceptions.ResourceNotFoundException;
import com.nicolasperussi.autodetailing_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;


    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find user with id " + id));
    }

    public User create(RegisterUserDTO data) {
        User user = new User(data.name(), data.email(), data.password(), data.role());

        return this.repository.save(user);
    }


    public User update(String id, UpdateUserDTO data) {
        User user = this.findById(id);

        if (data.name() != null) user.setName(data.name());
        if (data.email() != null) user.setEmail(data.email());
        if (data.role() != null) user.setRole(data.role());

        // TODO: add verification to see if anything has really changed before updating 'updatedAt'

        user.setUpdatedAt(Instant.now());

        return this.repository.save(user);
    }

    public void updateStatus(String id, boolean active) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado"));
        user.setActive(active);
        repository.save(user);
    }
}
