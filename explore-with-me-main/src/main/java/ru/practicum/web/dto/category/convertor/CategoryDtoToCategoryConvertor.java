package ru.practicum.web.dto.category.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.category.repository.entity.Category;
import ru.practicum.web.dto.category.CategoryDto;

@Component
public class CategoryDtoToCategoryConvertor implements Converter<CategoryDto, Category> {

    @Override
    public Category convert(CategoryDto source) {
        return Category.builder()
                .id(source.getId())
                .name(source.getName())
                .build();
    }
}
