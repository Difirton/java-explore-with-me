package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.event.error.EventNotFoundException;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.constant.EventSort;
import ru.practicum.event.repository.entity.Event;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.practicum.event.repository.constant.EventSort.*;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    @Override
    public List<Event> findAll(String text, List<Long> categoriesIds, Boolean isPaid, LocalDateTime rangeStart,
                               LocalDateTime rangeEnd, Boolean isOnlyAvailable, String sort,
                               Integer from, Integer size) {
        if (size < 1 || from < 0) {
            throw new IllegalStateException("Incorrect pagination request");
        }
        switch (this.checkInputEventSortData(sort)) {
            case ID:
                return eventRepository.findAllByParams(text, categoriesIds, isPaid, rangeStart, rangeEnd,
                        isOnlyAvailable, PageRequest.of(from, size));
            case EVENT_DATE:
                return eventRepository.findAllByParamsEventDateSort(text, categoriesIds, isPaid, rangeStart, rangeEnd,
                        isOnlyAvailable, PageRequest.of(from, size));
            case VIEWS:
                return eventRepository.findAllByParamsViewsSort(text, categoriesIds, isPaid, rangeStart, rangeEnd,
                        isOnlyAvailable, PageRequest.of(from, size));
            default:
                throw new IllegalStateException("Unknown sort: " + sort);
        };
    }

    @Override
    public List<Event> findAll(String text, List<Long> categoriesIds, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                               Boolean isOnlyAvailable, String sort, Integer from, Integer size) {
        if (size < 1 || from < 0) {
            throw new IllegalStateException("Incorrect pagination request");
        }
        return null;
    }

    private EventSort checkInputEventSortData(String eventSortName) {
        EventSort eventSort = UNSUPPORTED;
        if (Arrays.stream(EventSort.values()).anyMatch(s -> s.toString().equals(eventSortName))) {
            eventSort = eventSort.valueOf(eventSortName);
        }
        return eventSort;
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }
}
