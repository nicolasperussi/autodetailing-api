package com.nicolasperussi.autodetailing_api.services;

import com.nicolasperussi.autodetailing_api.domain.*;
import com.nicolasperussi.autodetailing_api.domain.dtos.booking.CreateBookingDTO;
import com.nicolasperussi.autodetailing_api.exceptions.ResourceNotFoundException;
import com.nicolasperussi.autodetailing_api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository repository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private BookingJobRepository bookingJobRepository;


    public List<Booking> findBookings(String customerId, String vehicleId, String userId, String jobId) {
        if (customerId != null) return this.repository.findByCustomerId(customerId);
        if (vehicleId != null) return this.repository.findByVehicleId(vehicleId);
        if (userId != null) return this.repository.findByUserId(userId);
        if (jobId != null) return this.repository.findByJobId(jobId);

        return this.repository.findAll();
    }

    public Booking findById(String id) {
        return this.repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Couldn't find Booking with id " + id));
    }

    public Booking create(CreateBookingDTO data) {
        Customer customer = customerRepository.findById(data.customerId()).orElseThrow(() -> new ResourceNotFoundException(
                "Couldn't find Customer with id " + data.customerId()));
        Vehicle vehicle = vehicleRepository.findById(data.vehicleId()).orElseThrow(() -> new ResourceNotFoundException(
                "Couldn't find Vehicle with id " + data.vehicleId()));
        User user = userRepository.findById(data.userId()).orElseThrow(() -> new ResourceNotFoundException(
                "Couldn't find User with id " + data.userId()));

        List<Job> jobs = jobRepository.findAllById(data.jobIds());
        if (jobs.isEmpty() || jobs.size() != data.jobIds().size()) {
            throw new ResourceNotFoundException("One or more Jobs provided do not exist");
        }

        BigDecimal totalPrice = jobs.stream().map(Job::getCurrentPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        Booking newBooking = new Booking(customer, vehicle, user, data.scheduledDate(), totalPrice);
        newBooking = repository.save(newBooking);

        List<BookingJob> bookingJobs = new ArrayList<>();
        for (Job job : jobs) {
            BookingJob bj = new BookingJob(newBooking, job, job.getCurrentPrice());
        }

        bookingJobRepository.saveAll(bookingJobs);

        return newBooking;
    }
}
