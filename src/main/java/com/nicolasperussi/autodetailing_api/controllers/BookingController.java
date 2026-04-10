package com.nicolasperussi.autodetailing_api.controllers;

import com.nicolasperussi.autodetailing_api.domain.Booking;
import com.nicolasperussi.autodetailing_api.domain.dtos.booking.CreateBookingDTO;
import com.nicolasperussi.autodetailing_api.services.BookingService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/booking", produces = {"application/json"})
public class BookingController {
    @Autowired
    private BookingService service;

    @GetMapping()
    public ResponseEntity<List<Booking>> findAll(
            @RequestParam(name = "customer", required = false) String customerId,
            @RequestParam(name = "vehicle", required = false) String vehicleId,
            @RequestParam(name = "user", required = false) String userId,
            @RequestParam(name = "job", required = false) String jobId
                                                ) {
        List<Booking> list = this.service.findBookings(customerId, vehicleId, userId, jobId);

        if (list.isEmpty()) new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> findById(@NonNull @PathVariable String id) {
        Booking booking = this.service.findById(id);
        return ResponseEntity.ok(booking);
    }

    @PostMapping()
    public ResponseEntity<Void> create(@Valid @RequestBody CreateBookingDTO data, UriComponentsBuilder uriBuilder) {
        Booking booking = this.service.create(data);
        URI uri = uriBuilder.path("/booking/{id}").buildAndExpand(booking.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

}
