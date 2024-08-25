package com.shivam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Journey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Start point cannot be empty")
    private String startPoint;

    @NotEmpty(message = "End point cannot be empty")
    private String endPoint;

    @NotNull(message = "Available seats cannot be null")
    private Integer availableSeats;

    @NotNull(message = "Journey status cannot be null")
    @Enumerated(EnumType.STRING)
    private JourneyStatus status;

    @ManyToOne
    @JoinColumn(name = "car_owner_id", nullable = false)
    private CarOwner carOwner;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer estimatedDuration;

    // New field for distance between start and end points
    private Double distance;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public JourneyStatus getStatus() {
        return status;
    }

    public void setStatus(JourneyStatus status) {
        this.status = status;
    }

    public CarOwner getCarOwner() {
        return carOwner;
    }

    public void setCarOwner(CarOwner carOwner) {
        this.carOwner = carOwner;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Integer estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "id=" + id +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", availableSeats=" + availableSeats +
                ", status=" + status +
                ", carOwner=" + carOwner +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", estimatedDuration=" + estimatedDuration +
                ", distance=" + distance +
                '}';
    }
}
