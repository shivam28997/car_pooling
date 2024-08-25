package com.shivam.service;


import com.shivam.entity.Payment;

import java.util.List;

public interface PaymentService {

    // Calculate the fare based on distance traveled
    double calculateFare(double distance, double ratePerKm);

    // Process a payment for a journey
    void processPayment(Long journeyId, String customerEmailId, double amount, String startPoint, String endPoint);

    // Get the payment details for a specific journey
    Payment getPaymentDetails(Long paymentId);

    // Get all payments made by a specific customer
    List<Payment> getPaymentsByCustomer(Long customerId);

    // Refund a payment (if applicable)
    void refundPayment(Long paymentId);
}
