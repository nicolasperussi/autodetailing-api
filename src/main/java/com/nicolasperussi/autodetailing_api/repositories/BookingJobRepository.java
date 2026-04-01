package com.nicolasperussi.autodetailing_api.repositories;

import com.nicolasperussi.autodetailing_api.domain.JobService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingJobRepository extends JpaRepository<JobService, String> {
}
