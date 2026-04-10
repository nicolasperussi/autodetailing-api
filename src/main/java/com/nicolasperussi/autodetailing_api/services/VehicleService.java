package com.nicolasperussi.autodetailing_api.services;

import com.nicolasperussi.autodetailing_api.domain.Customer;
import com.nicolasperussi.autodetailing_api.domain.Vehicle;
import com.nicolasperussi.autodetailing_api.domain.dtos.customer.UpdateCustomerDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.vehicle.CreateVehicleDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.vehicle.UpdateVehicleDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.vehicle.VehicleByCustomerResponseDTO;
import com.nicolasperussi.autodetailing_api.exceptions.ResourceNotFoundException;
import com.nicolasperussi.autodetailing_api.repositories.CustomerRepository;
import com.nicolasperussi.autodetailing_api.repositories.VehicleRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository repository;
    @Autowired
    private CustomerRepository customerRepository;

    public Page<Vehicle> findAll(Pageable pageable) {return repository.findAll(pageable);}

    public Vehicle findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Couldn't find Vehicle with id " + id));
    }

    public List<VehicleByCustomerResponseDTO> findByCustomerId(String customerId) {
        return this.repository.findByCustomerId(customerId)
                .stream()
                .map(this::convertToVehicleResponseDTO)
                .collect(Collectors.toList());
    }

    public Vehicle create(@NonNull CreateVehicleDTO data) {
        Customer owner = customerRepository.findById(data.customerId()).orElseThrow(() -> new ResourceNotFoundException(
                "Couldn't find customer with id " + data.customerId()));

        Vehicle newVehicle = new Vehicle(data.licensePlate(), data.brand(), data.model(), data.year(), data.color());

        newVehicle.setCustomer(owner);

        return repository.save(newVehicle);
    }


    public Vehicle update(String id, UpdateVehicleDTO data) {
        Vehicle vehicle = this.findById(id);

        if (data.licensePlate() != null) vehicle.setLicensePlate(data.licensePlate());
        if (data.brand() != null) vehicle.setBrand(data.brand());
        if (data.model() != null) vehicle.setModel(data.model());
        if (data.color() != null) vehicle.setColor(data.color());
        if (data.year() != null) vehicle.setYear(data.year());

        if (data.customerId() != null) {
            Customer newOwner = this.customerRepository.findById(data.customerId()).orElseThrow(() -> new ResourceNotFoundException(
                    "Couldn't find customer with id " + data.customerId()));

            vehicle.setCustomer(newOwner);
        }

        vehicle.setUpdatedAt(Instant.now());

        return this.repository.save(vehicle);
    }

    public VehicleByCustomerResponseDTO convertToVehicleResponseDTO(Vehicle vehicle) {
        return new VehicleByCustomerResponseDTO(
                vehicle.getId(),
                vehicle.getLicensePlate(),
                vehicle.getBrand(),
                vehicle.getModel(),
                vehicle.getYear(),
                vehicle.getColor(),
                vehicle.getCreatedAt(),
                vehicle.getUpdatedAt()
        );
    }
}
