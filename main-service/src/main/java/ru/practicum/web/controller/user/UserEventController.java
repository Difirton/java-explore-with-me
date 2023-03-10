package ru.practicum.web.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.service.EventService;
import ru.practicum.request.service.RequestService;
import ru.practicum.web.dto.event.EventDto;
import ru.practicum.web.dto.event.EventDtoInCollection;
import ru.practicum.web.dto.event.EventInDto;
import ru.practicum.web.dto.event.convertor.EventDtoConvertor;
import ru.practicum.web.dto.event.convertor.EventToEventDtoInCollectionConvertor;
import ru.practicum.web.dto.request.RequestDto;
import ru.practicum.web.dto.request.convertor.RequestToRequestDtoConvertor;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@RestController
@RequestMapping("users/{userId}/events")
public class UserEventController {
    private final EventService eventService;
    private final RequestService requestService;
    private final EventDtoConvertor eventDtoConvertor;
    private final EventToEventDtoInCollectionConvertor eventToEventDtoInCollectionConvertor;
    private final RequestToRequestDtoConvertor requestToRequestDtoConvertor;

    @GetMapping("/{eventId}")
    EventDto getEvent(@Valid @Positive @PathVariable Long userId, @Valid @Positive @PathVariable Long eventId) {
        return eventDtoConvertor.convertToDto(eventService.findByUserIdAndId(userId, eventId));
    }

    @GetMapping
    List<EventDtoInCollection> getEvents(@Valid @Positive @PathVariable Long userId,
                                         @Positive @RequestParam(defaultValue = "0") Integer from,
                                         @Positive @RequestParam(defaultValue = "10") Integer size) {
        return eventService.findEventsByUser(userId, from, size).stream()
                .map(eventToEventDtoInCollectionConvertor::convert)
                .collect(toList());
    }

    @PostMapping
    EventDto createEvent(@Valid @Positive @PathVariable Long userId, @RequestBody EventInDto eventDto) {
        return eventDtoConvertor.convertToDto(eventService.createEvent(userId,
                eventDtoConvertor.convertToEvent(eventDto)));
    }

    @PatchMapping
    EventDto updateEvent(@Valid @Positive @PathVariable Long userId, @RequestBody EventInDto eventDto) {
        return eventDtoConvertor.convertToDto(eventService.updateEvent(userId,
                eventDtoConvertor.convertToEvent(eventDto)));
    }

    @PatchMapping("/{eventId}")
    EventDto cancelEvent(@Valid @Positive @PathVariable Long userId, @Positive @PathVariable Long eventId) {
        return eventDtoConvertor.convertToDto(eventService.cancelEvent(userId, eventId));
    }

    @GetMapping("/{eventId}/requests")
    List<RequestDto> getEventRequests(@Valid @Positive @PathVariable Long userId, @Positive @PathVariable Long eventId) {
        return requestService.findRequestsByUserIdAndEventId(userId, eventId).stream()
                .map(requestToRequestDtoConvertor::convert)
                .collect(toList());
    }

    @PatchMapping("/{eventId}/requests/{requestId}/confirm")
    RequestDto confirmRequest(@Valid @Positive @PathVariable Long userId, @Valid @Positive @PathVariable Long eventId,
                              @Valid @Positive @PathVariable Long requestId) {
        return requestToRequestDtoConvertor.convert(requestService.confirmRequest(userId, eventId, requestId));
    }

    @PatchMapping("/{eventId}/requests/{requestId}/reject")
    RequestDto rejectRequest(@Valid @Positive @PathVariable Long userId, @Valid @Positive @PathVariable Long eventId,
                             @Valid @Positive @PathVariable Long requestId) {
        return requestToRequestDtoConvertor.convert(requestService.rejectRequest(userId, eventId, requestId));
    }
}
