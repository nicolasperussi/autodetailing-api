package com.nicolasperussi.autodetailing_api.repositories;

import com.nicolasperussi.autodetailing_api.domain.BookingJob;
import com.nicolasperussi.autodetailing_api.domain.pk.BookingJobPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingJobRepository extends JpaRepository<BookingJob, BookingJobPK> {
}
