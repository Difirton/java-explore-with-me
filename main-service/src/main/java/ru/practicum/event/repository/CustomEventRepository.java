package ru.practicum.event.repository;

import org.springframework.stereotype.Repository;
import ru.practicum.event.repository.constant.State;
import ru.practicum.event.repository.entity.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CustomEventRepository {
    List<Event> findAllByParams(String text, List<Long> categoriesIds, Boolean isPaid, LocalDateTime rangeStart,
                                LocalDateTime rangeEnd, Boolean isOnlyAvailable, Integer from, Integer size);

    List<Event> findAllByParamsEventDateSort(String text, List<Long> categoriesIds, Boolean isPaid,
                                             LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                             Boolean isOnlyAvailable, Integer from, Integer size);

    List<Event> findAllByParamsViewsSort(String text, List<Long> categoriesIds, Boolean isPaid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Boolean isOnlyAvailable, Integer from, Integer size);

    List<Event> findAllByParams(List<Long> users, List<State> states, List<Long> categories, LocalDateTime start,
                                LocalDateTime finish, Integer from, Integer size);
}
