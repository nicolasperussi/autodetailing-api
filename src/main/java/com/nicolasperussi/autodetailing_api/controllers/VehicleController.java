package com.nicolasperussi.autodetailing_api.controllers;

import com.nicolasperussi.autodetailing_api.domain.Vehicle;
import com.nicolasperussi.autodetailing_api.domain.dtos.vehicle.CreateVehicleDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.vehicle.UpdateVehicleDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.vehicle.VehicleByCustomerResponseDTO;
import com.nicolasperussi.autodetailing_api.services.VehicleService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/vehicle", produces = {"application/json"})
public class VehicleController {

    @Autowired
    private VehicleService service;

    @GetMapping()
    public ResponseEntity<List<Vehicle>> findAll() {
        List<Vehicle> vehicles = this.service.findAll();
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping(params = "customer")
    public ResponseEntity<List<VehicleByCustomerResponseDTO>> findByCustomerId(@RequestParam(name = "customer") String customerId) {
        List<VehicleByCustomerResponseDTO> vehicles = this.service.findByCustomerId(customerId);

        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(@NonNull @PathVariable String id) {
        Vehicle vehicle = this.service.findById(id);
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping()
    public ResponseEntity<Void> createVehicle(@Valid @RequestBody CreateVehicleDTO data, UriComponentsBuilder uriBuilder) {
        Vehicle newVehicle = this.service.create(data);
        URI uri = uriBuilder.path("/vehicle/{id}").buildAndExpand(newVehicle.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> updateVehicle(@NonNull @PathVariable String id, @Valid @RequestBody UpdateVehicleDTO data) {
        this.service.update(id, data);
        return ResponseEntity.noContent().build();
    }
}
