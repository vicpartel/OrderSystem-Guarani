package com.store.order_system.controller;

import com.store.order_system.model.Payment;
import com.store.order_system.model.enums.PaymentStatus;
import com.store.order_system.repository.PaymentRepository;
import com.store.order_system.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentRepository paymentRepository;
    private final PaymentService service;

    @Autowired
    public PaymentController(PaymentRepository paymentRepository, PaymentService service) {
        this.paymentRepository = paymentRepository;
        this.service = service;
    }

    @Operation(summary = "Inserir pagamento")
    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {

        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    @Operation(summary = "Listar todos os pagamentos")
    @GetMapping
    public List<Payment> getAllPayments(){
        return service.getAllPayments();
    }

    @Operation(summary = "Listar pagamento por id")
    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable Long id) {

        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @Operation(summary = "Atualizar pagamento")
    @PutMapping("/{id}")
    public Payment updatePayment(
            @PathVariable Long id,
            @RequestBody Payment payment){

        return service.updatePayment(id, payment);
    }
}