package com.nicolasperussi.autodetailing_api.services;

import com.nicolasperussi.autodetailing_api.domain.Customer;
import com.nicolasperussi.autodetailing_api.domain.Vehicle;
import com.nicolasperussi.autodetailing_api.domain.dtos.customer.CreateCustomerDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.customer.UpdateCustomerDTO;
import com.nicolasperussi.autodetailing_api.exceptions.ResourceNotFoundException;
import com.nicolasperussi.autodetailing_api.repositories.CustomerRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;

    public List<Customer> findAll() {return repository.findAll();}

    public Customer findById(@NonNull String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find Customer with id " + id));
    }

    public Customer create(@NonNull CreateCustomerDTO data) {
        Customer customer = new Customer(data.name(), data.phone(), data.email(), data.document());
        Vehicle customerVehicle = new Vehicle(
                data.vehicle().licensePlate(),
                data.vehicle().brand(),
                data.vehicle().model(),
                data.vehicle().year(),
                data.vehicle().color()
        );

        customer.addVehicle(customerVehicle);

        return this.repository.save(customer);
    }

    public Customer update(String id, UpdateCustomerDTO data) {
        Customer customer = this.findById(id);

        if (data.name() != null) customer.setName(data.name());
        if (data.phone() != null) customer.setPhone(data.phone());
        if (data.email() != null) customer.setEmail(data.email());
        if (data.document() != null) customer.setDocument(data.document());

        // TODO: add verification to see if anything has really changed before updating 'updatedAt'
        customer.setUpdatedAt(Instant.now());

        return this.repository.save(customer);
    }
}
