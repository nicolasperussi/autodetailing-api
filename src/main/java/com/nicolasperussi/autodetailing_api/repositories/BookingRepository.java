package com.nicolasperussi.autodetailing_api.repositories;

import com.nicolasperussi.autodetailing_api.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByCustomerId(String customerId);
    List<Booking> findByVehicleId(String vehicleId);
    List<Booking> findByUserId(String userId);
    @Query("SELECT bj.id.booking FROM BookingJob bj WHERE bj.id.job.id = :jobId")
    List<Booking> findByJobId(@Param("jobId") String jobId);
}
