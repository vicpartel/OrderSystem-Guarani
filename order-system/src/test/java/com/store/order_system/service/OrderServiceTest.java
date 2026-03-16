package com.store.order_system.service;

import com.store.order_system.model.Order;
import com.store.order_system.model.OrderItem;
import com.store.order_system.model.Product;
import com.store.order_system.repository.OrderRepository;
import com.store.order_system.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldCreateOrderAndCalculateTotal() {

        Product product = new Product();
        product.setPrice(new BigDecimal("100"));

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(2);

        Order order = new Order();
        order.setItems(List.of(item));

        when(productRepository.findById(product.getId()))
                .thenReturn(Optional.of(product));

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order savedOrder = orderService.createOrder(order);

        assertEquals(new BigDecimal("200"), savedOrder.getTotal());
        verify(orderRepository).save(order);
    }
}