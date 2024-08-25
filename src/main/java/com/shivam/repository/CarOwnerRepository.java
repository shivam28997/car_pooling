package com.shivam.repository;

import com.shivam.entity.CarOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {
    // Find by email (example custom method)
    CarOwner findByEmail(String email);
    Optional<CarOwner> findById(Long id);

}

