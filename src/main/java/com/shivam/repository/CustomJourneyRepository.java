package com.shivam.repository;


import com.shivam.entity.Journey;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomJourneyRepository {
    List<Journey> findOverlappingJourneys(Long carOwnerId, LocalDateTime startTime, LocalDateTime endTime);
}
