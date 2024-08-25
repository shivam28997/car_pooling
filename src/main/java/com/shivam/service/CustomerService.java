package com.shivam.service;

import com.shivam.entity.Customer;
import com.shivam.entity.Journey;
import com.shivam.entity.RideRequest;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    // Register a new customer
    Customer registerCustomer(Customer customer);

    // Update customer details
    Customer updateCustomer(Long customerId, Customer updatedCustomer);

    // Get customer details by ID
    Optional<Customer> getCustomerById(Long customerId);

    // Request to join a journey
    RideRequest requestRide(String CustomerEmailId, Long journeyId, String requestedStartPoint, String requestedEndPoint, String requestedTravelTime);

    // Update a ride request
    RideRequest updateRideRequest(Long rideRequestId, RideRequest updatedRideRequest);

    // Cancel a ride request
    void cancelRideRequest(Long rideRequestId);

    // Get all ride requests made by a customer
    List<RideRequest> getRideRequestsByCustomer(Long customerId);

    // Get all journeys that match the customer's starting and ending points
    List<Journey> searchJourneys(String startPoint, String endPoint);

    // Get all available journeys
    List<Journey> getAllAvailableJourneys();

    // Receive notifications about journeys
    void receiveNotifications(Long customerId, String notificationMessage);

    // Calculate fare based on distance
    double calculateFare(Long journeyId, double distance);

    // Make a payment for a journey
    void makePayment(Long journeyId, Long customerId, double amount);
}
