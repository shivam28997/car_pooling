package com.shivam.controller;

import com.shivam.entity.Customer;
import com.shivam.entity.Journey;
import com.shivam.entity.RideRequest;
import com.shivam.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
        Customer registeredCustomer = customerService.registerCustomer(customer);
        return ResponseEntity.ok(registeredCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/requestRide")
    public ResponseEntity<RideRequest> requestRide(
            @RequestParam String email,
            @RequestParam Long journeyId,
            @RequestParam String startPoint,
            @RequestParam String endPoint,
            @RequestParam String travelTime) {

        RideRequest rideRequest = customerService.requestRide(email, journeyId, startPoint, endPoint, travelTime);
        return ResponseEntity.ok(rideRequest);
    }

    @PutMapping("/rideRequests/{id}")
    public ResponseEntity<RideRequest> updateRideRequest(@PathVariable Long id, @RequestBody RideRequest rideRequest) {
        RideRequest updatedRideRequest = customerService.updateRideRequest(id, rideRequest);
        return ResponseEntity.ok(updatedRideRequest);
    }

    @DeleteMapping("/rideRequests/{id}")
    public ResponseEntity<Void> cancelRideRequest(@PathVariable Long id) {
        customerService.cancelRideRequest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/rideRequests")
    public ResponseEntity<List<RideRequest>> getRideRequestsByCustomer(@PathVariable Long id) {
        List<RideRequest> rideRequests = customerService.getRideRequestsByCustomer(id);
        return ResponseEntity.ok(rideRequests);
    }

    @GetMapping("/searchJourneys")
    public ResponseEntity<List<Journey>> searchJourneys(@RequestParam String startPoint, @RequestParam String endPoint) {
        List<Journey> journeys = customerService.searchJourneys(startPoint, endPoint);
        return ResponseEntity.ok(journeys);
    }

    @GetMapping("/availableJourneys")
    public ResponseEntity<List<Journey>> getAllAvailableJourneys() {
        List<Journey> journeys = customerService.getAllAvailableJourneys();
        return ResponseEntity.ok(journeys);
    }

    @PostMapping("/notifications/{customerId}")
    public ResponseEntity<Void> receiveNotifications(@PathVariable Long customerId, @RequestParam String message) {
        customerService.receiveNotifications(customerId, message);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/calculateFare")
    public ResponseEntity<Double> calculateFare(@RequestParam Long journeyId, @RequestParam double distance) {
        double fare = customerService.calculateFare(journeyId, distance);
        return ResponseEntity.ok(fare);
    }

    @PostMapping("/payment")
    public ResponseEntity<Void> makePayment(
            @RequestParam Long journeyId,
            @RequestParam Long customerId,
            @RequestParam double amount) {

        customerService.makePayment(journeyId, customerId, amount);
        return ResponseEntity.noContent().build();
    }
}
