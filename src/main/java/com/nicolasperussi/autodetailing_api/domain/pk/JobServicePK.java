package com.nicolasperussi.autodetailing_api.domain.pk;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nicolasperussi.autodetailing_api.domain.Booking;
import com.nicolasperussi.autodetailing_api.domain.Job;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class JobServicePK {
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Booking booking;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Job job;

    @JsonIgnore
    public Booking getJob() {
        return booking;
    }

    public void setJob(Booking booking) {
        this.booking = booking;
    }

    @JsonIgnore
    public Job getService() {
        return job;
    }

    public void setService(Job job) {
        this.job = job;
    }
}
