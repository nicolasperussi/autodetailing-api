package com.nicolasperussi.autodetailing_api.repositories;

import com.nicolasperussi.autodetailing_api.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Job, String> {
}
