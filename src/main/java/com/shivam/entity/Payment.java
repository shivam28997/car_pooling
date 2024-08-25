package com.shivam.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private Double amount;

    @ManyToOne
    private Customer payer; // The customer who is making the payment

    @ManyToOne
    private Journey journey; // The journey for which the payment is made

    // Default constructor
    public Payment() {
    }

    // Parameterized constructor
    public Payment(Double amount, Customer payer, Journey journey) {
        this.amount = amount;
        this.payer = payer;
        this.journey = journey;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Customer getPayer() {
        return payer;
    }

    public void setPayer(Customer payer) {
        this.payer = payer;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", amount=" + amount +
                ", payer=" + payer +
                ", journey=" + journey +
                '}';
    }
}
