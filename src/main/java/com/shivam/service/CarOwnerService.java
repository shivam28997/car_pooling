package com.shivam.service;


import com.shivam.dtos.CarOwnerUpdateDTO;
import com.shivam.entity.CarOwner;
import com.shivam.entity.Journey;
import com.shivam.entity.JourneyStatus;
import com.shivam.entity.RideRequestStatus;

import java.util.List;
import java.util.Optional;

public interface CarOwnerService {

    // Create a new journey
    Journey createJourney(CarOwner carOwner, Journey journey);

    // Update an existing journey
    Journey updateJourney(Long journeyId, Long id, Journey updatedJourney);

    // Get a specific journey by ID
    Optional<Journey> getJourneyById(Long journeyId);

    Journey getJourneyByCarOwnerId(Long CarOwnerId);

    // Get all journeys created by a specific car owner
    List<Journey> getJourneysByCarOwner(Long carOwnerId);

    // Get all available journeys
    List<Journey> getAllAvailableJourneys();

    // Update the status of a journey
    Journey updateJourneyStatus(Long journeyId, JourneyStatus status);

    // Update the available seats for a journey
    Journey updateAvailableSeats(Long journeyId, Integer availableSeats);

    // Accept or reject a ride request for a specific journey
    void respondToRideRequest(Long rideRequestId, RideRequestStatus rideRequestStatus);

    // Get car owner details
    Optional<CarOwner> getCarOwnerById(Long carOwnerId);

    // Register a new car owner
    CarOwner registerCarOwner(CarOwner carOwner);

    // Update car owner details
    CarOwner updateCarOwner(Long carOwnerId, CarOwnerUpdateDTO updatedCarOwner);
}

