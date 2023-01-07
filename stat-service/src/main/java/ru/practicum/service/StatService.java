package ru.practicum.service;

import ru.practicum.repository.entity.EndpointHit;
import ru.practicum.web.dto.StatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    void save(EndpointHit endpointHit);

    List<StatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean isUnique);
}
