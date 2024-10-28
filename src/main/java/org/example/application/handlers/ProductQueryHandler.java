package org.example.application.handlers;

import org.example.application.QuickSearchQuery;
import org.example.domain.Product;
import org.example.infraestructure.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryHandler {
    private final ProductRepository productRepository;

    public ProductQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable(value = "getAll")
    public List<Product> handle() {
        return productRepository.findAll();
    }

    @Cacheable(value = "quickSearch", key = "#query.name")
    public List<Product> handle(QuickSearchQuery query) {
        return productRepository.findByNameContainingIgnoreCase(query.getName());
    }
}
