package ru.practicum.web.dto.compilation.convertor;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.compilation.repository.Compilation;
import ru.practicum.web.dto.compilation.CompilationDto;
import ru.practicum.web.dto.event.convertor.EventToEventDtoInCollectionConvertor;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompilationToCompilationDtoConvertor implements Converter<Compilation, CompilationDto> {
    private final EventToEventDtoInCollectionConvertor eventConvertor;

    @Override
    public CompilationDto convert(Compilation source) {
        return CompilationDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .pinned(source.getPinned())
                .events(source.getEvents().stream()
                        .map(eventConvertor::convert)
                        .collect(Collectors.toList()))
                .build();
    }
}
