package com.nicolasperussi.autodetailing_api.services;

import com.nicolasperussi.autodetailing_api.domain.User;
import com.nicolasperussi.autodetailing_api.exceptions.ResourceNotFoundException;
import com.nicolasperussi.autodetailing_api.repositories.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(@NonNull String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find user with id " + id));
    }

    public User create(@NonNull User user) {
        return repository.save(user);
    }

    public void updateStatus(@NonNull String id, @NonNull boolean active) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado"));
        user.setActive(active);
        repository.save(user);
    }
}
