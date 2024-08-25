package com.shivam.controller;

import com.shivam.entity.Payment;
import com.shivam.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestBody Payment payment) {
        paymentService.processPayment(payment.getJourney().getId(), payment.getPayer().getEmail(), payment.getAmount(), payment.getJourney().getStartPoint(), payment.getJourney().getEndPoint());
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentDetails(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentDetails(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Payment>> getPaymentsByCustomer(@PathVariable Long customerId) {
        List<Payment> payments = paymentService.getPaymentsByCustomer(customerId);
        return ResponseEntity.ok(payments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> refundPayment(@PathVariable Long id) {
        paymentService.refundPayment(id);
        return ResponseEntity.noContent().build();
    }
}
