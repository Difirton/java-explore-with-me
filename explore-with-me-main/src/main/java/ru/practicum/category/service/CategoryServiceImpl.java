package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.error.CategoryNotFoundException;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.common.NotNullPropertiesCopier;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService, NotNullPropertiesCopier<Category> {
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

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        Category categoryToUpdate = this.findById(category.getId());
        this.copyNotNullProperties(category, categoryToUpdate);
        return categoryToUpdate;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
