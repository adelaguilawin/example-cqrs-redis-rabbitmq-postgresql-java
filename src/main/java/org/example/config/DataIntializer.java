package org.example.config;

import org.example.domain.Category;
import org.example.domain.Product;
import org.example.domain.Tag;
import org.example.infraestructure.CategoryRepository;
import org.example.infraestructure.ProductRepository;
import org.example.infraestructure.TagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
public class DataIntializer {
    @Bean
    public CommandLineRunner loadData(
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            TagRepository tagRepository) {
        return args -> {
            // Check if categories exist
            if (categoryRepository.count() == 0) {
                // Create categories
                Category electronics = new Category("Electronics");
                Category books = new Category("Books");
                Category clothing = new Category("Clothing");
                categoryRepository.saveAll(List.of(electronics, books, clothing));

                // Create tags
                Tag newTag = new Tag("New");
                Tag sale = new Tag("Sale");
                Tag popular = new Tag("Popular");
                tagRepository.saveAll(List.of(newTag, sale, popular));

                // Create products and associate categories and tags
                Product smartphone = new Product("Smartphone", "Latest model smartphone", 699.99, electronics, Set.of(newTag, popular));
                Product laptop = new Product("Laptop", "High-performance laptop", 1199.99, electronics, Set.of(sale));
                Product novel = new Product("Novel", "Bestselling novel", 19.99, books, Set.of(newTag));
                productRepository.saveAll(List.of(smartphone, laptop, novel));
            } else {
                System.out.println("Mock data already exists. Skipping initialization.");
            }
        };
    }
}
