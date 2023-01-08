package ru.practicum.web.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.category.service.CategoryService;
import ru.practicum.web.dto.category.CategoryDto;
import ru.practicum.web.dto.category.convertor.CategoryDtoToCategoryConvertor;
import ru.practicum.web.dto.category.convertor.CategoryToCategoryDtoConvertor;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final CategoryToCategoryDtoConvertor categoryToCategoryDtoConvertor;
    private final CategoryDtoToCategoryConvertor categoryDtoToCategoryConvertor;

    @PostMapping
    CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        Category newCategory = categoryService.createCategory(categoryDtoToCategoryConvertor.convert(categoryDto));
        return categoryToCategoryDtoConvertor.convert(newCategory);
    }

    @PatchMapping
    CategoryDto updateCategory(@RequestBody CategoryDto categoryDto) {
        Category updatedCategory = categoryService.updateCategory(categoryDtoToCategoryConvertor.convert(categoryDto));
        return categoryToCategoryDtoConvertor.convert(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    void deleteCategory(@Valid @Positive @PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
