package com.nicolasperussi.autodetailing_api.services;

import com.nicolasperussi.autodetailing_api.domain.Customer;
import com.nicolasperussi.autodetailing_api.exceptions.ResourceNotFoundException;
import com.nicolasperussi.autodetailing_api.repositories.CustomerRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public List<Customer> findAll() { return repository.findAll(); }

    public Customer findById(@NonNull String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find Customer with id " + id));
    }

    public Customer create(@NonNull Customer customer) {
        return repository.save(customer);
    }


}
