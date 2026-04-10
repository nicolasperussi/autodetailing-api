package com.nicolasperussi.autodetailing_api.controllers;

import com.nicolasperussi.autodetailing_api.domain.Job;
import com.nicolasperussi.autodetailing_api.domain.dtos.job.CreateJobDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.job.JobStatusDTO;
import com.nicolasperussi.autodetailing_api.domain.dtos.job.UpdateJobDTO;
import com.nicolasperussi.autodetailing_api.services.JobService;
import jakarta.validation.Valid;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/job", produces = {"application/json"})
public class JobController {

    @Autowired
    JobService service;

    @GetMapping()
    public ResponseEntity<List<Job>> findAll() {
        List<Job> list = this.service.findAll();

        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> findById(@NonNull @PathVariable String id) {
        Job job = this.service.findById(id);
        return ResponseEntity.ok(job);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateJobDTO data, UriComponentsBuilder uriBuilder) {
        Job newJob = this.service.create(data);

        URI uri = uriBuilder.path("/job/{id}").buildAndExpand(newJob.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<Void> update(@NonNull @PathVariable String id, @Valid @RequestBody UpdateJobDTO data) {
        this.service.updateJob(id, data);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> toggleJob(@NonNull @PathVariable String id, @Valid @RequestBody JobStatusDTO data) {
        this.service.toggleJob(id, data.active());
        return ResponseEntity.noContent().build();
    }

}
