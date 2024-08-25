package com.shivam.repository;

import com.shivam.entity.RideRequest;
import com.shivam.entity.RideRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {

    /**
     * Find all ride requests for a specific customer.
     * @param customerId The ID of the customer.
     * @return A list of ride requests for the customer.
     */
    List<RideRequest> findByCustomerId(Long customerId);

    /**
     * Find all ride requests for a specific journey.
     * @param journeyId The ID of the journey.
     * @return A list of ride requests for the journey.
     */
    List<RideRequest> findByJourneyId(Long journeyId);

    /**
     * Find a ride request by its ID and status.
     * @param rideRequestId The ID of the ride request.
     * @param status The status of the ride request.
     * @return An Optional containing the ride request if found.
     */
    RideRequest findByIdAndStatus(Long rideRequestId, RideRequestStatus status);

    /**
     * Find all ride requests with a specific status.
     * @param status The status of the ride requests.
     * @return A list of ride requests with the specified status.
     */
    List<RideRequest> findByStatus(RideRequestStatus status);

    /**
     * Find a ride request by its ID.
     *
     * @param rideRequestId The ID of the ride request.
     * @return An Optional containing the ride request if found.
     */
    Optional<RideRequest> findById(Long rideRequestId);

    // You can add more custom queries as needed
}
