package com.store.order_system.service;

import com.store.order_system.model.Order;
import com.store.order_system.model.OrderItem;
import com.store.order_system.model.Product;
import com.store.order_system.model.enums.OrderStatus;
import com.store.order_system.repository.OrderRepository;
import com.store.order_system.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    // Criar pedido
    public Order createOrder(Order order) {

        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        for(OrderItem item : order.getItems()){
            item.setOrder(order);
        }

        calculateOrderTotal(order);

        return orderRepository.save(order);
    }

    // Listar pedidos
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Buscar pedido por ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Atualizar pedido
    public Order updateOrder(Long id, Order updatedOrder) {

        Order order = getOrderById(id);

        order.setItems(updatedOrder.getItems());
        order.setStatus(updatedOrder.getStatus());
        order.setDiscounts(updatedOrder.getDiscounts());
        order.setFees(updatedOrder.getFees());

        for(OrderItem item : order.getItems()){
            item.setOrder(order);
        }

        calculateOrderTotal(order);

        return orderRepository.save(order);
    }

    // Remover pedido
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Calcular total do pedido
    private void calculateOrderTotal(Order order) {

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {

            Product product = productRepository.findById(
                    item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            BigDecimal itemTotal =
                    product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));

            item.setPrice(product.getPrice());
            item.setSubtotal(itemTotal);

            total = total.add(itemTotal);
        }

        // aplicar desconto se existir
        if (order.getDiscounts() != null) {
            total = total.subtract(order.getDiscounts());
        }

        // aplicar taxa se existir
        if (order.getFees() != null) {
            total = total.add(order.getFees());
        }

        order.setTotal(total);
    }

    public List<Order> getOrdersByStatus(OrderStatus status){
        return orderRepository.findByStatus(status);
    }

    public List<Order> getOrdersByDateRange(
            LocalDateTime start,
            LocalDateTime end){

        return orderRepository.findByCreatedAtBetween(start, end);
    }

    public List<Order> getOrdersByMinimumTotal(BigDecimal total){
        return orderRepository.findByTotalGreaterThanEqual(total);
    }

    public List<Order> getOrdersByStatusAndTotal(
        OrderStatus status,
        BigDecimal total){

        return orderRepository.findByStatusAndTotalGreaterThanEqual(status, total);
    }
}