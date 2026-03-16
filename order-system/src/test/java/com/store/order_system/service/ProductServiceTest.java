package com.store.order_system.service;

import com.store.order_system.model.Product;
import com.store.order_system.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldCreateProduct() {

        Product product = new Product();
        product.setName("Phone");
        product.setPrice(new BigDecimal("2000"));

        when(productRepository.save(product))
                .thenReturn(product);

        Product saved = productService.createProduct(product);

        assertEquals("Phone", saved.getName());
        verify(productRepository).save(product);
    }
}