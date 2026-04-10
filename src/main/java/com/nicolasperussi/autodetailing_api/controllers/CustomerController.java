package com.nicolasperussi.autodetailing_api.controllers;

import com.nicolasperussi.autodetailing_api.domain.Customer;
import com.nicolasperussi.autodetailing_api.domain.dtos.customer.CreateCustomerDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.customer.UpdateCustomerDTO;
import com.nicolasperussi.autodetailing_api.services.CustomerService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping()
    public ResponseEntity<Page<Customer>> findAll(Pageable pageable) {
        Page<Customer> list = service.findAll(pageable);

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
        Customer newCustomer = this.service.create(data);
        URI uri = uriBuilder.path("/customers/{id}").buildAndExpand(newCustomer.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Void> update(@NonNull @PathVariable String id, @Valid @RequestBody UpdateCustomerDTO data) {
        this.service.update(id, data);
        return ResponseEntity.noContent().build();
    }

    // TODO: remove vehicle from customer without deleting it from database
}
