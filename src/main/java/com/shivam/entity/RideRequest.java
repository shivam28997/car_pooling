package com.shivam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class RideRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer; // The customer who is making the ride request

    @ManyToOne
    private Journey journey; // The journey for which the ride request is made

    @NotNull(message = "Request status cannot be null")
    private RideRequestStatus status; // Using enum for status

    @NotEmpty(message = "Requested start point cannot be empty")
    private String requestedStartPoint;

    @NotEmpty(message = "Requested end point cannot be empty")
    private String requestedEndPoint;

    @NotNull(message = "Requested travel time cannot be null")
    private LocalDateTime requestedTravelTime; // Format could be "HH:mm"

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public RideRequestStatus getStatus() {
        return status;
    }

    public void setStatus(RideRequestStatus status) {
        this.status = status;
    }

    public String getRequestedStartPoint() {
        return requestedStartPoint;
    }

    public void setRequestedStartPoint(String requestedStartPoint) {
        this.requestedStartPoint = requestedStartPoint;
    }

    public String getRequestedEndPoint() {
        return requestedEndPoint;
    }

    public void setRequestedEndPoint(String requestedEndPoint) {
        this.requestedEndPoint = requestedEndPoint;
    }

    public LocalDateTime getRequestedTravelTime() {
        return requestedTravelTime;
    }

    public void setRequestedTravelTime(LocalDateTime requestedTravelTime) {
        this.requestedTravelTime = requestedTravelTime;
    }

    @Override
    public String toString() {
        return "RideRequest{" +
                "id=" + id +
                ", customer=" + customer +
                ", journey=" + journey +
                ", status=" + status +
                ", requestedStartPoint='" + requestedStartPoint + '\'' +
                ", requestedEndPoint='" + requestedEndPoint + '\'' +
                ", requestedTravelTime='" + requestedTravelTime + '\'' +
                '}';
    }
}
