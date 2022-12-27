package ru.practicum.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.category.service.CategoryService;
import ru.practicum.web.dto.category.CategoryDto;
import ru.practicum.web.dto.category.convertor.CategoryDtoToCategoryConvertor;
import ru.practicum.web.dto.category.convertor.CategoryToCategoryDtoConvertor;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final CategoryService categoryService;
    private final CategoryToCategoryDtoConvertor categoryToCategoryDtoConvertor;
    private final CategoryDtoToCategoryConvertor categoryDtoToCategoryConvertor;

    @PostMapping
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        Category newCategory = categoryService.createCategory(categoryDtoToCategoryConvertor.convert(categoryDto));
        return categoryToCategoryDtoConvertor.convert(newCategory);
    }

    @PatchMapping
    public CategoryDto updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        Category updatedCategory = categoryService.updateCategory(categoryDtoToCategoryConvertor.convert(categoryDto));
        return categoryToCategoryDtoConvertor.convert(updatedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }
}
