package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.error.CategoryNotFoundException;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.category.repository.entity.Category;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    @Override
    public List<Category> findAll(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from, size)).toList();
    }

    @Override
    public Category findById(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new CategoryNotFoundException(catId));
    }
}
