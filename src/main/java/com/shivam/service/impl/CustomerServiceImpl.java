package com.shivam.service.impl;

import com.shivam.entity.Customer;
import com.shivam.entity.Journey;
import com.shivam.entity.JourneyStatus;
import com.shivam.entity.RideRequest;
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
    public RideRequest requestRide(String CustomerEmailId, Long journeyId, String requestedStartPoint, String requestedEndPoint, String requestedTravelTime) {
        // Implement ride request logic
        // This is a placeholder implementation
        Journey journey = (Journey) journeyRepository.findByStartPointAndEndPoint(requestedStartPoint, requestedEndPoint);

        Customer customer = customerRepository.findByEmail(CustomerEmailId);
        RideRequest rideRequest = new RideRequest();
        rideRequest.setCustomer(customer);
        rideRequest.setJourney(journey);
        rideRequest.setRequestedStartPoint(requestedStartPoint);
        rideRequest.setRequestedEndPoint(requestedEndPoint);
        rideRequest.setRequestedTravelTime(requestedTravelTime);

        return rideRequestRepository.save(rideRequest);
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
