package com.shivam.service;

import com.shivam.entity.RideRequest;
import com.shivam.entity.Journey;

import java.util.List;
import java.util.Optional;

public interface RideRequestService {

    // Create a new ride request
    RideRequest createRideRequest(String CustomerEmailId, Long journeyId, String startPoint, String endPoint, String travelTime);

    // Update an existing ride request
    RideRequest updateRideRequest(Long rideRequestId, RideRequest updatedRideRequest);

    // Cancel a ride request
    void cancelRideRequest(Long rideRequestId);

    // Get a specific ride request by ID
    Optional<RideRequest> getRideRequestById(Long rideRequestId);

    // Get all ride requests for a specific journey
    List<RideRequest> getRideRequestsByJourney(Long journeyId);

    // Get all ride requests made by a specific customer
    List<RideRequest> getRideRequestsByCustomer(Long customerId);

    // Check if a ride request is valid based on the journey's route and available seats
    boolean isRideRequestValid(RideRequest rideRequest, Journey journey);

    // Accept or reject a ride request
    void respondToRideRequest(Long rideRequestId, boolean accept);
}
