package com.nicolasperussi.autodetailing_api.domain.dtos.job;

public record UpdateJobDTO(String name, String description, String currentPrice, Integer estimatedTimeMins) {
}
