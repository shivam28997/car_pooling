package com.shivam.repository;

import com.shivam.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find a customer by their email.
     * @param email The email of the customer.
     * @return An Optional containing the customer if found.
     */
    Customer findByEmail(String email);

    /**
     * Find a customer by their ID.
     *
     * @param customerId The ID of the customer.
     * @return An Optional containing the customer if found.
     */
    Optional<Customer> findById(Long customerId);

    // You can add more custom queries as needed
}

