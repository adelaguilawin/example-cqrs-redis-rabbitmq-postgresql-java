package org.example.controller;

import org.example.application.AddProductCommand;
import org.example.application.QuickSearchQuery;
import org.example.application.handlers.ProductCommandHandler;
import org.example.application.handlers.ProductQueryHandler;
import org.example.domain.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductCommandHandler productCommandHandler;
    private final ProductQueryHandler productQueryHandler;

    public ProductController(ProductCommandHandler productCommandHandler, ProductQueryHandler productQueryHandler) {
        this.productCommandHandler = productCommandHandler;
        this.productQueryHandler = productQueryHandler;
    }

    @PostMapping
    public Product addProduct(@RequestBody AddProductCommand command) {
        return productCommandHandler.handle(command);
    }

    @GetMapping("/quicksearch")
    public List<Product> quickSearch(@RequestParam String name) {
        return productQueryHandler.handle(new QuickSearchQuery(name));
    }

    @GetMapping
    public List<Product> getProducts() {
        return productQueryHandler.handle();
    }
}
