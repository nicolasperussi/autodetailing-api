package com.nicolasperussi.autodetailing_api.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "tb_service")
public class Service implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    @Lob
    private String description;
    @Column(precision = 10, scale = 2)
    private BigDecimal currentPrice;
    private int estimatedTimeMins;
    private boolean isActive;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant updatedAt;

    public Service() {
    }

    public Service(String name, String description, BigDecimal currentPrice, int estimatedTimeMins) {
        this.name = name;
        this.description = description;
        this.currentPrice = currentPrice;
        this.estimatedTimeMins = estimatedTimeMins;
        this.isActive = true;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getEstimatedTimeMins() {
        return estimatedTimeMins;
    }

    public void setEstimatedTimeMins(int estimatedTimeMins) {
        this.estimatedTimeMins = estimatedTimeMins;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
