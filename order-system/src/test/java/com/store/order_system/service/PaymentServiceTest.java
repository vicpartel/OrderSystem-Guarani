package com.store.order_system.service;

import com.store.order_system.model.Order;
import com.store.order_system.model.Payment;
import com.store.order_system.model.enums.OrderStatus;
import com.store.order_system.model.enums.PaymentStatus;
import com.store.order_system.repository.OrderRepository;
import com.store.order_system.repository.PaymentRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void shouldProcessPaymentAndUpdateOrderStatus() {

        Order order = new Order();

        Payment payment = new Payment();

        when(orderRepository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        when(paymentRepository.save(any()))
                .thenReturn(payment);

        Payment result = paymentService.processPayment(order.getId(), payment);

        assertEquals(PaymentStatus.APPROVED, result.getStatus());
        assertEquals(OrderStatus.PAID, order.getStatus());
    }
}