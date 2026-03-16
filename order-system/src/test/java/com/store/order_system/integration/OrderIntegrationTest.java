package com.store.order_system.integration;

import com.store.order_system.model.Product;
import com.store.order_system.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldReturn401WithoutAuth() throws Exception {
        mockMvc.perform(post("/orders"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void shouldCreateOrder() throws Exception {

        // cria produto no banco
        Product product = new Product();
        product.setName("Laptop");
        product.setPrice(new BigDecimal("2000"));

        product = productRepository.save(product);

        String orderJson = """
        {
          "items": [
            {
              "product": {
                "id": %d
              },
              "quantity": 2
            }
          ]
        }
        """.formatted(product.getId());

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson))
                .andExpect(status().isOk());
    }
}