package com.nicolasperussi.autodetailing_api.repositories;

import com.nicolasperussi.autodetailing_api.domain.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    Page<Booking> findByCustomerId(String customerId, Pageable pageable);
    Page<Booking> findByVehicleId(String vehicleId, Pageable pageable);
    Page<Booking> findByUserId(String userId, Pageable pageable);
    @Query("SELECT bj.id.booking FROM BookingJob bj WHERE bj.id.job.id = :jobId")
    Page<Booking> findByJobId(@Param("jobId") String jobId, Pageable pageable);
}
