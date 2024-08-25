package com.shivam.repository;

import com.shivam.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Find a payment by its ID.
     *
     * @param paymentId The ID of the payment.
     * @return An Optional containing the payment if found.
     */
    Optional<Payment> findById(Long paymentId);

    /**
     * Find all payments associated with a specific customer.
     * @param payerId The ID of the customer.
     * @return A list of payments associated with the customer.
     */
    List<Payment> findByPayerId(Long payerId);

    List<Payment> findByJourneyId(Long journeyId);
}

