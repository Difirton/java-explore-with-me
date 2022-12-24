package ru.practicum.event.service;

import ru.practicum.event.repository.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    List<Event> findAll(String text, List<Long> categoriesIds, Boolean isPaid, LocalDateTime rangeStart,
                        LocalDateTime rangeEnd, Boolean isOnlyAvailable, String sort, Integer from, Integer size);

    List<Event> findAll(String text, List<Long> categoriesIds, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                        Boolean isOnlyAvailable, String sort, Integer from, Integer size);

    Event findById(Long id);
}
