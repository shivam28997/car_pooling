package com.shivam.service;

import com.shivam.entity.Journey;
import com.shivam.entity.JourneyStatus;

import java.util.List;
import java.util.Optional;

public interface JourneyService {

    // Create a new journey
    Journey createJourney(Journey journey);

    // Update an existing journey
    Journey updateJourney(Long journeyId, Journey updatedJourney);

    // Get a specific journey by ID
    Optional<Journey> getJourneyById(Long journeyId);

    // Get all journeys with a specific status
    List<Journey> getJourneysByStatus(JourneyStatus status);

    // Get all journeys created by a specific car owner
    List<Journey> getJourneysByCarOwner(Long carOwnerId);

    // Get all available journeys
    List<Journey> getAllAvailableJourneys();

    // Update the status of a journey
    Journey updateJourneyStatus(Long journeyId, JourneyStatus status);

    // Update the available seats for a journey
    Journey updateAvailableSeats(Long journeyId, Integer availableSeats);

    // Search for journeys based on start and end points
    List<Journey> searchJourneys(String startPoint, String endPoint);

    // Check if a journey is within 5 km of a given location
    boolean isJourneyWithinProximity(Long journeyId, String location, double maxDistance);
}

