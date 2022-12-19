package ru.practicum.web.convertor;

import org.springframework.stereotype.Component;
import ru.practicum.web.dto.StatDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StatDtoConverter {

    public List<StatDto> convert(Map<String, Map<String, Long>> source) {
        List<StatDto> response = new ArrayList<>();
        source.forEach((app, uriMap) -> response.addAll(uriMap.entrySet().stream()
                .map(uriEntry -> new StatDto(app, uriEntry.getKey(), uriEntry.getValue()))
                .collect(Collectors.toList())));
        return response;
    }
}
