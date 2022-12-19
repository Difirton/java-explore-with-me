package ru.practicum.service;

import ru.practicum.repository.entity.Hit;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatService {
    Hit save(Hit hit);

    Map<String, Map<String, Long>> getStats(LocalDateTime start, LocalDateTime end, List<String> uris,
                                            Boolean isUnique);
}
