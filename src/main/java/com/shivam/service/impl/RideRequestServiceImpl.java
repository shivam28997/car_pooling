package com.shivam.service.impl;

import com.shivam.entity.Customer;
import com.shivam.entity.RideRequest;
import com.shivam.entity.Journey;
import com.shivam.entity.RideRequestStatus;
import com.shivam.repository.CustomerRepository;
import com.shivam.repository.RideRequestRepository;
import com.shivam.repository.JourneyRepository;
import com.shivam.service.RideRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RideRequestServiceImpl implements RideRequestService {

    @Autowired
    private RideRequestRepository rideRequestRepository;

    @Autowired
    private JourneyRepository journeyRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public RideRequest createRideRequest(String CustomerEmailId, Long journeyId, String startPoint, String endPoint, LocalDateTime travelTime) {
        Customer customer = customerRepository.findByEmail(CustomerEmailId);
        Journey journey = (Journey) journeyRepository.findByStartPointAndEndPoint(startPoint, endPoint);
        RideRequest rideRequest = new RideRequest();
        rideRequest.setCustomer(customer);
        rideRequest.setJourney(journey);
        rideRequest.setRequestedStartPoint(startPoint);
        rideRequest.setRequestedEndPoint(endPoint);
        rideRequest.setRequestedTravelTime(travelTime);
        rideRequest.setStatus(RideRequestStatus.PENDING); // Default status
        return rideRequestRepository.save(rideRequest);
    }

    @Override
    public RideRequest updateRideRequest(Long rideRequestId, RideRequest updatedRideRequest) {
        if (!rideRequestRepository.existsById(rideRequestId)) {
            throw new RuntimeException("Ride Request not found with ID: " + rideRequestId);
        }
        updatedRideRequest.setId(rideRequestId);
        return rideRequestRepository.save(updatedRideRequest);
    }

    @Override
    public void cancelRideRequest(Long rideRequestId) {
        RideRequest rideRequest = rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new RuntimeException("Ride Request not found with ID: " + rideRequestId));
        rideRequest.setStatus(RideRequestStatus.CANCELED);
        rideRequestRepository.save(rideRequest);
    }

    @Override
    public Optional<RideRequest> getRideRequestById(Long rideRequestId) {
        return rideRequestRepository.findById(rideRequestId);
    }

    @Override
    public List<RideRequest> getRideRequestsByJourney(Long journeyId) {
        return rideRequestRepository.findByJourneyId(journeyId);
    }

    @Override
    public List<RideRequest> getRideRequestsByCustomer(Long customerId) {
        return rideRequestRepository.findByCustomerId(customerId);
    }

    @Override
    public boolean isRideRequestValid(RideRequest rideRequest, Journey journey) {
        // Check if the ride request is within the journey's route and seats are available
        boolean isWithinProximity = isWithinProximity(rideRequest.getRequestedStartPoint(), journey.getStartPoint(), journey.getEndPoint()) &&
                isWithinProximity(rideRequest.getRequestedEndPoint(), journey.getStartPoint(), journey.getEndPoint());
        boolean isSeatsAvailable = journey.getAvailableSeats() > 0;

        return isWithinProximity && isSeatsAvailable;
    }

    private boolean isWithinProximity(String point, String startPoint, String endPoint) {
        // Implement proximity check logic (e.g., within 5 km)
        // For simplicity, this is a placeholder
        return true;
    }

    @Override
    public void respondToRideRequest(Long rideRequestId, boolean accept) {
        RideRequest rideRequest = rideRequestRepository.findById(rideRequestId)
                .orElseThrow(() -> new RuntimeException("Ride Request not found with ID: " + rideRequestId));

        if (accept) {
            rideRequest.setStatus(RideRequestStatus.ACCEPTED);
        } else {
            rideRequest.setStatus(RideRequestStatus.REJECTED);
        }
        rideRequestRepository.save(rideRequest);
    }
}
