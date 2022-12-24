package ru.practicum.category.service;

import ru.practicum.category.repository.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll(Integer orElse, Integer orElse1);

    Category findById(Long catId);
}
