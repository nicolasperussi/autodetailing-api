package com.nicolasperussi.autodetailing_api.repositories;

import com.nicolasperussi.autodetailing_api.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {
}
