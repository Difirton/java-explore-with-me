package ru.practicum.category.error;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long id) {
        super(String.format("Category request with id = %s, not found", id));
        log.error("Category request with id = {}, not found", id);
    }
}
