package com.nicolasperussi.autodetailing_api.controllers;

import com.nicolasperussi.autodetailing_api.domain.Customer;
import com.nicolasperussi.autodetailing_api.domain.Vehicle;
import com.nicolasperussi.autodetailing_api.domain.dtos.customer.CreateCustomerDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.customer.UpdateCustomerDTO;
import com.nicolasperussi.autodetailing_api.repositories.CustomerRepository;
import com.nicolasperussi.autodetailing_api.services.CustomerService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(value = "/customers", produces = {"application/json"})
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Autowired
    private CustomerRepository repository;

    @GetMapping()
    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> list = service.findAll();

        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> findById(@NonNull @PathVariable String id) {
        Customer customer = this.service.findById(id);
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping()
    public ResponseEntity<Void> create(@Valid @RequestBody CreateCustomerDTO data, UriComponentsBuilder uriBuilder) {
        Customer customer = new Customer(data.name(), data.phone(), data.email(), data.document());
        Vehicle customerVehicle = new Vehicle(
                data.vehicle().licensePlate(),
                data.vehicle().brand(),
                data.vehicle().model(),
                data.vehicle().year(),
                data.vehicle().color()
        );

        customer.addVehicle(customerVehicle);

        service.create(customer);

        URI uri = uriBuilder.path("/customers/{id}").buildAndExpand(customer.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> update(@NonNull @PathVariable String id, @Valid @RequestBody UpdateCustomerDTO data) {
        Customer customer = this.service.findById(id);

        if (data.name() != null) customer.setName(data.name());
        if (data.document() != null) customer.setDocument(data.document());
        if (data.email() != null) customer.setEmail(data.email());
        if (data.phone() != null) customer.setPhone(data.phone());

        // TODO: add verification to see if anything has really changed before updating 'updatedAt'

        customer.setUpdatedAt(Instant.now());
        this.repository.save(customer);

        return ResponseEntity.noContent().build();
    }

    // TODO: remove vehicle from customer without deleting it from database
}
