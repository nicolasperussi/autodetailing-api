package com.nicolasperussi.autodetailing_api.domain.dtos.booking;

import java.time.Instant;
import java.util.List;

public record CreateBookingDTO(
        String customerId,
        String vehicleId,
        String userId,
        Instant scheduledDate,
        List<String> jobIds
) {}
