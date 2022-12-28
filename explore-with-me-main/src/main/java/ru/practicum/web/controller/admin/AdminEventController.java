package ru.practicum.web.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.repository.constant.State;
import ru.practicum.event.repository.entity.Event;
import ru.practicum.event.service.EventService;
import ru.practicum.web.dto.event.EventDto;
import ru.practicum.web.dto.event.EventDtoInCollection;
import ru.practicum.web.dto.event.convertor.EventDtoConvertor;
import ru.practicum.web.dto.event.convertor.EventToEventDtoInCollectionConvertor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/events")
public class AdminEventController {
    private final EventService eventService;
    private final EventDtoConvertor eventDtoConvertor;
    private final EventToEventDtoInCollectionConvertor eventToEventDtoInCollectionConvertor;

    private final Integer DEFAULT_FROM = 10;
    private final Integer DEFAULT_SIZE = 0;
    private final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @GetMapping
    public List<EventDto> getEvents(@RequestParam(required = false) List<Long> users,
                                    @RequestParam(required = false) List<State> states,
                                    @RequestParam(required = false) List<Long> categories,
                                    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
                                        @RequestParam Optional<LocalDateTime> rangeStart,
                                    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
                                        @RequestParam Optional<LocalDateTime> rangeEnd,
                                    @RequestParam Optional<Integer> from,
                                    @RequestParam Optional<Integer> size) {
        return eventService.findAllByParams(users, states, categories,
                        rangeStart.orElse(LocalDateTime.now()), rangeEnd.orElse(LocalDateTime.MAX),
                        from.orElse(DEFAULT_FROM), size.orElse(DEFAULT_SIZE)).stream()
                .map(eventDtoConvertor::convertToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{eventId}")
    public EventDto updateEvent(@PathVariable Long eventId,
                                @RequestBody EventDto updateEventDto) {
        Event updatedEvent = eventService.updateEvent(eventId, eventDtoConvertor.convertToEvent(updateEventDto));
        return eventDtoConvertor.convertToDto(updatedEvent);
    }

    @PatchMapping("/{eventId}/publish")
    public EventDto publishEvent(@PathVariable Long eventId) {
        return eventDtoConvertor.convertToDto(eventService.publishEvent(eventId));
    }

    @PatchMapping("/{eventId}/reject")
    public EventDto rejectEvent(@PathVariable Long eventId) {
        return eventDtoConvertor.convertToDto(eventService.rejectEvent(eventId));
    }
}
