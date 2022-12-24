package ru.practicum.event.repository;

import org.springframework.data.domain.PageRequest;
import ru.practicum.event.repository.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomEventRepository {
    List<Event> findAllByParams(String text, List<Long> categoriesIds, Boolean isPaid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean isOnlyAvailable, PageRequest of);

    List<Event> findAllByParamsEventDateSort(String text, List<Long> categoriesIds, Boolean isPaid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean isOnlyAvailable, PageRequest of);

    List<Event> findAllByParamsViewsSort(String text, List<Long> categoriesIds, Boolean isPaid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean isOnlyAvailable, PageRequest of);
}

