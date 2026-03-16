package com.store.order_system.repository;

import com.store.order_system.model.Order;
import com.store.order_system.model.enums.OrderStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    List<Order> findByTotalGreaterThanEqual(BigDecimal total);

    List<Order> findByStatusAndTotalGreaterThanEqual(
            OrderStatus status,
            BigDecimal total
    );
}