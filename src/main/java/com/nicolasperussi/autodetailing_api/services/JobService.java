package com.nicolasperussi.autodetailing_api.services;

import com.nicolasperussi.autodetailing_api.domain.Job;
import com.nicolasperussi.autodetailing_api.domain.dtos.job.CreateJobDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.job.UpdateJobDTO;
import com.nicolasperussi.autodetailing_api.exceptions.ResourceNotFoundException;
import com.nicolasperussi.autodetailing_api.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository repository;

    public List<Job> findAll() {return this.repository.findAll();}

    public Job findById(String id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find Job with id " + id));
    }

    public Job create(CreateJobDTO data) {
        Job newJob = new Job(data.name(), data.description(), new BigDecimal(data.currentPrice()), data.estimatedTimeMins());

        return this.repository.save(newJob);
    }

    public Job updateJob(String id, UpdateJobDTO data) {
        Job job = this.findById(id);

        if (data.name() != null) job.setName(data.name());
        if (data.description() != null) job.setDescription(data.description());
        if (data.currentPrice() != null) job.setCurrentPrice(new BigDecimal(data.currentPrice()));
        if (data.estimatedTimeMins() != null) job.setEstimatedTimeMins(data.estimatedTimeMins());

        job.setUpdatedAt(Instant.now());

        return this.repository.save(job);
    }

    public void toggleJob(String id, boolean active) {
        Job job = this.findById(id);
        job.setActive(active);
        job.setUpdatedAt(Instant.now());
        this.repository.save(job);
    }
}
