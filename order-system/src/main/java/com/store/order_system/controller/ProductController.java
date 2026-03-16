package com.store.order_system.controller;

import com.store.order_system.model.Product;
import com.store.order_system.repository.ProductRepository;
import com.store.order_system.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService service;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductService service) {
        this.productRepository = productRepository;
        this.service = service;
    }

    @Operation(summary = "Listar todos os produtos")
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar produto por id")
    public Product getProduct(@PathVariable Long id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Operation(summary = "Inserir produto")
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @Operation(summary = "Atualizar produto")
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPrice(product.getPrice());

        return productRepository.save(existingProduct);
    }

    @Operation(summary = "Deletar produto por id")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    @Operation(summary = "Filtrar produtos por nome, categoria e preço")
    @GetMapping("/filter")
    public List<Product> filterProducts(

            @RequestParam(required = false)
            String name,

            @RequestParam(required = false)
            String category,

            @RequestParam(required = false)
            BigDecimal minPrice,

            @RequestParam(required = false)
            BigDecimal maxPrice
    ){

        if(name != null){
            return service.getProductsByName(name);
        }

        if(category != null){
            return service.getProductsByCategory(category);
        }

        if(minPrice != null && maxPrice != null){
            return service.getProductsByPriceRange(minPrice, maxPrice);
        }

        return service.getAllProducts();
    }
}