package com.shivam.controller;

import com.shivam.dtos.CarOwnerUpdateDTO;
import com.shivam.entity.CarOwner;
import com.shivam.entity.Journey;
import com.shivam.entity.RideRequestStatus;
import com.shivam.service.CarOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.shivam.util.CityCoordinates;

@RestController
@RequestMapping("/car-owner")
public class CarOwnerController {

    private final CarOwnerService carOwnerService;

    @Autowired
    public CarOwnerController(CarOwnerService carOwnerService) {
        this.carOwnerService = carOwnerService;
    }

    // Register a new car owner
    @PostMapping("/register")
    public ResponseEntity<CarOwner> registerCarOwner(@RequestBody CarOwner carOwner) {
        CarOwner newCarOwner = carOwnerService.registerCarOwner(carOwner);
        return ResponseEntity.ok(newCarOwner);
    }

    // Update car owner details
    @PutMapping("/{carOwnerId}")
    public ResponseEntity<CarOwner> updateCarOwner(@PathVariable("carOwnerId") Long carOwnerId, @RequestBody CarOwnerUpdateDTO updatedCarOwner) {
        CarOwner carOwner = carOwnerService.updateCarOwner(carOwnerId, updatedCarOwner);
        if (carOwner != null) {
            return ResponseEntity.ok(carOwner);
        }
        return ResponseEntity.notFound().build();
    }

    // Get car owner by ID
    @GetMapping("/{carOwnerId}")
    public ResponseEntity<Optional<CarOwner>> getCarOwnerById(@PathVariable("carOwnerId") Long carOwnerId) {
        Optional<CarOwner> carOwner = carOwnerService.getCarOwnerById(carOwnerId);
        if (carOwner != null) {
            return ResponseEntity.ok(carOwner);
        }
        return ResponseEntity.notFound().build();
    }

    // Create a new journey
    @PostMapping("/{carOwnerId}/journeys")
    public ResponseEntity<Journey> createJourney(@PathVariable("carOwnerId") Long carOwnerId, @RequestBody Journey journey) {
        Optional<CarOwner> carOwnerOpt = carOwnerService.getCarOwnerById(carOwnerId);
        if (carOwnerOpt.isPresent()) {
            try {
                Journey createdJourney = carOwnerService.createJourney(carOwnerOpt.get(), journey);
                return ResponseEntity.ok(createdJourney);
            } catch (IllegalStateException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/locations")
    public ResponseEntity<List<String>> getAvailableLocations() {
        List<String> locations = CityCoordinates.getCities().keySet().stream()
                .sorted()
                .collect(Collectors.toList());
        return ResponseEntity.ok(locations);
    }

    // Update an existing journey
    @PutMapping("/{carOwnerId}/journeys/{journeyId}")
    public ResponseEntity<Journey> updateJourney(@PathVariable("carOwnerId") Long carOwnerId, @PathVariable("journeyId") Long journeyId, @RequestBody Journey updatedJourney) {
        Journey journey = carOwnerService.updateJourney(carOwnerId, journeyId, updatedJourney);

        if (journey != null) {
            return ResponseEntity.ok(journey);
        }

        return ResponseEntity.notFound().build();
    }


    // Get a specific journey by ID
    @GetMapping("/journeys/{journeyId}")
    public ResponseEntity<Optional<Journey>> getJourneyById(@PathVariable("journeyId") Long journeyId) {
        Optional<Journey> journey = carOwnerService.getJourneyById(journeyId);
        return ResponseEntity.ok(journey);
    }

    // Get all journeys created by a specific car owner
    @GetMapping("/{carOwnerId}/journeys")
    public ResponseEntity<List<Journey>> getJourneysByCarOwner(@PathVariable("carOwnerId") Long carOwnerId) {
        List<Journey> journeys = carOwnerService.getJourneysByCarOwner(carOwnerId);
        return ResponseEntity.ok(journeys);
    }

    // Get all journeys created by a specific car owner
    @GetMapping("/{carOwnerId}/currentJourney")
    public ResponseEntity<Journey> getCurrentJourneysByCarOwner(@PathVariable("carOwnerId") Long carOwnerId) {
        Journey journey = carOwnerService.getJourneyByCarOwnerId(carOwnerId);
        return ResponseEntity.ok(journey);
    }

    // Respond to a ride request (accept or reject)
    @PutMapping("/ride-requests/{rideRequestId}/respond")
    public ResponseEntity<Void> respondToRideRequest(@PathVariable Long rideRequestId, @RequestParam RideRequestStatus requestStatus) {
        carOwnerService.respondToRideRequest(rideRequestId, requestStatus);
        return ResponseEntity.ok().build();
    }
}
