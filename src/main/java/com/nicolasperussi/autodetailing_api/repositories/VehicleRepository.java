package com.nicolasperussi.autodetailing_api.repositories;

import com.nicolasperussi.autodetailing_api.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    List<Vehicle> findByCustomerId(String customerId);
}
