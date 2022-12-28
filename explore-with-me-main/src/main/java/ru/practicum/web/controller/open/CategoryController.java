package ru.practicum.web.controller.open;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.service.CategoryService;
import ru.practicum.web.dto.category.convertor.CategoryToCategoryDtoConvertor;
import ru.practicum.web.dto.category.CategoryDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryToCategoryDtoConvertor categoryToCategoryDtoConvertor;

    private static final int DEFAULT_FROM = 0;
    private static final int DEFAULT_SIZE = 10;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CategoryDto> getCategories(@RequestParam Optional<Integer> from, @RequestParam Optional<Integer> size) {
        return categoryService.findAll(from.orElse(DEFAULT_FROM), size.orElse(DEFAULT_SIZE)).stream()
                .map(categoryToCategoryDtoConvertor::convert)
                .collect(Collectors.toList());
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    CategoryDto getCategoryById(@RequestParam Long categoryId) {
        return categoryToCategoryDtoConvertor.convert(categoryService.findById(categoryId));
    }
}
