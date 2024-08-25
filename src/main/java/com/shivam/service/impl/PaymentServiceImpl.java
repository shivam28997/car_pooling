package com.shivam.service.impl;

import com.shivam.entity.Customer;
import com.shivam.entity.Journey;
import com.shivam.entity.Payment;
import com.shivam.repository.CustomerRepository;
import com.shivam.repository.JourneyRepository;
import com.shivam.repository.PaymentRepository;
import com.shivam.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private JourneyRepository journeyRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public double calculateFare(double distance, double ratePerKm) {
        return distance * ratePerKm;
    }

    @Override
    public void processPayment(Long journeyId, String customerEmailId, double amount, String startPoint, String endPoint) {
        // Implement payment processing logic
        Journey journey = (Journey) journeyRepository.findByStartPointAndEndPoint(startPoint, endPoint);
        Customer customer = customerRepository.findByEmail(customerEmailId);
        Payment payment = new Payment();
        payment.setJourney(journey);
        payment.setPayer(customer);
        payment.setAmount(amount);
        // Save payment details to the database
        paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentDetails(Long paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));
    }

    @Override
    public List<Payment> getPaymentsByCustomer(Long customerId) {
        return paymentRepository.findByPayerId(customerId);
    }

    @Override
    public void refundPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + paymentId));
        // Implement refund logic, e.g., update payment status, etc.
        paymentRepository.delete(payment);
    }
}
