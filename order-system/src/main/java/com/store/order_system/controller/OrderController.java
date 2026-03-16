package com.store.order_system.controller;

import com.store.order_system.model.Order;
import com.store.order_system.model.enums.OrderStatus;
import com.store.order_system.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service){
        this.service = service;
    }

    @Operation(summary = "Criar pedido")
    @PostMapping
    public Order createOrder(@RequestBody Order order){
        return service.createOrder(order);
    }

    @Operation(summary = "Listar todos os pedidos")
    @GetMapping
    public List<Order> getAllOrders(){
        return service.getAllOrders();
    }

    @Operation(summary = "Listar pedido por id")
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id){
        return service.getOrderById(id);
    }

    @Operation(summary = "Atualizar pedido")
    @PutMapping("/{id}")
    public Order updateOrder(
            @PathVariable Long id,
            @RequestBody Order order){

        return service.updateOrder(id, order);
    }

    @Operation(summary = "Deletar pedido por id")
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
        service.deleteOrder(id);
    }

    @Operation(summary = "Filtrar pedidos por status e valor")
    @GetMapping("/filter")
    public List<Order> filterOrders(

            @RequestParam(required = false)
            OrderStatus status,

            @RequestParam(required = false)
            BigDecimal minTotal
    ) {

        if(status != null && minTotal != null){
            return service.getOrdersByStatusAndTotal(status, minTotal);
        }

        if(status != null){
            return service.getOrdersByStatus(status);
        }

        if(minTotal != null){
            return service.getOrdersByMinimumTotal(minTotal);
        }

        return service.getAllOrders();
    }

    @Operation(summary = "Filtrar pedidos por data")
    @GetMapping("/date-range")
    public List<Order> getOrdersByDateRange(

            @RequestParam String start,
            @RequestParam String end
    ){

        return service.getOrdersByDateRange(
                LocalDateTime.parse(start),
                LocalDateTime.parse(end)
        );
    }

}