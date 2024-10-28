package org.example.application.handlers;

import org.example.application.AddProductCommand;
import org.example.domain.Category;
import org.example.domain.Product;
import org.example.domain.Tag;
import org.example.infraestructure.CategoryRepository;
import org.example.infraestructure.ProductRepository;
import org.example.infraestructure.TagRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ProductCommandHandler {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final RabbitTemplate rabbitTemplate;


    public ProductCommandHandler(ProductRepository productRepository, CategoryRepository categoryRepository, TagRepository tagRepository, RabbitTemplate rabbitTemplate) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Product handle(AddProductCommand command) {
        Category category = categoryRepository.findById(command.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Set<Tag> tags = command.getTagIds().stream()
                .map(id -> tagRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Tag not found")))
                .collect(Collectors.toSet());

        Product product = new Product(
                command.getName(),
                command.getDescription(),
                command.getPrice(),
                category,
                tags
        );
        product = productRepository.save(product);

        rabbitTemplate.convertAndSend("productExchange", "product.added", product);
        return product;
    }
}
