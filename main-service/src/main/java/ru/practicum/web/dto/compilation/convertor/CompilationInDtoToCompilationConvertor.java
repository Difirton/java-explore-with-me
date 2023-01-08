package ru.practicum.web.dto.compilation.convertor;

import org.springframework.stereotype.Component;
import ru.practicum.compilation.repository.Compilation;
import org.springframework.core.convert.converter.Converter;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.web.dto.compilation.CompilationDto;
import ru.practicum.web.dto.compilation.CompilationInDto;

import java.util.List;
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
                        .map(x -> Event.builder()
                                .id(x)
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public Compilation convert(CompilationDto source, List<Event> eventList) {
        return Compilation.builder()
                .id(source.getId())
                .title(source.getTitle())
                .pinned(source.getPinned())
                .events(eventList)
                .build();
    }
}
