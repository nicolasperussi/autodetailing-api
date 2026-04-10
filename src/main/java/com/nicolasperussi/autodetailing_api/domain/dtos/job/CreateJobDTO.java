package com.nicolasperussi.autodetailing_api.domain.dtos.job;

public record CreateJobDTO(String name, String description, String currentPrice, Integer estimatedTimeMins) {
}
