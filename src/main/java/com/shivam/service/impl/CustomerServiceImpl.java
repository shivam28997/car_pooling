package com.shivam.service.impl;

import com.shivam.entity.*;
import com.shivam.exception.ResourceNotFoundException;
import com.shivam.repository.CustomerRepository;
import com.shivam.repository.JourneyRepository;
import com.shivam.repository.RideRequestRepository;
import com.shivam.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private RideRequestRepository rideRequestRepository;

    @Override
    public Customer registerCustomer(Customer customer) {
        Customer newCustomer = new Customer();
        newCustomer.setName(customer.getName());
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setPhoneNumber(customer.getPhoneNumber());
        return customerRepository.save(newCustomer);
    }

    @Override
    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        if (customerRepository.existsById(customerId)) {
            updatedCustomer.setId(customerId);
            return customerRepository.save(updatedCustomer);
        }
        throw new RuntimeException("Customer not found with ID: " + customerId);
    }

    @Override
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    public RideRequest requestRide(Long customerId, Long journeyId, RideRequest rideRequest) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        Optional<Journey> journey = journeyRepository.findById(journeyId);

        if (customer.isPresent() && journey.isPresent()) {
            rideRequest.setCustomer(customer.get());
            rideRequest.setJourney(journey.get());
            rideRequest.setRequestedStartPoint(rideRequest.getRequestedStartPoint());
            rideRequest.setRequestedEndPoint(rideRequest.getRequestedEndPoint());
            rideRequest.setRequestedTravelTime(rideRequest.getRequestedTravelTime());
            if (rideRequest.getStatus() == null) {
                rideRequest.setStatus(RideRequestStatus.PENDING);
            }
            return rideRequestRepository.save(rideRequest);
        } else {
            throw new ResourceNotFoundException("Customer or Journey not found");
        }
    }

    @Override
    public RideRequest updateRideRequest(Long rideRequestId, RideRequest updatedRideRequest) {
        if (rideRequestRepository.existsById(rideRequestId)) {
            updatedRideRequest.setId(rideRequestId);
            return rideRequestRepository.save(updatedRideRequest);
        }
        throw new RuntimeException("Ride request not found with ID: " + rideRequestId);
    }

    @Override
    public void cancelRideRequest(Long rideRequestId) {
        if (rideRequestRepository.existsById(rideRequestId)) {
            rideRequestRepository.deleteById(rideRequestId);
        } else {
            throw new RuntimeException("Ride request not found with ID: " + rideRequestId);
        }
    }

    @Override
    public List<RideRequest> getRideRequestsByCustomer(Long customerId) {
        return rideRequestRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Journey> searchJourneys(String startPoint, String endPoint) {
        // Implement search logic
        // This is a placeholder implementation
        return journeyRepository.findByStartPointAndEndPoint(startPoint, endPoint);
    }

    @Override
    public List<Journey> getAllAvailableJourneys() {
        return journeyRepository.findByStatus(JourneyStatus.NOT_STARTED);
    }

    @Override
    public void receiveNotifications(Long customerId, String notificationMessage) {
        // Implement notification logic
        // This is a placeholder implementation
        System.out.println("Notification to customer ID " + customerId + ": " + notificationMessage);
    }

    @Override
    public double calculateFare(Long journeyId, double distance) {
        // Implement fare calculation logic
        // This is a placeholder implementation
        double ratePerKm = 10.0; // Example rate
        return ratePerKm * distance;
    }

    @Override
    public void makePayment(Long journeyId, Long customerId, double amount) {
        // Implement payment logic
        // This is a placeholder implementation
        System.out.println("Payment of " + amount + " for journey ID " + journeyId + " by customer ID " + customerId);
    }
}
