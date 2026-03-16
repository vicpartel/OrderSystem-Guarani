package com.store.order_system.service;

import com.store.order_system.model.Order;
import com.store.order_system.model.Payment;
import com.store.order_system.model.enums.PaymentStatus;
import com.store.order_system.model.enums.OrderStatus;
import com.store.order_system.repository.OrderRepository;
import com.store.order_system.repository.PaymentRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    // Processar pagamento
    public Payment processPayment(Long orderId, Payment payment) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        payment.setOrder(order);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setStatus(PaymentStatus.APPROVED);

        Payment savedPayment = paymentRepository.save(payment);

        // atualizar status do pedido
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        return savedPayment;
    }

    // Listar pagamentos
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Buscar pagamento
    public Payment getPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    // Atualizar pagamento
    public Payment updatePayment(Long id, Payment updatedPayment) {

        Payment payment = getPayment(id);
        payment.setStatus(updatedPayment.getStatus());
        payment.setMethod(updatedPayment.getMethod());

        return paymentRepository.save(payment);
    }
}