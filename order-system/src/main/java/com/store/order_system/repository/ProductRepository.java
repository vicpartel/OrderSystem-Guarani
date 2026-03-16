package com.store.order_system.repository;

import com.store.order_system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    List<Product> findByPriceLessThanEqual(Double price);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByPriceBetween(
        BigDecimal min,
        BigDecimal max
    );
}