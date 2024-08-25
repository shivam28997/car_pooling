package com.shivam.repository;

import com.shivam.entity.Journey;
import com.shivam.entity.JourneyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface JourneyRepository extends CrudRepository<Journey, Long>, CustomJourneyRepository {

    // Find a specific journey by ID
    Optional<Journey> findById(Long journeyId);

    // Find all journeys created by a specific car owner
    List<Journey> findByCarOwnerId(Long carOwnerId);

    // Find all available journeys
    List<Journey> findByStatus(JourneyStatus status);

    List<Journey> findByStartPointAndEndPoint(String startPoint, String endPoint);
}
