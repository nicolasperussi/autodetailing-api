package com.nicolasperussi.autodetailing_api.domain;

import com.nicolasperussi.autodetailing_api.domain.pk.BookingJobPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_job_item")
public class BookingJob {
    @EmbeddedId
    private BookingJobPK id = new BookingJobPK();

    private BigDecimal priceAtTheTime;

    public BookingJob() {
    }

    public BookingJob(Booking booking, Job job, BigDecimal price) {
        id.setBooking(booking);
        id.setJob(job);
        this.priceAtTheTime = price;
    }

    public Booking getBooking() {return id.getBooking();}

    public Job getJob() {return id.getJob();}

    public BigDecimal getPriceAtTheTime() {return this.priceAtTheTime;}
}
