package ru.practicum.web.dto.compilation.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.compilation.repository.Compilation;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.web.dto.compilation.CompilationInDto;

import java.util.stream.Collectors;

@Component
public class CompilationInDtoToCompilationConvertor implements Converter<CompilationInDto, Compilation> {

    @Override
    public Compilation convert(CompilationInDto source) {
        return Compilation.builder()
                .id(source.getId())
                .title(source.getTitle())
                .pinned(source.getPinned())
                .events(source.getEvents().stream()
                        .map(id -> Event.builder()
                                .id(id)
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
