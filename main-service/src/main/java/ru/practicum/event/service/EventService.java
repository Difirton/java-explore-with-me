package ru.practicum.event.service;

import ru.practicum.event.repository.constant.State;
import ru.practicum.event.repository.entity.Event;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> findAllByParams(String text, List<Long> categoriesIds, Boolean isPaid, LocalDateTime rangeStart,
                                LocalDateTime rangeEnd, Boolean isOnlyAvailable, String sort, Integer from,
                                Integer size);

    List<Event> findAllByParams(List<Long> users, List<State> states, List<Long> categories, LocalDateTime start,
                                LocalDateTime finish, Integer from, Integer size);

    Event findById(Long id);

    Event findByUserIdAndId(Long userId, Long eventId);

    List<Event> findEventsByUser(Long userId, Integer from, Integer size);

    Event createEvent(Long userId, Event event);

    Event updateEvent(Long userId, Event event);

    Event updateEvent(Event event);

    Event cancelEvent(Long userId, Long eventId);

    Event publishEvent(Long eventId);

    Event rejectEvent(Long eventId);

    List<Event> findMostPopular(Integer from, Integer size);

    List<Event> findUserRecommendation(Long userId, Integer from, Integer size);
}
