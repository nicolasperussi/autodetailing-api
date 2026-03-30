package com.nicolasperussi.autodetailing_api.domain;

import com.nicolasperussi.autodetailing_api.domain.pk.JobServicePK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_job_item")
public class JobService {
    @EmbeddedId
    private JobServicePK id = new JobServicePK();

    private BigDecimal priceAtTheTime;

    public JobService() {
    }

    public JobService(Job job, Service service, BigDecimal price) {
        id.setJob(job);
        id.setService(service);
        this.priceAtTheTime = price;
    }

    public Job getJob() {return id.getJob();}

    public Service getService() {return id.getService();}

    public BigDecimal getPriceAtTheTime() {return this.priceAtTheTime;}
}
