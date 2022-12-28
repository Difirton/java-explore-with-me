package ru.practicum.web.dto.compilation.convertor;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.compilation.repository.Compilation;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.web.dto.compilation.CompilationDto;

import java.util.stream.Collectors;

@Component
public class CompilationDtoToCompilationConvertor implements Converter<CompilationDto, Compilation> {

    @Override
    public Compilation convert(CompilationDto source) {
        return Compilation.builder()
                .id(source.getId())
                .title(source.getTitle())
                .pinned(source.getPinned())
                .events(source.getEvents().stream()
                        .map(s -> Event.builder()
                                .id(s.getId())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
