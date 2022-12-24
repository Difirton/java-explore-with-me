package ru.practicum.web.open.category.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.web.open.category.dto.CategoryDto;

@Component
public class CategoryToCategoryDtoConvertor implements Converter<Category, CategoryDto> {
    @Override
    public CategoryDto convert(Category source) {
        return CategoryDto.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
