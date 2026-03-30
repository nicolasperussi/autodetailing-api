package com.nicolasperussi.autodetailing_api.domain.pk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicolasperussi.autodetailing_api.domain.Job;
import com.nicolasperussi.autodetailing_api.domain.Service;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class JobServicePK {
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    @JsonIgnore
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @JsonIgnore
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
