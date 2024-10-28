package org.example.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class AddProductCommand {
    private final String name;
    private final String description;
    private final Double price;
    private final Long categoryId;
    private final Set<Long> tagIds;
}
